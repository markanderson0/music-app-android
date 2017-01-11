package app.application.dagger.component;

import javax.inject.Singleton;

import app.application.MainActivity;
import app.application.artist.albums.ArtistAlbumsFragment;
import app.application.artist.merch.ArtistMerchFragment;
import app.application.artist.shows.ArtistShowsFragment;
import app.application.artist.tickets.ArtistTicketsFragment;
import app.application.artist.topvideos.TopVideosFragment;
import app.application.artist.videos.ArtistVideosFragment;
import app.application.browse.BrowseActivity;
import app.application.browse.BrowseGenreActivity;
import app.application.dagger.module.AppModule;
import app.application.merch.merchcategory.MerchCategoryFragment;
import app.application.merch.merchsearch.MerchSearchActivity;
import app.application.search.SearchActivity;
import app.application.tickets.ticketsgenre.TicketsGenreFragment;
import app.application.tickets.ticketssearch.TicketsSearchActivity;
import app.application.tickets.toptickets.TopTicketsFragment;
import app.application.user.following.FollowingActivity;
import app.application.user.profile.favourites.FavouritesFragment;
import app.application.user.profile.following.FollowingFragment;
import app.application.user.profile.videos.VideosFragment;
import app.application.user.upload.myuploads.MyUploadsFragment;
import app.application.user.upload.uploadvideo.UploadVideoFragment;
import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(SearchActivity activity);
    void inject(FollowingActivity activity);

    //ProfileActivity
    void inject(FavouritesFragment fragment);
    void inject(FollowingFragment fragment);
    void inject(VideosFragment fragment);

    //UploadActivity
    void inject(MyUploadsFragment fragment);
    void inject(UploadVideoFragment fragment);

    //TicketsActivity
    void inject(TopTicketsFragment fragment);
    void inject(TicketsGenreFragment fragment);
    void inject(TicketsSearchActivity activity);

    //MerchActivity
    void inject(MerchCategoryFragment fragment);
    void inject(MerchSearchActivity activity);

    //BrowseActivity
    void inject(BrowseActivity activity);
    void inject(BrowseGenreActivity activity);

    //ArtistActivity
    void inject(TopVideosFragment fragment);
    void inject(ArtistVideosFragment fragment);
    void inject(ArtistTicketsFragment fragment);
    void inject(ArtistMerchFragment fragment);
    void inject(ArtistShowsFragment fragment);
    void inject(ArtistAlbumsFragment fragment);
}
