package com.binarium.calendarmanager.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
        AccountFragment accountFragment = AccountFragment.newInstance();
        fragmentTransaction.add(R.id.frame_container, accountFragment);
        fragmentTransaction.commit();
    }
}
