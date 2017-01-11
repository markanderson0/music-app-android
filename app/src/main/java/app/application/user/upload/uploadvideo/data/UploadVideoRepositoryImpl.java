package app.application.user.upload.uploadvideo.data;

import java.io.IOException;

import rx.Observable;

/**
 * Implementation of {@link UploadVideoRepository}
 */
public class UploadVideoRepositoryImpl implements UploadVideoRepository {

    UploadVideoService uploadVideoService;

    public UploadVideoRepositoryImpl(UploadVideoService uploadVideoService) {
        this.uploadVideoService = uploadVideoService;
    }

    /**
     * Returns the events found from the query. If the user has no internet connection an
     * IOException is thrown by retrofit.
     *
     * @param artistMbid artists MusicBrainz identifier
     * @param year year that events took place
     * @param p page number
     * @return events for the given artists that happened on the requested year
     */
    @Override
    public Observable<Object> getEvent(String artistMbid, String year, String p) {
        return Observable.defer(() -> uploadVideoService.getEvent(artistMbid, year, p)).retryWhen(
                observable ->
                        observable.flatMap(o -> {
                            if (o instanceof IOException) {
                                return Observable.just(null);
                            }
                            return Observable.error(o);
                        }));
    }

    /**
     * Returns the songs found from the query. If the user has no internet connection an
     * IOException is thrown by retrofit.
     *
     * @param eventId id of the event to find
     * @return songs for the given event
     */
    @Override
    public Observable<Object> getSongs(String eventId) {
        return Observable.defer(() -> uploadVideoService.getSongs(eventId)).retryWhen(
                observable ->
                        observable.flatMap(o -> {
                            if (o instanceof IOException) {
                                return Observable.just(null);
                            }
                            return Observable.error(o);
                        }));
    }
}
