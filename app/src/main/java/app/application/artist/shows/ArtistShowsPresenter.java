package app.application.artist.shows;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.artist.shows.data.ArtistShowsRepository;
import app.application.artist.shows.data.model.ArtistShowsModel;
import app.application.artist.shows.data.model.City;
import app.application.artist.shows.data.model.Coords;
import app.application.artist.shows.data.model.Country;
import app.application.artist.shows.data.model.Set;
import app.application.artist.shows.data.model.Setlist;
import app.application.artist.shows.data.model.Setlists;
import app.application.artist.shows.data.model.Sets;
import app.application.artist.shows.data.model.Venue;
import app.application.base.BasePresenter;
import app.application.search.data.SearchRepository;
import app.application.search.data.model.SearchModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.lang.Integer.parseInt;

/**
 * Listens to user actions from the UI ({@link ArtistShowsFragment}), retrieves the data and updates
 * the UI as required.
 */
public class ArtistShowsPresenter extends BasePresenter<ArtistShowsContract.View> implements ArtistShowsContract.Presenter {

    private ArtistShowsRepository artistShowsRepository;
    private SearchRepository searchRepository;
    private ArrayList<Setlist> artistShows;
    private int pageNum;
    int totalPages;
    String mbid;

    @Inject
    public ArtistShowsPresenter(ArtistShowsRepository artistShowsRepository, SearchRepository searchRepository) {
        this.artistShowsRepository = artistShowsRepository;
        this.searchRepository = searchRepository;
        pageNum = 1;
    }

    @Override
    public void getArtistName(String artistName) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(searchRepository.searchArtists(artistName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(SearchModel searchModel) {
                        String name = searchModel.getArtists().getItems().get(0).getName();
                        getArtist(name, String.valueOf(pageNum));
                    }
                }));
    }

    @Override
    public void getArtist(String artistName, String page) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(artistShowsRepository.getShows(artistName, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(Object searchArtistShows) {
                        getView().hideLoading();
                        getView().showShows(mapArtistShows(searchArtistShows));
                        getView().setTotalPages(totalPages);
                    }
                }));
    }

    @Override
    public void getArtistShows(String page) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(artistShowsRepository.getArtistShows(mbid, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(Object searchArtistShows) {
                        getView().hideLoading();
                        getView().setCurrentPage(page);
                        getView().showShows(mapArtistShows(searchArtistShows));
                    }
                }));
    }

    /**
     * Manually map the response Object from the setlist.fm api to a list of setlists.
     *
     * @param searchArtistShows response from the setlist.fm api
     * @return list of setlists
     */
    @Override
    public ArrayList<Setlist> mapArtistShows(Object searchArtistShows) {
        ArtistShowsModel artistShowsModel = new ArtistShowsModel();
        LinkedTreeMap artistShowsMap = (LinkedTreeMap) searchArtistShows;
        LinkedTreeMap setlistsMap = (LinkedTreeMap) artistShowsMap.get("setlists");
        ArrayList<LinkedTreeMap> setlistMap = (ArrayList<LinkedTreeMap>) setlistsMap.get("setlist");

        ArrayList<ArtistShowsModel> artistShowsList = new ArrayList<>();
        ArrayList<Setlist> setlistList = new ArrayList<>();
        Setlists setlists = new Setlists();

        getTotalPages(setlists, setlistsMap);
        getMBID(setlistMap);

        for (int i = 0; i < setlistMap.size(); i++) {
            Setlist setlist = new Setlist();
            setlist.setId((String) setlistMap.get(i).get("@id"));
            //if the show is already in the list of shows
            if (!artistShowsList.contains(setlist.getId())) {
                setlist.setTour((String) setlistMap.get(i).get("@tour"));
                setlist.setEventDate((String) setlistMap.get(i).get("@eventDate"));
                //if the setlist has a venue
                if (setlistMap.get(i).get("venue") != null) {
                    Venue venue = getVenue(setlistMap.get(i).get("venue"));

                    Set set = new Set();
                    set.setSong(ArtistShowsUtil.getSetlist(setlistMap.get(i).get("sets")));
                    ArrayList<Set> setsList = new ArrayList<>();
                    setsList.add(set);

                    Sets sets = new Sets();
                    sets.setSet(setsList);

                    setlist.setSets(sets);
                    setlist.setVenue(venue);
                    setlistList.add(setlist);
                }
                setlists.setSetlist(setlistList);
                artistShowsModel.setSetlists(setlists);
                artistShowsList.add(artistShowsModel);
            }
        }
        artistShows = (ArrayList<Setlist>) setlists.getSetlist();
        return artistShows;
    }

    private void getTotalPages(Setlists setlists, LinkedTreeMap setlistsMap) {
        setlists.setItemsPerPage((String) setlistsMap.get("@itemsPerPage"));
        setlists.setTotal((String) setlistsMap.get("@total"));
        totalPages = parseInt(setlists.getTotal()) / 20;
    }

    private void getMBID(ArrayList<LinkedTreeMap> setlistMap) {
        LinkedTreeMap artistMap = (LinkedTreeMap) setlistMap.get(0).get("artist");
        mbid = (String) artistMap.get("@mbid");
    }

    /**
     * Returns a {@link Venue} completely mapped from the corresponding Object with the required
     * data
     *
     * @param setlistVenue Object containing the venue data
     * @return fully mapped {@link Venue}
     */
    private Venue getVenue(Object setlistVenue){
        LinkedTreeMap venueMap = (LinkedTreeMap) setlistVenue;
        LinkedTreeMap cityMap = (LinkedTreeMap) venueMap.get("city");
        Venue venue = new Venue();
        venue.setName((String) venueMap.get("@name"));
        City city = new City();
        city.setName((String) cityMap.get("@name"));
        //if the city has a country
        if (cityMap.get("country") != null) {
            LinkedTreeMap countryMap = (LinkedTreeMap) cityMap.get("country");
            Country country = new Country();
            country.setName((String) countryMap.get("@name"));
            city.setCountry(country);
            //if the country is the United States get the state code
            if (country.getName().equals("United States") && cityMap.get("@stateCode") != null) {
                city.setStateCode((String) cityMap.get("@stateCode"));
            } else {
                city.setStateCode("");
            }
            //if the city has coordinates
            if (cityMap.get("coords") != null) {
                LinkedTreeMap coordsMap = (LinkedTreeMap) cityMap.get("coords");
                Coords coords = new Coords();
                coords.setLat((String) coordsMap.get("@lat"));
                coords.set_long((String) coordsMap.get("@long"));
                city.setCoords(coords);
            }
        }
        venue.setCity(city);
        return venue;
    }
}
