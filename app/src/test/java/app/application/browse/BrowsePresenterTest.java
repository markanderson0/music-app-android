package app.application.browse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.App;
import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.browse.data.BrowseRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link BrowsePresenter}
 */
public class BrowsePresenterTest {

    public static final String NAVIGATION_PATH = "browseNavigation";
    public static final String GENRE_PATH = "genre";

    @Mock
    BrowseRepository browseRepository;
    @Mock
    BrowseContract.View view;

    BrowsePresenter browsePresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        browsePresenter = new BrowsePresenter(browseRepository);
        browsePresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void browseNavigation_VaildBrowseModel_ReturnsResults() {
        when(browseRepository.getArtists(any(), anyString()))
                .thenReturn(new MockBrowseServiceImpl().getArtists(App.getContext(), NAVIGATION_PATH));
        browsePresenter.getArtists(App.getContext(), NAVIGATION_PATH);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showGridData(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void browseGenre_VaildBrowseModel_ReturnsResults() {
        when(browseRepository.getArtists(any(), anyString()))
                .thenReturn(new MockBrowseServiceImpl().getArtists(App.getContext(), GENRE_PATH));
        browsePresenter.getArtists(App.getContext(), GENRE_PATH);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showGridData(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void browse_BrowseRepositoryError_ErrorMsg() {
        String errorMsg = "There was a problem loading this page.";
        when(browseRepository.getArtists(any(), anyString()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        browsePresenter.getArtists(App.getContext(), "");
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showGridData(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void browse_NotAttached_ThrowsMvpException() {
        browsePresenter.detachView();
        browsePresenter.getArtists(App.getContext(), "");
        verify(view, never()).showLoading();
        verify(view, never()).showGridData(any());
    }
}
