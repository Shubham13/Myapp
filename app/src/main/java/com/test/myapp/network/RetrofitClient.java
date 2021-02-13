package com.test.myapp.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static final String BASE_URL = "https://reqres.in/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new NetworkConnectionInterceptor()).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

    /*OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new NetworkConnectionInterceptor() {
                                                                              @Override
                                                                              public Response intercept(Chain chain) throws IOException {
                                                                                  Request original = chain.request();

                                                                                  Request.Builder requestBuilder = original.newBuilder()
                                                                                          .addHeader("Authorization", "")
                                                                                          .method(original.method(), original.body());

                                                                                  Request request = requestBuilder.build();
                                                                                  return chain.proceed(request);
                                                                              }
                                                                          }
    ).build();*/
}
