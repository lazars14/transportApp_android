package services;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.transportapp.lazar.transportapp.R;
import com.transportapp.lazar.transportapp.activities.MainActivity;

import org.json.JSONObject;

import java.util.Map;

import helpers.NavigationHelper;

public class RequestService  extends AsyncTask<Void, Void, Void> {

    private int api;
    private String body;
    private String userId;
    private Context context;
    private AppCompatActivity currentActivity;

    private boolean valid;
    private JSONObject response;
    private String errorMessage;
    private int successMessage;
    private String requestId;

    public RequestService(int api, String body, String userId, String requestId, Context context, AppCompatActivity currentActivity) {
        this.api = api;
        this.body = body;
        this.userId = userId;
        this.requestId = requestId;
        this.context = context;
        this.currentActivity = currentActivity;
        this.valid = true;
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
                case 11:
                    url = "/user/" + userId + "/requests";
                    successMessage = R.string.requests_success;
                    response = httpService.makeApiCallJsonBody(true, url, body, "get");

                    errorMessage = getErrorMessage();
                    if(errorMessage != null) valid = false;
                    else {
                        // fillRequests
                    }

                    break;
                case 12:
                    url = "/user/" + userId + "/requests";
                    successMessage = R.string.added_request_success;
                    response = httpService.makeApiCallJsonBody(true, url, body, "post");

                    errorMessage = getErrorMessage();
                    if(errorMessage != null) valid = false;

                    break;
                case 13:
                    url = "/user/" + userId + "/requests/" + requestId + "/accept";
                    Log.v("TALAMBASKO", "Change password user id is " + userId);
                    successMessage = R.string.accepted_request_success;
                    response = httpService.makeApiCallJsonBody(true, url, body, "put");

                    errorMessage = getErrorMessage();
                    if(errorMessage != null) valid = false;

                    break;
                case 14:
                    url = "/user/" + userId + "/requests/" + requestId + "/accept";
                    successMessage = R.string.rejected_request_success;
                    response = httpService.makeApiCallJsonBody(true, url, body, "put");

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
}
