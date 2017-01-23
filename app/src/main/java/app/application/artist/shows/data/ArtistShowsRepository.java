package app.application.artist.shows.data;

import app.application.artist.shows.data.model.ArtistShowsModel;
import rx.Observable;

/**
 * Repository to search an artists shows
 */
public interface ArtistShowsRepository {
    Observable<ArtistShowsModel> getShows(String artistName, String p);
    Observable<ArtistShowsModel> getArtistShows(String p);
}
