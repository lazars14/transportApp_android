package services;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class DrawPolylineService extends AsyncTask<Void, Void, Void> {

    private GoogleMapsService mapsService;
    private GoogleMap googleMap;
    private List<LatLng> points;
    private PolylineOptions polylineOptions;

    private Document doc;

    public DrawPolylineService(GoogleMap googleMap, List<LatLng> points, PolylineOptions polylineOptions){
        this.mapsService = new GoogleMapsService();
        this.googleMap = googleMap;
        this.points = points;
        this.polylineOptions = polylineOptions;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected Void doInBackground(Void... voids) {
        doc = mapsService.getDocument(points.get(0), points.get(1));

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        List<LatLng> forDrawing = mapsService.getDirection(doc);
        polylineOptions = new PolylineOptions().width(3).color(Color.RED).geodesic(true);
        polylineOptions.addAll(forDrawing);
        googleMap.addPolyline(polylineOptions);
    }
}
