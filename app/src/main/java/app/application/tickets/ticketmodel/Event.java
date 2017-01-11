package app.application.tickets.ticketmodel;

import java.util.ArrayList;
import java.util.List;

public class Event {
    public String name;
    public List<Classification> classifications = new ArrayList<Classification>();
    public Embedded_ _embedded;
    public String url;
    public Dates dates;

    public Event(String name, List<Classification> classifications, Embedded_ _embedded, String url, Dates dates) {
        this.name = name;
        this.classifications = classifications;
        this._embedded = _embedded;
        this.url = url;
        this.dates = dates;
    }
}
