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
    private int userId;
    private Context context;
    private AppCompatActivity currentActivity;

    public UserService(int api, Map<String, String> body, int userId, Context context, AppCompatActivity currentActivity) {
        this.api = api;
        this.body = body;
        this.userId = userId;
        this.context = context;
        this.currentActivity = currentActivity;
    }

    public UserService(Context context, AppCompatActivity currentActivity) {
        this.context = context;
        this.currentActivity = currentActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpService httpService = new HttpService(context);

        ObjectMapper mapper = new ObjectMapper();
        JSONObject response = null;
        User user = null;
        String url = "";
        int invalidMessage = 0;
        try {
            switch(api) {
                case 1:
                    url = API_URL + "/user/login";
                    invalidMessage = R.string.login_invalid;
                    response = httpService.makeApiCall(false, url, body, "post");
                    user = mapper.readValue(response.getJSONObject("user").toString(), User.class);

                    fillUserData(user, response.getString("token"));
                    break;
                case 2:
                    url = API_URL + "/user/register";
                    invalidMessage = R.string.register_error;
                    response = httpService.makeApiCall(false, url, body, "post");
                    user = mapper.readValue(response.getJSONObject("user").toString(), User.class);

                    fillUserData(user, response.getString("token"));
                    break;
                case 3:
                    url = API_URL + "/user/" + userId + "/updateInfo";
                    invalidMessage = R.string.updateInfo_invalid;
                    response = httpService.makeApiCall(true, url, body, "put");
                    user = mapper.readValue(response.toString(), User.class);

                    updatePersonalInfo(user.getFirstName(), user.getLastName(), user.getAddress(), user.getPhone());
                    break;
                case 4:
                    url = API_URL + "/user/" + userId + "/changeEmail";
                    invalidMessage = R.string.changeEmail_invalid;
                    response = httpService.makeApiCall(true, url, body, "put");

                    user = mapper.readValue(response.toString(), User.class);
                    updateEmail(user.getEmail());
                    break;
                case 5:
                    url = API_URL + "/user/" + userId + "/changePassword";
                    invalidMessage = R.string.changePassword_invalid;
                    response = httpService.makeApiCall(true, url, body, "put");

                    user = mapper.readValue(response.toString(), User.class);
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(context, invalidMessage, Toast.LENGTH_LONG).show();
        }

        navigateCallback();

        return null;
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

    private void fillUserData(User loggedUser, String authToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_id", loggedUser.getId());
        editor.putString("user_email", loggedUser.getEmail());
        editor.putString("user_firstName", loggedUser.getFirstName());
        editor.putString("user_lastName", loggedUser.getLastName());
        editor.putString("user_address", loggedUser.getAddress());
        editor.putString("user_phone", loggedUser.getPhone());
        editor.putString("user_firebaseToken", loggedUser.getFirebaseToken());

        editor.putString("user_authToken", authToken);

        editor.apply();
    }

    private void updateEmail(String newEmail) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_email", newEmail);

        editor.apply();
    }

    private void updatePersonalInfo(String firstName, String lastName, String address, String phone) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_firstName", firstName);
        editor.putString("user_lastName", lastName);
        editor.putString("user_address", address);
        editor.putString("user_phone", phone);

        editor.apply();
    }
}
