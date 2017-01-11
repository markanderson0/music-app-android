package app.application.user.profile.favourites;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.App;
import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.user.profile.favourites.data.FavouritesRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link FavouritesPresenter}
 */
public class FavouritesPresenterTest {
    @Mock
    FavouritesRepository favouritesRepository;
    @Mock
    FavouritesContract.View view;

    FavouritesPresenter favouritesPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        favouritesPresenter = new FavouritesPresenter(favouritesRepository);
        favouritesPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void favourites_ValidFavouritesModel_ReturnsResults() {
        when(favouritesRepository.getFavourites(any()))
                .thenReturn(new MockFavouritesServiceImpl().getFavourites(App.getContext()));
        favouritesPresenter.getFavourites(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showFavourites(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void favourites_FavouritesRepositoryError_ErrorMsg() {
        String errorMsg = "There was a problem loading this page.";
        when(favouritesRepository.getFavourites(any()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        favouritesPresenter.getFavourites(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showFavourites(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void favourites_NotAttached_ThrowsMvpException() {
        favouritesPresenter.detachView();
        favouritesPresenter.getFavourites(App.getContext());
        verify(view, never()).showLoading();
        verify(view, never()).showFavourites(any());
    }
}
