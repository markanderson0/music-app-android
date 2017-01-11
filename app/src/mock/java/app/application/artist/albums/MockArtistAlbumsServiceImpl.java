package app.application.artist.albums;

import java.util.ArrayList;
import java.util.List;

import app.application.artist.albums.data.ArtistAlbumsService;
import app.application.artist.albums.data.models.artistalbumsmodel.AlbumItem;
import app.application.artist.albums.data.models.artistalbumsmodel.ArtistAlbumsModel;
import app.application.artist.albums.data.models.artistalbumtracksmodel.Album;
import app.application.artist.albums.data.models.artistalbumtracksmodel.AlbumImage;
import app.application.artist.albums.data.models.artistalbumtracksmodel.ArtistAlbumTracksModel;
import app.application.artist.albums.data.models.artistalbumtracksmodel.Tracks;
import app.application.artist.albums.data.models.artistalbumtracksmodel.TracksItem;
import app.application.artist.albums.data.models.artistidmodel.ArtistIdModel;
import app.application.artist.albums.data.models.artistidmodel.Artists;
import app.application.artist.albums.data.models.artistidmodel.Item;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Mock implementation of the {@link ArtistAlbumsService}
 */
public class MockArtistAlbumsServiceImpl implements ArtistAlbumsService {
    @Override
    public Observable<ArtistIdModel> getArtistId(@Query("q") String name, @Query("type") String artist, @Query("limit") String limit) {
        return Observable.just(getArtistIdModel());
    }

    private ArtistIdModel getArtistIdModel() {
        return new ArtistIdModel(getArtists());
    }

    private Artists getArtists() {
        return new Artists(getItems());
    }

    private List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("artistId", "artist1"));
        return items;
    }

    @Override
    public Observable<ArtistAlbumsModel> getArtistAlbums(@Path("newArtistId") String newArtistId, @Query("album_type") String album_type) {
        return Observable.just(getArtistAlbumsModel());
    }

    private ArtistAlbumsModel getArtistAlbumsModel() {
        return new ArtistAlbumsModel(getAlbumItems());
    }

    private List<AlbumItem> getAlbumItems() {
        List<AlbumItem> albumItems = new ArrayList<>();
        albumItems.add(new AlbumItem("albumId", "albumName"));
        return albumItems;
    }

    @Override
    public Observable<ArtistAlbumTracksModel> getAlbumTracks(@Query("ids") String ids) {
        return Observable.just(getArtistAlbumTracks());
    }

    private ArtistAlbumTracksModel getArtistAlbumTracks() {
        return new ArtistAlbumTracksModel(getAlbums());
    }

    private List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("albumId", "albumName", "2017", getTracks(), getAlbumImages()));
        return albums;
    }

    private Tracks getTracks() {
        return new Tracks(getTracksItems());
    }

    private List<TracksItem> getTracksItems() {
        List<TracksItem> tracksItems = new ArrayList<>();
        tracksItems.add(new TracksItem("song", 1));
        return tracksItems;
    }

    private List<AlbumImage> getAlbumImages() {
        List<AlbumImage> albumImages = new ArrayList<>();
        albumImages.add(new AlbumImage("image"));
        return albumImages;
    }
}
