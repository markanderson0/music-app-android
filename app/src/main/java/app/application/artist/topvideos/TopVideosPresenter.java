package app.application.artist.topvideos;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.artist.topvideos.data.TopVideosRepository;
import app.application.artist.topvideos.data.model.TopVideosModel;
import app.application.base.BasePresenter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link TopVideosFragment}), retrieves the data and updates
 * the UI as required.
 */
public class TopVideosPresenter extends BasePresenter<TopVideosContract.View> implements TopVideosContract.Presenter {

    public TopVideosRepository topVideosRepository;

    @Inject
    public TopVideosPresenter(TopVideosRepository topVideosRepository) {
        this.topVideosRepository = topVideosRepository;
    }

    @Override
    public void getTopVideos(Context context) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(topVideosRepository.getTopVideos(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<TopVideosModel>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    getView().hideLoading();
                    getView().showError("There was a problem loading this page.");
                }

                @Override
                public void onNext(List<TopVideosModel> topVideosModel) {
                    getView().hideLoading();
                    getView().showTopVideos((ArrayList) topVideosModel);
                }
            }));
    }
}
