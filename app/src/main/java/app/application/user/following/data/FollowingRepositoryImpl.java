package app.application.user.following.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.user.following.data.model.FollowingModel;
import rx.Observable;

/**
 * Implementation of {@link FollowingRepository}
 */
public class FollowingRepositoryImpl implements FollowingRepository {

    /**
     * Loads mock data from assets containing the artists that a user is following
     *
     * @param context
     * @return an observable containing a list of {@link FollowingModel}s
     */
    @Override
    public Observable<List<FollowingModel>> getArtists(Context context) {
        List<FollowingModel> artists = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open("mock-data/following.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                FollowingModel artist = gson.fromJson(reader, FollowingModel.class);
                artists.add(artist);
            }
            reader.endArray();
            reader.close();
            return Observable.just(artists);
        } catch (IOException e) {
            throw new RuntimeException("This should never happen", e);
        }
    }
}
