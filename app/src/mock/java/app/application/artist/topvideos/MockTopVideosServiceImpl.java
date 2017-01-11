package app.application.artist.topvideos;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.artist.topvideos.data.TopVideosRepository;
import app.application.artist.topvideos.data.model.TopVideosModel;
import rx.Observable;

/**
 * Mock implementation of the {@link TopVideosService}
 *
 * @note As the data for top videos is currently loaded locally this class currently implements
 * the repository instead of the service. This mock is currently named as a service to keep
 * a consistent naming pattern across all mocks and for when the service is used in the future.
 */
public class MockTopVideosServiceImpl implements TopVideosRepository {
    @Override
    public Observable<List<TopVideosModel>> getTopVideos(Context context) {
        List<TopVideosModel> topVideos = new ArrayList<>();
        TopVideosModel topVideo = new TopVideosModel();
        topVideo.setAudio(1);
        topVideo.setVideo(1);
        topVideo.setTime("20:00");
        topVideo.setViews(1);
        topVideo.setSongsString("song1");
        topVideo.setUser("testUser1");
        topVideo.setImage("image");
        topVideos.add(topVideo);
        return Observable.just(topVideos);
    }
}
