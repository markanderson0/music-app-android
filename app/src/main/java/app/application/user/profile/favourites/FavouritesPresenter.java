package app.application.user.profile.favourites;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.base.BasePresenter;
import app.application.user.profile.favourites.data.FavouritesRepository;
import app.application.user.profile.favourites.data.model.FavouritesModel;
import app.application.user.profile.favourites.data.model.Video;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link FavouritesFragment}), retrieves the data and updates
 * the UI as required.
 */
public class FavouritesPresenter extends BasePresenter<FavouritesContract.View> implements FavouritesContract.Presenter {

    public FavouritesRepository favouritesRepository;

    @Inject
    public FavouritesPresenter(FavouritesRepository favouritesRepository) { this.favouritesRepository = favouritesRepository; }

    @Override
    public void getFavourites(Context context) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(favouritesRepository.getFavourites(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FavouritesModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page.");
                    }

                    @Override
                    public void onNext(List<FavouritesModel> favouritesModel) {
                        getView().hideLoading();
                        ArrayList<FavouritesModel> favouriteVideos = getGridData(favouritesModel);
                        if(favouriteVideos.size() == 0){
                            getView().showError("You have not favourited any videos.");
                        } else {
                            getView().showFavourites(favouriteVideos);
                        }
                    }
                }));
    }

    /**
     * Returns a list of {@link FavouritesModel}s. The ExpandableGroup class requires that
     * a title (String) and items (List)  are injected via the constructer of the
     * {@link FavouritesModel} in order to correctly display the expandable recyclerview on the UI
     *
     * @param favourites videos that the user has favourited
     * @return list of {@link FavouritesModel}s to display in the UI
     */
    @Override
    public ArrayList<FavouritesModel> getGridData(List<FavouritesModel> favourites) {
        ArrayList<FavouritesModel> favouriteVideos = new ArrayList<>();
        ArrayList<Video> playlistVideos = new ArrayList<>();
        for (int i = 0; i < favourites.size(); i++) {
            for (int j = 0; j < favourites.get(i).videos.size(); j++) {
                Video video = favourites.get(i).getVideos().get(j);
                playlistVideos.add(video);
            }
            ArrayList<Video> itemVideo = new ArrayList<>();
            itemVideo.add(playlistVideos.get(0));
            FavouritesModel artist = new FavouritesModel(favourites.get(i).artistName, itemVideo);
            artist.setArtistName(favourites.get(i).artistName);
            artist.setImage(favourites.get(i).image);
            artist.setVideos(playlistVideos);
            favouriteVideos.add(artist);
            playlistVideos = new ArrayList<>();
        }
        return favouriteVideos;
    }
}
