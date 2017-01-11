package app.application.user.profile.videos.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Videos implements Parcelable {
    public String id;
    public String file;
    public String image;
    public List<String> songs = new ArrayList<String>();
    public String songsString;
    public String time;
    public Integer views;
    public Integer audio;
    public Integer video;

    protected Videos(Parcel in) {
        id = in.readString();
        file = in.readString();
        image = in.readString();
        songs = in.createStringArrayList();
        songsString = in.readString();
        time = in.readString();
        views = in.readInt();
        audio = in.readInt();
        video = in.readInt();
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };

    public Videos(String id, String file, String image, List<String> songs, String songsString, String time, Integer views, Integer audio, Integer video) {
        this.id = id;
        this.file = file;
        this.image = image;
        this.songs = songs;
        this.songsString = songsString;
        this.time = time;
        this.views = views;
        this.audio = audio;
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(file);
        parcel.writeString(image);
        parcel.writeStringList(songs);
        parcel.writeString(songsString);
        parcel.writeString(time);
        parcel.writeInt(views);
        parcel.writeInt(audio);
        parcel.writeInt(video);
    }
}
