package com.test.myapp.ui.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.test.myapp.callback.NetworkCallBack;
import com.test.myapp.data.model.Login;
import com.test.myapp.network.NoConnectivityException;
import com.test.myapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private NetworkCallBack networkCallBack;
    private MutableLiveData<Login> loginMutableLiveData = new MutableLiveData<>();
    private static LoginRepository loginRepository = null;

    public static LoginRepository getInstance(){
        if(loginRepository == null){
            loginRepository = new LoginRepository();
        }
        return loginRepository;
    }

    private Call<Login> mLoginCall;

    public MutableLiveData<Login> callLogin(String email, String password, NetworkCallBack callBack){
        this.networkCallBack = callBack;
        mLoginCall = RetrofitClient.getInstance().getApi().loginCall(email,password);
        mLoginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.e("onResponse",response.body().toString());
                loginMutableLiveData.setValue(response.body());
                networkCallBack.onNetworkResponse();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                if(t instanceof NoConnectivityException){
                    networkCallBack.onInternetFailure();
                }
                Log.e("onFailure", t.getMessage());
                networkCallBack.onFailureResponse(t);
            }
        });
        return loginMutableLiveData;
    }
}
