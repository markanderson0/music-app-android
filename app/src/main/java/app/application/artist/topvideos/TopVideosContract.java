package app.application.artist.topvideos;

import android.content.Context;

import java.util.ArrayList;

import app.application.artist.topvideos.data.model.TopVideosModel;
import app.application.base.MvpPresenter;
import app.application.base.MvpView;

/**
 * Specifies the contract between the {@link TopVideosFragment} and {@link TopVideosPresenter}.
 */
public interface TopVideosContract {

    interface View extends MvpView {

        void showTopVideos(ArrayList<TopVideosModel> topVideos);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getTopVideos(Context context);
    }
}
