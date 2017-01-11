package app.application.user.following;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.user.following.data.FollowingRepository;
import app.application.user.following.data.model.FollowingModel;
import rx.Observable;

/**
 * Mock implementation of the {@link FollowingService}
 *
 * @note As the data for following is currently loaded locally this class currently implements
 * the repository instead of the service. This mock is currently named as a service to keep
 * a consistent naming pattern across all mocks and for when the service is used in the future.
 */
public class MockFollowingServiceImpl implements FollowingRepository {

    @Override
    public Observable<List<FollowingModel>> getArtists(Context context) {
        List<FollowingModel> following = new ArrayList<>();
        following.add(new FollowingModel("artist3", "artist3image.jpg"));
        following.add(new FollowingModel("artist4", "artist4image.jpg"));
        return Observable.just(following);
    }
}
