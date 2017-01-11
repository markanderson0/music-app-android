package app.application.artist.albums;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.application.RxSchedulersOverrideRule;
import app.application.artist.albums.data.ArtistAlbumsRepository;
import app.application.artist.albums.data.models.artistalbumtracksmodel.Album;
import app.application.artist.albums.data.models.artistalbumtracksmodel.AlbumImage;
import app.application.artist.albums.data.models.artistalbumtracksmodel.Tracks;
import app.application.artist.albums.data.models.artistalbumtracksmodel.TracksItem;
import app.application.base.BasePresenter;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.plugins.RxJavaHooks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to validate the behaviour of the {@link ArtistAlbumsPresenter}
 */
public class ArtistAlbumsPresenterTest {
    @Mock
    ArtistAlbumsRepository artistAlbumsRepository;
    @Mock
    ArtistAlbumsContract.View view;

    ArtistAlbumsPresenter artistAlbumsPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        artistAlbumsPresenter = new ArtistAlbumsPresenter(artistAlbumsRepository);
        artistAlbumsPresenter.attachView(view);
        new RxSchedulersOverrideRule();
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void getAlbums_ValidArtist_ReturnsResults() {
        List<Album> albumList = getMockAlbumModel();
        when(artistAlbumsRepository.getAlbums(anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(albumList));
        artistAlbumsPresenter.getAlbums("artist1", "artist", "1");
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showAlbums(any());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void getAlbums_RepositoryError_ErrorMessage() {
        String errorMsg = "There was a problem loading this page";
        when(artistAlbumsRepository.getAlbums(anyString(), anyString(), anyString()))
                .thenReturn(Observable.error(new IOException(errorMsg)));
        artistAlbumsPresenter.getAlbums("artist1", "artist", "1");
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showAlbums(any());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void getAlbums_ViewNotAttached_ThrowsMvpException() {
        artistAlbumsPresenter.detachView();
        artistAlbumsPresenter.getAlbums("artist1", "artist", "1");
        verify(view, never()).showLoading();
        verify(view, never()).showAlbums(any());
    }

    private List<Album> getMockAlbumModel() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("id", "name", "releaseDate", getTracks(), getAlbumImage()));
        return albums;
    }

    private Tracks getTracks() {
        return new Tracks(getTracksItem());
    }

    private List<TracksItem> getTracksItem() {
        List<TracksItem> tracksItems = new ArrayList<>();
        tracksItems.add(new TracksItem("track" , 1));
        return tracksItems;
    }

    private List<AlbumImage> getAlbumImage() {
        List<AlbumImage> albumImages = new ArrayList<>();
        albumImages.add(new AlbumImage("image"));
        return albumImages;
    }
}
