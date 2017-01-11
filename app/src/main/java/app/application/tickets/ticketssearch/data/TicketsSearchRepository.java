package app.application.tickets.ticketssearch.data;

import app.application.tickets.ticketmodel.TicketsModel;
import rx.Observable;

/**
 * Repository to search for tickets
 */
public interface TicketsSearchRepository {
    Observable<TicketsModel> getSearchArtists(String artist, String page);
}
