package services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transportapp.lazar.transportapp.R;
import com.transportapp.lazar.transportapp.activities.LoginActivity;
import com.transportapp.lazar.transportapp.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import helpers.NavigationHelper;
import model.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static utils.Constants.API_URL;

public class UserService extends AsyncTask<Void, Void, Void> {

    private int api;
    private Map<String, String> body;
    private String userId;
    private Context context;
    private AppCompatActivity currentActivity;

    private boolean valid;
    private JSONObject response;
    private String errorMessage;
    private int successMessage;

    public UserService(int api, Map<String, String> body, String userId, Context context, AppCompatActivity currentActivity) {
        this.api = api;
        this.body = body;
        this.userId = userId;
        this.context = context;
        this.currentActivity = currentActivity;
        this.valid = true;
    }

    public UserService(Context context, AppCompatActivity currentActivity) {
        this.context = context;
        this.currentActivity = currentActivity;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(!valid) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, successMessage, Toast.LENGTH_LONG).show();
            navigateCallback();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpService httpService = new HttpService(context);
        String url = "";
        try {
            switch(api) {
                case 1:
                    url = "/user/login";
                    successMessage = R.string.login_success;
                    response = httpService.makeApiCall(false, url, body, "post");

                    errorMessage = getErrorMessage();
                    if(errorMessage != null) valid = false;
                    else {
                        fillUserData(response.getJSONObject("user"), response.getString("token"));
                    }

                    break;
                case 2:
                    url = "/user/register";
                    successMessage = R.string.register_success;
                    response = httpService.makeApiCall(false, url, body, "post");

                    errorMessage = getErrorMessage();
                    if(errorMessage != null) valid = false;
                    else {
                        fillUserData(response.getJSONObject("user"), response.getString("token"));
                    }

                    break;
                case 3:
                    url = "/user/" + userId + "/updateInfo";
                    Log.v("TALAMBASKO", "Change password user id is " + userId);
                    successMessage = R.string.update_info_success;
                    response = httpService.makeApiCall(true, url, body, "put");

                    errorMessage = getErrorMessage();
                    if(errorMessage != null) valid = false;
                    else {
                        updatePersonalInfo(response.getJSONObject("user"));
                    }

                    break;
                case 4:
                    url = "/user/" + userId + "/changeEmail";
                    successMessage = R.string.change_email_success;
                    response = httpService.makeApiCall(true, url, body, "put");

                    errorMessage = getErrorMessage();
                    if(errorMessage != null) valid = false;
                    else {
                        updateEmail(body.get("newEmail"));
                    }

                    break;
                case 5:
                    url = "/user/" + userId + "/changePassword";
                    successMessage = R.string.change_password_success;
                    response = httpService.makeApiCall(true, url, body, "put");

                    errorMessage = getErrorMessage();
                    if(errorMessage != null) valid = false;

                    break;
            }
        } catch (Exception e) {
            Log.v("TALAMBASKO", "exception in user service " + e.getMessage());
        }

        return null;
    }

    public String getErrorMessage() {
        JSONObject errorObject = null;
        try {
            errorObject = response.getJSONObject("error");
            return errorObject.getString("message");
        } catch(Exception e) {
            return null;
        }
    }

    public void navigateCallback() {
        NavigationHelper navigationHelper = new NavigationHelper(context);
        navigationHelper.navigateTo(MainActivity.class, currentActivity);
    }

    public void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_id", "default_id");
        editor.putString("user_email", "default_email");
        editor.putString("user_firstName", "default_firstName");
        editor.putString("user_lastName", "default_lastName");
        editor.putString("user_address", "default_address");
        editor.putString("user_phone", "default_phone");
        editor.putString("user_firebaseToken", "default_firebaseToken");

        editor.putString("user_authToken", context.getString(R.string.default_authToken));

        editor.apply();

        NavigationHelper navHelper = new NavigationHelper(context);
        navHelper.navigateTo(LoginActivity.class, currentActivity);
    }

    private void fillUserData(JSONObject loggedUser, String authToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        try {
            Log.v("TALAMBASKO", "User id is " + loggedUser.getString("_id"));
            editor.putString("user_id", loggedUser.getString("_id"));
            editor.putString("user_email", loggedUser.getString("email"));
            editor.putString("user_firstName", loggedUser.getString("firstName"));
            editor.putString("user_lastName", loggedUser.getString("lastName"));
            editor.putString("user_address", loggedUser.getString("address"));
            editor.putString("user_phone", loggedUser.getString("phone"));
            editor.putString("user_firebaseToken", loggedUser.getString("firebaseToken"));

            editor.putString("user_authToken", authToken);

            editor.apply();
        } catch (Exception e) {
            Log.v("TALAMBASKO", "Fill user data exception is " + e.getMessage());
        }
    }

    private void updateEmail(String newEmail) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_email", newEmail);

        editor.apply();
    }

    private void updatePersonalInfo(JSONObject user) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        try {
            editor.putString("user_firstName", user.getString("firstName"));
            editor.putString("user_lastName", user.getString("lastName"));
            editor.putString("user_address", user.getString("address"));
            editor.putString("user_phone", user.getString("phone"));

            editor.apply();
        } catch (Exception e) {

        }

    }
}
