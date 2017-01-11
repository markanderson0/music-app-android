package app.application.artist.shows.data.model;

public class ArtistShowsModel {
    public Setlists setlists;

    public ArtistShowsModel() {}

    public ArtistShowsModel(Setlists setlists) {
        this.setlists = setlists;
    }

    public Setlists getSetlists() {
        return setlists;
    }

    public void setSetlists(Setlists setlists) {
        this.setlists = setlists;
    }
}