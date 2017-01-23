package app.application.artist.shows.data.model;

import com.google.gson.annotations.SerializedName;

public class Venue {
    @SerializedName("@name")
    public String name;
    public City city;

    public Venue(String name, City city) {
        this.name = name;
        this.city = city;
    }

    public Venue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
