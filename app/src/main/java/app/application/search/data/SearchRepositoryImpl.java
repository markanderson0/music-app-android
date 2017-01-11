package app.application.search.data;

import java.io.IOException;

import app.application.search.data.model.SearchModel;
import rx.Observable;

/**
 * Implementation of {@link SearchRepository}
 */
public class SearchRepositoryImpl implements SearchRepository {

    private static final String TYPE = "artist";
    private static final String LIMIT = "20";

    SearchService searchService;

    public SearchRepositoryImpl(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Returns the artists found from the query. If the user has no internet connection an
     * IOException is thrown by retrofit.
     *
     * @param artistName name of the artist to be queried
     * @return artists found from the query
     */
    @Override
    public Observable<SearchModel> searchArtists(String artistName) {
        return Observable.defer(() -> searchService.searchArtists(artistName, TYPE, LIMIT)).retryWhen(
                observable ->
                        observable.flatMap(o -> {
                            if (o instanceof IOException) {
                                return Observable.just(null);
                            }
                            return Observable.error(o);
                        }));
    }
}
