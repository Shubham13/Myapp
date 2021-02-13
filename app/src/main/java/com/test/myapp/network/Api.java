package com.test.myapp.network;

import com.test.myapp.data.model.Login;
import com.test.myapp.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("api/login")
    Call<Login> loginCall(@Field("email") String email, @Field("password") String password);


    @GET("api/users?page=1")
    Call<User> getUser();

}
