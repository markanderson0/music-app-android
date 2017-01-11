package app.application.user.following;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.shared.GridItem;
import app.application.base.BasePresenter;
import app.application.user.following.data.FollowingRepository;
import app.application.user.following.data.model.FollowingModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UIs ({@link FollowingActivity} / {@link FollowingFragment}),
 * retrieves the data and updates the UI as required.
 */
public class FollowingPresenter extends BasePresenter<FollowingContract.View> implements FollowingContract.Presenter {

    private ArrayList<GridItem> gridData;
    public FollowingRepository followingRepository;

    @Inject
    public FollowingPresenter(FollowingRepository followingRepository) { this.followingRepository = followingRepository; }

    @Override
    public void getArtists(Context context) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(followingRepository.getArtists(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FollowingModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page.");
                    }

                    @Override
                    public void onNext(List<FollowingModel> followingModel) {
                        getView().hideLoading();
                        ArrayList<GridItem> gridData = getGridData(followingModel);
                        if(gridData.size() == 0) {
                            getView().showError("You are not following any artists");
                        } else {
                            getView().showResults(gridData);
                        }
                    }
                }));
    }

    /**
     * Returns a list of {@link GridItem}s that contains data mapped from the list of
     * {@link FollowingModel}s that is returned from the api response
     *
     * @param artists artist data returned from the local data
     * @return list of {@link GridItem}s to display in the UI
     */
    @Override
    public ArrayList<GridItem> getGridData(List<FollowingModel> artists) {
        ArrayList<GridItem> gridData = new ArrayList<>();
        GridItem item;
        for (int i = 0; i < artists.size(); i++) {
            item = new GridItem();
            item.setTitle(artists.get(i).name);
            item.setImage(artists.get(i).picture);
            gridData.add(item);
        }
        return gridData;
    }
}
