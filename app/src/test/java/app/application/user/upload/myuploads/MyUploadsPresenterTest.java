package app.application.user.upload.myuploads;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.App;
import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.user.upload.myuploads.data.MyUploadsRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link MyUploadsPresenter}
 */
public class MyUploadsPresenterTest {
    @Mock
    MyUploadsRepository myUploadsRepository;
    @Mock
    MyUploadsContract.View view;

    MyUploadsPresenter myUploadsPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        myUploadsPresenter = new MyUploadsPresenter(myUploadsRepository);
        myUploadsPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void myUploads_ValidVideosModel_ReturnsResults() {
        when(myUploadsRepository.getMyUploads(any()))
                .thenReturn(new MockMyUploadsServiceImpl().getMyUploads(App.getContext()));
        myUploadsPresenter.getMyUploads(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showMyUploads(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void myUploads_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page.";
        when(myUploadsRepository.getMyUploads(any()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        myUploadsPresenter.getMyUploads(App.getContext());
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showMyUploads(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void myUploads_ViewNotAttached_ThrowsMvpException() {
        myUploadsPresenter.detachView();
        myUploadsPresenter.getMyUploads(App.getContext());
        verify(view, never()).showLoading();
        verify(view, never()).showMyUploads(any());
    }
}
