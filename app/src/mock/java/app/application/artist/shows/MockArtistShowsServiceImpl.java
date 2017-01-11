package app.application.artist.shows;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.application.App;
import app.application.artist.shows.data.ArtistShowsService;
import retrofit2.http.Query;
import rx.Observable;

import static app.application.App.getContext;

/**
 * Mock implementation of the {@link ArtistShowsService}
 */
public class MockArtistShowsServiceImpl implements ArtistShowsService {
    @Override
    public Observable<Object> getArtist(@Query("artistName") String artistName, @Query("p") String p) {
        Object artist = new Object();
        try {
            InputStream inputStream = App.getContext().getAssets().open("mock-data/artist/shows/artistshowsArtistResponse.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                artist = gson.fromJson(reader, Object.class);
            }
            reader.endArray();
            reader.close();
            return Observable.just(artist);
        } catch (IOException e) {
            throw new RuntimeException("This should never happen", e);
        }
    }

    @Override
    public Observable<Object> getArtistShows(@Query("artistMbid") String artistMbid, @Query("p") String p) {
        Object shows = new Object();
        try {
            InputStream inputStream = getContext().getAssets().open("mock-data/artist/shows/artistshowsShowsResponse.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                shows = gson.fromJson(reader, Object.class);
            }
            reader.endArray();
            reader.close();
            return Observable.just(shows);
        } catch (IOException e) {
            throw new RuntimeException("This should never happen", e);
        }
    }
}
