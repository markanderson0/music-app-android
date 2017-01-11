package app.application.artist.videos.data;

import android.content.Context;

import java.util.List;

import app.application.user.profile.videos.data.model.VideosModel;
import rx.Observable;

/**
 * Repository to return an artists videos
 */
public interface ArtistVideosRepository {
    Observable<List<VideosModel>> getVideos(Context context);
}
