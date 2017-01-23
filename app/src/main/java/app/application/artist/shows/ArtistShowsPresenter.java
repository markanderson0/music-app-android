package app.application.artist.shows;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.artist.shows.data.ArtistShowsRepository;
import app.application.artist.shows.data.model.ArtistShowsModel;
import app.application.artist.shows.data.model.Setlist;
import app.application.base.BasePresenter;
import app.application.search.data.SearchRepository;
import app.application.search.data.model.SearchModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.lang.Integer.parseInt;

/**
 * Listens to user actions from the UI ({@link ArtistShowsFragment}), retrieves the data and updates
 * the UI as required.
 */
public class ArtistShowsPresenter extends BasePresenter<ArtistShowsContract.View> implements ArtistShowsContract.Presenter {

    private ArtistShowsRepository artistShowsRepository;
    private SearchRepository searchRepository;

    @Inject
    public ArtistShowsPresenter(ArtistShowsRepository artistShowsRepository, SearchRepository searchRepository) {
        this.artistShowsRepository = artistShowsRepository;
        this.searchRepository = searchRepository;
    }

    @Override
    public void getArtistName(String artistName) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(searchRepository.searchArtists(artistName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(SearchModel searchModel) {
                        String name = searchModel.getArtists().getItems().get(0).getName();
                        getArtist(name, "1");
                    }
                }));
    }

    @Override
    public void getArtist(String artistName, String page) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(artistShowsRepository.getShows(artistName, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArtistShowsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(ArtistShowsModel searchArtistShows) {
                        getView().hideLoading();
                        getView().showShows((ArrayList<Setlist>) searchArtistShows.getSetlists().getSetlist());
                        getView().setTotalPages(parseInt(searchArtistShows.getSetlists().getTotal()) / 20);
                    }
                }));
    }

    @Override
    public void getArtistShows(String page) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(artistShowsRepository.getArtistShows(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArtistShowsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(ArtistShowsModel searchArtistShows) {
                        getView().hideLoading();
                        getView().setCurrentPage(page);
                        getView().showShows((ArrayList<Setlist>) searchArtistShows.getSetlists().getSetlist());
                    }
                }));
    }
}
