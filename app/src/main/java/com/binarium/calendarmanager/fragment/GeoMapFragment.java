package com.binarium.calendarmanager.fragment;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.IntegerValidations;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.infrastructure.SnackBarExtensions;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapView;
import com.binarium.calendarmanager.myapp.geofence.GeofenceRequestReceiver;
import com.binarium.calendarmanager.myapp.geofence.GeofenceTransitionsIntentService;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.presenters.GeoMapPresenterImpl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class GeoMapFragment extends Fragment implements GeoMapView, OnClickListener, ConnectionCallbacks, OnConnectionFailedListener, ResultCallback, OnMapReadyCallback {
    @Bind(R.id.fab_btn_check_in)
    FloatingActionButton fab_btn_check_in;

    private ProgressDialog progressDialog;
    private static GeoMapFragment instance;
    private GeofenceRequestReceiver receiver;
    GoogleApiClient googleApiClient;
    ArrayList<Geofence> geofenceList;
    PendingIntent geofencePendingIntent;
    GoogleMap googleMap;

    @Inject
    GeoMapPresenterImpl geoMapPresenter;

    public GeoMapFragment() {
    }

    public static GeoMapFragment newInstance() {
        GeoMapFragment geoMapFragment = new GeoMapFragment();
        Bundle args = new Bundle();
        geoMapFragment.setArguments(args);
        return geoMapFragment;
    }

    public static GeoMapFragment getInstace(){
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectorUtils.getInjector(this).inject(this);
        geoMapPresenter.setGeoMapView(this);
        progressDialog = Util.createModalProgressDialog(getActivity());
        instance = this;

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        IntentFilter filter = new IntentFilter(GeofenceRequestReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new GeofenceRequestReceiver();
        getActivity().registerReceiver(receiver, filter);
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
        View root = inflater.inflate(R.layout.fragment_geo_map, container, false);
        ButterKnife.bind(this, root);
        fab_btn_check_in.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
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

    //region GeoMapView

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
    public void createCheckInSuccess(int userId, int locationId) {

    }

    //endregion

    //region OnClickListener

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_btn_check_in:
                createCheckIn();
                break;
            default:
                break;
        }
    }

    //endregion

    //region ConnectionCallbacks

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showErrorMessage(ResourcesExtensions.toString(R.string.without_permission));
            return;
        }

        LocationServices.GeofencingApi.addGeofences(
                googleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent()
        ).setResultCallback(this);

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (ObjectValidations.IsNotNull(location)) {
            LatLng latitudeLongitude = new LatLng(location.getLatitude(), location.getLongitude());
            Integer zoom = ResourcesExtensions.toInt(R.integer.google_maps_zoom);
            CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(latitudeLongitude, zoom);
            googleMap.moveCamera(cameraPosition);
        } else {
            showErrorMessage(ResourcesExtensions.toString(R.string.check_in_without_location));
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        addLocation();
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    public void addLocation() {
        geofenceList = new ArrayList<>();
        float radius = (float) 100;
        geofenceList.add(new Geofence.Builder()
                .setRequestId("locationId")
                .setCircularRegion(21.013143, -89.589417, radius)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT | Geofence.GEOFENCE_TRANSITION_DWELL)
                .setLoiteringDelay(Geofence.GEOFENCE_TRANSITION_DWELL)
                .build());
    }

    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(getActivity(), GeofenceTransitionsIntentService.class);
        geofencePendingIntent = PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "GoogleApiClient Connection Suspended", Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region OnConnectionFailedListener

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "GoogleApiClient Connection Failed", Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region ResultCallback

    @Override
    public void onResult(@NonNull Result result) {
        String toastMessage;
        if (result.getStatus().isSuccess()) {
            toastMessage = "Success";
            Log.i("si", "location_4");
        } else {
            toastMessage = "Error";
        }
        //Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region OnMapReadyCallback

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showErrorMessage(ResourcesExtensions.toString(R.string.without_permission));
            return;
        }

        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        double latitude = 21.013143;
        double longitude = -89.589417;
        double radius = 100;
        String title = "Plenumsoft";
        drawMarkerWithCircle(new LatLng(latitude, longitude), radius, title);
        //drawMarkerWithCircle(new LatLng(21.013143, -89.589417));
        //drawMarkerWithCircle(new LatLng(21.131046, -89.781007), 30);
    }

    private void drawMarkerWithCircle(LatLng position, double radius, String title){
        int strokeColor =0xff00ff00;
        int shadeColor = 0x4400ff00;

        CircleOptions circleOptions = new CircleOptions().center(position).radius(radius).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        googleMap.addCircle(circleOptions);
        MarkerOptions markerOptions = new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_clock)).title(title);
        googleMap.addMarker(markerOptions).showInfoWindow();
    }

    //endregion

    //region Custom Methods

    public void setButtonVisibility(int isVisible) {
        fab_btn_check_in.setScaleX(0);
        fab_btn_check_in.setScaleY(0);
        fab_btn_check_in.setVisibility(isVisible);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && IntegerValidations.IsZero(isVisible)) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getActivity(), android.R.interpolator.fast_out_slow_in);
            fab_btn_check_in.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(600)
                    .setStartDelay(1000);
        } else if (IntegerValidations.IsZero(isVisible)) {
            fab_btn_check_in.setScaleX(1);
            fab_btn_check_in.setScaleY(1);
        }
    }

    private void createCheckIn() {
        geoMapPresenter.createCheckIn(22, 23);
    }

    //endregion
}