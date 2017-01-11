package app.application.search;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import app.application.search.data.SearchRepository;
import app.application.search.data.SearchRepositoryImpl;
import app.application.search.data.SearchService;
import app.application.search.data.model.SearchModel;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link SearchRepositoryImpl}
 */
public class SearchRepositoryImlTest {

    private static final String ARTIST = "artist1";
    private static final String TYPE = "artist";
    private static final String LIMIT = "20";

    @Mock
    SearchService searchService;

    private SearchRepository searchRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        searchRepository = new SearchRepositoryImpl(searchService);
    }

    @Test
    public void searchUsers_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(searchService.searchArtists(anyString(), anyString(), anyString()))
                .thenReturn(new MockSearchServiceImpl().searchArtists(ARTIST, TYPE, LIMIT));

        //When
        TestSubscriber<SearchModel> subscriber = new TestSubscriber<>();
        searchRepository.searchArtists(ARTIST).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<SearchModel> onNextEvents = subscriber.getOnNextEvents();
        SearchModel users = onNextEvents.get(0);
        Assert.assertEquals(ARTIST, users.getArtists().getItems().get(0).getName());
        verify(searchService).searchArtists(ARTIST, TYPE, LIMIT);
    }

    @Test
    public void searchUsers_IOExceptionThenSuccess_SearchUsersRetried() {
        //Given
        when(searchService.searchArtists(anyString(), anyString(), anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockSearchServiceImpl().searchArtists(ARTIST, TYPE, LIMIT));

        //When
        TestSubscriber<SearchModel> subscriber = new TestSubscriber<>();
        searchRepository.searchArtists(ARTIST).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        verify(searchService, times(2)).searchArtists(ARTIST, TYPE, LIMIT);
    }

    @Test
    public void searchUsers_OtherHttpError_SearchTerminatedWithError() {
        //Given
        when(searchService.searchArtists(anyString(), anyString(), anyString()))
                .thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<SearchModel> subscriber = new TestSubscriber<>();
        searchRepository.searchArtists(ARTIST).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(searchService).searchArtists(ARTIST, TYPE, LIMIT);
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

    private Observable<SearchModel> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }
}
