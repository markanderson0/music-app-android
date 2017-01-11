package app.application.search.data;

import app.application.search.data.model.SearchModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Service to search for artists via the spotify api
 */
public interface SearchService {
    @GET("/v1/search")
    Observable<SearchModel> searchArtists(
            @Query("q") String name,
            @Query("type") String artist,
            @Query("limit") String limit
    );
}
