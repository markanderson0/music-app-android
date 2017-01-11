package app.application.tickets.ticketsgenre;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.shared.GridItem;
import app.application.base.BasePresenter;
import app.application.tickets.TicketsGridModel;
import app.application.tickets.ticketmodel.TicketsModel;
import app.application.tickets.ticketsgenre.data.TicketsGenreRepository;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link TicketsGenreFragment}), retrieves the data and updates
 * the UI as required.
 */
public class TicketsGenrePresenter extends BasePresenter<TicketsGenreContract.View> implements TicketsGenreContract.Presenter {

    public TicketsGenreRepository ticketsGenreRepository;
    private int pageNum;

    @Inject
    public TicketsGenrePresenter(TicketsGenreRepository ticketsGenreRepository) {
        this.ticketsGenreRepository = ticketsGenreRepository;
        pageNum = 0;
    }

    @Override
    public void getGridArtists(Context context, String genre) {
        checkViewAttached();
        getView().showLoading();
        Log.i("TicketsGenre", "Show Loading");
        addSubscription(ticketsGenreRepository.getGridArtists(context, genre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TicketsGridModel>>() {
                    @Override
                    public void onCompleted() {
                        getView().hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page.");
                    }

                    @Override
                    public void onNext(List<TicketsGridModel> ticketsGridModel) {
                        getView().hideLoading();
                        getView().showGridArtists(getGridItems(ticketsGridModel));
                    }
                }));
    }

    @Override
    public void getGenreArtists(String keyword) {
        checkViewAttached();
        getView().showLoading();
        Log.i("TicketsGenre", "Show Loading");
        addSubscription(ticketsGenreRepository.getGenreArtists(keyword, pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TicketsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(TicketsModel ticketsModel) {
                        pageNum++;
                        getView().hideLoading();
                        getView().showTableArtists(mapTableResults(ticketsModel, keyword));
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
    public ArrayList<GridItem> getGridItems(List<TicketsGridModel> artists) {
        ArrayList<GridItem> gridItems = new ArrayList<>();
        GridItem item;
        for (int i = 0; i < artists.size(); i++) {
            item = new GridItem();
            item.setTitle(artists.get(i).name);
            item.setImage(artists.get(i).image);
            gridItems.add(item);
        }
        return gridItems;
    }

    /**
     * Returns a list of strings that contain data mapped from the {@TicketsModel} that is returned
     * from the api reponse.
     *
     * @param genreArtists ticket data returned from the api response
     * @param genreSearch genre that has been searched
     * @return list of strings containing the names of the artists to display in the table
     */
    @Override
    public ArrayList<String> mapTableResults(TicketsModel genreArtists, String genreSearch) {
        ArrayList<String> tableArtists = new ArrayList<>();
        if(genreArtists._embedded != null) {
            for (int i = 0; i < genreArtists._embedded.events.size(); i++) {
                //if the genre has any events
                if (genreArtists._embedded.events.get(i)._embedded != null) {
                    //if the event has any attractions
                    if (genreArtists._embedded.events.get(i)._embedded.attractions != null) {
                        //if the attraction has a name
                        if (genreArtists._embedded.events.get(i)._embedded.attractions.get(0).name != null) {
                            //if the artist hasnt already been added
                            if ((!tableArtists.contains(genreArtists._embedded.events.get(i)._embedded.attractions.get(0).name))
                                //and the artist has a name
                                && (!genreArtists._embedded.events.get(i)._embedded.attractions.get(0).name.equals(""))
                                //and the classification of the event is music
                                && genreArtists._embedded.events.get(i).classifications.get(0).segment.name.equals("Music")) {
                                //if searching for festivals
                                if (genreSearch.equals("festival")) {
                                    //only add events with fest in their name
                                    if (genreArtists._embedded.events.get(i).name.toLowerCase().contains("fest")) {
                                        tableArtists.add(genreArtists._embedded.events.get(i)._embedded.attractions.get(0).name);
                                    }
                                } else {
                                    if (!genreArtists._embedded.events.get(i).name.toLowerCase().contains("fest")) {
                                        tableArtists.add(genreArtists._embedded.events.get(i)._embedded.attractions.get(0).name);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return tableArtists;
    }
}
