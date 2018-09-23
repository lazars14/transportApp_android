package services;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.transportapp.lazar.transportapp.R;
import com.transportapp.lazar.transportapp.activities.AddRequestActivity;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import helpers.DialogHelper;

public class DrawPolylineService extends AsyncTask<Void, Void, Void> {

    private GoogleMapsService mapsService;
    private GoogleMap googleMap;
    private List<LatLng> points;
    private PolylineOptions polylineOptions;
    private View loadBackground;
    private ProgressBar progressBar;
    private Context context;

    private Document doc;
    private double distance;
    private int duration;

    public DrawPolylineService(GoogleMap googleMap, List<LatLng> points,
                               PolylineOptions polylineOptions, View loadBackground,
                               ProgressBar progressBar, Context context){
        this.mapsService = new GoogleMapsService();
        this.googleMap = googleMap;
        this.points = points;
        this.polylineOptions = polylineOptions;
        this.loadBackground = loadBackground;
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        loadBackground.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        doc = mapsService.getDocument(points.get(0), points.get(1));

        distance = mapsService.getDistanceValue(doc);
        duration = mapsService.getDurationValue(doc);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        boolean valid = true;
        String bestRouteInfo = "";
        if(duration == -1) {
            // bad request
            bestRouteInfo = "Bad Request! Try again and put the markers on the road";
            valid = false;
        } else {
            String distanceString = String.valueOf(Math.round((distance) / 1000));
            String durationString = String.valueOf(duration);
            if(duration > 60) {
                int hours = duration / 60;
                int minutes = duration - hours * 60;
                durationString = String.valueOf(hours) + "h " + String.valueOf(minutes) + "min";
            } else durationString += "min";

            bestRouteInfo = "Optimal Distance: " + distanceString + "km\n" + "Optimal Time: " + durationString;
        }

        DialogHelper.showDialogInfo(context, "Optimal Route Info", bestRouteInfo);

        if(!valid) {
            googleMap.clear();
            points.clear();
        } else {
            List<LatLng> forDrawing = mapsService.getDirection(doc);
            polylineOptions = new PolylineOptions().width(3).color(Color.RED).geodesic(true);
            polylineOptions.addAll(forDrawing);
            googleMap.addPolyline(polylineOptions);
        }

        loadBackground.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }
}
