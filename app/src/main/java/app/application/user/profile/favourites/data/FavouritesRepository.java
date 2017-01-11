package app.application.user.profile.favourites.data;

import android.content.Context;

import java.util.List;

import app.application.user.profile.favourites.data.model.FavouritesModel;
import rx.Observable;

/**
 * Repository to return a users favourite videos
 */
public interface FavouritesRepository {
    Observable<List<FavouritesModel>> getFavourites(Context context);
}
