package app.application.user.upload.uploadvideo;

import java.util.ArrayList;

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

        void setEvents(ArrayList<String> eventIds, ArrayList<String> events);

        void setSongs(ArrayList<String> songs, ArrayList<String> selectedSongs);

        void initializeSelection();
    }

    interface Presenter extends MvpPresenter<View> {

        void getArtistName(String artistName);

        void getArtist(String artistName, String page);

        void getEvent(String eventYear, String pageNumber);

        void getSongs(String eventId);

        void parseEvents(Object searchEvent);

        void parseSongs(Object searchSongs);

        void initializeSelection();
    }
}
