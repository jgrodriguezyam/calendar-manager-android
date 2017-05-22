package com.binarium.calendarmanager.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.CollectionValidations;
import com.binarium.calendarmanager.infrastructure.EnumExtensions;
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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public class LocationFragment extends Fragment implements LocationView, ConnectionCallbacks, OnConnectionFailedListener, OnMapReadyCallback, OnMarkerClickListener, OnMapLongClickListener, OnMarkerDragListener {
    private ProgressDialog progressDialog;
    GoogleApiClient googleApiClient;
    GoogleMap googleMap;

    @Inject
    LocationPresenterImpl locationPresenter;

    private static final String LOCATIONS = "Locations";
    List<Location> locations;

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

        if (ObjectValidations.IsNotNull(savedInstanceState)) {
            locations = savedInstanceState.getParcelableArrayList(LOCATIONS);
        }

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(LOCATIONS, (ArrayList<Location>) locations);
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
        View root = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, root);
        return root;
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
    public void updateLocationSuccess() {
        addLocationsToMap();
    }

    //endregion

    //region ConnectionCallbacks

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (CollectionValidations.IsEmpty(locations)) {
            locationPresenter.getAllLocations(Preferences.getUserId());
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
        int strokeColor = 0xff00000f;
        int shadeColor = 0x4400000f;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CircleOptions circleOptions = new CircleOptions().center(latLng).radius(location.getRadius()).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(4);
        googleMap.addCircle(circleOptions);
        String snippet = location.getStartDate().equals(location.getEndDate()) ? location.getStartDate() : location.getStartDate() + " - " + location.getEndDate();
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(EnumExtensions.getImageOfLocationType(location.getType()))).title(location.getName()).snippet(snippet);
        Marker marker = googleMap.addMarker(markerOptions);
        marker.showInfoWindow();
        marker.setTag(location);
        if (location.isOwner())
            marker.setDraggable(true);
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
    }

    //endregion

    //region OnMarkerClickListener

    @Override
    public boolean onMarkerClick(Marker marker) {

        Location location = (Location) marker.getTag();
        //marker.setSnippet(String.valueOf(location.getName()));
        return false;
    }

    //endregion

    //region OnMapLongClickListener

    @Override
    public void onMapLongClick(LatLng latLng) {

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
}
