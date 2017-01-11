package app.application.search;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.shared.GridItem;
import app.application.base.BasePresenter;
import app.application.search.data.SearchRepository;
import app.application.search.data.model.SearchModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link SearchActivity}), retrieves the data and updates
 * the UI as required.
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    public SearchRepository searchRepository;

    @Inject
    public SearchPresenter(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public void searchArtists(String artistName) {
        checkViewAttached();
        getView().showLoading();
        searchRepository
                .searchArtists(artistName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchModel>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(SearchModel searchModel) {
                        getView().hideLoading();
                        ArrayList<GridItem> gridData = getGridData(searchModel);
                        if(gridData.size() == 0) {
                            getView().showError("No results for " + artistName + " found.");
                        } else {
                            getView().showResults(gridData);
                        }
                    }
                });
    }

    /**
     * Returns a list of {@link GridItem}s that contains data mapped from the {@link SearchModel}
     * that is returned from the api response
     *
     * @param searchModel artist data returned from the api request
     * @return list of {@link GridItem}s to display in the UI
     */
    @Override
    public ArrayList<GridItem> getGridData(SearchModel searchModel) {
        ArrayList<GridItem> gridData = new ArrayList<>();
        GridItem item;
        for (int i = 0; i < searchModel.artists.items.size(); i++) {
            item = new GridItem();
            item.setTitle(searchModel.artists.items.get(i).name);
            if (searchModel.artists.items.get(i).images.size() > 0) {
                if (searchModel.artists.items.get(i).images.size() > 1) {
                    item.setImage(searchModel.artists.items.get(i).images.get(1).url);
                } else if (searchModel.artists.items.get(i).images.size() == 1) {
                    item.setImage(searchModel.artists.items.get(i).images.get(0).url);
                } else {
                    item.setImage("http://freetwitterheaders.com/wp-content/uploads/2012/09/Gradient_02.jpg");
                }
            } else {
                item.setImage("http://freetwitterheaders.com/wp-content/uploads/2012/09/Gradient_02.jpg");
            }
            gridData.add(item);
        }
        return gridData;
    }
}
