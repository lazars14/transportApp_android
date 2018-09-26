package services;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static utils.Constants.API_URL;

public class HttpService {

    private Context context;

    public HttpService(Context context) {
        this.context = context;
    }

    public JSONObject makeApiCall(boolean needsToken, String url, Map<String, String> body, String method) {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");

        if(needsToken) headers.put("x-access-token", AuthService.getAuthToken(context));

        Headers headersObj = Headers.of(headers);

        RequestBody requestBody = null;
        if(body != null) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (String key: body.keySet()) {
                bodyBuilder.add(key, body.get(key));
            }

            requestBody = bodyBuilder.build();
        }

        OkHttpClient client = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(API_URL + url).headers(headersObj);

        switch (method) {
            case "post":
                requestBuilder.post(requestBody);
                break;
            case "put":
                requestBuilder.put(requestBody);
                break;
            case "get":
                requestBuilder.get();
                break;
            case "delete":
                requestBuilder.delete();
                break;
        }

        Request request = requestBuilder.build();

        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();

            return new JSONObject(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
