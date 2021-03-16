package com.binarium.calendarmanager.activity;

import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.fragment.FriendshipFragment;
import com.binarium.calendarmanager.infrastructure.NavigationExtensions;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendshipActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigation)
    NavigationView navigationView;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendship);

        ButterKnife.bind(this);
        configureActionBar();
        configureNavigationDrawer();

        if (ObjectValidations.IsNull(savedInstanceState))
            initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment friendshipFragment = FriendshipFragment.newInstance();
        fragmentTransaction.add(R.id.frame_container, friendshipFragment);
        fragmentTransaction.commit();
    }

    private void configureActionBar(){
        toolbar.setTitle(R.string.title_friendship_activity);
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
        Util.sendAndFinish(this, GeoMapActivity.class);
    }
}