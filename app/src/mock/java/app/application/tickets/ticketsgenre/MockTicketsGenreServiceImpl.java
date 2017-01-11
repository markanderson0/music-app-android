package app.application.tickets.ticketsgenre;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.tickets.TicketsGridModel;
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
import app.application.tickets.ticketsgenre.data.TicketsGenreRepository;
import app.application.tickets.ticketssearch.data.TicketsSearchService;
import rx.Observable;

import static app.application.App.getContext;

/**
 * Mock implementation of the {@link TicketsGenreService}
 *
 * @note As the data for tickets grid is currently loaded locally this class currently implements
 * the repository instead of the service. This mock is currently named as a service to keep
 * a consistent naming pattern across all mocks and for when the service is used in the future.
 */
public class MockTicketsGenreServiceImpl implements TicketsGenreRepository {
    @Override
    public Observable<TicketsModel> getGenreArtists(String keyword, int page) {
        return Observable.just(getTicketsModel());
    }

    @Override
    public Observable<List<TicketsGridModel>> getGridArtists(Context context, String genre) {
        List<TicketsGridModel> tickets = new ArrayList<>();
        tickets.add(new TicketsGridModel("artist1image.jpg", "artist1"));
        tickets.add(new TicketsGridModel("artist2image.jpg", "artist2"));
        return Observable.just(tickets);
    }

    public TicketsModel getTicketsModel() {
        return new TicketsModel(getEmbedded(), new Page(1));
    }

    private Embedded getEmbedded() {
        return new Embedded(getEvents());
    }

    private List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("artist3", getClassifications(), getEmbedded_("artist3"), "url", getDates()));
        events.add(new Event("artist festival", getClassifications(), getEmbedded_("artist festival"), "url", getDates()));
        return events;
    }

    private List<Classification> getClassifications() {
        List<Classification> classifications = new ArrayList<>();
        classifications.add(new Classification(getSegment()));
        return classifications;
    }

    private Segment getSegment() {
        return new Segment("Music");
    }

    private Embedded_ getEmbedded_(String name) {
        return new Embedded_(getAttractions(name), getVenues());
    }

    private List<Attraction> getAttractions(String name) {
        List<Attraction> attractions = new ArrayList<>();
        attractions.add(new Attraction(name));
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
