package app.application.artist.albums.data;

import java.util.ArrayList;
import java.util.List;

import app.application.artist.albums.data.models.artistalbumsmodel.ArtistAlbumsModel;
import app.application.artist.albums.data.models.artistalbumtracksmodel.Album;
import rx.Observable;

/**
 * Implementation of {@link ArtistAlbumsRepository}
 */
public class ArtistAlbumsRepositoryImpl implements ArtistAlbumsRepository {

    ArtistAlbumsService artistAlbumsService;

    public ArtistAlbumsRepositoryImpl(ArtistAlbumsService artistAlbumsService) {
        this.artistAlbumsService = artistAlbumsService;
    }

    @Override
    public Observable<List<Album>> getAlbums(String name, String artist, String limit){
        return Observable.defer(() -> artistAlbumsService.getArtistId(name, artist, limit).concatMap(
                artistIdModel ->
                    artistAlbumsService.getArtistAlbums(artistIdModel.artists.items.get(0).id, "album").concatMap(
                        artistAlbumsModel ->
                            artistAlbumsService.getAlbumTracks(getAlbumIds(artistAlbumsModel)).concatMap(
                                artistAlbumTracksModel ->
                                    Observable.just(artistAlbumTracksModel.getAlbums())
                            )
                    )
                ));
    }

    /**
     * Extracts the album ids from the getArtistAlbums response. The album ids are used to
     * query for a specific album in order to get its tracks.
     *
     * @param artistAlbumsModel artists albums
     * @return list of album ids
     */
    private String getAlbumIds(ArtistAlbumsModel artistAlbumsModel) {
        ArrayList<String> albumNames = new ArrayList<>();
        String ids = "";
        int albums = artistAlbumsModel.items.size();
        if(albums >= 20){
            albums = 20;
        }
        for(int i = 0; i < albums; i++){
            if(!albumNames.contains(artistAlbumsModel.items.get(i).name)) {
                albumNames.add(artistAlbumsModel.items.get(i).name);
                ids += artistAlbumsModel.items.get(i).id + ",";
            }
        }
        ids = ids.substring(0, ids.length()-1);
        return ids;
    }
}
