package app.application.artist.shows.data.model;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("@name")
    public String name;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
