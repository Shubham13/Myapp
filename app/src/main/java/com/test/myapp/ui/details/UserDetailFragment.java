package com.test.myapp.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.test.myapp.R;
import com.test.myapp.data.model.User;
import com.test.myapp.databinding.FragmentDetailsBinding;
import com.test.myapp.ui.user.UserListFragment;

public class UserDetailFragment extends Fragment {

    FragmentDetailsBinding detailsBinding;
    private User.Datum datum;

    public UserDetailFragment(User.Datum datum) {
        this.datum = datum;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        detailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details,container,false);
        return detailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailsBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new UserListFragment())
                        .commit();
            }
        });

        Glide.with(getActivity()).load(datum.getAvatar()).into(detailsBinding.profile);
        detailsBinding.fullname.setText(datum.getFirstName()+" "+datum.getLastName());
        detailsBinding.detail.setText(datum.getEmail());
    }
}
