package com.test.myapp.ui.user;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.test.myapp.callback.NetworkCallBack;
import com.test.myapp.data.model.Login;
import com.test.myapp.data.model.User;


import java.util.List;

public class UserListViewModel extends AndroidViewModel {

    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> networkError = new MutableLiveData();
    private UserListRepository userListRepository = UserListRepository.getInstance();

    public UserListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<User> callGetUserApi(String page){
        userMutableLiveData = userListRepository.callUserList(page, new NetworkCallBack() {
            @Override
            public void onNetworkResponse() {
                Log.e("onnetwork response","success");
            }

            @Override
            public void onFailureResponse(Throwable t) {
                Log.e("onnetwork failure","failure");
            }

            @Override
            public void onInternetFailure() {
                networkError.setValue(true);
            }
        });
        return userMutableLiveData;
    }

}
