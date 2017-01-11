package app.application.artist.videos;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.artist.videos.data.ArtistVideosRepository;
import app.application.base.BasePresenter;
import app.application.user.profile.videos.data.model.Playlist;
import app.application.user.profile.videos.data.model.Show;
import app.application.user.profile.videos.data.model.Videos;
import app.application.user.profile.videos.data.model.VideosModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link ArtistVideosFragment}), retrieves the data and updates
 * the UI as required.
 */
public class ArtistVideosPresenter extends BasePresenter<ArtistVideosContract.View> implements ArtistVideosContract.Presenter {

    public ArtistVideosRepository artistVideosRepository;

    @Inject
    public ArtistVideosPresenter(ArtistVideosRepository artistVideosRepository) {this.artistVideosRepository = artistVideosRepository; }

    @Override
    public void getVideos(Context context) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(artistVideosRepository.getVideos(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<VideosModel>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    getView().hideLoading();
                    getView().showError("There was a problem loading this page.");
                }

                @Override
                public void onNext(List<VideosModel> videosModel) {
                    getView().hideLoading();
                    getView().showVideos(parseVideos(videosModel));
                }
            }));
    }

    /**
     * Returns a list of {@link Show}s. The ExpandableGroup class requires that
     * a title (String) and items (List)  are injected via the constructer of the
     * {@link Show} in order to correctly display the expandable recyclerview on the UI.
     *
     * @param videos list of videos that the user has uploaded
     * @return list of {@link Show}s to display in the UI
     */
    @Override
    public ArrayList<Show> parseVideos(List<VideosModel> videos) {
        Show show;
        ArrayList<Show> shows = new ArrayList<>();
        Playlist playlist;
        ArrayList<Playlist> playlists = new ArrayList<>();
        Videos video;
        ArrayList<Videos> playlistVideos = new ArrayList<>();
        for (int i = 0; i < videos.size(); i++) {
            for (int j = 0; j < videos.get(i).shows.size(); j++) {
                for (int k = 0; k < videos.get(i).shows.get(j).videos.size(); k++) {
                    if (videos.get(i).shows.get(j).videos.get(k).getUser().equals("testUser1")) {
                        for (int l = 0; l < videos.get(i).shows.get(j).videos.get(k).video.size(); l++) {
                            video = videos.get(i).shows.get(j).videos.get(k).getVideos().get(l);
                            playlistVideos.add(video);
                        }
                    }
                    playlist = videos.get(i).shows.get(j).videos.get(k);
                    playlists.add(playlist);
                    playlistVideos = new ArrayList<>();
                }
                show = new Show(videos.get(i).shows.get(j).date + ":" + videos.get(i).shows.get(j).venue + ":" + videos.get(i).shows.get(j).location, playlists);
                show.setVenue(videos.get(i).shows.get(j).venue);
                show.setLocation(videos.get(i).shows.get(j).location);
                show.setDate(videos.get(i).shows.get(j).date);
                show.setPlaylist(playlists);
                shows.add(show);
                playlists = new ArrayList<>();
            }
        }
        return shows;
    }
}
