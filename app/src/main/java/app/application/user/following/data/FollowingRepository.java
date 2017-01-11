package app.application.user.following.data;

import android.content.Context;

import java.util.List;

import app.application.user.following.data.model.FollowingModel;
import rx.Observable;

/**
 * Repository to get the artists a user is following
 */
public interface FollowingRepository {
    Observable<List<FollowingModel>> getArtists(Context context);
}
