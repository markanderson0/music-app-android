package app.application.user.profile.videos.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements Parcelable {
    public String user;
    public String playlistId;
    public List<Videos> video = new ArrayList<Videos>();

    public Playlist(String user, String playlistId, List<Videos> video) {
        this.user = user;
        this.playlistId = playlistId;
        this.video = video;
    }

    protected Playlist(Parcel in) {
        user = in.readString();
        playlistId = in.readString();
    }

    public final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public List<Videos> getVideos() {
        return video;
    }

    public void setVideos(List<Videos> video) {
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(playlistId);
    }
}
