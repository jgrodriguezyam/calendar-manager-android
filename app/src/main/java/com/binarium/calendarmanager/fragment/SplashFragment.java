package com.binarium.calendarmanager.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.activity.LoginActivity;
import com.binarium.calendarmanager.activity.GeoMapActivity;
import com.binarium.calendarmanager.infrastructure.Constants;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.infrastructure.SnackBarExtensions;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.interfaces.splash.SplashView;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.presenters.SplashPresenterImpl;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class SplashFragment extends Fragment implements SplashView {
    @Inject
    SplashPresenterImpl splashPresenter;

    public SplashFragment() {
    }

    public static SplashFragment newInstance() {
        SplashFragment splashFragment = new SplashFragment();
        Bundle args = new Bundle();
        splashFragment.setArguments(args);
        return splashFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectorUtils.getInjector(this).inject(this);
        splashPresenter.setSplashView(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ObjectValidations.IsNotNull(savedInstanceState)) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermissions();
    }

    //region SplashView

    @Override
    public void showErrorMessage(String message) {
        SnackBarExtensions.showErrorMessage(getView(), message);
    }

    @Override
    public void showSuccessMessage(String message) {
        SnackBarExtensions.showSuccessMessage(getView(), message);
    }

    @Override
    public void userLogin() {
        String userName = Preferences.getUserName();
        String password = Preferences.getPassword();
        if (ObjectValidations.IsNotNull(userName) && ObjectValidations.IsNotNull(password)) {
            splashPresenter.userLogin(userName, password);
        } else {
            navigateToLogin();
        }
    }

    @Override
    public void navigateToMap() {
        Util.sendAndFinish(getActivity(), GeoMapActivity.class);
    }

    @Override
    public void navigateToLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Util.sendAndFinish(getActivity(), LoginActivity.class);
            }
        }, 3000);
    }

    //endregion

    //region Custom Methods

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                showErrorMessage(ResourcesExtensions.toString(R.string.without_permission_location));
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                                Manifest.permission.ACCESS_FINE_LOCATION}, Constants.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }else {
            userLogin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    userLogin();
                } else {
                    showErrorMessage(ResourcesExtensions.toString(R.string.permissions_required));
                }
                return;
            }
        }
    }



    //endregion
}
