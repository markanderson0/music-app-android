package app.application.user.upload.uploadvideo;

import java.util.List;

import app.application.artist.shows.data.model.ArtistShowsModel;
import app.application.base.MvpPresenter;
import app.application.base.MvpView;

/**
 * Specifies the contract between the {@link UploadVideoFragment} and {@link UploadVideoPresenter}.
 */
public interface UploadVideoContract {

    interface View extends MvpView {

        void getArtist(String artistName);

        void setMbid(String mbid);

        void getEvent(String eventYear, String page);

        void setEvents(List<String> eventIds, List<String> events);

        void setSongs(List<String> songs, List<String> selectedSongs);

        void initializeSelection();
    }

    interface Presenter extends MvpPresenter<View> {

        void getArtistName(String artistName);

        void getArtist(String artistName, String page);

        void getEvent(String eventYear, String pageNumber);

        void getSongs(String eventId);

        void parseEvents(ArtistShowsModel searchEvent);

        void parseSongs(ArtistShowsModel searchSongs);

        void initializeSelection();
    }
}
