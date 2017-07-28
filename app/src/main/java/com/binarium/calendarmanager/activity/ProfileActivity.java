package com.binarium.calendarmanager.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.fragment.ProfileFragment;
import com.binarium.calendarmanager.infrastructure.NavigationExtensions;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.navigation)
    NavigationView navigationView;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);
        configureActionBar();
        configureNavigationDrawer();

        if (ObjectValidations.IsNull(savedInstanceState))
            initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment profileFragment = ProfileFragment.newInstance();
        fragmentTransaction.add(R.id.frame_container, profileFragment);
        fragmentTransaction.commit();
    }

    private void configureActionBar(){
        toolbar.setTitle(R.string.title_profile_activity);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
    }

    private void configureNavigationDrawer(){
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Header
        NavigationExtensions.setWorkerName(navigationView);
        //Body
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        NavigationExtensions.sendTo(this, item.getItemId());
        return true;
    }

    @Override
    public void onBackPressed() {

    }
}
