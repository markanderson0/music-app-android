package app.application.artist.shows.data.model;

import com.google.gson.annotations.SerializedName;

public class Cover {
    @SerializedName("@name")
    public String name;
    @SerializedName("@mbid")
    public String mbid;
    @SerializedName("@sortName")
    public String sortName;

    public Cover() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}
