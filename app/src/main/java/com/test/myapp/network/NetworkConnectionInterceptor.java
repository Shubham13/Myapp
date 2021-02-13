package com.test.myapp.network;

import android.content.Context;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {


    public NetworkConnectionInterceptor() {
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        if (!isInternetAvailable()) {
            throw new NoConnectivityException();
            // Throwing our custom exception 'NoConnectivityException'
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
        }
        return false;
    }

}
