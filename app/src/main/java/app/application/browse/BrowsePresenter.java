package app.application.browse;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.shared.GridItem;
import app.application.base.BasePresenter;
import app.application.browse.data.BrowseRepository;
import app.application.browse.data.model.BrowseModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UIs ({@link BrowseActivity} and {@link BrowseGenreActivity}),
 * retrieves the data and updates the UI as required.
 */
public class BrowsePresenter extends BasePresenter<BrowseContract.View> implements BrowseContract.Presenter {

    public BrowseRepository browseRepository;
    private ArrayList<GridItem> gridData;

    @Inject
    public BrowsePresenter(BrowseRepository browseRepository) {
        this.browseRepository = browseRepository;
        gridData = new ArrayList<>();
    }

    @Override
    public void getArtists(Context context, String path) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(browseRepository.getArtists(context, path).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BrowseModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page.");
                    }

                    @Override
                    public void onNext(List<BrowseModel> browseModel) {
                        getView().hideLoading();
                        getView().showGridData(parseResults(browseModel));
                    }
                }));
    }

    /**
     * Returns a list of {@link GridItem}s that contains data mapped from the list of
     * {@link BrowseModel}s that are returned from the api response
     *
     * @param artists list of artists or genres
     * @return list of {@link GridItem}s to display in the UI
     */
    @Override
    public ArrayList<GridItem> parseResults(List<BrowseModel> artists) {
        GridItem item;
        for (int i = 0; i < artists.size(); i++) {
            item = new GridItem();
            item.setTitle(artists.get(i).name);
            item.setImage(artists.get(i).pic);
            gridData.add(item);
        }
        return gridData;
    }
}
