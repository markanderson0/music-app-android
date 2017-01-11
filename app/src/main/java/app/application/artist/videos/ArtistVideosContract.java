package app.application.artist.videos;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.user.profile.videos.data.model.Show;
import app.application.user.profile.videos.data.model.VideosModel;

/**
 * Specifies the contract between the {@link ArtistVideosFragment} and {@link ArtistVideosPresenter}.
 */
public interface ArtistVideosContract {

    interface View extends MvpView {

        void showVideos(ArrayList<Show> shows);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getVideos(Context context);

        ArrayList<Show> parseVideos(List<VideosModel> videos);
    }
}
