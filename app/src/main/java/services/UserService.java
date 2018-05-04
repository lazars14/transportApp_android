package services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.transportapp.lazar.transportapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.User;

public class UserService {

    private Context context;
    private RequestQueue queue;

    public UserService(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    public User login(String email, String password) {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        String url = "apiUrl";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", AuthService.getAuthToken(context));
                return headers;
            }
        };
        queue.add(request);

//        if successfull fillUserData
        return null;
    }

    public User register(String email, String password, String firstName, String lastName, String address, String phoneNumber) {
        String firebaseToken = FirebaseInstanceId.getInstance().getToken();

        Map<String, String>  params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("address", address);
        params.put("phone", phoneNumber);

        String url = "apiUrl";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", AuthService.getAuthToken(context));
                return headers;
            }
        };
        queue.add(request);

//        if successfull fillUserData

        return null;
    }

    public boolean updateInfo(String firstName, String lastName, String address, String phoneNumber) {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("address", address);
        params.put("phone", phoneNumber);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = preferences.getString("user_id", "default_userId");

        String url = "apiUrl/" + userId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", AuthService.getAuthToken(context));
                return headers;
            }
        };
        queue.add(request);

        return true;
    }

    public boolean changeEmail(String oldEmail, String newEmail) {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("oldEmail", oldEmail);
        params.put("newEmail", newEmail);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = preferences.getString("user_id", "default_userId");

        String url = "apiUrl/" + userId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", AuthService.getAuthToken(context));
                return headers;
            }
        };
        queue.add(request);

        return true;
    }

    public boolean changePassword(String oldPassword, String newPassword, String repeatPassword) {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);
        params.put("repeatPassword", repeatPassword);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = preferences.getString("user_id", "default_userId");

        String url = "apiUrl/" + userId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", AuthService.getAuthToken(context));
                return headers;
            }
        };
        queue.add(request);

        return true;
    }

    public void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_id", "default_id");
        editor.putString("user_email", "default_email");
        editor.putString("user_firstName", "default_firstName");
        editor.putString("user_lastName", "default_lastName");
        editor.putString("user_address", "default_address");
        editor.putString("user_phoneNumber", "default_phoneNumber");
        editor.putString("user_firebaseToken", "default_firebaseToken");

        editor.putString("user_authToken", context.getString(R.string.default_authToken));

        editor.commit();
    }

    private void fillUserData(User loggedUser, String authToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_id", loggedUser.getId());
        editor.putString("user_email", loggedUser.getEmail());
        editor.putString("user_firstName", loggedUser.getFirstName());
        editor.putString("user_lastName", loggedUser.getLastName());
        editor.putString("user_address", loggedUser.getAddress());
        editor.putString("user_phoneNumber", loggedUser.getPhoneNumber());
        editor.putString("user_firebaseToken", loggedUser.getFirebaseToken());

        editor.putString("user_authToken", authToken);

        editor.commit();
    }

    private void updateEmail(String newEmail) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_email", newEmail);

        editor.commit();
    }

    private void updatePersonalInfo(String firstName, String lastName, String address, String phoneNumber) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_firstName", firstName);
        editor.putString("user_lastName", lastName);
        editor.putString("user_address", address);
        editor.putString("user_phoneNumber", phoneNumber);

        editor.commit();
    }


}
