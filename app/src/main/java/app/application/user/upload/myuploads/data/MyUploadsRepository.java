package app.application.user.upload.myuploads.data;

import android.content.Context;

import java.util.List;

import app.application.user.profile.videos.data.model.VideosModel;
import rx.Observable;

/**
 * Repository to return a users uploaded videos
 */
public interface MyUploadsRepository {
    Observable<List<VideosModel>> getMyUploads(Context context);
}
