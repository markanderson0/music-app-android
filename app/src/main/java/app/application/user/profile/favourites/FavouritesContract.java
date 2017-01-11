package app.application.user.profile.favourites;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.user.profile.favourites.data.model.FavouritesModel;

/**
 * Specifies the contract between the {@link FavouritesFragment} and {@link FavouritesPresenter}.
 */
public interface FavouritesContract {

    interface View extends MvpView {

        void showFavourites(List<FavouritesModel> favouriteVideos);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getFavourites(Context context);

        ArrayList<FavouritesModel> getGridData(List<FavouritesModel> favourites);
    }
}
