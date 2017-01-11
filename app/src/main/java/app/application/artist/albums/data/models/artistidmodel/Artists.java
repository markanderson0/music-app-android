package app.application.artist.albums.data.models.artistidmodel;

import java.util.ArrayList;
import java.util.List;

public class Artists {
    public List<Item> items = new ArrayList<Item>();

    public Artists(List<Item> items) {
        this.items = items;
    }
}
