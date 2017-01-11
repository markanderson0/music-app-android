package app.application.artist.albums.data.models.artistalbumtracksmodel;

public class TracksItem {
    public String name;
    public Integer track_number;

    public TracksItem(String name, Integer track_number) {
        this.name = name;
        this.track_number = track_number;
    }

    public Integer getTrackNumber() {
        return track_number;
    }

    public void setTrackNumber(Integer track_number) {
        this.track_number = track_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
