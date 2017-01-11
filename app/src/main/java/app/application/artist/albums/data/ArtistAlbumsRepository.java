package app.application.artist.albums.data;

import java.util.List;

import app.application.artist.albums.data.models.artistalbumtracksmodel.Album;
import rx.Observable;

/**
 * Repository to search for an artists albums
 */
public interface ArtistAlbumsRepository {
    Observable<List<Album>> getAlbums(String name, String artist, String limit);
}
