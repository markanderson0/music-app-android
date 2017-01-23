package app.application.user.upload.uploadvideo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.artist.shows.ArtistShowsUtil;
import app.application.artist.shows.data.ArtistShowsRepository;
import app.application.artist.shows.data.model.ArtistShowsModel;
import app.application.artist.shows.data.model.Setlist;
import app.application.artist.shows.data.model.Song;
import app.application.base.BasePresenter;
import app.application.search.data.SearchRepository;
import app.application.search.data.model.SearchModel;
import app.application.user.upload.uploadvideo.data.UploadVideoRepository;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link UploadVideoFragment}), retrieves the data and updates
 * the UI as required.
 */
public class UploadVideoPresenter extends BasePresenter<UploadVideoContract.View> implements UploadVideoContract.Presenter {

    public UploadVideoRepository uploadVideoRepository;
    public SearchRepository searchRepository;
    public ArtistShowsRepository artistShowsRepository;

    private String mbid;
    private String eventYear;
    private int pageNum;
    private ArrayList<String> eventIds;
    private ArrayList<String> events;
    private ArrayList<String> songs;
    private ArrayList<String> selectedSongs;

    @Inject
    public UploadVideoPresenter(UploadVideoRepository uploadVideoRepository, SearchRepository searchRepository, ArtistShowsRepository artistShowsRepository) {
        this.uploadVideoRepository = uploadVideoRepository;
        this.searchRepository = searchRepository;
        this.artistShowsRepository = artistShowsRepository;
        mbid = "";
        eventYear = "";
        pageNum = 1;
        eventIds = new ArrayList<>();
        events = new ArrayList<>();
        songs = new ArrayList<>();
        selectedSongs = new ArrayList<>();
    }

    @Override
    public void getArtistName(String artistName) {
        addSubscription(searchRepository.searchArtists(artistName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchModel searchModel) {
                        if (searchModel.getArtists().getItems().size() > 0) {
                            String name = searchModel.getArtists().getItems().get(0).getName();
                            getView().getArtist(name);
                        }
                    }
                }));
    }

    @Override
    public void getArtist(String artistName, String page) {
        addSubscription(artistShowsRepository.getShows(artistName, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArtistShowsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArtistShowsModel searchArtistShows) {
                        mbid = ArtistShowsUtil.getMbid(searchArtistShows, artistName);
                        getView().setMbid(mbid);
                        if (!mbid.equals("") && eventYear.length() == 4) {
                            getView().getEvent(eventYear, page);
                        }
                    }
                }));
    }

    @Override
    public void getEvent(String eventYear, String pageNumber) {
        this.eventYear = eventYear;
        addSubscription(uploadVideoRepository.getEvent(mbid, eventYear, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArtistShowsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArtistShowsModel searchEvent) {
                        parseEvents(searchEvent);
                    }
                }));
    }

    @Override
    public void getSongs(String eventId) {
        addSubscription(uploadVideoRepository.getSongs(eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArtistShowsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArtistShowsModel searchSongs) {
                        parseSongs(searchSongs);
                    }
                }));
    }

    /**
     * Maps the searchEvents Object returned from the api call to two lists of strings with one
     * containing the name of the venue and the date that the event took place and the other
     * containing a list of the event ids. Only 20 events are returned from a given api call so to
     * get all events that took place in the one year multiple calls to getEvent is often required.
     * When all events have been returned the view is updated with the full list of events and
     * event ids.
     *
     * @param searchEvent events returned from the api call
     */
    @Override
    public void parseEvents(ArtistShowsModel searchEvent) {
        int totalPages = Integer.parseInt(searchEvent.getSetlists().getTotal()) / 20;
        for (int i = 0; i < searchEvent.getSetlists().getSetlist().size(); i++) {
            Setlist setlist = searchEvent.getSetlists().getSetlist().get(i);
            if (!eventIds.contains(setlist.getId())) {
                if (setlist.getVenue() != null) {
                    eventIds.add(setlist.getId());
                    events.add(setlist.getEventDate() + ", " + setlist.getVenue().getName());
                }
            }
        }
        if (pageNum <= totalPages) {
            pageNum++;
            getEvent(eventYear, String.valueOf(pageNum));
        } else {
            getView().setEvents(eventIds, events);
        }
    }

    /**
     * Returns a list of songs that where on the setlist of the chosen event.
     *
     * @param searchSongs songs that where played at an event.
     */
    @Override
    public void parseSongs(ArtistShowsModel searchSongs) {
        List<Song> setlist = searchSongs.getSetlists().getSetlist().get(0).getSets().getSet().get(0).getSong();
        songs = new ArrayList<>();
        selectedSongs = new ArrayList<>();
        for (Song song : setlist) {
            songs.add(song.getName());
            selectedSongs.add("");
        }
        getView().setSongs(songs, selectedSongs);
    }

    /**
     * Resets the variables associated with events and songs in the presenter and also updates
     * the view with the default values so it displayed blank fields for events and songs.
     */
    @Override
    public void initializeSelection() {
        pageNum = 1;
        events = new ArrayList<>();
        eventIds = new ArrayList<>();
        songs = new ArrayList<>();
        selectedSongs = new ArrayList<>();
        getView().setEvents(eventIds, events);
        getView().setSongs(songs, selectedSongs);
    }
}
