package app.application.user.upload.uploadvideo.data;

import app.application.artist.shows.data.model.ArtistShowsModel;
import rx.Observable;

/**
 * Repository to search for events and songs when uploading a video
 */
public interface UploadVideoRepository {
    Observable<ArtistShowsModel> getEvent(String artistMbid, String year, String p);
    Observable<ArtistShowsModel> getSongs(String eventId);
}
