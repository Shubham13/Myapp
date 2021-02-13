package com.test.myapp.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.myapp.R;
import com.test.myapp.callback.OnItemClickListener;
import com.test.myapp.data.model.User;
import com.test.myapp.databinding.FragmentListBinding;
import com.test.myapp.dialog.CommonDialog;
import com.test.myapp.dialog.OnDialogButtonsClick;
import com.test.myapp.ui.adapter.MyListAdapter;
import com.test.myapp.ui.details.UserDetailFragment;


import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    FragmentListBinding fragmentListBinding;
    private UserListViewModel userListViewModel;
    private MyListAdapter adapter;
    private ArrayList<User.Datum> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        return fragmentListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        fragmentListBinding.userList.setLayoutManager(linearLayoutManager);
        adapter = new MyListAdapter();
        fragmentListBinding.userList.setAdapter(adapter);

        userListViewModel = ViewModelProviders.of(this).get(UserListViewModel.class);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        userListViewModel.networkError.observe(new LifecycleOwner() {
            @NonNull
            @Override
            public Lifecycle getLifecycle() {
                return UserListFragment.this.getLifecycle();
            }
        }, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                CommonDialog.showGeneralDialog(getActivity(),
                        "Connection Issue", "No Internet", "Ok",
                        new OnDialogButtonsClick() {
                            @Override
                            public void onPositiveButtonClick() {
                            }

                            @Override
                            public void onNegativeButtonClick() {
                            }
                        });
            }
        });

        userListViewModel.callGetUserApi("").observe(new LifecycleOwner() {
            @NonNull
            @Override
            public Lifecycle getLifecycle() {
                return UserListFragment.this.getLifecycle();
            }
        }, new Observer<User>() {
            @Override
            public void onChanged(User users) {
                for(int i=0;i<users.getData().size();i++){
                    String fullName = users.getData().get(i).getFirstName()+" "+users.getData().get(i).getLastName();
                    int vowels = 0;
                    fullName = fullName.toLowerCase();
                    for(int j=0;j<fullName.length();j++){
                        char ch = fullName.charAt(j);

                        if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                            ++vowels;
                        }
                    }

                    if(vowels==5 || vowels>5){
                        list.add(users.getData().get(i));
                    }
                }
                fragmentListBinding.totalCount.setText("Total User: "+list.size());
                adapter.setUserList(list);
            }
        });

        adapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, Object value) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new UserDetailFragment(list.get(position)))
                        .commit();
            }
        });
    }
}
