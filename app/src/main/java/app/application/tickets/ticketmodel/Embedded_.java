package app.application.tickets.ticketmodel;

import java.util.ArrayList;
import java.util.List;

public class Embedded_ {
    public List<Attraction> attractions = new ArrayList<Attraction>();
    public List<Venue> venues = new ArrayList<Venue>();

    public Embedded_(List<Attraction> attractions, List<Venue> venues) {
        this.attractions = attractions;
        this.venues = venues;
    }
}
