package com.test.myapp.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.test.myapp.callback.NetworkCallBack;
import com.test.myapp.data.model.Login;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<Login> loginMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> networkError = new MutableLiveData();
    private LoginRepository loginRepository = LoginRepository.getInstance();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Login> callLoginApi(String email,String password){
        loginMutableLiveData = loginRepository.callLogin(email, password, new NetworkCallBack() {
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
        return loginMutableLiveData;
    }

    public void onLoginClick(String email, String password){
        callLoginApi(email,password);
    }
}
