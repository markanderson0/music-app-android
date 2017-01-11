package app.application.artist.albums;

import java.util.List;

import javax.inject.Inject;

import app.application.artist.albums.data.ArtistAlbumsRepository;
import app.application.artist.albums.data.models.artistalbumtracksmodel.Album;
import app.application.base.BasePresenter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link ArtistAlbumsFragment}), retrieves the data and updates
 * the UI as required.
 */
public class ArtistAlbumsPresenter extends BasePresenter<ArtistAlbumsContract.View> implements ArtistAlbumsContract.Presenter {

    ArtistAlbumsRepository artistAlbumsRepository;

    @Inject
    public ArtistAlbumsPresenter(ArtistAlbumsRepository artistAlbumsRepository) {
        this.artistAlbumsRepository = artistAlbumsRepository;
    }

    @Override
    public void getAlbums(String name, String type, String limit){
        checkViewAttached();
        getView().showLoading();
        addSubscription(artistAlbumsRepository.getAlbums(name, type, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Album>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    getView().hideLoading();
                    getView().showError("There was a problem loading this page");
                }

                @Override
                public void onNext(List<Album> albums) {
                    getView().hideLoading();
                    getView().showAlbums(albums);
                }
            }));
    }
}
