package app.application.search;

import java.util.ArrayList;

import app.application.search.data.SearchService;
import app.application.search.data.model.Artists;
import app.application.search.data.model.Image;
import app.application.search.data.model.Item;
import app.application.search.data.model.SearchModel;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Mock implementation of the {@link SearchService}
 */
public class MockSearchServiceImpl implements SearchService {

    @Override
    public Observable<SearchModel> searchArtists(@Query("q") String name, @Query("type") String artist, @Query("limit") String limit) {
        SearchModel searchModel = new SearchModel();

        ArrayList<Image> artist1images = new ArrayList<>();
        artist1images.add(new Image("artist1image.jpg"));
        Item artist1 = new Item("1", "artist1", artist1images);

        ArrayList<Image> artist2images = new ArrayList<>();
        artist2images.add(new Image("artist2image.jpg"));
        Item artist2 = new Item("2", "artist2", artist2images);

        ArrayList<Item> items = new ArrayList<>();
        items.add(artist1);
        items.add(artist2);

        searchModel.setArtists(new Artists(items));
        return Observable.just(searchModel);
    }
}
