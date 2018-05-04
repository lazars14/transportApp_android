package com.transportapp.lazar.transportapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.Toast;

import com.transportapp.lazar.transportapp.R;

import helpers.InternetHelper;
import helpers.NavigationHelper;
import services.UserService;

public class UpdateInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private NavigationHelper navigationHelper;
    private UserService userService;

    /* UI references */
    private EditText firstNameTextView;
    private EditText lastNameTextView;
    private EditText addressTextView;
    private EditText phoneNumberTextView;
    private Button updateInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationHelper = new NavigationHelper(this);
        userService = new UserService(this);

        firstNameTextView = findViewById(R.id.firstName_etxt);
        lastNameTextView = findViewById(R.id.lastName_etxt);
        addressTextView = findViewById(R.id.address_etxt);
        phoneNumberTextView = findViewById(R.id.phoneNumber_etxt);

        updateInfoButton = findViewById(R.id.updateInfo_btn);

        EditText[] editTexts = {firstNameTextView, lastNameTextView, addressTextView, phoneNumberTextView};

        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!updateInfoButton.isEnabled()) {
                        /* button disabled */
                        if(!TextUtils.isEmpty(firstNameTextView.getText().toString()) && !TextUtils.isEmpty(lastNameTextView.getText().toString()) &&
                                !TextUtils.isEmpty(addressTextView.getText().toString()) && !TextUtils.isEmpty(phoneNumberTextView.getText().toString())) {
                            updateInfoButton.setEnabled(true);
                        }
                    } else {
                        /* button enabled */
                        if(TextUtils.isEmpty(firstNameTextView.getText().toString()) || TextUtils.isEmpty(lastNameTextView.getText().toString()) ||
                                TextUtils.isEmpty(addressTextView.getText().toString()) || TextUtils.isEmpty(phoneNumberTextView.getText().toString())) {
                            updateInfoButton.setEnabled(false);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_requests) {
            navigationHelper.navigateTo(MainActivity.class, this);
        } else if (id == R.id.nav_changeEmail) {
            navigationHelper.navigateTo(ChangeEmailActivity.class, this);
        } else if (id == R.id.nav_changePassword) {
            navigationHelper.navigateTo(ChangePasswordActivity.class, this);
        } else if (id == R.id.nav_updateInfo) {
            // do nothing
        } else if (id == R.id.nav_logout) {
            userService.logout();
            navigationHelper.navigateTo(LoginActivity.class, this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login_button) {

            InternetHelper.checkIfConnected(this);

            if(InternetHelper.internet) {
                boolean successfull = userService.updateInfo(firstNameTextView.getText().toString(), lastNameTextView.getText().toString(),
                        addressTextView.getText().toString(), phoneNumberTextView.getText().toString());

                if(successfull) {
                    navigationHelper.navigateTo(MainActivity.class, this);
                } else {
                    Toast.makeText(this, R.string.updateInfo_invalid, Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}
