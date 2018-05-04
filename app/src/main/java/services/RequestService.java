package services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.Location;
import model.Request;

public class RequestService {

    private Context context;
    private RequestQueue queue;

    public RequestService(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    public Request[] getUserRequests() {
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
        );
        queue.add(request);

        return null;
    }

    public Request addRequest(Location startLocation, Location endLocation) {
        JSONObject params = new JSONObject();
//        set params
//        to do

        String url = "apiUrl";
        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.POST, url,
                params,
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
        );
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
        );
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
        );
        queue.add(request);

        return null;

    }
}
