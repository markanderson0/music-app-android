package app.application.tickets.toptickets;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.tickets.TicketsGridModel;
import app.application.tickets.toptickets.data.TopTicketsRepository;
import rx.Observable;

/**
 * Mock implementation of the {@link TopTicketsService}
 *
 * @note As the data for top tickets is currently loaded locally this class currently implements
 * the repository instead of the service. This mock is currently named as a service to keep
 * a consistent naming pattern across all mocks and for when the service is used in the future.
 */
public class MockTopTicketsServiceImpl implements TopTicketsRepository {
    @Override
    public Observable<List<TicketsGridModel>> getTopTickets(Context context) {
        List<TicketsGridModel> topTickets = new ArrayList<>();
        topTickets.add(new TicketsGridModel("image", "artist1"));
        return Observable.just(topTickets);
    }
}
