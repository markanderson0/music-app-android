package app.application.merch;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import app.application.merch.data.MerchRepository;
import app.application.merch.data.MerchRepositoryImpl;
import app.application.merch.data.MerchService;
import app.application.merch.data.models.merchlistmodel.MerchListModel;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link MerchRepositoryImpl}
 */
public class MerchRepositoryImplTest {

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

    @Mock
    MerchService merchService;

    private MerchRepository merchRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        merchRepository = new MerchRepositoryImpl(merchService);
    }

    @Test
    public void searchUsers_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(merchService.getMerch(anyString(), anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyInt(), anyString(), anyString()))
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

        //When
        TestSubscriber<MerchListModel> subscriber = new TestSubscriber<>();
        merchRepository.getMerch(KEYWORDS, CATEGORY_ID, SORT_ORDER, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<MerchListModel> onNextEvents = subscriber.getOnNextEvents();
        MerchListModel merch = onNextEvents.get(0);
        Assert.assertEquals(KEYWORDS, merch.findItemsAdvancedResponse.get(0).searchResult.get(0).item.get(0).title.get(0));
        Assert.assertEquals("itemId", merch.findItemsAdvancedResponse.get(0).searchResult.get(0).item.get(0).itemId.get(0));
        Assert.assertEquals("galleryUrl", merch.findItemsAdvancedResponse.get(0).searchResult.get(0).item.get(0).galleryURL.get(0));
        Assert.assertEquals("viewItemUrl", merch.findItemsAdvancedResponse.get(0).searchResult.get(0).item.get(0).viewItemURL.get(0));
        Assert.assertEquals("5", merch.findItemsAdvancedResponse.get(0).searchResult.get(0).item.get(0).sellingStatus.get(0).convertedCurrentPrice.get(0).__value__);
        Assert.assertEquals("1", merch.findItemsAdvancedResponse.get(0).searchResult.get(0).item.get(0).shippingInfo.get(0).shippingServiceCost.get(0).__value__);
        verify(merchService).getMerch(
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
        );
    }

    @Test
    public void searchUsers_IOExceptionThenSuccess_SearchUsersRetried() {
        //Given
        when(merchService.getMerch(anyString(), anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockMerchServiceImpl().getMerch(
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

        //When
        TestSubscriber<MerchListModel> subscriber = new TestSubscriber<>();
        merchRepository.getMerch(KEYWORDS, CATEGORY_ID, SORT_ORDER, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        verify(merchService, times(2)).getMerch(
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
                ITEM_FILTER_VALUE);
    }

    @Test
    public void searchUsers_GetUserIOExceptionThenSuccess_SearchUsersRetried() {
        //Given
        when(merchService.getMerch(anyString(), anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockMerchServiceImpl().getMerch(
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

        //When
        TestSubscriber<MerchListModel> subscriber = new TestSubscriber<>();
        merchRepository.getMerch(KEYWORDS, CATEGORY_ID, SORT_ORDER, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        verify(merchService, times(2)).getMerch(
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
                ITEM_FILTER_VALUE);
    }

    @Test
    public void searchUsers_OtherHttpError_SearchTerminatedWithError() {
        //Given
        when(merchService.getMerch(anyString(), anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<MerchListModel> subscriber = new TestSubscriber<>();
        merchRepository.getMerch(KEYWORDS, CATEGORY_ID, SORT_ORDER, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(merchService).getMerch(
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
        );
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

    private Observable<MerchListModel> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));
    }
}
