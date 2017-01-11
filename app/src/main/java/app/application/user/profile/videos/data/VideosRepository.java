package app.application.user.profile.videos.data;

import android.content.Context;

import java.util.List;

import app.application.user.profile.videos.data.model.VideosModel;
import rx.Observable;

/**
 * Repository to return a users videos
 */
public interface VideosRepository {
    Observable<List<VideosModel>> getVideos(Context context);
}
