package com.transportapp.lazar.transportapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.transportapp.lazar.transportapp.R;

import helpers.InternetHelper;
import helpers.NavigationHelper;
import model.User;
import services.UserService;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private NavigationHelper navigationHelper;
    private UserService userService;

    /* UI references */
    private EditText emailTextView;
    private EditText passwordTextView;
    private EditText firstNameTextView;
    private EditText lastNameTextView;
    private EditText addressTextView;
    private EditText phoneNumberTextView;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationHelper = new NavigationHelper(this);
        userService = new UserService(this);

        emailTextView = findViewById(R.id.email_etxt);
        passwordTextView = findViewById(R.id.password_etxt);
        firstNameTextView = findViewById(R.id.firstName_etxt);
        lastNameTextView = findViewById(R.id.lastName_etxt);
        addressTextView = findViewById(R.id.address_etxt);
        phoneNumberTextView = findViewById(R.id.phoneNumber_etxt);

        registerButton = findViewById(R.id.register_btn);

        EditText[] editTexts = {emailTextView, passwordTextView};

        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!registerButton.isEnabled()) {
                        /* button disabled */
                        if(!TextUtils.isEmpty(emailTextView.getText().toString()) && !TextUtils.isEmpty(passwordTextView.getText().toString())) {
                            registerButton.setEnabled(true);
                        }
                    } else {
                        /* button enabled */
                        if(TextUtils.isEmpty(emailTextView.getText().toString()) || TextUtils.isEmpty(passwordTextView.getText().toString())) {
                            registerButton.setEnabled(false);
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
    protected void onResume() {
        super.onResume();

        InternetHelper.checkIfConnected(this);
    }

    @Override
    public void onClick(View view) {
        InternetHelper.checkIfConnected(getApplicationContext());

        if(InternetHelper.internet) {
            User loggedUser = userService.register(emailTextView.getText().toString(), passwordTextView.getText().toString(), firstNameTextView.getText().toString(),
                    lastNameTextView.getText().toString(), addressTextView.getText().toString(), phoneNumberTextView.getText().toString());

            if(loggedUser != null) {
                navigationHelper.navigateTo(MainActivity.class, this);
            } else {
                Toast.makeText(getApplicationContext(), R.string.register_error, Toast.LENGTH_LONG).show();
            }
        }
    }
}
