package app.application.user.following;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.shared.GridItem;
import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.user.following.data.model.FollowingModel;

/**
 * Specifies the contract between the {@link FollowingActivity} / {@link FollowingFragment} and {@link FollowingPresenter}.
 */
public interface FollowingContract {

    interface View extends MvpView {

        void showResults(ArrayList<GridItem> followingModel);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getArtists(Context context);

        ArrayList<GridItem> getGridData(List<FollowingModel> artists);
    }
}
