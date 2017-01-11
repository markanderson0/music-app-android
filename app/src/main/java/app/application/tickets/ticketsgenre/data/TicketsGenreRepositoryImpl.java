package app.application.tickets.ticketsgenre.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.tickets.TicketsGridModel;
import app.application.tickets.ticketmodel.TicketsModel;
import rx.Observable;

/**
 * Implementation of {@link TicketsGenreRepository}
 */
public class TicketsGenreRepositoryImpl implements TicketsGenreRepository {

    private static final String END_DATE_TIME = "2018-01-01T00:00:00Z";
    private static final String API_KEY = "XXXXX";

    TicketsGenreService ticketsGenreService;

    public TicketsGenreRepositoryImpl(TicketsGenreService ticketsGenreService) {
        this.ticketsGenreService = ticketsGenreService;
    }

    /**
     * Returns the artists found from the query. If the user has no internet connection an
     * IOException is thrown by retrofit
     *
     * @param genre name of the genre to be queried
     * @param page
     * @return tickets found from the query
     */
    @Override
    public Observable<TicketsModel> getGenreArtists(String genre, int page) {
        return Observable.defer(() -> ticketsGenreService.getGenreArtists(genre, END_DATE_TIME, API_KEY, page)).retryWhen(
                observable ->
                        observable.flatMap(o -> {
                            if (o instanceof IOException) {
                                return Observable.just(null);
                            }
                            return Observable.error(o);
                        }));
    }

    /**
     * Loads mock data from assets containing the most popular tickets from the selected genre
     *
     * @param context Android context to access the assets
     * @param genre location in assets of the required data
     * @return an observable containing a list of {@link TicketsGridModel}s
     */
    @Override
    public Observable<List<TicketsGridModel>> getGridArtists(Context context, String genre) {
        List<TicketsGridModel> tickets = new ArrayList<TicketsGridModel>();
        try {
            InputStream inputStream = context.getAssets().open("mock-data/tickets/"+genre+".json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                TicketsGridModel ticket = gson.fromJson(reader, TicketsGridModel.class);
                tickets.add(ticket);
            }
            reader.endArray();
            reader.close();
            return Observable.just(tickets);
        } catch (IOException e) {
            throw new RuntimeException("This should never happen", e);
        }
    }
}
