package com.binarium.calendarmanager.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.fragment.SplashFragment;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (ObjectValidations.IsNull(savedInstanceState)) {
            initFragment();
        }
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment splashFragment = SplashFragment.newInstance();
        fragmentTransaction.add(R.id.frame_container, splashFragment);
        fragmentTransaction.commit();
    }
}