package app.application.user.profile.favourites.data.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class FavouritesModel extends ExpandableGroup<Video> {
    public String id;
    public String artistName;
    public String image;
    public List<Video> videos = new ArrayList<Video>();

    public FavouritesModel(String title, List<Video> items) {
        super(title, items);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}

