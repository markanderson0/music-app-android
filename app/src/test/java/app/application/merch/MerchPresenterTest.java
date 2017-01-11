package app.application.merch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import app.application.RxSchedulersOverrideRule;
import app.application.base.BasePresenter;
import app.application.merch.data.MerchRepository;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link MerchPresenter}
 */
public class MerchPresenterTest {
    @Mock
    MerchRepository merchRepository;
    @Mock
    MerchContract.View view;

    MerchPresenter merchPresenter;

    private static final String KEYWORDS = "merchItem";
    private static final String CATEGORY_ID = "categoryId";
    private static final String SORT_ORDER = "sortOrder";
    private static final int PAGE = 1;

    private static final String OPERATION_NAME = "findItemsAdvanced";
    private static final String SERVICE_VERSION = "1.0.0";
    private static final String SECURITY_APPNAME = "adsaa-test-PRD-25a6153ed-58e4b551";
    private static final String RESPONSE_DATA_FORMAT = "JSON";
    private static final String OUTPUT_SELECTOR = "AspectHistogram";
    private static final String ITEM_FILTER_NAME = "Condition";
    private static final String ITEM_FILTER_VALUE = "New";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        merchPresenter = new MerchPresenter(merchRepository);
        merchPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void merch_ValidMerch_ReturnsResults() {
        when(merchRepository.getMerch(anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(new MockMerchServiceImpl().getMerch(
                        OPERATION_NAME,
                        SERVICE_VERSION,
                        SECURITY_APPNAME,
                        RESPONSE_DATA_FORMAT,
                        KEYWORDS,
                        CATEGORY_ID,
                        SORT_ORDER,
                        OUTPUT_SELECTOR,
                        PAGE,
                        ITEM_FILTER_NAME,
                        ITEM_FILTER_VALUE
                ));
        merchPresenter.getMerch(KEYWORDS, CATEGORY_ID, SORT_ORDER);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showMerch(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void merch_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page";
        when(merchRepository.getMerch(anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        merchPresenter.getMerch(KEYWORDS, CATEGORY_ID, SORT_ORDER);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showMerch(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void merch_ViewNotAttached_ThrowsMvpException() {
        merchPresenter.detachView();
        merchPresenter.getMerch(KEYWORDS, CATEGORY_ID, SORT_ORDER);
        verify(view, never()).showLoading();
        verify(view, never()).showMerch(any());
    }
}
