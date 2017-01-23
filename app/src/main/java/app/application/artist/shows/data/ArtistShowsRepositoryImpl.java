package app.application.artist.shows.data;

import java.io.IOException;

import app.application.artist.shows.ArtistShowsUtil;
import app.application.artist.shows.data.model.ArtistShowsModel;
import rx.Observable;

/**
 * Implementation of {@link ArtistShowsRepository}
 *
 * Since the setlist.fm api responds with different data types for certain json fields this
 * causes problems when modelling the response. An example of this problem is when a show has
 * an encore or not. If there is an encore then the sets field will return an arraylist of set
 * fields and if not it will return an individual set field not contained within an arraylist.
 * To handle this the api response is recieved as on Object and manually mapped to an
 * {@link ArtistShowsModel}.
 *
 * @note The above problem is to be solved by using a custom GSON deserializer.
 */
public class ArtistShowsRepositoryImpl implements ArtistShowsRepository {

    ArtistShowsService artistShowsService;
    String mbid;

    public ArtistShowsRepositoryImpl(ArtistShowsService artistShowsService) {
        this.artistShowsService = artistShowsService;
    }

    /**
     * Returns an observable containing the artists previous shows.
     *
     * When querying the setlist.fm API for an artist the top results dont always return the
     * most popular artists with the particular name that was queried. This can occur as some
     * artists have the same name. To solve this when an artist is selected the name of the
     * artist is firstly queried by the spotify API to get the exact spelling of the requried
     * artist. Then that name is used to query the setlist.fm API for artists who have an
     * artist with that name. In order to get setlists from an exact artist, the artists
     * MusicBrainz identifier (MBID) is required. The MBID is extracted from the response that is
     * returned when the getArtist method is called from the {@link ArtistShowsService}.
     *
     * @param artistName name of the artist
     * @param p page number
     * @return the result of the getArtistShows method
     */
    @Override
    public Observable<ArtistShowsModel> getShows(String artistName, String p){
        return Observable.defer(() -> artistShowsService.getArtist(artistName, p).concatMap(
                searchArtist -> {
                    mbid = ArtistShowsUtil.getMbid(searchArtist, artistName);
                    if (!mbid.equals("")) {
                        return artistShowsService.getArtistShows(mbid, p);
                    } else {
                        return Observable.error(null);
                    }
                }));
    }

    /**
     * Returns the shows found from the query using the artists MBID. If the user has no internet
     * connection an IOException is thrown by retrofit.
     *
     * @param p page number
     * @return an observable containing the artists previous shows
     */
    @Override
    public Observable<ArtistShowsModel> getArtistShows(String p) {
        return Observable.defer(() -> artistShowsService.getArtistShows(mbid, p)).retryWhen(
                observable ->
                        observable.flatMap(o -> {
                            if (o instanceof IOException) {
                                return Observable.just(null);
                            }
                            return Observable.error(o);
                        }));
    }
}
