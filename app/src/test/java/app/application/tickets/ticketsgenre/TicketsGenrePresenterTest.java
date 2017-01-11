package app.application.tickets.ticketsgenre;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.tickets.ticketsgenre.data.TicketsGenreRepository;
import app.application.tickets.ticketssearch.MockTicketsSearchServiceImpl;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link TicketsGenrePresenter}
 */
public class TicketsGenrePresenterTest {

    private static final String ARTIST = "artist1";
    private static final String END_DATE_TIME = "2018-01-01T00:00:00Z";
    private static final String API_KEY = "8boYYxplyS2Y6JCayW4YIk7CVhUsqmzK";
    private static final String PAGE = "1";

    @Mock
    TicketsGenreRepository ticketsGenreRepository;
    @Mock
    TicketsGenreContract.View view;

    TicketsGenrePresenter ticketsGenrePresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ticketsGenrePresenter = new TicketsGenrePresenter(ticketsGenreRepository);
        ticketsGenrePresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void getGenreArtists_ValidGenre_ReturnsResults() {
        when(ticketsGenreRepository.getGenreArtists(anyString(), anyInt()))
                .thenReturn(new MockTicketsSearchServiceImpl().getSearchArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE));
        ticketsGenrePresenter.getGenreArtists(ARTIST);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showTableArtists(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void getGenreArtists_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page";
        when(ticketsGenreRepository.getGenreArtists(anyString(), anyInt()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        ticketsGenrePresenter.getGenreArtists(ARTIST);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showTableArtists(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void ticketsGenrePresenter_ViewNotAttached_ThrowsMvpException() {
        ticketsGenrePresenter.detachView();
        ticketsGenrePresenter.getGenreArtists(ARTIST);
        verify(view, never()).showLoading();
        verify(view, never()).showTableArtists(any());
    }
}
