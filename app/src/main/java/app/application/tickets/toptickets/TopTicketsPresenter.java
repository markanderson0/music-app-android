package app.application.tickets.toptickets;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.shared.GridItem;
import app.application.base.BasePresenter;
import app.application.tickets.TicketsGridModel;
import app.application.tickets.toptickets.data.TopTicketsRepository;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link TopTicketsFragment}), retrieves the data and updates
 * the UI as required.
 */
public class TopTicketsPresenter extends BasePresenter<TopTicketsContract.View> implements TopTicketsContract.Presenter {

    public TopTicketsRepository topTicketsRepository;

    @Inject
    public TopTicketsPresenter(TopTicketsRepository topTicketsRepository) {
        this.topTicketsRepository = topTicketsRepository;
    }

    @Override
    public void getArtists(Context context) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(topTicketsRepository.getTopTickets(context).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TicketsGridModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page.");
                    }

                    @Override
                    public void onNext(List<TicketsGridModel> ticketsGridModel) {
                        getView().hideLoading();
                        getView().showTopTickets(getGridData(ticketsGridModel));
                    }
                }));
    }

    /**
     * Returns a list of {@link GridItem}s that contain data mapped from the list of
     * {@link TicketsGridModel}s that is returned from the api response
     *
     * @param artists list containing artist names and images
     * @return list of {@link GridItem}s to display in the UI
     */
    @Override
    public ArrayList<GridItem> getGridData(List<TicketsGridModel> artists) {
        ArrayList<GridItem> gridData = new ArrayList<>();
        GridItem item;
        for (int i = 0; i < artists.size(); i++) {
            item = new GridItem();
            item.setTitle(artists.get(i).name);
            item.setImage(artists.get(i).image);
            gridData.add(item);
        }
        return gridData;
    }
}
