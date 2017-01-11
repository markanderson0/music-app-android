package app.application.tickets.ticketssearch;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.base.BasePresenter;
import app.application.tickets.ticketmodel.TicketsModel;
import app.application.tickets.ticketmodel.Venue;
import app.application.tickets.ticketssearch.data.TicketsSearchRepository;
import app.application.tickets.ticketssearch.data.model.TicketsSearchModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link TicketsSearchActivity}), retrieves the data and updates
 * the UI as required.
 */
public class TicketsSearchPresenter extends BasePresenter<TicketsSearchContract.View> implements TicketsSearchContract.Presenter {

    public TicketsSearchRepository ticketsSearchRepository;
    private ArrayList<TicketsSearchModel> tickets;
    private int pageNum;
    private String venueName;
    private String city;
    private String country;
    private String state;

    @Inject
    public TicketsSearchPresenter(TicketsSearchRepository ticketsSearchRepository) {
        this.ticketsSearchRepository = ticketsSearchRepository;
        tickets = new ArrayList<>();
        pageNum = 0;
        venueName = "";
        city = "";
        country = "";
        state = "";
    }

    @Override
    public void getSearchArtists(String artist) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(ticketsSearchRepository.getSearchArtists(artist, String.valueOf(pageNum))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TicketsModel>() {
                    @Override
                    public void onCompleted(){

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(TicketsModel ticketsModel) {
                        getView().hideLoading();
                        parseTableResults(ticketsModel, artist);
                    }
                }));
    }

    @Override
    public void parseTableResults(TicketsModel ticketsModel, String artist) {
        if(ticketsModel.page.totalPages == 0){
            getView().showError("No Tickets Found.");
        } else if (pageNum < ticketsModel.page.totalPages) {
            pageNum++;
            mapTickets(ticketsModel, artist);
            getSearchArtists(artist);
        } else {
            getView().showTableArtists(tickets);
        }
    }

    /**
     * Maps the results from the api response to a list of {@link TicketsSearchModel} for displaying
     * in the UI.
     *
     * For every ticket returned from the response if there are coords for the venue they are
     * extracted from the response and in cases where an artist performs multiple nights in a
     * row at the same venue, if any of the subsequent shows doesnt contain any coords then the
     * coords from the previous night are used. The coords are then used for displaying markers
     * on the map displayed in the {@link TicketsMapActivity}
     *
     * @param searchArtists
     * @param query
     * @return
     */
    @Override
    public ArrayList<TicketsSearchModel> mapTickets(TicketsModel searchArtists, String query) {
        double latitude = 1000;
        double longitude = 1000;;
        if (searchArtists._embedded != null) {
            for (int i = 0; i < searchArtists._embedded.events.size(); i++) {
                String name = searchArtists._embedded.events.get(i).name;
                String url = searchArtists._embedded.events.get(i).url;
                String date = searchArtists._embedded.events.get(i).dates.start.localDate;
                for (int j = 0; j < searchArtists._embedded.events.get(i)._embedded.venues.size(); j++) {
                    getVenueData(searchArtists._embedded.events.get(i)._embedded.venues);
                    if (tickets.size() > 0) {
                        // If performing multi night shows get location of prev night
                        int previousNightIndex = tickets.size() - 1;
                        if (tickets.get(previousNightIndex).venue == searchArtists._embedded.events.get(i)._embedded.venues.get(j).name &&
                                tickets.get(previousNightIndex).city == searchArtists._embedded.events.get(i)._embedded.venues.get(j).city.name &&
                                tickets.get(previousNightIndex).country == searchArtists._embedded.events.get(i)._embedded.venues.get(j).country.countryCode &&
                                (searchArtists._embedded.events.get(i)._embedded.venues.get(j).location != null && tickets.get(previousNightIndex).lat == 1000)) {
                            latitude = tickets.get(previousNightIndex).lat;
                            longitude = tickets.get(previousNightIndex).lng;
                        }
                        // If no location given
                        else if (searchArtists._embedded.events.get(i)._embedded.venues.get(j).location == null) {
                            latitude = 1000;
                            longitude = 1000;
                        }
                        // If first night or only night
                        else {
                            latitude = Double.parseDouble(searchArtists._embedded.events.get(i)._embedded.venues.get(j).location.latitude);
                            longitude = Double.parseDouble(searchArtists._embedded.events.get(i)._embedded.venues.get(j).location.longitude);
                        }
                    }
                    // For first element
                    else if (searchArtists._embedded.events.get(i)._embedded.venues.get(j).location == null) {
                        latitude = 1000;
                        longitude = 1000;
                    } else {
                        latitude = Double.parseDouble(searchArtists._embedded.events.get(i)._embedded.venues.get(j).location.latitude);
                        longitude = Double.parseDouble(searchArtists._embedded.events.get(i)._embedded.venues.get(j).location.longitude);
                    }
                }
                // Check if its a tribute act
                if (!searchArtists._embedded.events.get(i).name.toLowerCase().contains("tribute")
                        && searchArtists._embedded.events.get(i).name.toLowerCase().contains(query.toLowerCase())) {
                    TicketsSearchModel ticket = new TicketsSearchModel();
                    ticket.setName(name);
                    ticket.setUrl(url);
                    ticket.setDate(date);
                    ticket.setVenue(venueName);
                    ticket.setCity(city);
                    ticket.setState(state);
                    ticket.setCountry(country);
                    ticket.setLat(latitude);
                    ticket.setLng(longitude);
                    tickets.add(ticket);
                }
            }
        }
        return tickets;
    }

    /**
     * Extracts the name, city, country and state of the venue
     *
     * @param venue list of data regarding the current venue
     */
    @Override
    public void getVenueData(List<Venue> venue) {
        if (venue.get(0).name != null) {
            venueName = venue.get(0).name;
        }
        else {
            venueName = "";
        }
        if (venue.get(0).city != null) {
            city = venue.get(0).city.name;
        }
        else {
            city = "";
        }
        if (venue.get(0).country != null) {
            country = venue.get(0).country.countryCode;
        }
        else {
            country = "";
        }
        if (venue.get(0).state != null) {
            state = venue.get(0).state.stateCode;
        }
        else {
            state = "";
        }
    }
}
