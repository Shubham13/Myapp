package com.test.myapp.ui.home;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;


import com.test.myapp.R;
import com.test.myapp.databinding.ActivityHomeBinding;
import com.test.myapp.ui.user.UserListFragment;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding activityHomeBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new UserListFragment());
        fragmentTransaction.commit();
    }
}
