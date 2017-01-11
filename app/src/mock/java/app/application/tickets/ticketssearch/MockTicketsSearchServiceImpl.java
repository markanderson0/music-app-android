package app.application.tickets.ticketssearch;

import java.util.ArrayList;
import java.util.List;

import app.application.tickets.ticketmodel.Attraction;
import app.application.tickets.ticketmodel.City;
import app.application.tickets.ticketmodel.Classification;
import app.application.tickets.ticketmodel.Country;
import app.application.tickets.ticketmodel.Dates;
import app.application.tickets.ticketmodel.Embedded;
import app.application.tickets.ticketmodel.Embedded_;
import app.application.tickets.ticketmodel.Event;
import app.application.tickets.ticketmodel.Location;
import app.application.tickets.ticketmodel.Page;
import app.application.tickets.ticketmodel.Segment;
import app.application.tickets.ticketmodel.Start;
import app.application.tickets.ticketmodel.State;
import app.application.tickets.ticketmodel.TicketsModel;
import app.application.tickets.ticketmodel.Venue;
import app.application.tickets.ticketssearch.data.TicketsSearchService;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Mock implementation of the {@link TicketsSearchService}
 */
public class MockTicketsSearchServiceImpl implements TicketsSearchService {
    @Override
    public Observable<TicketsModel> getSearchArtists(@Query("keyword") String keyword, @Query("endDateTime") String endDateTime, @Query("apikey") String apikey, @Query("page") String page) {
        return Observable.just(getTicketsModel());
    }

    public TicketsModel getTicketsModel() {
        return new TicketsModel(getEmbedded(), new Page(1));
    }

    private Embedded getEmbedded() {
        return new Embedded(getEvents());
    }

    private List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("artist1", getClassifications(), getEmbedded_(), "url", getDates()));
        return events;
    }

    private List<Classification> getClassifications() {
        List<Classification> classifications = new ArrayList<>();
        classifications.add(new Classification(getSegment()));
        return classifications;
    }

    private Segment getSegment() {
        return new Segment("segment");
    }

    private Embedded_ getEmbedded_() {
        return new Embedded_(getAttractions(), getVenues());
    }

    private List<Attraction> getAttractions() {
        List<Attraction> attractions = new ArrayList<>();
        attractions.add(new Attraction("artist1"));
        return attractions;
    }

    private List<Venue> getVenues() {
        List<Venue> venues = new ArrayList<>();
        venues.add(
            new Venue(
                "venue1",
                getCity("city1"),
                getState("state1", "s1"),
                getCountry("United States", "US"),
                getLocation("1", "1"))
            );
        return venues;
    }

    private City getCity(String name) {
        return new City(name);
    }

    private State getState(String name, String code) {
        return new State(name, code);
    }

    private Country getCountry(String name, String code) {
        return new Country(name, code);
    }

    private Location getLocation(String lat, String lng) {
        return new Location(lat, lng);
    }

    private Dates getDates() {
        return new Dates(getStart("2017-01-01"));
    }

    private Start getStart(String date) {
        return new Start(date);
    }
}
