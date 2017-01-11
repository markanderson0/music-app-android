package app.application.tickets.ticketssearch;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import app.application.tickets.ticketmodel.TicketsModel;
import app.application.tickets.ticketssearch.data.TicketsSearchRepository;
import app.application.tickets.ticketssearch.data.TicketsSearchRepositoryImpl;
import app.application.tickets.ticketssearch.data.TicketsSearchService;
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
 * Test class to validate the behaviour of the {@link TicketsSearchRepositoryImpl}
 */
public class TicketsSearchRepositoryImplTest {

    private static final String ARTIST = "artist1";
    private static final String END_DATE_TIME = "2018-01-01T00:00:00Z";
    private static final String API_KEY = "8boYYxplyS2Y6JCayW4YIk7CVhUsqmzK";
    private static final String PAGE = "1";

    @Mock
    TicketsSearchService ticketsSearchService;

    private TicketsSearchRepository ticketsSearchRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ticketsSearchRepository = new TicketsSearchRepositoryImpl(ticketsSearchService);
    }

    @Test
    public void getSearchArtists_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(ticketsSearchService.getSearchArtists(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new MockTicketsSearchServiceImpl().getSearchArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE));

        //When
        TestSubscriber<TicketsModel> subscriber = new TestSubscriber<>();
        ticketsSearchRepository.getSearchArtists(ARTIST, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<TicketsModel> onNextEvents = subscriber.getOnNextEvents();
        TicketsModel tickets = onNextEvents.get(0);
        Assert.assertEquals(ARTIST, tickets._embedded.events.get(0)._embedded.attractions.get(0).name);

        verify(ticketsSearchService).getSearchArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE);
    }

    @Test
    public void getSearchArtists_IOExceptionThenSuccess_GetSearchArtistsRetried() {
        //Given
        when(ticketsSearchService.getSearchArtists(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockTicketsSearchServiceImpl().getSearchArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE));

        //When
        TestSubscriber<TicketsModel> subscriber = new TestSubscriber<>();
        ticketsSearchRepository.getSearchArtists(ARTIST, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        verify(ticketsSearchService, times(2)).getSearchArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE);
    }

    @Test
    public void getSearchArtists_OtherHttpError_TerminatedWithError() {
        //Given
        when(ticketsSearchService.getSearchArtists(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<TicketsModel> subscriber = new TestSubscriber<>();
        ticketsSearchRepository.getSearchArtists(ARTIST, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(ticketsSearchService).getSearchArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE);
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

    private Observable<TicketsModel> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));
    }
}
