package app.application.artist.albums.data.models.artistalbumtracksmodel;

import java.util.ArrayList;
import java.util.List;

public class Album {
    public String id;
    public String name;
    public String release_date;
    public Tracks tracks;

    public Album(String id, String name, String release_date, Tracks tracks, List<AlbumImage> images) {
        this.id = id;
        this.name = name;
        this.release_date = release_date;
        this.tracks = tracks;
        this.images = images;
    }

    public List<AlbumImage> getImages() {
        return images;
    }

    public void setImages(List<AlbumImage> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public List<AlbumImage> images = new ArrayList<AlbumImage>();
}
