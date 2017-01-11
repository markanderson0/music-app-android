package app.application.artist.topvideos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.App;
import app.application.RxSchedulersOverrideRule;
import app.application.artist.topvideos.data.TopVideosRepository;
import app.application.base.BasePresenter;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link TopVideosPresenter}
 */
public class TopVideosPresenterTest {
    @Mock
    TopVideosRepository topVideosRepository;
    @Mock
    TopVideosContract.View view;

    TopVideosPresenter topVideosPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        topVideosPresenter = new TopVideosPresenter(topVideosRepository);
        topVideosPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void getTopVideos_ValidVideosModel_ReturnsResults() {
        when(topVideosRepository.getTopVideos(any()))
                .thenReturn(new MockTopVideosServiceImpl().getTopVideos(App.getContext()));
        topVideosPresenter.getTopVideos(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showTopVideos(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void getTopVideos_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page.";
        when(topVideosRepository.getTopVideos(any()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        topVideosPresenter.getTopVideos(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showTopVideos(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void getTopVideos_ViewNotAttached_ThrowsMvpException() {
        topVideosPresenter.detachView();
        topVideosPresenter.getTopVideos(App.getContext());
        verify(view, never()).showLoading();
        verify(view, never()).showTopVideos(any());
    }
}
