package app.application.user.upload.uploadvideo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import app.application.RxSchedulersOverrideRule;
import app.application.artist.shows.MockArtistShowsServiceImpl;
import app.application.artist.shows.data.ArtistShowsRepository;
import app.application.search.MockSearchServiceImpl;
import app.application.search.data.SearchRepository;
import app.application.user.upload.uploadvideo.data.UploadVideoRepository;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link UploadVideoPresenter}
 */
public class UploadVideoPresenterTest {
    private static final String ARTIST = "artist1";
    private static final String TYPE = "artist";
    private static final String LIMIT = "20";
    private static final String PAGE = "1";
    private static final String MBID = "mbid";
    private static final String YEAR = "2017";
    private static final String EVENT_ID = "eventId";
    private static final List<String> EVENT_IDS = new ArrayList<>();
    private static final List<String> EVENTS = new ArrayList<>();
    private static final String EVENT1_ID = "123";
    private static final String EVENT2_ID = "234";
    private static final String EVENT1 = "01-01-2017, venue";
    private static final String EVENT2 = "02-01-2017, venue";
    private static final List<String> SONGS = new ArrayList<>();
    private static final List<String> SELECTED_SONGS = new ArrayList<>();
    private static final String SONG1 = "song1";
    private static final String SONG2 = "song2";
    private static final String SONG3 = "song3";

    @Mock
    UploadVideoRepository uploadVideoRepository;
    @Mock
    SearchRepository searchRepository;
    @Mock
    ArtistShowsRepository artistShowsRepository;
    @Mock
    UploadVideoContract.View view;

    UploadVideoPresenter uploadVideoPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uploadVideoPresenter = new UploadVideoPresenter(uploadVideoRepository, searchRepository, artistShowsRepository);
        uploadVideoPresenter.attachView(view);
        EVENT_IDS.add(EVENT1_ID);
        EVENT_IDS.add(EVENT2_ID);
        EVENTS.add(EVENT1);
        EVENTS.add(EVENT2);
        SONGS.add(SONG1);
        SONGS.add(SONG2);
        SONGS.add(SONG3);
        SELECTED_SONGS.add("");
        SELECTED_SONGS.add("");
        SELECTED_SONGS.add("");
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void artistShows_ValidArtist_ReturnsResults() {
        when(searchRepository.searchArtists(anyString()))
                .thenReturn(new MockSearchServiceImpl().searchArtists(ARTIST, TYPE, LIMIT));
        when(artistShowsRepository.getShows(anyString(), anyString()))
                .thenReturn(new MockArtistShowsServiceImpl().getArtist(ARTIST, PAGE));
        when(uploadVideoRepository.getEvent(anyString(),anyString(), anyString()))
                .thenReturn(new MockUploadVideoServiceImpl().getEvent(MBID, YEAR, PAGE));
        when(uploadVideoRepository.getSongs(anyString()))
                .thenReturn(new MockUploadVideoServiceImpl().getSongs(EVENT_ID));
        uploadVideoPresenter.getArtistName(ARTIST);
        uploadVideoPresenter.getArtist(ARTIST, PAGE);
        uploadVideoPresenter.getEvent(MBID, YEAR);
        uploadVideoPresenter.getSongs(EVENT_ID);

        verify(view).getArtist(ARTIST);
        verify(view).setMbid(MBID);
        verify(view).setEvents(EVENT_IDS, EVENTS);
        verify(view).setSongs(SONGS, SELECTED_SONGS);
    }
}
