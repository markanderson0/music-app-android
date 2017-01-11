package app.application.search;

import java.util.ArrayList;

import app.application.shared.GridItem;
import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.search.data.model.SearchModel;

/**
 * Specifies the contract between the {@link SearchActivity} and {@link SearchPresenter}.
 */
public interface SearchContract {

    interface View extends MvpView{

        void showResults(ArrayList<GridItem> searchModel);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void searchArtists(String artistName);

        ArrayList<GridItem> getGridData(SearchModel searchModel);
    }

}
