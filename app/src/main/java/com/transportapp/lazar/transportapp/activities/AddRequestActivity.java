package com.transportapp.lazar.transportapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.transportapp.lazar.transportapp.R;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import helpers.DialogHelper;
import helpers.InternetHelper;
import helpers.LocationHelper;
import model.Location;
import services.DrawPolylineService;
import services.GoogleMapsService;
import services.RequestService;

public class AddRequestActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private RequestService requestService;
    private GoogleMapsService mapsService;

    private GoogleMap map;
    private View loadingBackground;
    private ProgressBar progressBar;
    private int markersCount = 0;
    private List<LatLng> points = new ArrayList<LatLng>();
    private Polyline polyline;
    private PolylineOptions polylineOptions;

    private LocationManager locationManager;
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            float zoom = (float) 12.00;
            LatLng start = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, zoom));

            endLoading();

            locationManager.removeUpdates(this);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        requestService = new RequestService(this, null, this);
        mapsService = new GoogleMapsService();

        loadingBackground = findViewById(R.id.load_background);
        progressBar = findViewById(R.id.progress_bar);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        InternetHelper.checkIfConnected(this);

        if (map != null && points.size() == 0) {
            if (!LocationHelper.isGPSEnabled(getApplicationContext())) {
                // do nothing
            } else {
                startLoading();
                getCurrentLocation();
            }
        }

    }

    private void startLoading() {
        loadingBackground.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void endLoading() {
        loadingBackground.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                if (markersCount < 2) {
                    String title = (markersCount == 0) ? "Start" : "End";
                    points.add(point);
                    map.addMarker(new MarkerOptions().title(title).position(point)).showInfoWindow();
                    markersCount++;
                    if (markersCount == 2) {
                        DrawPolylineService service = new DrawPolylineService(map, points, polylineOptions, loadingBackground, progressBar, AddRequestActivity.this);
                        service.execute();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.maxPoints_warning, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (!LocationHelper.isGPSEnabled(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS is turned off, do you want to turn it on?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            // set default location
//                            float zoom = (float) 12.00;
//                            LatLng start = new LatLng(45.2671, 19.8335);
//                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, zoom));

                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            // get current location
            startLoading();
            getCurrentLocation();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_btn) {
            requestService.addRequest(points.get(0), points.get(1));
        } else if (view.getId() == R.id.reset_btn) {
            map.clear();
            markersCount = 0;
            points.clear();
        } else if (view.getId() == R.id.help_btn) {
            DialogHelper.showDialogInfo(this, "Help", getString(R.string.addRequest_instructions));
        }
    }

    private void showInstructionsToast() {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.addRequest_instructions, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 50);
        toast.show();
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        boolean hasPermission = checkLocationPermission();
        if(hasPermission) {
            Criteria criteria = new Criteria();
            locationManager.requestSingleUpdate(criteria, locationListener, null);
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Criteria criteria = null;
                locationManager.requestSingleUpdate(criteria, locationListener, null);
            } else {
                // do nothing
            }
        }

    }
}
