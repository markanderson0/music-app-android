package app.application.artist.shows;

import java.util.ArrayList;

import app.application.artist.shows.data.model.Setlist;
import app.application.base.MvpPresenter;
import app.application.base.MvpView;

/**
 * Specifies the contract between the {@link ArtistShowsFragment} and {@link ArtistShowsPresenter}.
 */
public interface ArtistShowsContract {

    interface View extends MvpView {

        void showShows(ArrayList<Setlist> shows);

        void setTotalPages(int totalPages);

        void setCurrentPage(String page);

        void showError(String message);

        void showLoading();

        void hideLoading();

        void setPages();

        void configurePaginationButtons(int selectedPageNumber);
    }

    interface Presenter extends MvpPresenter<View> {

        void getArtistName(String artistName);

        void getArtist(String artistName, String p);

        void getArtistShows(String page);
    }
}
