package app.application.user.profile.videos;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.user.profile.videos.data.model.VideosModel;

/**
 * Specifies the contract between the {@link VideosFragment} and {@link VideosPresenter}.
 */
public interface VideosContract {

    interface View extends MvpView {

        void showVideos(ArrayList<VideosModel> videos);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getVideos(Context context);

        ArrayList<VideosModel> getGridData(List<VideosModel> videos);
    }
}
