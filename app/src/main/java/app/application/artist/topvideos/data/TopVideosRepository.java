package app.application.artist.topvideos.data;

import android.content.Context;

import java.util.List;

import app.application.artist.topvideos.data.model.TopVideosModel;
import rx.Observable;

/**
 * Repository to return an artists top videos
 */
public interface TopVideosRepository {
    Observable<List<TopVideosModel>> getTopVideos(Context context);
}
