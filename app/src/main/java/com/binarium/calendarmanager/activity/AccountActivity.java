package com.binarium.calendarmanager.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.fragment.AccountFragment;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        if (ObjectValidations.IsNull(savedInstanceState)) {
            initFragment();
        }
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment accountFragment = AccountFragment.newInstance();
        fragmentTransaction.add(R.id.frame_container, accountFragment);
        fragmentTransaction.commit();
    }
}
