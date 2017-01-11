package app.application.artist.shows.data.model;

public class Artist {
        public String name;
        public String sortName;
        public String mbid;

        public Artist(){}

    public Artist(String name, String sortName, String mbid) {
        this.name = name;
        this.sortName = sortName;
        this.mbid = mbid;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public String getMbid() {
            return mbid;
        }

        public void setMbid(String mbid) {
            this.mbid = mbid;
        }
    }
