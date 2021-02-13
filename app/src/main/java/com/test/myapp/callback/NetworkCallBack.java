package com.test.myapp.callback;

public interface NetworkCallBack {
    void onNetworkResponse();
    void onFailureResponse(Throwable t);
    void onInternetFailure();
}
