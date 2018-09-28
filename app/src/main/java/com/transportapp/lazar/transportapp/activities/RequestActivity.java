package com.transportapp.lazar.transportapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.transportapp.lazar.transportapp.R;

import java.util.Date;

import helpers.DateHelper;
import helpers.NavigationHelper;
import helpers.ValuePairViewHelper;
import services.UserService;
import utils.Constants;

import static utils.Constants.SLASH;

public class RequestActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OnMapReadyCallback {

    private NavigationHelper navigationHelper;
    private UserService userService;

    private String request_id;
    private Date startDate;
    private Date endDate;
    private GoogleMap map;

    private Button acceptBtn;
    private Button rejectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationHelper = new NavigationHelper(this);
        userService = new UserService(this, this);

        acceptBtn = findViewById(R.id.accept_btn);
        rejectBtn = findViewById(R.id.reject_btn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            request_id = bundle.getString("id");

            fillRequestInfo(bundle);
        }

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        // This calls OnMapReady(..). (Asynchronously)
        mapFragment.getMapAsync(this);
    }

    private void fillRequestInfo(Bundle bundle) {

        View parentView = findViewById(R.id.request_content);

        String startDateStrTransformed = null;
        String endDateStrTransformed = null;

        long start_dateLong = bundle.getLong("startDate");
        if(start_dateLong == 0) {
            startDate = null;
            startDateStrTransformed = SLASH;
            endDateStrTransformed = SLASH;
        } else {
            startDate = new Date(start_dateLong);
            endDate = new Date(bundle.getLong("endDate"));

            startDateStrTransformed = DateHelper.dateToString(startDate);
            endDateStrTransformed = DateHelper.dateToString(endDate);
        }

        ValuePairViewHelper.setLabelValuePair(parentView, R.id.start_date_info, Constants.REQUEST_INFO_LABELS[0], startDateStrTransformed);
        ValuePairViewHelper.setLabelValuePair(parentView, R.id.end_date_info, Constants.REQUEST_INFO_LABELS[1], endDateStrTransformed);

        int statusInt = bundle.getInt("status");
        String statusString = Constants.statuses.get(statusInt);
        ValuePairViewHelper.setLabelValuePair(parentView, R.id.status_info, Constants.REQUEST_INFO_LABELS[4], statusString);

        String confirmationDateStrTransformed = SLASH;
        long confirmationDateLong = bundle.getLong("confirmationRequestDate");
        if(confirmationDateLong != 0) {
            Date confirmationDate = new Date(confirmationDateLong);
            confirmationDateStrTransformed = DateHelper.dateToString(confirmationDate);
        }
        ValuePairViewHelper.setLabelValuePair(parentView, R.id.confirmationRequestDate_info, Constants.REQUEST_INFO_LABELS[6], confirmationDateStrTransformed);

        float distance = bundle.getFloat("distance");
        ValuePairViewHelper.setLabelValuePair(parentView, R.id.distance_info, Constants.REQUEST_INFO_LABELS[7], String.valueOf(distance) + " km");

        ValuePairViewHelper.setLabelValuePair(parentView, R.id.price_info, Constants.REQUEST_INFO_LABELS[2], String.valueOf(bundle.getFloat("price")) + " DIN");
        ValuePairViewHelper.setLabelValuePair(parentView, R.id.discount_info, Constants.REQUEST_INFO_LABELS[3], String.valueOf(bundle.getFloat("discount")) + "%");

        Date submissionDate = new Date(bundle.getLong("submissionDate"));
        ValuePairViewHelper.setLabelValuePair(parentView, R.id.submissionDate_info, Constants.REQUEST_INFO_LABELS[5], DateHelper.dateToString(submissionDate));

        if(startDate == null) {
            acceptBtn.setEnabled(false);
        } else if(startDate.getTime() < new Date().getTime()) {
            rejectBtn.setEnabled(false);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        Bundle bundle = getIntent().getExtras();

        LatLng startLocation = new LatLng(bundle.getDouble("startLocationLat"), bundle.getDouble("startLocationLng"));
        LatLng endLocation = new LatLng(bundle.getDouble("endLocationLat"), bundle.getDouble("endLocationLng"));

        Log.d("startLocation ", "start location is: " + startLocation.latitude + ", " + startLocation.longitude);
        Log.d("endLocation ", "end location is: " + endLocation.latitude + ", " + endLocation.longitude);

        float zoom = (float) 9.00;
        map.addMarker(new MarkerOptions().position(startLocation).title("Start")).showInfoWindow();
        map.addMarker(new MarkerOptions().position(endLocation).title("End")).showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, zoom));

        // draw direction
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_requests) {
            finish();
        } else if (id == R.id.nav_changeEmail) {
            navigationHelper.navigateTo(ChangeEmailActivity.class, this);
        } else if (id == R.id.nav_changePassword) {
            navigationHelper.navigateTo(ChangePasswordActivity.class, this);
        } else if (id == R.id.nav_updateInfo) {
            navigationHelper.navigateTo(UpdateInfoActivity.class, this);
        } else if (id == R.id.nav_logout) {
            userService.logout();
            navigationHelper.navigateTo(LoginActivity.class, this);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.accept_btn) {
//            requestService.acceptRequest(request_id);
        } else if(view.getId() == R.id.reject_btn) {
//            requestService.rejectRequest(request_id);
        }
    }
}
