package app.application.browse.data;

import android.content.Context;

import java.util.List;

import app.application.browse.data.model.BrowseModel;
import rx.Observable;

/**
 * Repository to browse genres or artists
 */
public interface BrowseRepository {
    Observable<List<BrowseModel>> getArtists(Context context, String path);
}
