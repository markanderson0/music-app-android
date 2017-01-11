package app.application.artist.shows.data;

import rx.Observable;

/**
 * Repository to search an artists shows
 */
public interface ArtistShowsRepository {
    Observable<Object> getShows(String artistName, String p);
    Observable<Object> getArtistShows(String artistMbid, String p);
}
