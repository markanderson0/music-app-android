package app.application.artist.topvideos.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.artist.topvideos.data.model.TopVideosModel;
import rx.Observable;

/**
 * Implementation of {@link TopVideosRepository}
 */
public class TopVideosRepositoryImpl implements TopVideosRepository {

    /**
     * Loads mock data from assets containing an artists top videos (Currently not artist
     * specific).
     *
     * @param context Android context to access the assets
     * @return an observable containing a list of {@link TopVideosModel}s
     */
    @Override
    public Observable<List<TopVideosModel>> getTopVideos(Context context) {
        List<TopVideosModel> videos = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open("mock-data/topVideos.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                TopVideosModel genre = gson.fromJson(reader, TopVideosModel.class);
                videos.add(genre);
            }
            reader.endArray();
            reader.close();
            return Observable.just(videos);
        } catch (IOException e) {
            throw new RuntimeException("This should never happen", e);
        }
    }
}
