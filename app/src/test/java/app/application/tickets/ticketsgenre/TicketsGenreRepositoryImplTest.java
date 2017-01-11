package app.application.tickets.ticketsgenre;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import app.application.tickets.ticketmodel.TicketsModel;
import app.application.tickets.ticketsgenre.data.TicketsGenreRepository;
import app.application.tickets.ticketsgenre.data.TicketsGenreRepositoryImpl;
import app.application.tickets.ticketsgenre.data.TicketsGenreService;
import app.application.tickets.ticketssearch.MockTicketsSearchServiceImpl;
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
 * Test class to validate the behaviour of the {@link TicketsGenreRepositoryImpl}
 */
public class TicketsGenreRepositoryImplTest {

    private static final String ARTIST = "artist1";
    private static final String END_DATE_TIME = "2018-01-01T00:00:00Z";
    private static final String API_KEY = "8boYYxplyS2Y6JCayW4YIk7CVhUsqmzK";
    private static final int PAGE_INT = 1;
    private static final String PAGE_STRING = "1";

    @Mock
    TicketsGenreService ticketsGenreService;

    private TicketsGenreRepository ticketsGenreRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ticketsGenreRepository = new TicketsGenreRepositoryImpl(ticketsGenreService);
    }

    @Test
    public void getGenreArtists_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(ticketsGenreService.getGenreArtists(anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(new MockTicketsSearchServiceImpl()
                        .getSearchArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE_STRING));

        //When
        TestSubscriber<TicketsModel> subscriber = new TestSubscriber<>();
        ticketsGenreRepository.getGenreArtists(ARTIST, PAGE_INT).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<TicketsModel> onNextEvents = subscriber.getOnNextEvents();
        TicketsModel tickets = onNextEvents.get(0);
        Assert.assertEquals(ARTIST, tickets._embedded.events.get(0)._embedded.attractions.get(0).name);

        verify(ticketsGenreService).getGenreArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE_INT);
    }

    @Test
    public void getGenreArtists_IOExceptionThenSuccess_GetGenreArtistsRetried() {
        //Given
        when(ticketsGenreService.getGenreArtists(anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(getIOExceptionError(),
                        new MockTicketsSearchServiceImpl()
                                .getSearchArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE_STRING));

        //When
        TestSubscriber<TicketsModel> subscriber = new TestSubscriber<>();
        ticketsGenreRepository.getGenreArtists(ARTIST, PAGE_INT).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        verify(ticketsGenreService, times(2)).getGenreArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE_INT);
    }

    @Test
    public void getGenreArtists_OtherHttpError_TerminatedWithError() {
        //Given
        when(ticketsGenreService.getGenreArtists(anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<TicketsModel> subscriber = new TestSubscriber<>();
        ticketsGenreRepository.getGenreArtists(ARTIST, PAGE_INT).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(ticketsGenreService).getGenreArtists(ARTIST, END_DATE_TIME, API_KEY, PAGE_INT);
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

    private Observable<TicketsModel> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));
    }
}
