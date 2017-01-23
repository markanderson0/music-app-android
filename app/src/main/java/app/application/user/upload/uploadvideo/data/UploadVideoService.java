package app.application.user.upload.uploadvideo.data;

import app.application.artist.shows.data.model.ArtistShowsModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Service to search for events and songs via the setlist.fm api
 */
public interface UploadVideoService {
    @GET("/rest/0.1/search/setlists.json")
    Observable<ArtistShowsModel> getEvent(
            @Query("artistMbid") String artistMbid,
            @Query("year") String year,
            @Query("p") String p
    );

    @GET("/rest/0.1/setlist/{eventId}.json")
    Observable<ArtistShowsModel> getSongs(
            @Path("eventId") String eventId
    );
}
