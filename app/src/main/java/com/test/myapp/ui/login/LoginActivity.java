package com.test.myapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.test.myapp.R;
import com.test.myapp.data.model.Login;
import com.test.myapp.databinding.ActivityLoginBinding;
import com.test.myapp.dialog.CommonDialog;
import com.test.myapp.dialog.OnDialogButtonsClick;
import com.test.myapp.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setLifecycleOwner(this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        init();
    }

    private void init() {
        loginViewModel.networkError.observe(new LifecycleOwner() {
            @NonNull
            @Override
            public Lifecycle getLifecycle() {
                return LoginActivity.this.getLifecycle();
            }
        }, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                CommonDialog.showGeneralDialog(LoginActivity.this,
                        "Connection Issue","No Internet","Ok",
                        new OnDialogButtonsClick(){
                            @Override
                            public void onPositiveButtonClick() {
                            }

                            @Override
                            public void onNegativeButtonClick() {
                            }
                        });
            }
        });

        activityLoginBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.callLoginApi(activityLoginBinding.username.getText().toString(),
                        activityLoginBinding.password.getText().toString())
                        .observe(new LifecycleOwner() {
                            @NonNull
                            @Override
                            public Lifecycle getLifecycle() {
                                return LoginActivity.this.getLifecycle();
                            }
                        }, new Observer<Login>() {
                            @Override
                            public void onChanged(Login login) {
                                Log.e("login", login.getToken());
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
}
