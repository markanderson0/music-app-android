package app.application.browse.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.browse.data.model.BrowseModel;
import rx.Observable;

/**
 * Implementation of {@link BrowseRepository}
 */
public class BrowseRepositoryImpl implements BrowseRepository {

    /**
     * Loads mock data from assets containing either the browse genres or the selected genres
     * artists
     *
     * @param context Android context to access the assets
     * @param path location in assets of the required data
     * @return an observable containing a list of {@link BrowseModel}s
     */
    @Override
    public Observable<List<BrowseModel>> getArtists(Context context, String path) {
        List<BrowseModel> genres = new ArrayList<BrowseModel>();
        try {
            InputStream inputStream = context.getAssets().open(path);
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                BrowseModel genre = gson.fromJson(reader, BrowseModel.class);
                genres.add(genre);
            }
            reader.endArray();
            reader.close();
            return Observable.just(genres);
        } catch (IOException e) {
            throw new RuntimeException("This should never happen", e);
        }
    }
}
