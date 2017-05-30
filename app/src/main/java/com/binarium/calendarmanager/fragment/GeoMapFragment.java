package com.binarium.calendarmanager.fragment;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnTouchListener;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.activity.LocationActivity;
import com.binarium.calendarmanager.fragment.dialog.DatePickerDialogFragment;
import com.binarium.calendarmanager.fragment.listener.DatePickerDialogListener;
import com.binarium.calendarmanager.infrastructure.CollectionValidations;
import com.binarium.calendarmanager.infrastructure.Constants;
import com.binarium.calendarmanager.infrastructure.DateExtensions;
import com.binarium.calendarmanager.infrastructure.EnumExtensions;
import com.binarium.calendarmanager.infrastructure.IntegerValidations;
import com.binarium.calendarmanager.infrastructure.MapExtensions;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.infrastructure.SnackBarExtensions;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapView;
import com.binarium.calendarmanager.myapp.geofence.GeofenceRequestReceiver;
import com.binarium.calendarmanager.myapp.geofence.GeofenceTransitionsIntentService;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.presenters.GeoMapPresenterImpl;
import com.binarium.calendarmanager.viewmodels.location.Location;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class GeoMapFragment extends Fragment implements GeoMapView, OnClickListener, ConnectionCallbacks, OnConnectionFailedListener, ResultCallback, OnMapReadyCallback, OnMarkerClickListener, InfoWindowAdapter, OnTouchListener, DatePickerDialogListener {
    @Bind(R.id.fab_btn_check_in)
    FloatingActionButton fab_btn_check_in;

    private ProgressDialog progressDialog;
    private static GeoMapFragment instance;
    private GeofenceRequestReceiver receiver;
    GoogleApiClient googleApiClient;
    List<Geofence> geofenceList;
    PendingIntent geofencePendingIntent;
    GoogleMap googleMap;

    @Inject
    GeoMapPresenterImpl geoMapPresenter;

    private static final String LOCATIONS = "Locations";
    List<Location> locations;
    int locationId;

    private static final String DATE = "DATE";
    private static final String TAG_DATE = "TAG_DATE";
    EditText etLocationDate;

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
        setHasOptionsMenu(true);

        if (ObjectValidations.IsNotNull(savedInstanceState))
            locations = savedInstanceState.getParcelableArrayList(LOCATIONS);

        if (ObjectValidations.IsNull(googleApiClient))
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        IntentFilter filter = new IntentFilter(GeofenceRequestReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new GeofenceRequestReceiver();
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(LOCATIONS, (ArrayList<Location>) locations);
        outState.putString(DATE, etLocationDate.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_geo_map, container, false);
        ButterKnife.bind(this, root);
        fab_btn_check_in.setOnClickListener(this);
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
        setCheckedToLocation(locationId);
        setButtonVisibility(View.GONE);
        addLocationsToMap();
    }

    @Override
    public void getAllLocationsSuccess(List<Location> locations) {
        this.locations = locations;
        addLocationsToMap();
        addMyLocationToMap();
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
        if (CollectionValidations.IsEmpty(locations)) {
            geoMapPresenter.getAllLocations(Preferences.getUserId(), etLocationDate.getText().toString());
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
        geofenceList = new ArrayList<>();
        for (Location location : locations) {
            if(location.isChecked() == false) {
                Geofence geofence = createGeofence(location);
                geofenceList.add(geofence);
            }
            addDrawMarkerWithRadius(location);
        }

        if (CollectionValidations.IsNotEmpty(locations))
            LocationServices.GeofencingApi.addGeofences(
                googleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent()
            ).setResultCallback(this);
    }

    private void clearMap() {
        LocationServices.GeofencingApi.removeGeofences(googleApiClient, getGeofencePendingIntent());
        googleMap.clear();
        setButtonVisibility(View.GONE);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(getActivity(), GeofenceTransitionsIntentService.class);
        geofencePendingIntent = PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    private void addDrawMarkerWithRadius(Location location){
        int strokeColor;
        int fillColor;
        int image = EnumExtensions.getImageOfLocationType(location.getType());

        if (location.isChecked()) {
            strokeColor = ResourcesExtensions.toInt(R.color.stroke_color_green);
            fillColor = ResourcesExtensions.toInt(R.color.fill_color_green);
            image = R.drawable.ic_checkbox_circle_green;
        } else if (location.isOwner()) {
            strokeColor = ResourcesExtensions.toInt(R.color.stroke_color_red);
            fillColor = ResourcesExtensions.toInt(R.color.fill_color_red);
        } else {
            strokeColor = ResourcesExtensions.toInt(R.color.stroke_color_yellow);
            fillColor = ResourcesExtensions.toInt(R.color.fill_color_yellow);
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CircleOptions circleOptions = new CircleOptions().center(latLng).radius(location.getRadius()).fillColor(fillColor).strokeColor(strokeColor).strokeWidth(4);
        googleMap.addCircle(circleOptions);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(image));
        Marker marker = googleMap.addMarker(markerOptions);
        marker.showInfoWindow();
        marker.setTag(location);
    }

    private Geofence createGeofence(Location location) {
        String requestId = String.valueOf(location.getId());
        float radius = (float) location.getRadius();
        return new Geofence.Builder()
                .setRequestId(requestId)
                .setCircularRegion(location.getLatitude(), location.getLongitude(), radius)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT | Geofence.GEOFENCE_TRANSITION_DWELL)
                .setLoiteringDelay(Geofence.GEOFENCE_TRANSITION_DWELL)
                .build();
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
            showErrorMessage(ResourcesExtensions.toString(R.string.check_in_without_location));
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

    //region ResultCallback

    @Override
    public void onResult(@NonNull Result result) {
        if (result.getStatus().isSuccess()) {
            Log.i("si", "location_4");
        } else {
            Log.i("no", "error");
        }
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
        googleMap.setInfoWindowAdapter(this);
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

    public void setLocationToCheckIn(int locationId) {
        this.locationId = locationId;
    }

    private void createCheckIn() {
        geoMapPresenter.createCheckIn(Preferences.getUserId(), locationId);
    }

    private void setCheckedToLocation(final int locationId) {
        List<Location> locations = FluentIterable.from(this.locations).filter(new Predicate<Location>() {
            @Override
            public boolean apply(Location location) {
                return location.getId() == locationId;
            }
        }).toList();
        Location location = Iterables.getFirst(locations, null);
        location.setChecked(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.geo_map_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.btn_refresh_map:
                String date = etLocationDate.getText().toString();
                geoMapPresenter.getAllLocations(Preferences.getUserId(), date);
                return true;
            case R.id.btn_add_location:
                Util.sendAndFinish(getActivity(), LocationActivity.class);
                return true;
            case R.id.btn_road_map:
                setTraffic(item);
                return true;
            case R.id.btn_map_type_satellite:
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                item.setChecked(true);
                return true;
            case R.id.btn_map_type_terrain:
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                item.setChecked(true);
                return true;
            case R.id.btn_map_type_normal:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                item.setChecked(true);
                return true;
        }
        return false;
    }

    private void setTraffic(MenuItem item) {
        if (googleMap.isTrafficEnabled()) {
            googleMap.setTrafficEnabled(false);
            item.setChecked(false);
        } else {
            googleMap.setTrafficEnabled(true);
            item.setChecked(true);
        }
    }

    private void addFilterToMenu() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(Constants.EMPTY_STRING);
        View filterMenu = getLayoutInflater(null).inflate(R.layout.geo_map_filter_menu, null);
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
        geoMapPresenter.getAllLocations(Preferences.getUserId(), date);
    }

    //endregion
}