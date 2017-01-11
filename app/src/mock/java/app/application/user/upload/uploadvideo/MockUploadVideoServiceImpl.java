package app.application.user.upload.uploadvideo;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.application.user.upload.uploadvideo.data.UploadVideoService;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static app.application.App.context;

/**
 * Mock implementation of the {@link UploadVideoService}
 */
public class MockUploadVideoServiceImpl implements UploadVideoService {
    @Override
    public Observable<Object> getEvent(@Query("artistMbid") String artistMbid, @Query("year") String year, @Query("p") String p) {
        Object event = new Object();
        try {
            InputStream inputStream = context.getAssets().open("mock-data/user/upload/uploadEventResponse.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                event = gson.fromJson(reader, Object.class);
            }
            reader.endArray();
            reader.close();
            return Observable.just(event);
        } catch (IOException e) {
            throw new RuntimeException("This should never happen", e);
        }
    }

    @Override
    public Observable<Object> getSongs(@Path("eventId") String eventId) {
        Object songs = new Object();
        try {
            InputStream inputStream = context.getAssets().open("mock-data/user/upload/uploadSongsResponse.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            Gson gson = new Gson();
            while (reader.hasNext()) {
                songs = gson.fromJson(reader, Object.class);
            }
            reader.endArray();
            reader.close();
            return Observable.just(songs);
        } catch (IOException e) {
            throw new RuntimeException("This should never happen", e);
        }
    }
}
