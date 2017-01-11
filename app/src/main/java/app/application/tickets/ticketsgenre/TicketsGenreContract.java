package app.application.tickets.ticketsgenre;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.shared.GridItem;
import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.tickets.TicketsGridModel;
import app.application.tickets.ticketmodel.TicketsModel;

/**
 * Specifies the contract between the {@link TicketsGenreFragment} and {@link TicketsGenrePresenter}.
 */
public interface TicketsGenreContract {

    interface View extends MvpView {

        void showGridArtists(ArrayList<GridItem> gridData);

        void showTableArtists(ArrayList<String> tableArtists);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getGridArtists(Context context, String genre);

        void getGenreArtists(String keyword);

        ArrayList<GridItem> getGridItems(List<TicketsGridModel> artists);

        ArrayList<String> mapTableResults(TicketsModel genreArtists, String mGenreSearch);
    }
}
