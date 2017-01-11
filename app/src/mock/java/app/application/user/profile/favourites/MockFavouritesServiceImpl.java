package app.application.user.profile.favourites;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.application.user.profile.favourites.data.FavouritesRepository;
import app.application.user.profile.favourites.data.model.FavouritesModel;
import app.application.user.profile.favourites.data.model.Video;
import rx.Observable;

/**
 * Mock implementation of the {@link FavouritesService}
 *
 * @note As the data for favourites is currently loaded locally this class currently implements
 * the repository instead of the service. This mock is currently named as a service to keep
 * a consistent naming pattern across all mocks and for when the service is used in the future.
 */
public class MockFavouritesServiceImpl implements FavouritesRepository {
    @Override
    public Observable<List<FavouritesModel>> getFavourites(Context context) {
        return Observable.just(getFavouritesModel());
    }

    private List<FavouritesModel> getFavouritesModel() {
        List<FavouritesModel> following = new ArrayList<>();
        List<Video> artist1videos = new ArrayList<>();
        artist1videos.add(new Video());
        following.add(getFavourite());
        return following;
    }

    private FavouritesModel getFavourite() {
        FavouritesModel favouritesModel = new FavouritesModel("artist1", getArtistVideos());
        favouritesModel.setId("1");
        favouritesModel.setImage("artist1image.jpg");
        favouritesModel.setArtistName("artist5");
        favouritesModel.setVideos(getArtistVideos());
        return favouritesModel;
    }

    private List<Video> getArtistVideos(){
        List<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setShowId("show");
        video.setDate("01-01-17");
        video.setVenue("venue");
        video.setLocation("location");
        video.setUser("testUser1");
        video.setPlaylistId("playlist");
        video.setVideoId("video");
        video.setFile("file");
        video.setImage("iamge");
        List<String> songs = new ArrayList<>();
        songs.add("song1");
        video.setSongs(songs);
        video.setSongsString("song1");
        video.setTime("20:00");
        video.setViews(100);
        video.setAudio(5);
        video.setVideo(7);
        videos.add(video);
        return videos;
    }
}
