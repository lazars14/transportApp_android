package com.transportapp.lazar.transportapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.transportapp.lazar.transportapp.R;

import helpers.InternetHelper;
import helpers.NavigationHelper;
import model.User;
import services.UserService;

import static java.lang.Thread.sleep;

public class SplashScreenActivity extends AppCompatActivity {

    private UserService userService = new UserService();
    private NavigationHelper navigationHelper = new NavigationHelper(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(InternetHelper.isNetworkAvailable(this)){

            User loggedUser = userService.login("nekiEmail", "nekiPassword");
            Class navigateTo = null;

            if(loggedUser == null) {
                navigateTo = LoginActivity.class;

            } else {
                navigateTo = MainActivity.class;
            }

//            wait for one second
            try {
                sleep(1000);

                navigationHelper.navigateTo(navigateTo, this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }




        } else {
            Toast.makeText(this, R.string.turn_on_internet, Toast.LENGTH_LONG).show();
        }

    }
}
