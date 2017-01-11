package app.application.tickets.ticketsgenre.data;

import app.application.tickets.ticketmodel.TicketsModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Service to search for tickets via the ticketmaster api
 */
public interface TicketsGenreService {
    @GET("/discovery/v2/events.json")
    Observable<TicketsModel> getGenreArtists(
            @Query("keyword") String genre,
            @Query("endDateTime") String endDateTime,
            @Query("apikey") String apikey,
            @Query("page") int page
    );
}
