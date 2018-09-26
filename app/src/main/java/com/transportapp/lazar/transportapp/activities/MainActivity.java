package com.transportapp.lazar.transportapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.transportapp.lazar.transportapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.RequestAdapter;
import helpers.InternetHelper;
import helpers.NavigationHelper;
import model.Location;
import model.Request;
import services.RequestService;
import services.UserService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationHelper navigationHelper;
    private UserService userService;
    private RequestService requestService;

    Request request = new Request("dummyId", new Location(45.2671, 19.8335), new Location(46.100376, 19.667587), new Date(), new Date(),
            100, 5, 0, "asdf;ksajdfassnlnnaosidfn", new Date(), new Date(), "asdfsadfsadfasfasdf", 0, 0);
    Request request2 = new Request("dummyId", new Location(44.7866, 20.4489), new Location(45.2497, 19.3968), new Date(), new Date(),
            100, 5, 0, "asdf;ksajdfassnlnnaosidfn", new Date(), new Date(), "asdfsadfsadfasfasdf", 0, 0);
    private List<Request> requests = new ArrayList<Request>();

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private RequestAdapter adapter;


    private void loadData() {
        requests = new ArrayList<Request>();

        requests.add(request);
        requests.add(request2);

        adapter = new RequestAdapter(this, requests);
        recyclerView.setAdapter(adapter); // for testing only
//        requestService.getRequests(requests);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* important - refresh data on every resume */
        loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        requestService = new RequestService(this, this, null);

        InternetHelper.checkIfConnected(this);

        recyclerView = findViewById(R.id.recycler_view);

        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        // recycler view set in service method

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationHelper.navigateTo(AddRequestActivity.class, MainActivity.this);
            }
        });
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_requests) {
            // do nothing
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

}
