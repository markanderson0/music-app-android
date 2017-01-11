package app.application.tickets.toptickets;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.shared.GridItem;
import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.tickets.TicketsGridModel;

/**
 * Specifies the contract between the {@link TopTicketsFragment} and {@link TopTicketsPresenter}.
 */
public interface TopTicketsContract {

    interface View extends MvpView {

        void showTopTickets(ArrayList<GridItem> gridData);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getArtists(Context context);

        ArrayList<GridItem> getGridData(List<TicketsGridModel> artists);
    }
}
