package app.application.artist.shows;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.RxSchedulersOverrideRule;
import app.application.artist.shows.data.ArtistShowsRepository;
import app.application.base.BasePresenter;
import app.application.search.MockSearchServiceImpl;
import app.application.search.data.SearchRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test class to validate the behaviour of the {@link ArtistShowsPresenter}
 */
public class ArtistShowsPresenterTest {

    private static final String ARTIST = "artist1";
    private static final String TYPE = "artist";
    private static final String LIMIT = "20";
    private static final String PAGE = "1";
    private static final String MBID = "mbid";

    @Mock
    ArtistShowsRepository artistShowsRepository;
    @Mock
    SearchRepository searchRepository;
    @Mock
    ArtistShowsContract.View view;

    ArtistShowsPresenter artistShowsPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        artistShowsPresenter = new ArtistShowsPresenter(artistShowsRepository, searchRepository);
        artistShowsPresenter.attachView(view);
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
        when(artistShowsRepository.getArtistShows(anyString()))
                .thenReturn(new MockArtistShowsServiceImpl().getArtistShows(MBID, PAGE));
        artistShowsPresenter.getArtistName(ARTIST);
        artistShowsPresenter.getArtist(ARTIST, PAGE);
        artistShowsPresenter.getArtistShows(PAGE);
        verify(view, times(4)).showLoading();
        verify(view, times(3)).hideLoading();
        verify(view).setCurrentPage(anyString());
        verify(view, times(3)).showShows(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void artistShows_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page";
        when(artistShowsRepository.getShows(anyString(), anyString()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        when(artistShowsRepository.getArtistShows(anyString()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        artistShowsPresenter.getArtist(ARTIST, PAGE);
        artistShowsPresenter.getArtistShows(PAGE);
        verify(view, times(2)).showLoading();
        verify(view, times(2)).hideLoading();
        verify(view, never()).setCurrentPage(anyString());
        verify(view, never()).showShows(any());
        verify(view, times(2)).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void artistShows_ViewNotAttached_ThrowsMvpException() {
        artistShowsPresenter.detachView();
        artistShowsPresenter.getArtistName(ARTIST);
        artistShowsPresenter.getArtist(ARTIST, PAGE);
        artistShowsPresenter.getArtistShows(PAGE);
        verify(view, never()).showLoading();
        verify(view, never()).setCurrentPage(anyString());
        verify(view, never()).showShows(any());
    }
}
