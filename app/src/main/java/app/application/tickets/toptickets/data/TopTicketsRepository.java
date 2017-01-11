package app.application.tickets.toptickets.data;

import android.content.Context;

import java.util.List;

import app.application.tickets.TicketsGridModel;
import rx.Observable;

/**
 * Created by Mark on 29/12/2016.
 */

public interface TopTicketsRepository {
    Observable<List<TicketsGridModel>> getTopTickets(Context context);
}
