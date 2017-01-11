package app.application.user.profile.favourites.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.user.profile.favourites.data.model.FavouritesModel;
import rx.Observable;

/**
 * Implementation of {@link FavouritesRepository}
 */
public class FavouritesRepositoryImpl implements FavouritesRepository {

    /**
     * Loads mock data from assets containing a users favourite videos
     *
     * @param context Android context to access the assets
     * @return an observable containing a list of {@link FavouritesModel}s
     */
    @Override
    public Observable<List<FavouritesModel>> getFavourites(Context context) {
        List<FavouritesModel> videos = new ArrayList<FavouritesModel>();
        try {
            InputStream inputStream = context.getAssets().open("mock-data/favourites.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                FavouritesModel video = gson.fromJson(reader, FavouritesModel.class);
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
