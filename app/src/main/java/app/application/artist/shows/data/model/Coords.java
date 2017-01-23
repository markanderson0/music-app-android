package app.application.artist.shows.data.model;

import com.google.gson.annotations.SerializedName;

public class Coords {
    @SerializedName("@lat")
    public String lat;
    @SerializedName("@long")
    public String _long;

    public Coords() {
    }

    public Coords(String lat, String _long) {
        this.lat = lat;
        this._long = _long;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
    }
}
