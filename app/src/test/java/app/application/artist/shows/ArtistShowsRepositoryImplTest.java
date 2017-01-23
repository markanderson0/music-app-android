package app.application.artist.shows;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import app.application.artist.shows.data.ArtistShowsRepository;
import app.application.artist.shows.data.ArtistShowsRepositoryImpl;
import app.application.artist.shows.data.ArtistShowsService;
import app.application.artist.shows.data.model.ArtistShowsModel;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link ArtistShowsRepositoryImpl}
 */
public class ArtistShowsRepositoryImplTest {

    private static final String ARTIST = "artist1";
    private static final String MBID = "mbid";
    private static final String PAGE = "1";

    @Mock
    ArtistShowsService artistShowsService;

    private ArtistShowsRepository artistShowsRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        artistShowsRepository = new ArtistShowsRepositoryImpl(artistShowsService);
    }

    @Test
    public void getArtistAndGetArtistShows_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(artistShowsService.getArtist(anyString(), anyString()))
                .thenReturn(new MockArtistShowsServiceImpl().getArtist(ARTIST, PAGE));
        when(artistShowsService.getArtistShows(anyString(), anyString()))
                .thenReturn(new MockArtistShowsServiceImpl().getArtistShows(MBID, PAGE));

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        artistShowsRepository.getShows(ARTIST, PAGE).subscribe(subscriber);
        artistShowsRepository.getArtistShows(PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<ArtistShowsModel> onNextEvents = subscriber.getOnNextEvents();
        ArtistShowsModel artistShowsModel = onNextEvents.get(0);
        Assert.assertEquals(ARTIST, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getName());
        Assert.assertEquals(MBID, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getMbid());
        verify(artistShowsService).getArtist(ARTIST, PAGE);
        verify(artistShowsService).getArtistShows(MBID, PAGE);
    }

    @Test
    public void getArtistShows_IOExceptionThenSuccess_GetArtistShowsRetried() {
        //Given
        when(artistShowsService.getArtistShows(anyString(), anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockArtistShowsServiceImpl().getArtistShows(MBID, PAGE));

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        artistShowsRepository.getArtistShows(PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<ArtistShowsModel> onNextEvents = subscriber.getOnNextEvents();
        ArtistShowsModel artistShowsModel = onNextEvents.get(0);
        Assert.assertEquals(ARTIST, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getName());
        Assert.assertEquals(MBID, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getMbid());
        verify(artistShowsService, times(2)).getArtistShows(null, PAGE);
    }

    @Test
    public void getArtist_OtherHttpError_GetArtistTerminatedWithError() {
        //Given
        when(artistShowsService.getArtist(anyString(), anyString()))
                .thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        artistShowsRepository.getShows(ARTIST, PAGE).subscribe(subscriber);
        artistShowsRepository.getArtistShows(PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(artistShowsService).getArtist(ARTIST, PAGE);
    }

    @Test
    public void getArtistShows_OtherHttpError_GetArtistShowsTerminatedWithError() {
        //Given
        when(artistShowsService.getArtistShows(anyString(), anyString()))
                .thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        artistShowsRepository.getShows(ARTIST, PAGE).subscribe(subscriber);
        artistShowsRepository.getArtistShows(PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(NullPointerException.class);

        verify(artistShowsService).getArtist(ARTIST, PAGE);
        verify(artistShowsService, never()).getArtistShows(MBID, PAGE);
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

    private Observable<ArtistShowsModel> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }
}
