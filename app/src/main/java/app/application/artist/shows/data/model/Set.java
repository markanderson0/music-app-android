package app.application.artist.shows.data.model;

import java.util.ArrayList;
import java.util.List;

public class Set {
        public List<Song> song = new ArrayList<Song>();
        public String encore;

        public Set(){}

    public Set(List<Song> song, String encore) {
        this.song = song;
        this.encore = encore;
    }

    public List<Song> getSong() {
            return song;
        }

        public void setSong(List<Song> song) {
            this.song = song;
        }

        public String getEncore() {
            return encore;
        }

        public void setEncore(String encore) {
            this.encore = encore;
        }
    }
