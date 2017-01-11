package app.application.user.upload.uploadvideo.data;

import rx.Observable;

/**
 * Repository to search for events and songs when uploading a video
 */
public interface UploadVideoRepository {
    Observable<Object> getEvent(String artistMbid, String year, String p);
    Observable<Object> getSongs(String eventId);
}
