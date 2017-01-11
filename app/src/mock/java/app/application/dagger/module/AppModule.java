package app.application.dagger.module;

import javax.inject.Singleton;

import app.application.artist.albums.MockArtistAlbumsServiceImpl;
import app.application.artist.albums.data.ArtistAlbumsRepository;
import app.application.artist.albums.data.ArtistAlbumsRepositoryImpl;
import app.application.artist.albums.data.ArtistAlbumsService;
import app.application.artist.shows.MockArtistShowsServiceImpl;
import app.application.artist.shows.data.ArtistShowsRepository;
import app.application.artist.shows.data.ArtistShowsRepositoryImpl;
import app.application.artist.shows.data.ArtistShowsService;
import app.application.artist.topvideos.MockTopVideosServiceImpl;
import app.application.artist.topvideos.data.TopVideosRepository;
import app.application.artist.topvideos.data.TopVideosService;
import app.application.artist.videos.MockArtistVideosServiceImpl;
import app.application.artist.videos.data.ArtistVideosRepository;
import app.application.artist.videos.data.ArtistVideosService;
import app.application.browse.MockBrowseServiceImpl;
import app.application.browse.data.BrowseRepository;
import app.application.browse.data.BrowseService;
import app.application.merch.MockMerchServiceImpl;
import app.application.merch.data.MerchRepository;
import app.application.merch.data.MerchRepositoryImpl;
import app.application.merch.data.MerchService;
import app.application.search.MockSearchServiceImpl;
import app.application.search.data.SearchRepository;
import app.application.search.data.SearchRepositoryImpl;
import app.application.search.data.SearchService;
import app.application.tickets.ticketsgenre.MockTicketsGenreServiceImpl;
import app.application.tickets.ticketsgenre.data.TicketsGenreRepository;
import app.application.tickets.ticketsgenre.data.TicketsGenreService;
import app.application.tickets.ticketssearch.MockTicketsSearchServiceImpl;
import app.application.tickets.ticketssearch.data.TicketsSearchRepository;
import app.application.tickets.ticketssearch.data.TicketsSearchRepositoryImpl;
import app.application.tickets.ticketssearch.data.TicketsSearchService;
import app.application.tickets.toptickets.MockTopTicketsServiceImpl;
import app.application.tickets.toptickets.data.TopTicketsRepository;
import app.application.tickets.toptickets.data.TopTicketsService;
import app.application.user.following.MockFollowingServiceImpl;
import app.application.user.following.data.FollowingRepository;
import app.application.user.following.data.FollowingService;
import app.application.user.profile.favourites.MockFavouritesServiceImpl;
import app.application.user.profile.favourites.data.FavouritesRepository;
import app.application.user.profile.favourites.data.FavouritesService;
import app.application.user.profile.videos.MockVideosServiceImpl;
import app.application.user.profile.videos.data.VideosRepository;
import app.application.user.profile.videos.data.VideosService;
import app.application.user.upload.myuploads.MockMyUploadsServiceImpl;
import app.application.user.upload.myuploads.data.MyUploadsRepository;
import app.application.user.upload.myuploads.data.MyUploadsService;
import app.application.user.upload.uploadvideo.MockUploadVideoServiceImpl;
import app.application.user.upload.uploadvideo.data.UploadVideoRepository;
import app.application.user.upload.uploadvideo.data.UploadVideoRepositoryImpl;
import app.application.user.upload.uploadvideo.data.UploadVideoService;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private static SearchService searchService;
    private static FollowingService followingService;
    private static FavouritesService favouritesService;
    private static VideosService videosService;
    private static MyUploadsService myUploadsService;
    private static UploadVideoService uploadVideoService;
    private static TopTicketsService topTicketsService;
    private static TicketsGenreService ticketsGenreService;
    private static TicketsSearchService ticketsSearchService;
    private static MerchService merchService;
    private static BrowseService browseService;
    private static TopVideosService topVideosService;
    private static ArtistVideosService artistVideosService;
    private static ArtistShowsService artistShowsService;
    private static ArtistAlbumsService artistAlbumsService;

    @Provides
    @Singleton
    SearchRepository provideSearchRepository() { return new SearchRepositoryImpl(provideSearchService()); }

    static SearchService provideSearchService() {
        if (searchService == null) {
            searchService = new MockSearchServiceImpl();
        }
        return searchService;
    }

    //ProfileActivity

    @Provides
    @Singleton
    FollowingRepository provideFollowingRepository() {
        return new MockFollowingServiceImpl();
    }

    @Provides
    @Singleton
    FavouritesRepository provideFavouritesRepository() {
        return new MockFavouritesServiceImpl();
    }

    @Provides
    @Singleton
    VideosRepository provideVideosRepository() { return new MockVideosServiceImpl(); }

    //UploadActivity

    @Provides
    @Singleton
    MyUploadsRepository provideMyUploadsRepository() { return new MockMyUploadsServiceImpl(); }

    @Provides
    @Singleton
    UploadVideoRepository provideUploadVideoRepository() {
        return new UploadVideoRepositoryImpl(provideUploadVideoService());
    }

    static UploadVideoService provideUploadVideoService() {
        if (uploadVideoService == null) {
            uploadVideoService = new MockUploadVideoServiceImpl();
        }
        return uploadVideoService;
    }

    //TicketsActivity

    @Provides
    @Singleton
    TopTicketsRepository provideTopTicketsRepository() { return new MockTopTicketsServiceImpl(); }

    @Provides
    @Singleton
    TicketsGenreRepository provideTicketsGenreRepository() {
        return new MockTicketsGenreServiceImpl();
    }

    @Provides
    @Singleton
    TicketsSearchRepository provideTicketsSearchRepository() {
        return new TicketsSearchRepositoryImpl(provideTicketsSearchService());
    }

    static TicketsSearchService provideTicketsSearchService() {
        if (ticketsSearchService == null) {
            ticketsSearchService = new MockTicketsSearchServiceImpl();
        }
        return ticketsSearchService;
    }

    //MerchActivity

    @Provides
    @Singleton
    MerchRepository provideMerchRepository() {
        return new MerchRepositoryImpl(provideMerchService());
    }

    static MerchService provideMerchService() {
        if (merchService == null) {
            merchService = new MockMerchServiceImpl();
        }
        return merchService;
    }

    //BrowseActivity

    @Provides
    @Singleton
    BrowseRepository provideBrowseRepository() { return new MockBrowseServiceImpl(); }

    //ArtistActivity

    @Provides
    @Singleton
    TopVideosRepository provideTopVideosRepository() { return new MockTopVideosServiceImpl(); }

    @Provides
    @Singleton
    ArtistVideosRepository provideArtistVideosRepository() { return new MockArtistVideosServiceImpl(); }

    @Provides
    @Singleton
    ArtistShowsRepository provideArtistShowsRepository() {
        return new ArtistShowsRepositoryImpl(provideArtistShowsService());
    }

    static ArtistShowsService provideArtistShowsService() {
        if (artistShowsService == null) {
            artistShowsService = new MockArtistShowsServiceImpl();
        }
        return artistShowsService;
    }

    @Provides
    @Singleton
    ArtistAlbumsRepository provideArtistAlbumsRepository() {
        return new ArtistAlbumsRepositoryImpl(provideArtistAlbumsService());
    }

    static ArtistAlbumsService provideArtistAlbumsService() {
        if (artistAlbumsService == null) {
            artistAlbumsService = new MockArtistAlbumsServiceImpl();
        }
        return artistAlbumsService;
    }
}
