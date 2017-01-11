package app.application.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.search.data.SearchRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link SearchPresenter}
 */
public class SearchPresenterTest {

    private static final String ARTIST = "artist1";
    private static final String TYPE = "artist";
    private static final String LIMIT = "20";

    @Mock
    SearchRepository searchRepository;
    @Mock
    SearchContract.View view;

    SearchPresenter searchPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        searchPresenter = new SearchPresenter(searchRepository);
        searchPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void searchArtists_ValidSearchTerm_ReturnsResults() {
        when(searchRepository.searchArtists(anyString()))
                .thenReturn(new MockSearchServiceImpl().searchArtists(ARTIST, TYPE, LIMIT));
        searchPresenter.searchArtists(ARTIST);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showResults(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void searchArtists_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page";
        when(searchRepository.searchArtists(anyString()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        searchPresenter.searchArtists(ARTIST);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showResults(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void searchArtists_ViewNotAttached_ThrowsMvpException() {
        searchPresenter.detachView();
        searchPresenter.searchArtists(ARTIST);
        verify(view, never()).showLoading();
        verify(view, never()).showResults(any());
    }
}
