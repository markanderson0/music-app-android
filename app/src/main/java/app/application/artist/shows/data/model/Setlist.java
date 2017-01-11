package app.application.artist.shows.data.model;

public class Setlist {
    public String id;
    public String tour;
    public String eventDate;
    public Venue venue;
    public Artist artist;
    public Sets sets;

    public Setlist() {
    }

    public Setlist(String id, String tour, String eventDate, Venue venue, Artist artist, Sets sets) {
        this.id = id;
        this.tour = tour;
        this.eventDate = eventDate;
        this.venue = venue;
        this.artist = artist;
        this.sets = sets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Sets getSets() {
        return sets;
    }

    public void setSets(Sets sets) {
        this.sets = sets;
    }
}
