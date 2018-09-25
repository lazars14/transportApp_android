package services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.transportapp.lazar.transportapp.R;
import com.transportapp.lazar.transportapp.activities.LoginActivity;
import com.transportapp.lazar.transportapp.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import helpers.NavigationHelper;
import model.User;

import static utils.Constants.API_URL;

public class UserService {

    private Context context;
    private RequestQueue queue;
    private AppCompatActivity currentActivity;

    private ObjectMapper mapper = new ObjectMapper();

    public UserService(Context context, AppCompatActivity currentActivity) {
        this.context = context;
        this.currentActivity = currentActivity;
        this.queue = Volley.newRequestQueue(context);
        this.queue.start();
    }

    public void navigateCallback() {
        NavigationHelper navigationHelper = new NavigationHelper(context);
        navigationHelper.navigateTo(MainActivity.class, currentActivity);
    }

    public void login(String email, String password) {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        String url = API_URL + "/user/login";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            JSONObject userJson = response.getJSONObject("user");
                            User user = mapper.readValue(userJson.toString(), User.class);

                            fillUserData(user, token);
                            navigateCallback();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("LOGIN", error.getMessage());
                        Toast.makeText(context, R.string.login_invalid, Toast.LENGTH_LONG).show();
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(request);
    }

    public void register(String email, String password, String firstName, String lastName, String address, String phoneNumber) {
        String firebaseToken = FirebaseInstanceId.getInstance().getToken();

        Map<String, String>  params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("address", address);
        params.put("phone", phoneNumber);
        params.put("firebaseToken", firebaseToken);

        String url = API_URL + "/user/register";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            JSONObject userJson = response.getJSONObject("user");
                            User user = mapper.readValue(userJson.toString(), User.class);

                            fillUserData(user, token);
                            navigateCallback();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }                   }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("REGISTER", error.getMessage());
                        Toast.makeText(context, R.string.register_error, Toast.LENGTH_LONG).show();
                    }
                }
        ){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(request);
    }

    public void updateInfo(String firstName, String lastName, String address, String phoneNumber) {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("address", address);
        params.put("phone", phoneNumber);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = preferences.getString("user_id", "default_userId");

        String url = API_URL + "/user/" + userId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userJson = response.getJSONObject("user");
                            User user = mapper.readValue(userJson.toString(), User.class);

                            updatePersonalInfo(user.getFirstName(), user.getLastName(), user.getAddress(), user.getPhone());
                            navigateCallback();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("UPDATEINFO", error.getMessage());
                        Toast.makeText(context, R.string.updateInfo_invalid, Toast.LENGTH_LONG).show();
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
    }

    public void changeEmail(String oldEmail, String newEmail) {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("oldEmail", oldEmail);
        params.put("newEmail", newEmail);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = preferences.getString("user_id", "default_userId");

        String url = API_URL + "/user/" + userId + "/changeEmail";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userJson = response.getJSONObject("user");
                            User user = mapper.readValue(userJson.toString(), User.class);

                            updateEmail(user.getEmail());
                            navigateCallback();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("UPDATEEMAIL", error.getMessage());
                        Toast.makeText(context, R.string.changeEmail_invalid, Toast.LENGTH_LONG).show();
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
    }

    public void changePassword(String oldPassword, String newPassword, String repeatPassword) {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);
        params.put("repeatPassword", repeatPassword);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = preferences.getString("user_id", "default_userId");

        String url = API_URL + "/user/" + userId + "/changePassword";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userJson = response.getJSONObject("user");
                            User user = mapper.readValue(userJson.toString(), User.class);

                            navigateCallback();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("CHANGEPASSWORD", error.getMessage());
                        Toast.makeText(context, R.string.changePassword_invalid, Toast.LENGTH_LONG).show();
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
