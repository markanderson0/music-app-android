package app.application.user.profile.favourites.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Video implements Parcelable {
    public String showId;
    public String date;
    public String venue;
    public String location;
    public String user;
    public String playlistId;
    public String videoId;
    public String file;
    public String image;
    public List<String> songs = new ArrayList<String>();
    public String songsString;
    public String time;
    public Integer views;
    public Integer audio;
    public Integer video;

    public Video(){}

    protected Video(Parcel in) {
        showId = in.readString();
        date = in.readString();
        venue = in.readString();
        location = in.readString();
        user = in.readString();
        playlistId = in.readString();
        videoId = in.readString();
        file = in.readString();
        image = in.readString();
        songs = in.createStringArrayList();
        songsString = in.readString();
        time = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

    public String getSongsString() {
        return songsString;
    }

    public void setSongsString(String songsString) {
        this.songsString = songsString;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getAudio() {
        return audio;
    }

    public void setAudio(Integer audio) {
        this.audio = audio;
    }

    public Integer getVideo() {
        return video;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(showId);
        dest.writeString(date);
        dest.writeString(venue);
        dest.writeString(location);
        dest.writeString(user);
        dest.writeString(playlistId);
        dest.writeString(videoId);
        dest.writeString(file);
        dest.writeString(image);
        dest.writeStringList(songs);
        dest.writeString(songsString);
        dest.writeString(time);
    }
}
