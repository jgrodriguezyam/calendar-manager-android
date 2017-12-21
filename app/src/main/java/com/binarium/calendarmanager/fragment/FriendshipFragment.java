package com.binarium.calendarmanager.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 03/08/2017.
 */

public class FriendshipFragment extends Fragment {

    private ProgressDialog progressDialog;

    public FriendshipFragment() {

    }

    public static FriendshipFragment newInstance() {
        FriendshipFragment friendshipFragment = new FriendshipFragment();
        Bundle args = new Bundle();
        friendshipFragment.setArguments(args);
        return friendshipFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        InjectorUtils.getInjector(this).inject(this);
//        locationPresenter.setLocationView(this);
        progressDialog = Util.createModalProgressDialog(getActivity());

//        if (ObjectValidations.IsNotNull(savedInstanceState))
//            locations = savedInstanceState.getParcelableArrayList(LOCATIONS);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putParcelableArrayList(LOCATIONS, (ArrayList<Location>) locations);
//        outState.putString(DATE, etLocationDate.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friendship, container, false);
        ButterKnife.bind(this, root);
//        addFilterToMenu();
//        recyclerViewConfig();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (ObjectValidations.IsNotNull(savedInstanceState))
//            etLocationDate.setText(savedInstanceState.getString(DATE));
    }

    @Override
    public void onResume() {
        super.onResume();
//        locationPresenter.getAllLocations(Preferences.getUserId(), etLocationDate.getText().toString());
    }
}
