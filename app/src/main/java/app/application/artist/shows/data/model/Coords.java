package app.application.artist.shows.data.model;

public class Coords {
        public String lat;
        public String _long;

        public Coords(){}

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
