package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

    @JsonProperty("lat")
    private float lat;
    @JsonProperty("lng")
    private float lng;

    public Location(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @JsonProperty("lat")
    public float getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(float lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public float getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(float lng) {
        this.lng = lng;
    }
}
