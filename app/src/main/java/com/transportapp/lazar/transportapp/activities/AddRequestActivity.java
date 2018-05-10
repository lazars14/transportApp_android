package com.transportapp.lazar.transportapp.activities;

import android.app.AlertDialog;
import android.graphics.Color;
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
import java.util.List;

import helpers.DialogHelper;
import helpers.InternetHelper;
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        InternetHelper.checkIfConnected(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                if(markersCount < 2) {
                    String title = (markersCount == 0) ? "Start" : "End";
                    points.add(point);
                    map.addMarker(new MarkerOptions().title(title).position(point)).showInfoWindow();
                    markersCount++;
                    if(markersCount == 2) {
                        DrawPolylineService service = new DrawPolylineService(map, points, polylineOptions, loadingBackground, progressBar, AddRequestActivity.this);
                        service.execute();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.maxPoints_warning, Toast.LENGTH_SHORT).show();
                }
            }
        });

        float zoom = (float) 12.00;
        LatLng start = new LatLng(45.2671, 19.8335);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, zoom));

        showInstructionsToast();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.add_btn) {
            requestService.addRequest(points.get(0), points.get(1));
        } else if (view.getId() == R.id.reset_btn){
            map.clear();
            markersCount = 0;
            points.clear();
        } else if (view.getId() == R.id.help_btn) {
            DialogHelper.showDialogInfo(this, "Help", getString(R.string.addRequest_instructions));
        }
    }

    private void showInstructionsToast() {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.addRequest_instructions, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 50);
        toast.show();
    }
}
