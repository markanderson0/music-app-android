package app.application.artist.albums.data.models.artistalbumtracksmodel;

import java.util.ArrayList;
import java.util.List;

public class ArtistAlbumTracksModel {
    public List<Album> albums = new ArrayList<Album>();

    public ArtistAlbumTracksModel() {}

    public ArtistAlbumTracksModel(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}