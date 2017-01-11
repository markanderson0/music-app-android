package app.application.tickets.ticketsgenre.data;

import android.content.Context;

import java.util.List;

import app.application.tickets.TicketsGridModel;
import app.application.tickets.ticketmodel.TicketsModel;
import rx.Observable;

/**
 * Repository to find genre specific tickets
 */
public interface TicketsGenreRepository {
    Observable<TicketsModel> getGenreArtists(String genre, int page);
    Observable<List<TicketsGridModel>> getGridArtists(Context context, String genre);
}
