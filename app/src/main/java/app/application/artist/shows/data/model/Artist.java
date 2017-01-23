package app.application.artist.shows.data.model;

import com.google.gson.annotations.SerializedName;

public class Artist {
    @SerializedName("@name")
    public String name;
    @SerializedName("@sortName")
    public String sortName;
    @SerializedName("@mbid")
    public String mbid;

    public Artist() {
    }

    public Artist(String name, String sortName, String mbid) {
        this.name = name;
        this.sortName = sortName;
        this.mbid = mbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }
}
