package com.binarium.calendarmanager.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.adapter.LocationAdapter;
import com.binarium.calendarmanager.fragment.dialog.DatePickerDialogFragment;
import com.binarium.calendarmanager.fragment.listener.DatePickerDialogListener;
import com.binarium.calendarmanager.infrastructure.Constants;
import com.binarium.calendarmanager.infrastructure.DateExtensions;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.infrastructure.SnackBarExtensions;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.interfaces.location.LocationView;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.presenters.LocationPresenterImpl;
import com.binarium.calendarmanager.viewmodels.location.Location;
import com.google.common.collect.Iterables;
import com.google.common.base.Predicate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public class LocationFragment extends Fragment implements LocationView, OnTouchListener, DatePickerDialogListener, OnClickListener {
    @BindView(R.id.recycler_view_location)
    RecyclerView recyclerViewLocation;

    private ProgressDialog progressDialog;

    @Inject
    LocationPresenterImpl locationPresenter;

    private static final String LOCATIONS = "Locations";
    List<Location> locations;

    private static final String DATE = "DATE";
    private static final String TAG_DATE = "TAG_DATE";
    EditText etLocationDate;

    public LocationFragment() {
    }

    public static LocationFragment newInstance() {
        LocationFragment locationFragment = new LocationFragment();
        Bundle args = new Bundle();
        locationFragment.setArguments(args);
        return locationFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectorUtils.getInjector(this).inject(this);
        locationPresenter.setLocationView(this);
        progressDialog = Util.createModalProgressDialog(getActivity());

        if (ObjectValidations.IsNotNull(savedInstanceState))
            locations = savedInstanceState.getParcelableArrayList(LOCATIONS);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(LOCATIONS, (ArrayList<Location>) locations);
        outState.putString(DATE, etLocationDate.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, root);
        addFilterToMenu();
        recyclerViewConfig();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ObjectValidations.IsNotNull(savedInstanceState))
            etLocationDate.setText(savedInstanceState.getString(DATE));
    }

    @Override
    public void onResume() {
        super.onResume();
        locationPresenter.getAllLocations(Preferences.getUserId(), etLocationDate.getText().toString());
    }

    //region LocationView

    @Override
    public void showProgress(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showErrorMessage(String message) {
        SnackBarExtensions.showErrorMessage(getView(), message);
    }

    @Override
    public void showSuccessMessage(String message) {
        SnackBarExtensions.showSuccessMessage(getView(), message);
    }

    @Override
    public void getAllLocationsSuccess(List<Location> locations) {
        this.locations = locations;
        RecyclerView.Adapter locationAdapter = new LocationAdapter(this.locations);
        recyclerViewLocation.setAdapter(locationAdapter);
        locationAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteLocationSuccess(Location location) {
        removeLocationOfList(location);
    }

    //endregion

    //region Menu

    private void addFilterToMenu() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(Constants.EMPTY_STRING);
        View filterMenu = getActivity().getLayoutInflater().inflate(R.layout.map_filter_menu, null);
        this.etLocationDate = (EditText) filterMenu.findViewById(R.id.et_location_date);
        etLocationDate.setText(Preferences.getTodayDate());
        etLocationDate.setOnTouchListener(this);
        toolbar.addView(filterMenu);
    }

    //endregion

    //region OnTouchListener

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP)
            return false;

        if (view.getId() == etLocationDate.getId())
            showDate();

        return false;
    }

    public void showDate() {
        Calendar currentDate = new DateExtensions().convertToCalendar(etLocationDate.getText().toString());
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int month = currentDate.get(Calendar.MONTH);
        int year = currentDate.get(Calendar.YEAR);
        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(null, null, year, month, day);
        datePickerDialogFragment.setDatePickerDialogListener(this);
        datePickerDialogFragment.show(getChildFragmentManager(), TAG_DATE);
    }

    //endregion

    //region DatePickerDialogListener

    @Override
    public void setDate(DatePicker view, int year, int month, int day, String tag) {
        String date = new DateExtensions().convertToStringDate(year, month, day);
        etLocationDate.setText(date);
        locationPresenter.getAllLocations(Preferences.getUserId(), date);
    }

    //endregion

    //region OnClickListener

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.fab_btn_delete:
//                Location locationToDelete = (Location) fabBtnDelete.getTag();
//                showDeleteAlertDialog(locationToDelete);
//                break;
//            default:
//                break;
//        }
    }

    private void showDeleteAlertDialog(final Location location) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle(ResourcesExtensions.toString(R.string.title_delete_location))
            .setMessage(ResourcesExtensions.toString(R.string.message_delete_location)+ " " + location.getName() + "?")
            .setPositiveButton(R.string.btn_ok_delete_location, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    locationPresenter.deleteLocation(location);
                }
            })
            .setNegativeButton(R.string.btn_cancel_delete_location, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            })
            .setIcon(R.drawable.ic_delete_red)
            .show();
    }

    //endregion

    //region Custom Methods

    private void removeLocationOfList(final Location locationToRemove) {
        Iterables.removeIf(this.locations, new Predicate<Location>() {
            @Override
            public boolean apply(Location location) {
                return location.getId() == locationToRemove.getId();
            }
        });
    }

    private void recyclerViewConfig() {
        recyclerViewLocation.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewLocation.setLayoutManager(layoutManager);
    }

    //endregion
}
