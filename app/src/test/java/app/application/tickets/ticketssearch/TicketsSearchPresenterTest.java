package app.application.tickets.ticketssearch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.tickets.ticketssearch.data.TicketsSearchRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link TicketsSearchPresenter}
 */
public class TicketsSearchPresenterTest {

    private static final String ARTIST = "artist1";
    private static final String END_DATE_TIME = "2018-01-01T00:00:00Z";
    private static final String API_KEY = "8boYYxplyS2Y6JCayW4YIk7CVhUsqmzK";
    private static final String PAGE = "1";

    @Mock
    TicketsSearchRepository ticketsSearchRepository;
    @Mock
    TicketsSearchContract.View view;

    TicketsSearchPresenter ticketsSearchPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ticketsSearchPresenter = new TicketsSearchPresenter(ticketsSearchRepository);
        ticketsSearchPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void getSearchArtists_ValidSearchTerm_ReturnsResults() {
        when(ticketsSearchRepository.getSearchArtists(anyString(), anyString()))
                .thenReturn(new MockTicketsSearchServiceImpl().getSearchArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE));
        ticketsSearchPresenter.getSearchArtists(ARTIST);
        verify(view, times(2)).showLoading();
        verify(view, times(2)).hideLoading();
        verify(view).showTableArtists(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void getSearchArtists_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page";
        when(ticketsSearchRepository.getSearchArtists(anyString(), anyString()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        ticketsSearchPresenter.getSearchArtists(ARTIST);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showTableArtists(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void ticketsSearchPresenter_ViewNotAttached_ThrowsMvpException() {
        ticketsSearchPresenter.detachView();
        ticketsSearchPresenter.getSearchArtists(ARTIST);
        verify(view, never()).showLoading();
        verify(view, never()).showTableArtists(any());
    }
}
