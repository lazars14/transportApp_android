package services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transportapp.lazar.transportapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Location;
import model.Request;

public class RequestService {

    private Context context;
    private RequestQueue queue;

    private ObjectMapper mapper = new ObjectMapper();

    public RequestService(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.queue.start();
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

    public Request addRequest(Location startLocation, Location endLocation) {
        JSONObject params = new JSONObject();
        JSONObject startLocationJson = new JSONObject();
        JSONObject endLocationJson = new JSONObject();
        try {
            startLocationJson.put("lat", startLocation.getLat());
            startLocationJson.put("lng", startLocation.getLng());
            endLocationJson.put("lat", endLocation.getLat());
            endLocationJson.put("lng", endLocation.getLng());
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
                        // response

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
                        // response

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
                        // response

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
