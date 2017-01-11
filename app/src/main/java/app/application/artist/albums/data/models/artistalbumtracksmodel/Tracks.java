package app.application.artist.albums.data.models.artistalbumtracksmodel;

import java.util.ArrayList;
import java.util.List;

public class Tracks {
    public List<TracksItem> items = new ArrayList<TracksItem>();

    public Tracks(List<TracksItem> items) {
        this.items = items;
    }

    public List<TracksItem> getItems() {
        return items;
    }

    public void setItems(List<TracksItem> items) {
        this.items = items;
    }
}
