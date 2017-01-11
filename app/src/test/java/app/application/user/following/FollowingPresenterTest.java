package app.application.user.following;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.App;
import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.user.following.data.FollowingRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link FollowingPresenter}
 */
public class FollowingPresenterTest {
    @Mock
    FollowingRepository followingRepository;
    @Mock
    FollowingContract.View view;

    FollowingPresenter followingPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        followingPresenter = new FollowingPresenter(followingRepository);
        followingPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void following_VaildFollowingModel_ReturnsResults() {
        when(followingRepository.getArtists(any()))
                .thenReturn(new MockFollowingServiceImpl().getArtists(App.getContext()));
        followingPresenter.getArtists(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showResults(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void following_FollowingRepositoryError_ErrorMsg() {
        String errorMsg = "There was a problem loading this page.";
        when(followingRepository.getArtists(any()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        followingPresenter.getArtists(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showResults(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void following_NotAttached_ThrowsMvpException() {
        followingPresenter.detachView();
        followingPresenter.getArtists(App.getContext());
        verify(view, never()).showLoading();
        verify(view, never()).showResults(any());
    }
}
