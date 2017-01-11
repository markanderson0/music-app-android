package app.application.user.profile.videos.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.user.profile.videos.data.model.VideosModel;
import rx.Observable;

/**
 * Implementation of {@link VideosRepository}
 */
public class VideosRepositoryImpl implements VideosRepository {

    /**
     * Loads mock data from assets containing a users videos
     *
     * @param context Android context to access the assets
     * @return an observable containing a list of {@link VideosModel}s
     */
    @Override
    public Observable<List<VideosModel>> getVideos(Context context) {
        List<VideosModel> videos = new ArrayList<VideosModel>();
        try {
            InputStream inputStream = context.getAssets().open("mock-data/videos.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                VideosModel video = gson.fromJson(reader, VideosModel.class);
                videos.add(video);
            }
            reader.endArray();
            reader.close();
            return Observable.just(videos);
        } catch (IOException e) {
            throw new RuntimeException("This should never happen", e);
        }
    }
}
