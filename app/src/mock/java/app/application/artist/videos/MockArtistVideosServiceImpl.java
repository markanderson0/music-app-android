package app.application.artist.videos;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.artist.videos.data.ArtistVideosRepository;
import app.application.user.profile.videos.data.model.Playlist;
import app.application.user.profile.videos.data.model.Show;
import app.application.user.profile.videos.data.model.Videos;
import app.application.user.profile.videos.data.model.VideosModel;
import rx.Observable;

/**
 * Mock implementation of the {@link ArtistVideosService}
 *
 * @note As the data for artist videos is currently loaded locally this class currently implements
 * the repository instead of the service. This mock is currently named as a service to keep
 * a consistent naming pattern across all mocks and for when the service is used in the future.
 */
public class MockArtistVideosServiceImpl implements ArtistVideosRepository {
    @Override
    public Observable<List<VideosModel>> getVideos(Context context) {
        return Observable.just(getVideosModel());
    }

    private List<VideosModel> getVideosModel() {
        List<VideosModel> videos = new ArrayList<>();
        VideosModel videosModel = new VideosModel("title", getDummyShows());
        videosModel.setId("videoId");
        videosModel.setArtist("artist");
        videosModel.setImage("image");
        videosModel.setShows(getDummyShows());
        videos.add(videosModel);
        return videos;
    }

    private List<Show> getDummyShows() {
        List<Show> shows = new ArrayList<>();
        Show show = new Show("show", getDummyPlaylists());
        show.setId("showId");
        show.setDate("date");
        show.setLocation("city1, country1");
        show.setVenue("venue");
        show.setPlaylist(getDummyPlaylists());
        shows.add(show);
        return shows;
    }

    private List<Playlist> getDummyPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(new Playlist("testUser1", "playlistId", getDummyVideos()));
        return playlists;
    }

    private List<Videos> getDummyVideos() {
        List<Videos> videos = new ArrayList<>();
        List<String> songs = new ArrayList<>();
        songs.add("song2");
        Videos video = new Videos(
                "id",
                "file",
                "image",
                songs,
                "song2",
                "21:00",
                2,
                2,
                2
        );
        videos.add(video);
        return videos;
    }
}
