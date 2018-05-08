package services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;
import com.transportapp.lazar.transportapp.R;
import com.transportapp.lazar.transportapp.activities.AddRequestActivity;
import com.transportapp.lazar.transportapp.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helpers.NavigationHelper;
import model.Location;
import model.Request;

public class RequestService {

    private AppCompatActivity currentActivity;
    private FragmentActivity currentFragment;

    private Context context;
    private RequestQueue queue;

    private ObjectMapper mapper = new ObjectMapper();

    public RequestService(Context context, AppCompatActivity currentActivity, FragmentActivity currentFragment) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.queue.start();
        this.currentActivity = currentActivity;
        this.currentFragment = currentFragment;
    }

    public void navigateCallback() {
        Intent intent = new Intent(context, MainActivity.class);
        if(currentActivity != null) currentActivity.startActivity(intent);
        else currentFragment.startActivity(intent);
    }

    public Request[] getRequests(final List<Request> requests) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = preferences.getString("user_id", "default_userId");

        /* add userId to apiUrl */
        String url = "apiUrl/" + userId;
        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Request> results = Arrays.asList(mapper.readValue(response.toString(), Request[].class));
                            for (Request r : results) {
                                requests.add(r);
                            }
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
                        Toast.makeText(context, R.string.getRequests_invalid, Toast.LENGTH_LONG).show();
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

        return null;
    }

    public Request addRequest(LatLng startLocation, LatLng endLocation) {
        JSONObject params = new JSONObject();
        JSONObject startLocationJson = new JSONObject();
        JSONObject endLocationJson = new JSONObject();
        try {
            startLocationJson.put("lat", startLocation.latitude);
            startLocationJson.put("lng", startLocation.longitude);
            endLocationJson.put("lat", endLocation.latitude);
            endLocationJson.put("lng", endLocation.longitude);
            params.put("startLocation", startLocationJson);
            params.put("endLocation", endLocationJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "apiUrl";
        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.POST, url,
                params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        navigateCallback();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(context, R.string.addRequest_invalid, Toast.LENGTH_LONG).show();
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

        return null;

    }

    public Request acceptRequest(String requestId) {
        String url = "apiUrl/" + requestId;
        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.PUT, url,
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        navigateCallback();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(context, R.string.acceptRequest_invalid, Toast.LENGTH_LONG).show();
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

        return null;

    }

    public Request rejectRequest(String requestId) {
        String url = "apiUrl/" + requestId;
        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.PUT, url,
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        navigateCallback();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(context, R.string.rejectRequest_invalid, Toast.LENGTH_LONG).show();
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

        return null;

    }
}
