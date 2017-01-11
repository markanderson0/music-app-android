package app.application.user.profile.videos.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class Show extends ExpandableGroup<Playlist> implements Parcelable {
    public String id;
    public String date;
    public String venue;
    public String location;
    public List<Playlist> videos = new ArrayList<Playlist>();

    public Show(String title, List<Playlist> items) {
        super(title, items);
    }

    protected Show(Parcel in) {
        super(in);
        id = in.readString();
        date = in.readString();
        venue = in.readString();
        location = in.readString();
    }

    public final Creator<Show> CREATOR = new Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        @Override
        public Show[] newArray(int size) {
            return new Show[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Playlist> getPlaylist() {
        return videos;
    }

    public void setPlaylist(List<Playlist> videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(date);
        dest.writeString(venue);
        dest.writeString(location);
    }
}
