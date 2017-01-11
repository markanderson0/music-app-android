package app.application.search.data;

import app.application.search.data.model.SearchModel;
import rx.Observable;

/**
 * Repository to search for artists
 */
public interface SearchRepository {
    Observable<SearchModel> searchArtists(String artistName);
}
