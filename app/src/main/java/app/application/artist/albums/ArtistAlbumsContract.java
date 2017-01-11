package app.application.artist.albums;

import java.util.List;

import app.application.artist.albums.data.models.artistalbumtracksmodel.Album;
import app.application.base.MvpPresenter;
import app.application.base.MvpView;

/**
 * Specifies the contract between the {@link ArtistAlbumsFragment} and {@link ArtistAlbumsPresenter}.
 */
public interface ArtistAlbumsContract {

    interface View extends MvpView {

        void showAlbums(List<Album> albums);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getAlbums(String name, String type, String limit);
    }
}
