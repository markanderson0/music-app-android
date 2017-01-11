package app.application.tickets.ticketssearch.data;

import app.application.tickets.ticketmodel.TicketsModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Service to search for tickets via the ticketmaster api
 */
public interface TicketsSearchService {
    @GET("/discovery/v2/events.json")
    Observable<TicketsModel> getSearchArtists(
            @Query("keyword") String keyword,
            @Query("endDateTime") String endDateTime,
            @Query("apikey") String apikey,
            @Query("page") String page
    );
}
