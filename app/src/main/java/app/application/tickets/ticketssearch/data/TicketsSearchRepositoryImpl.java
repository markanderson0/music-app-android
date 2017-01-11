package app.application.tickets.ticketssearch.data;

import java.io.IOException;

import app.application.tickets.ticketmodel.TicketsModel;
import rx.Observable;

/**
 * Implementation of {@link TicketsSearchRepository}
 */
public class TicketsSearchRepositoryImpl implements TicketsSearchRepository {

    private static final String END_DATE_TIME = "2018-01-01T00:00:00Z";
    private static final String API_KEY = "XXXXX";

    TicketsSearchService ticketsSearchService;

    public TicketsSearchRepositoryImpl(TicketsSearchService ticketsSearchService) {
        this.ticketsSearchService = ticketsSearchService;
    }

    /**
     * Returns the tickets found from the query. If the user has no internet connection an
     * IOException is thrown by retrofit
     *
     * @param artist name of the genre to be queried
     * @param page page number
     * @return tickets found from the query
     */
    @Override
    public Observable<TicketsModel> getSearchArtists(String artist, String page) {
        return Observable.defer(() -> ticketsSearchService.getSearchArtists(artist, END_DATE_TIME, API_KEY, page)).retryWhen(
                observable ->
                        observable.flatMap(o -> {
                            if (o instanceof IOException) {
                                return Observable.just(null);
                            }
                            return Observable.error(o);
                        }));
    }
}
