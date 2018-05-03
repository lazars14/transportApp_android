package com.transportapp.lazar.transportapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.transportapp.lazar.transportapp.R;

import helpers.NavigationHelper;

import static java.lang.Thread.sleep;

public class SplashScreenActivity extends AppCompatActivity {

    private NavigationHelper navigationHelper;
    private Class navActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        navigationHelper = new NavigationHelper(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Thread thread = new Thread(){
            @Override
            public void run() {
                sleepFor1Sec();

                navActivity = checkIfLoggedIn();

                Intent intent = new Intent(getApplicationContext(), navActivity);
                startActivity(intent);
            }
        };

        thread.start();

    }

    public void sleepFor1Sec() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Class checkIfLoggedIn() {
        Class navigateTo = null;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(preferences.getString("user_token", "invalid").equals("invalid")) {
            navigateTo = LoginActivity.class;
        } else {
            navigateTo = MainActivity.class;
        }

        return navigateTo;
    }
}
