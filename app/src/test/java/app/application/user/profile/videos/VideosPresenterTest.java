package app.application.user.profile.videos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.App;
import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.user.profile.videos.data.VideosRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link VideosPresenter}
 */
public class VideosPresenterTest {
    @Mock
    VideosRepository videosRepository;
    @Mock
    VideosContract.View view;

    VideosPresenter videosPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        videosPresenter = new VideosPresenter(videosRepository);
        videosPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void videos_ValidVideosModel_ReturnsResults() {
        when(videosRepository.getVideos(any()))
                .thenReturn(new MockVideosServiceImpl().getVideos(App.getContext()));
        videosPresenter.getVideos(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showVideos(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void videos_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page.";
        when(videosRepository.getVideos(any()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        videosPresenter.getVideos(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showVideos(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void videos_ViewNotAttached_ThrowsMvpException() {
        videosPresenter.detachView();
        videosPresenter.getVideos(App.getContext());
        verify(view, never()).showLoading();
        verify(view, never()).showVideos(any());
    }
}
