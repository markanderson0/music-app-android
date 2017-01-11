package app.application.artist.shows.data.model;

public class Song {
        public String name;
        public String tape;
        public Cover cover;
        public With with;

        public Song(){}

    public Song(String name, With with, Cover cover, String tape) {
        this.name = name;
        this.with = with;
        this.cover = cover;
        this.tape = tape;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTape() {
            return tape;
        }

        public void setTape(String tape) {
            this.tape = tape;
        }

        public Cover getCover() {
            return cover;
        }

        public void setCover(Cover cover) {
            this.cover = cover;
        }

        public With getWith() {
            return with;
        }

        public void setWith(With with) {
            this.with = with;
        }
    }
