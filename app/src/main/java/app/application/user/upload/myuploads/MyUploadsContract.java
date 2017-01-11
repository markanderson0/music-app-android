package app.application.user.upload.myuploads;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.user.profile.videos.data.model.VideosModel;

/**
 * Specifies the contract between the {@link MyUploadsFragment} and {@link MyUploadsPresenter}.
 */
public interface MyUploadsContract {

    interface View extends MvpView {

        void showMyUploads(ArrayList<VideosModel> myUploads);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getMyUploads(Context context);

        ArrayList<VideosModel> getGridData(List<VideosModel> videos);
    }
}
