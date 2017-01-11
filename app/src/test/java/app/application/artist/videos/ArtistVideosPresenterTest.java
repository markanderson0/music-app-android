package app.application.artist.videos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.App;
import app.application.RxSchedulersOverrideRule;
import app.application.artist.videos.data.ArtistVideosRepository;
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
 * Test class to validate the behaviour of the {@link ArtistVideosPresenter}
 */
public class ArtistVideosPresenterTest {
    @Mock
    ArtistVideosRepository artistVideosRepository;
    @Mock
    ArtistVideosContract.View view;

    ArtistVideosPresenter artistVideosPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        artistVideosPresenter = new ArtistVideosPresenter(artistVideosRepository);
        artistVideosPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void getVideos_ValidVideosModel_ReturnsResults() {
        when(artistVideosRepository.getVideos(any()))
                .thenReturn(new MockArtistVideosServiceImpl().getVideos(App.getContext()));
        artistVideosPresenter.getVideos(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showVideos(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void getVideos_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page.";
        when(artistVideosRepository.getVideos(any()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        artistVideosPresenter.getVideos(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showVideos(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void getVideos_ViewNotAttached_ThrowsMvpException() {
        artistVideosPresenter.detachView();
        artistVideosPresenter.getVideos(App.getContext());
        verify(view, never()).showLoading();
        verify(view, never()).showVideos(any());
    }
}
