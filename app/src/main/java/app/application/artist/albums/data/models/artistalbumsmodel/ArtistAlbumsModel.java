package app.application.artist.albums.data.models.artistalbumsmodel;

import java.util.ArrayList;
import java.util.List;

public class ArtistAlbumsModel {
    public List<AlbumItem> items = new ArrayList<AlbumItem>();

    public ArtistAlbumsModel() {}

    public ArtistAlbumsModel(List<AlbumItem> items) {
        this.items = items;
    }
}