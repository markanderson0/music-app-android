package app.application.artist.shows.data.model;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("@name")
    public String name;
    @SerializedName("@stateCode")
    public String stateCode;
    public Country country;
    public Coords coords;

    public City() {
    }

    public City(String name, String stateCode, Country country, Coords coords) {
        this.name = name;
        this.stateCode = stateCode;
        this.country = country;
        this.coords = coords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }
}
