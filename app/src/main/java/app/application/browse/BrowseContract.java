package app.application.browse;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.shared.GridItem;
import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.browse.data.model.BrowseModel;

/**
 * Specifies the contract between {@link BrowseActivity} / {@link BrowseGenreActivity}
 * and {@link BrowsePresenter}.
 */
public interface BrowseContract {

    interface View extends MvpView {

        void showGridData(ArrayList<GridItem> gridItems);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getArtists(Context context, String path);

        ArrayList<GridItem> parseResults(List<BrowseModel> artists);
    }
}
