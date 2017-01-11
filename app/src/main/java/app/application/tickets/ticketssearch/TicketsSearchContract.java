package app.application.tickets.ticketssearch;

import java.util.ArrayList;
import java.util.List;

import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.tickets.ticketmodel.TicketsModel;
import app.application.tickets.ticketmodel.Venue;
import app.application.tickets.ticketssearch.data.model.TicketsSearchModel;

/**
 * Specifies the contract between the {@link TicketsSearchActivity} and {@link TicketsSearchPresenter}.
 */
public interface TicketsSearchContract {

    interface View extends MvpView {

        void showTableArtists(ArrayList<TicketsSearchModel> tickets);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getSearchArtists(String keyword);

        void parseTableResults(TicketsModel ticketsModel, String artist);

        ArrayList<TicketsSearchModel> mapTickets(TicketsModel searchArtists, String mSearch);

        void getVenueData(List<Venue> venue);
    }

}
