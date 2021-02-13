package com.test.myapp.data.model;

import java.io.Serializable;

public class Login implements Serializable {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
