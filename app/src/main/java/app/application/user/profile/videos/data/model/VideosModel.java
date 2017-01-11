package app.application.user.profile.videos.data.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class VideosModel extends ExpandableGroup<Show> {
    public String id;
    public String artist;
    public String image;
    public List<Show> shows = new ArrayList<Show>();

    public VideosModel(String title, List<Show> items) {
        super(title, items);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

}


