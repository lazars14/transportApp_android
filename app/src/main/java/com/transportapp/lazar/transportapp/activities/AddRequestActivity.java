package com.transportapp.lazar.transportapp.activities;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
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

import helpers.InternetHelper;
import model.Location;
import services.GoogleMapsService;
import services.RequestService;

public class AddRequestActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private RequestService requestService;
    private GoogleMapsService mapsService;

    private GoogleMap map;
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
//                map.clear();
                if(markersCount < 2) {
                    String title = (markersCount == 0) ? "Start" : "End";
                    points.add(point);
                    map.addMarker(new MarkerOptions().title(title).position(point)).showInfoWindow();
                    markersCount++;
                    if(markersCount == 2) {
                        Document doc = mapsService.getDocument(points.get(0), points.get(1));
                        Toast.makeText(getApplicationContext(), "this is doc " + doc, Toast.LENGTH_LONG).show();
//                        List<LatLng> forDrawing = mapsService.getDirection(doc);
                        polylineOptions = new PolylineOptions().width(3).color(Color.RED).geodesic(true);
                        polylineOptions.addAll(points);
//                        polylineOptions.addAll(forDrawing);
                        map.addPolyline(polylineOptions);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.maxPoints_warning, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.add_btn) {
            requestService.addRequest(points.get(0), points.get(1));
        } else if (view.getId() == R.id.reset_btn){
            map.clear();
            markersCount = 0;
            points.clear();
        }
    }
}
