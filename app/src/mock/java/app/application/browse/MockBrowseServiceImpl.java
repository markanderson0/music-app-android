package app.application.browse;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.application.browse.data.BrowseRepository;
import app.application.browse.data.model.BrowseModel;
import rx.Observable;

/**
 * Mock implementation of the {@link BrowseService}
 *
 * @note As the data for browsing is currently loaded locally this class currently implements
 * the repository instead of the service. This mock is currently named as a service to keep
 * a consistent naming pattern across all mocks and for when the service is used in the future.
 */
public class MockBrowseServiceImpl implements BrowseRepository {
    @Override
    public Observable<List<BrowseModel>> getArtists(Context context, String path) {
        if (path.equals("browseNavigation")) {
            return Observable.just(getBrowseNavigation());
        } else {
            return Observable.just(getBrowseGenre());
        }
    }

    public List<BrowseModel> getBrowseNavigation() {
        List<BrowseModel> browseModels = new ArrayList<>();
        browseModels.add(new BrowseModel("Genre", "picture"));
        return browseModels;
    }

    public List<BrowseModel> getBrowseGenre() {
        List<BrowseModel> browseModels = new ArrayList<>();
        browseModels.add(new BrowseModel("artist1", "picture"));
        return browseModels;
    }
}
