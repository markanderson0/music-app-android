package app.application.tickets.ticketmodel;

import java.util.ArrayList;
import java.util.List;

public class Embedded {
    public List<Event> events = new ArrayList<Event>();

    public Embedded(List<Event> events) {
        this.events = events;
    }
}
