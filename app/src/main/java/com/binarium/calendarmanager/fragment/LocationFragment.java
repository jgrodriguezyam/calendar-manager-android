package com.binarium.calendarmanager.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.fragment.dialog.DatePickerDialogFragment;
import com.binarium.calendarmanager.fragment.dialog.FormLocationDialogFragment;
import com.binarium.calendarmanager.fragment.listener.DatePickerDialogListener;
import com.binarium.calendarmanager.fragment.listener.FormLocationDialogListener;
import com.binarium.calendarmanager.infrastructure.CollectionValidations;
import com.binarium.calendarmanager.infrastructure.Constants;
import com.binarium.calendarmanager.infrastructure.DateExtensions;
import com.binarium.calendarmanager.infrastructure.EnumExtensions;
import com.binarium.calendarmanager.infrastructure.MapExtensions;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.infrastructure.SnackBarExtensions;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.interfaces.location.LocationView;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.presenters.LocationPresenterImpl;
import com.binarium.calendarmanager.viewmodels.location.Location;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public class LocationFragment extends Fragment implements LocationView, ConnectionCallbacks, OnConnectionFailedListener, OnMapReadyCallback, OnMarkerClickListener, OnMapLongClickListener, OnMarkerDragListener, FormLocationDialogListener, InfoWindowAdapter, OnTouchListener, DatePickerDialogListener, OnClickListener {
    @Bind(R.id.fab_btn_plus)
    FloatingActionButton fabBtnPlus;

    @Bind(R.id.fab_btn_delete)
    FloatingActionButton fabBtnDelete;

    @Bind(R.id.fab_btn_edit)
    FloatingActionButton fabBtnEdit;

    private ProgressDialog progressDialog;
    GoogleApiClient googleApiClient;
    GoogleMap googleMap;

    @Inject
    LocationPresenterImpl locationPresenter;

    public static final String FORM_LOCATION_TAG = "FORM_LOCATION_TAG";
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

        if (googleApiClient == null)
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
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
        fabBtnPlus.setOnClickListener(this);
        addFilterToMenu();
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
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.store_location_map);
        mapFragment.getMapAsync(this);
    }

    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
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
        addLocationsToMap();
        addMyLocationToMap();
    }

    @Override
    public void createLocationSuccess(Location location) {
        locations.add(location);
        addLocationsToMap();
    }

    @Override
    public void updateLocationSuccess(Location location) {
        addLocationsToMap();
    }

    //endregion

    //region ConnectionCallbacks

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (CollectionValidations.IsEmpty(locations)) {
            locationPresenter.getAllLocations(Preferences.getUserId(), etLocationDate.getText().toString());
        } else {
            addLocationsToMap();
            addMyLocationToMap();
        }
    }

    public void addLocationsToMap() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showErrorMessage(ResourcesExtensions.toString(R.string.without_permission));
            return;
        }

        clearMap();
        for (Location location : locations) {
            addDrawMarkerWithRadius(location);
        }
    }

    private void clearMap() {
        googleMap.clear();
    }

    private void addDrawMarkerWithRadius(Location location){
        int strokeColor = ResourcesExtensions.toInt(R.color.stroke_color_black);
        int fillColor = ResourcesExtensions.toInt(R.color.fill_color_black);
        int image = R.drawable.ic_map_marker_yellow;
        boolean isDraggable = false;
        if (location.isOwner()) {
            image = R.drawable.ic_map_marker_red;
            isDraggable = true;
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CircleOptions circleOptions = new CircleOptions().center(latLng).radius(location.getRadius()).fillColor(fillColor).strokeColor(strokeColor).strokeWidth(4);
        googleMap.addCircle(circleOptions);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(image));
        Marker marker = googleMap.addMarker(markerOptions);
        marker.showInfoWindow();
        marker.setTag(location);
        marker.setDraggable(isDraggable);
    }

    private void addMyLocationToMap() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showErrorMessage(ResourcesExtensions.toString(R.string.without_permission));
            return;
        }

        android.location.Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (ObjectValidations.IsNotNull(location)) {
            LatLng latitudeLongitude = new LatLng(location.getLatitude(), location.getLongitude());
            Integer zoom = ResourcesExtensions.toInt(R.integer.google_maps_zoom);
            CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(latitudeLongitude, zoom);
            googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(cameraPosition);
        } else {
            showErrorMessage(ResourcesExtensions.toString(R.string.location_fragmnet_without_location));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        showErrorMessage(ResourcesExtensions.toString(R.string.google_api_client_connection_suspended));
    }

    //endregion

    //region OnConnectionFailedListener

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showErrorMessage(ResourcesExtensions.toString(R.string.google_api_client_connection_failed));
    }

    //endregion

    //region OnMapReadyCallback

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setInfoWindowAdapter(this);
    }

    //endregion

    //region OnMarkerClickListener

    @Override
    public boolean onMarkerClick(Marker marker) {
        Location location = (Location) marker.getTag();
        hideAllFabButtons();
        if (location.isOwner()) {
            fabBtnPlus.setImageResource(R.drawable.ic_plus);
            fabBtnPlus.setVisibility(View.VISIBLE);
            fabBtnPlus.setTag(true);
        }else {

        }
        return false;
    }

    private void hideAllFabButtons() {
        fabBtnPlus.setVisibility(View.GONE);
        fabBtnDelete.setVisibility(View.GONE);
        fabBtnEdit.setVisibility(View.GONE);
    }

    //endregion

    //region OnMapLongClickListener

    @Override
    public void onMapLongClick(LatLng latLng) {
        Location location = new Location();
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        FormLocationDialogFragment formLocationDialogFragment = FormLocationDialogFragment.newInstance(location);
        formLocationDialogFragment.setTargetFragment(this, 0);
        formLocationDialogFragment.show(getFragmentManager(), FORM_LOCATION_TAG);
    }

    //endregion

    //region OnMarkerDragListener

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Location location = (Location) marker.getTag();
        if (ObjectValidations.IsNotNull(location)) {
            location.setLatitude(marker.getPosition().latitude);
            location.setLongitude(marker.getPosition().longitude);
            locationPresenter.updateLocation(location);
        }
    }

    //endregion

    //region FormLocationDialogListener

    @Override
    public void createLocation(Location location) {
        locationPresenter.createLocation(location);
    }

    @Override
    public void updateLocation(Location location) {
        locationPresenter.updateLocation(location);
    }

    //endregion

    //region InfoWindowAdapter

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Location location = (Location) marker.getTag();
        if (ObjectValidations.IsNull(location))
            return null;

        String snippetOfLocation = MapExtensions.getSnippetOfLocation(location);
        View customInfoContents = getLayoutInflater(null).inflate(R.layout.custom_info_contents, null);
        ImageView image = (ImageView) customInfoContents.findViewById(R.id.image);
        image.setBackgroundResource(EnumExtensions.getImageOfLocationType(location.getType()));
        TextView title = (TextView) customInfoContents.findViewById(R.id.title);
        title.setText(location.getName());
        TextView snippet = (TextView) customInfoContents.findViewById(R.id.snippet);
        snippet.setText(snippetOfLocation);
        return customInfoContents;
    }

    //endregion

    //region Menu

    private void addFilterToMenu() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(Constants.EMPTY_STRING);
        View filterMenu = getLayoutInflater(null).inflate(R.layout.map_filter_menu, null);
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
        switch (v.getId()) {
            case R.id.fab_btn_plus:
                setOptionsBtnPlus();
                break;
            default:
                break;
        }
    }

    private void setOptionsBtnPlus() {
        Boolean isShow = (Boolean) fabBtnPlus.getTag();
        if (isShow) {
            fabBtnPlus.setImageResource(R.drawable.ic_minus);
            fabBtnDelete.setVisibility(View.VISIBLE);
            fabBtnEdit.setVisibility(View.VISIBLE);
            fabBtnPlus.setTag(false);
        } else {
            fabBtnPlus.setImageResource(R.drawable.ic_plus);
            fabBtnDelete.setVisibility(View.GONE);
            fabBtnEdit.setVisibility(View.GONE);
            fabBtnPlus.setTag(true);
        }
    }

    //endregion
}
