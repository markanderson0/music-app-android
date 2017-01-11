package app.application.tickets.toptickets.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.tickets.TicketsGridModel;
import rx.Observable;

/**
 * Implementation of {@link TopTicketsRepository}
 */
public class TopTicketsRepositoryImpl implements TopTicketsRepository {

    /**
     * Loads mock data from assets containing the most popular tickets
     *
     * @param context Android context to access the assets
     * @return an observable containing a list of {@link TicketsGridModel}s
     */
    @Override
    public Observable<List<TicketsGridModel>> getTopTickets(Context context) {
        List<TicketsGridModel> tickets = new ArrayList<TicketsGridModel>();
        try {
            InputStream inputStream = context.getAssets().open("mock-data/tickets/top-tickets.json");
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
