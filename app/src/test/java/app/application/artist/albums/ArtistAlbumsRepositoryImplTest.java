package app.application.artist.albums;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import app.application.artist.albums.data.ArtistAlbumsRepository;
import app.application.artist.albums.data.ArtistAlbumsRepositoryImpl;
import app.application.artist.albums.data.ArtistAlbumsService;
import app.application.artist.albums.data.models.artistalbumsmodel.ArtistAlbumsModel;
import app.application.artist.albums.data.models.artistalbumtracksmodel.Album;
import app.application.artist.albums.data.models.artistalbumtracksmodel.ArtistAlbumTracksModel;
import app.application.artist.albums.data.models.artistidmodel.ArtistIdModel;
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
 * Test class to validate the behaviour of the {@link ArtistAlbumsRepositoryImpl}
 */
public class ArtistAlbumsRepositoryImplTest {

    private static final String ARTIST = "artist1";
    private static final String TYPE = "artist";
    private static final String LIMIT = "20";

    private static final String ARTIST_ID = "artistId";
    private static final String ALBUM_TYPE = "album";

    private static final String ALBUM_IDS = "albumId";

    @Mock
    ArtistAlbumsService artistAlbumsService;

    private ArtistAlbumsRepository artistAlbumsRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        artistAlbumsRepository = new ArtistAlbumsRepositoryImpl(artistAlbumsService);
    }

    @Test
    public void getAlbums_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(artistAlbumsService.getArtistId(anyString(), anyString(), anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getArtistId(ARTIST, TYPE, LIMIT));
        when(artistAlbumsService.getArtistAlbums(anyString(), anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getArtistAlbums(ARTIST_ID, ALBUM_TYPE));
        when(artistAlbumsService.getAlbumTracks(anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getAlbumTracks(ALBUM_IDS));

        //When
        TestSubscriber<List<Album>> subscriber = new TestSubscriber<>();
        artistAlbumsRepository.getAlbums("artist1", "artist", "1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<Album>> onNextEvents = subscriber.getOnNextEvents();
        List<Album> albums = onNextEvents.get(0);
        Assert.assertEquals("albumName", albums.get(0).getName());
        Assert.assertEquals("albumId", albums.get(0).getId());
        verify(artistAlbumsService).getArtistId("artist1", "artist", "1");
        verify(artistAlbumsService).getArtistAlbums("artistId", "album");
        verify(artistAlbumsService).getAlbumTracks("albumId");
    }

    @Test
    public void getArtistId_IOExceptionThenSuccess_GetArtistIdRetried() {
        //Given
        when(artistAlbumsService.getArtistId(anyString(), anyString(), anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockArtistAlbumsServiceImpl().getArtistId(ARTIST, TYPE, LIMIT));

        //When
        TestSubscriber<List<Album>> subscriber = new TestSubscriber<>();
        artistAlbumsRepository.getAlbums("artist1", "artist", "1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<Album>> onNextEvents = subscriber.getOnNextEvents();
        List<Album> albums = onNextEvents.get(0);
        Assert.assertEquals("albumName", albums.get(0).getName());
        Assert.assertEquals("albumId", albums.get(0).getId());
        verify(artistAlbumsService, times(2)).getArtistId("artist1", "artist", "1");
    }

    @Test
    public void getArtistAlbums_IOExceptionThenSuccess_GetArtistAlbumsRetried() {
        //Given
        when(artistAlbumsService.getArtistId(anyString(), anyString(), anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getArtistId(ARTIST, TYPE, LIMIT));
        when(artistAlbumsService.getArtistAlbums(anyString(), anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockArtistAlbumsServiceImpl().getArtistAlbums(ARTIST_ID, ALBUM_TYPE));
        when(artistAlbumsService.getAlbumTracks(anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getAlbumTracks(ALBUM_IDS));

        //When
        TestSubscriber<List<Album>> subscriber = new TestSubscriber<>();
        artistAlbumsRepository.getAlbums("artist1", "artist", "1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<Album>> onNextEvents = subscriber.getOnNextEvents();
        List<Album> albums = onNextEvents.get(0);
        Assert.assertEquals("albumName", albums.get(0).getName());
        Assert.assertEquals("albumId", albums.get(0).getId());
        verify(artistAlbumsService).getArtistId("artist1", "artist", "1");
        verify(artistAlbumsService, times(2)).getArtistAlbums("artistId", "album");
        verify(artistAlbumsService).getAlbumTracks("albumId");
    }

    @Test
    public void getAlbumTracks_IOExceptionThenSuccess_GetAlbumTracksRetried() {
        //Given
        when(artistAlbumsService.getArtistId(anyString(), anyString(), anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getArtistId(ARTIST, TYPE, LIMIT));
        when(artistAlbumsService.getArtistAlbums(anyString(), anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getArtistAlbums(ARTIST_ID, ALBUM_TYPE));
        when(artistAlbumsService.getAlbumTracks(anyString()))
                .thenReturn(getIOExceptionError(),
                        new MockArtistAlbumsServiceImpl().getAlbumTracks(ALBUM_IDS));

        //When
        TestSubscriber<List<Album>> subscriber = new TestSubscriber<>();
        artistAlbumsRepository.getAlbums("artist1", "artist", "1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<Album>> onNextEvents = subscriber.getOnNextEvents();
        List<Album> albums = onNextEvents.get(0);
        Assert.assertEquals("albumName", albums.get(0).getName());
        Assert.assertEquals("albumId", albums.get(0).getId());
        verify(artistAlbumsService).getArtistId("artist1", "artist", "1");
        verify(artistAlbumsService).getArtistAlbums("artistId", "album");
        verify(artistAlbumsService, times(2)).getAlbumTracks("albumId");
    }

    @Test
    public void getArtistId_OtherHttpError_TerminatedWithError() {
        //Given
        when(artistAlbumsService.getArtistId(anyString(), anyString(), anyString()))
                .thenReturn(getArtistIdModel403ForbiddenError());
        when(artistAlbumsService.getArtistAlbums(anyString(), anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getArtistAlbums(ARTIST_ID, ALBUM_TYPE));
        when(artistAlbumsService.getAlbumTracks(anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getAlbumTracks(ALBUM_IDS));

        //When
        TestSubscriber<List<Album>> subscriber = new TestSubscriber<>();
        artistAlbumsRepository.getAlbums("artist1", "artist", "1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(artistAlbumsService, never()).getArtistId("artist1", "artist", "1");
        verify(artistAlbumsService, never()).getArtistAlbums("artistId", "album");
        verify(artistAlbumsService, never()).getAlbumTracks("albumId");
    }

    @Test
    public void getArtistAlbums_OtherHttpError_TerminatedWithError() {
        //Given
        when(artistAlbumsService.getArtistId(anyString(), anyString(), anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getArtistId(ARTIST, TYPE, LIMIT));
        when(artistAlbumsService.getArtistAlbums(anyString(), anyString()))
                .thenReturn(getArtistAlbumsModel403ForbiddenError());
        when(artistAlbumsService.getAlbumTracks(anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getAlbumTracks(ALBUM_IDS));

        //When
        TestSubscriber<List<Album>> subscriber = new TestSubscriber<>();
        artistAlbumsRepository.getAlbums("artist1", "artist", "1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(artistAlbumsService).getArtistId("artist1", "artist", "1");
        verify(artistAlbumsService, never()).getArtistAlbums("artistId", "album");
        verify(artistAlbumsService, never()).getAlbumTracks("albumId");
    }

    @Test
    public void getAlbumTracks_OtherHttpError_TerminatedWithError() {
        //Given
        when(artistAlbumsService.getArtistId(anyString(), anyString(), anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getArtistId(ARTIST, TYPE, LIMIT));
        when(artistAlbumsService.getArtistAlbums(anyString(), anyString()))
                .thenReturn(new MockArtistAlbumsServiceImpl().getArtistAlbums(ARTIST_ID, ALBUM_TYPE));
        when(artistAlbumsService.getAlbumTracks(anyString()))
                .thenReturn(getArtistAlbumTracksModel403ForbiddenError());

        //When
        TestSubscriber<List<Album>> subscriber = new TestSubscriber<>();
        artistAlbumsRepository.getAlbums("artist1", "artist", "1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(artistAlbumsService).getArtistId("artist1", "artist", "1");
        verify(artistAlbumsService).getArtistAlbums("artistId", "album");
        verify(artistAlbumsService, never()).getAlbumTracks("albumId");
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

    private Observable<ArtistIdModel> getArtistIdModel403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }

    private Observable<ArtistAlbumsModel> getArtistAlbumsModel403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }

    private Observable<ArtistAlbumTracksModel> getArtistAlbumTracksModel403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }
}
