package app.application.artist.shows.data;

import app.application.artist.shows.data.model.ArtistShowsModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Service to search for an artists previous shows and setlists
 */
public interface ArtistShowsService {
    @GET("/rest/0.1/search/setlists.json")
    Observable<ArtistShowsModel> getArtist(
            @Query("artistName") String artistName,
            @Query("p") String p
    );

    @GET("/rest/0.1/search/setlists.json")
    Observable<ArtistShowsModel> getArtistShows(
            @Query("artistMbid") String artistMbid,
            @Query("p") String p
    );
}
