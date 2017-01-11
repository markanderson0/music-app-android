package app.application.tickets.toptickets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.App;
import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.tickets.toptickets.data.TopTicketsRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link TopTicketsPresenter}
 */
public class TopTicketsPresenterTest {
    @Mock
    TopTicketsRepository topTicketsRepository;
    @Mock
    TopTicketsContract.View view;

    TopTicketsPresenter topTicketsPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        topTicketsPresenter = new TopTicketsPresenter(topTicketsRepository);
        topTicketsPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void topTickets_ValidTicketsGridModel_ReturnsResults() {
        when(topTicketsRepository.getTopTickets(any()))
                .thenReturn(new MockTopTicketsServiceImpl().getTopTickets(App.getContext()));
        topTicketsPresenter.getArtists(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showTopTickets(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void topTickets_TopTicketsRepositoryError_ErrorMsg() {
        String errorMsg = "There was a problem loading this page.";
        when(topTicketsRepository.getTopTickets(any()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        topTicketsPresenter.getArtists((App.getContext()));
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showTopTickets(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void topTickets_NotAttached_ThrowsMvpException() {
        topTicketsPresenter.detachView();
        topTicketsPresenter.getArtists((App.getContext()));
        verify(view, never()).showLoading();
        verify(view, never()).showTopTickets(any());
    }
}
