package app.application.user.upload.myuploads;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.base.BasePresenter;
import app.application.user.profile.videos.data.model.Playlist;
import app.application.user.profile.videos.data.model.Show;
import app.application.user.profile.videos.data.model.Videos;
import app.application.user.profile.videos.data.model.VideosModel;
import app.application.user.upload.myuploads.data.MyUploadsRepository;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link MyUploadsFragment}), retrieves the data and updates
 * the UI as required.
 */
public class MyUploadsPresenter extends BasePresenter<MyUploadsContract.View> implements MyUploadsContract.Presenter {

    public MyUploadsRepository myUploadsRepository;

    @Inject
    public MyUploadsPresenter(MyUploadsRepository myUploadsRepository) {
        this.myUploadsRepository = myUploadsRepository;
    }

    @Override
    public void getMyUploads(Context context) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(myUploadsRepository.getMyUploads(context)
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
                        ArrayList<VideosModel> userVideos = getGridData(videosModel);
                        if (userVideos.size() == 0) {
                            getView().showError("You have not uploaded any videos.");
                        } else {
                            getView().showMyUploads(userVideos);
                        }
                    }

                }));
    }

    /**
     * Returns a list of {@link VideosModel}s. The ExpandableGroup class requires that
     * a title (String) and items (List)  are injected via the constructer of the
     * {@link VideosModel} in order to correctly display the expandable recyclerview on the UI.
     *
     * @param videos list of videos that the user has uploaded
     * @return list of {@link VideosModel}s to display in the UI
     */
    @Override
    public ArrayList<VideosModel> getGridData(List<VideosModel> videos) {
        ArrayList<VideosModel> userVideos = new ArrayList<>();
        ArrayList<Show> shows = new ArrayList<>();
        ArrayList<Playlist> playlists = new ArrayList<>();
        ArrayList<Videos> playlistVideos = new ArrayList<>();
        for (int i = 0; i < videos.size(); i++) {
            for (int j = 0; j < videos.get(i).shows.size(); j++) {
                for (int k = 0; k < videos.get(i).shows.get(j).videos.size(); k++) {
                    if (videos.get(i).shows.get(j).videos.get(k).getUser().equals("testUser1")) {
                        for (int l = 0; l < videos.get(i).shows.get(j).videos.get(k).video.size(); l++) {
                            Videos video = videos.get(i).shows.get(j).videos.get(k).getVideos().get(l);
                            playlistVideos.add(video);
                        }
                    }
                    Playlist playlist = videos.get(i).shows.get(j).videos.get(k);
                    playlists.add(playlist);
                    playlistVideos = new ArrayList<>();
                }
                Show show = new Show(videos.get(i).shows.get(j).date + " | " + videos.get(i).shows.get(j).venue + " | " + videos.get(i).shows.get(j).location, playlists);
                show.setVenue(videos.get(i).shows.get(j).venue);
                show.setLocation(videos.get(i).shows.get(j).location);
                show.setDate(videos.get(i).shows.get(j).date);
                show.setPlaylist(playlists);
                shows.add(show);
                playlists = new ArrayList<>();
            }
            VideosModel artist = new VideosModel(videos.get(i).artist, shows);
            artist.setArtist(videos.get(i).artist);
            artist.setImage(videos.get(i).image);
            artist.setShows(shows);
            userVideos.add(artist);
            shows = new ArrayList<>();
        }
        return userVideos;
    }
}
