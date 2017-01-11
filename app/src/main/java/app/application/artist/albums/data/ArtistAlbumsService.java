package app.application.artist.albums.data;

import app.application.artist.albums.data.models.artistalbumsmodel.ArtistAlbumsModel;
import app.application.artist.albums.data.models.artistalbumtracksmodel.ArtistAlbumTracksModel;
import app.application.artist.albums.data.models.artistidmodel.ArtistIdModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Service to search for an artists albums via the spotify api
 */
public interface ArtistAlbumsService {
    @GET("/v1/search")
    Observable<ArtistIdModel> getArtistId(
            @Query("q") String name,
            @Query("type") String artist,
            @Query("limit") String limit
    );

    @GET("/v1/artists/{newArtistId}/albums")
    Observable<ArtistAlbumsModel> getArtistAlbums(
            @Path("newArtistId") String newArtistId,
            @Query("album_type") String album_type
    );

    @GET("/v1/albums/")
    Observable<ArtistAlbumTracksModel> getAlbumTracks(
            @Query("ids") String ids
    );
}
