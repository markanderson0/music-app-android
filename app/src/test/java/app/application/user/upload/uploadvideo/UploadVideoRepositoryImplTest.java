package app.application.user.upload.uploadvideo;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import app.application.artist.shows.data.model.ArtistShowsModel;
import app.application.user.upload.uploadvideo.data.UploadVideoRepository;
import app.application.user.upload.uploadvideo.data.UploadVideoRepositoryImpl;
import app.application.user.upload.uploadvideo.data.UploadVideoService;
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
 * Test class to validate the behaviour of the {@link UploadVideoRepositoryImpl}
 */
public class UploadVideoRepositoryImplTest {

    private static final String ARTIST = "artist1";
    private static final String MBID = "mbid";
    private static final String PAGE = "1";
    private static final String YEAR = "2017";
    private static final String EVENT_ID = "eventId";

    @Mock
    UploadVideoService uploadVideoService;

    private UploadVideoRepository uploadVideoRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        uploadVideoRepository = new UploadVideoRepositoryImpl(uploadVideoService);
    }

    @Test
    public void getEvent_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(uploadVideoService.getEvent(anyString(), anyString(), anyString()))
                .thenReturn(new MockUploadVideoServiceImpl().getEvent(MBID, YEAR, PAGE));

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        uploadVideoRepository.getEvent(MBID, YEAR, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<ArtistShowsModel> onNextEvents = subscriber.getOnNextEvents();
        ArtistShowsModel artistShowsModel = onNextEvents.get(0);
        Assert.assertEquals(ARTIST, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getName());
        Assert.assertEquals(MBID, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getMbid());
        verify(uploadVideoService).getEvent(MBID, YEAR, PAGE);
    }

    @Test
    public void getSongs_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(uploadVideoService.getSongs(anyString()))
                .thenReturn(new MockUploadVideoServiceImpl().getSongs(EVENT_ID));

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        uploadVideoRepository.getSongs(EVENT_ID).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<ArtistShowsModel> onNextEvents = subscriber.getOnNextEvents();
        ArtistShowsModel artistShowsModel = onNextEvents.get(0);
        Assert.assertEquals(ARTIST, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getName());
        Assert.assertEquals(MBID, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getMbid());
        verify(uploadVideoService).getSongs(EVENT_ID);
    }

    @Test
    public void getEvent_IOExceptionThenSuccess_GetEventRetried() {
        //Given
        when(uploadVideoService.getEvent(anyString(), anyString(), anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockUploadVideoServiceImpl().getEvent(MBID, YEAR, PAGE));

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        uploadVideoRepository.getEvent(MBID, YEAR, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<ArtistShowsModel> onNextEvents = subscriber.getOnNextEvents();
        ArtistShowsModel artistShowsModel = onNextEvents.get(0);
        Assert.assertEquals(ARTIST, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getName());
        Assert.assertEquals(MBID, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getMbid());
        verify(uploadVideoService, times(2)).getEvent(MBID, YEAR, PAGE);
    }

    @Test
    public void getSongs_IOExceptionThenSuccess_GetSongsRetried() {
        //Given
        when(uploadVideoService.getSongs(anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockUploadVideoServiceImpl().getEvent(MBID, YEAR, PAGE));

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        uploadVideoRepository.getSongs(EVENT_ID).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<ArtistShowsModel> onNextEvents = subscriber.getOnNextEvents();
        ArtistShowsModel artistShowsModel = onNextEvents.get(0);
        Assert.assertEquals(ARTIST, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getName());
        Assert.assertEquals(MBID, artistShowsModel.getSetlists().getSetlist().get(0).getArtist().getMbid());
        verify(uploadVideoService, times(2)).getSongs(EVENT_ID);
    }

    @Test
    public void getEvent_OtherHttpError_GetEventTerminatedWithError() {
        //Given
        when(uploadVideoService.getEvent(anyString(), anyString(), anyString()))
                .thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        uploadVideoRepository.getEvent(MBID, YEAR, PAGE).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(uploadVideoService).getEvent(MBID, YEAR, PAGE);
    }

    @Test
    public void getSongs_OtherHttpError_GetSongsTerminatedWithError() {
        //Given
        when(uploadVideoService.getSongs(anyString())).thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<ArtistShowsModel> subscriber = new TestSubscriber<>();
        uploadVideoRepository.getSongs(EVENT_ID).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(uploadVideoService).getSongs(EVENT_ID);
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

    private Observable<ArtistShowsModel> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }
}
