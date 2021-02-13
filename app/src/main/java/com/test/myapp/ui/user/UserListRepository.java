package com.test.myapp.ui.user;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.test.myapp.callback.NetworkCallBack;
import com.test.myapp.data.model.Login;
import com.test.myapp.data.model.User;
import com.test.myapp.network.NoConnectivityException;
import com.test.myapp.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListRepository {
    private NetworkCallBack networkCallBack;
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private static UserListRepository userRepository = null;

    public static UserListRepository getInstance(){
        if(userRepository == null){
            userRepository = new UserListRepository();
        }
        return userRepository;
    }

    private Call<User> mUserCall;

    public MutableLiveData<User> callUserList(String page, NetworkCallBack callBack){
        this.networkCallBack = callBack;
        mUserCall = RetrofitClient.getInstance().getApi().getUser();
        mUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("onResponse",response.body().toString());
                userMutableLiveData.setValue(response.body());
                networkCallBack.onNetworkResponse();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(t instanceof NoConnectivityException){
                    networkCallBack.onInternetFailure();
                }
                Log.e("onFailure", t.getMessage());
                networkCallBack.onFailureResponse(t);
            }
        });
        return userMutableLiveData;
    }
}
