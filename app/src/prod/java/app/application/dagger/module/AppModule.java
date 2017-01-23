package app.application.dagger.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import app.application.artist.albums.data.ArtistAlbumsRepository;
import app.application.artist.albums.data.ArtistAlbumsRepositoryImpl;
import app.application.artist.albums.data.ArtistAlbumsService;
import app.application.artist.shows.data.ArtistShowsDeserializer;
import app.application.artist.shows.data.ArtistShowsRepository;
import app.application.artist.shows.data.ArtistShowsRepositoryImpl;
import app.application.artist.shows.data.ArtistShowsService;
import app.application.artist.shows.data.model.ArtistShowsModel;
import app.application.artist.topvideos.data.TopVideosRepository;
import app.application.artist.topvideos.data.TopVideosRepositoryImpl;
import app.application.artist.topvideos.data.TopVideosService;
import app.application.artist.videos.data.ArtistVideosRepository;
import app.application.artist.videos.data.ArtistVideosRepositoryImpl;
import app.application.artist.videos.data.ArtistVideosService;
import app.application.browse.data.BrowseRepository;
import app.application.browse.data.BrowseRepositoryImpl;
import app.application.browse.data.BrowseService;
import app.application.merch.data.MerchRepository;
import app.application.merch.data.MerchRepositoryImpl;
import app.application.merch.data.MerchService;
import app.application.search.data.SearchRepository;
import app.application.search.data.SearchRepositoryImpl;
import app.application.search.data.SearchService;
import app.application.tickets.ticketsgenre.data.TicketsGenreRepository;
import app.application.tickets.ticketsgenre.data.TicketsGenreRepositoryImpl;
import app.application.tickets.ticketsgenre.data.TicketsGenreService;
import app.application.tickets.ticketssearch.data.TicketsSearchRepository;
import app.application.tickets.ticketssearch.data.TicketsSearchRepositoryImpl;
import app.application.tickets.ticketssearch.data.TicketsSearchService;
import app.application.tickets.toptickets.data.TopTicketsRepository;
import app.application.tickets.toptickets.data.TopTicketsRepositoryImpl;
import app.application.tickets.toptickets.data.TopTicketsService;
import app.application.user.following.data.FollowingRepository;
import app.application.user.following.data.FollowingRepositoryImpl;
import app.application.user.following.data.FollowingService;
import app.application.user.profile.favourites.data.FavouritesRepository;
import app.application.user.profile.favourites.data.FavouritesRepositoryImpl;
import app.application.user.profile.favourites.data.FavouritesService;
import app.application.user.profile.videos.data.VideosRepository;
import app.application.user.profile.videos.data.VideosRepositoryImpl;
import app.application.user.profile.videos.data.VideosService;
import app.application.user.upload.myuploads.data.MyUploadsRepository;
import app.application.user.upload.myuploads.data.MyUploadsRepositoryImpl;
import app.application.user.upload.myuploads.data.MyUploadsService;
import app.application.user.upload.uploadvideo.data.UploadVideoRepository;
import app.application.user.upload.uploadvideo.data.UploadVideoRepositoryImpl;
import app.application.user.upload.uploadvideo.data.UploadVideoService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    Application application;
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
    SearchRepository provideSearchRepository() {
        return new SearchRepositoryImpl(provideSearchService());
    }

    static SearchService provideSearchService() {
        if (searchService == null) {
            searchService = getRetrofitInstance("https://api.spotify.com").create(SearchService.class);
        }
        return searchService;
    }

    //ProfileActivity

    @Provides
    @Singleton
    FollowingRepository provideFollowingRepository() { return new FollowingRepositoryImpl(); }

    @Provides
    @Singleton
    FavouritesRepository provideFavouritesRepository() {
        return new FavouritesRepositoryImpl();
    }

    @Provides
    @Singleton
    VideosRepository provideVideosRepository() { return new VideosRepositoryImpl(); }

    //UploadActivity

    @Provides
    @Singleton
    MyUploadsRepository provideMyUploadsRepository() { return new MyUploadsRepositoryImpl(); }

    @Provides
    @Singleton
    UploadVideoRepository provideUploadVideoRepository() {
        return new UploadVideoRepositoryImpl(provideUploadVideoService());
    }

    static UploadVideoService provideUploadVideoService() {
        if (uploadVideoService == null) {
            uploadVideoService = getShowsRetrofitInstance("http://api.setlist.fm").create(UploadVideoService.class);
        }
        return uploadVideoService;
    }

    //TicketsActivity

    @Provides
    @Singleton
    TopTicketsRepository provideTopTicketsRepository() { return new TopTicketsRepositoryImpl(); }

    @Provides
    @Singleton
    TicketsGenreRepository provideTicketsGenreRepository() {
        return new TicketsGenreRepositoryImpl(provideTicketsGenreService());
    }

    static TicketsGenreService provideTicketsGenreService() {
        if (ticketsGenreService == null) {
            ticketsGenreService = getRetrofitInstance("https://app.ticketmaster.com").create(TicketsGenreService.class);
        }
        return ticketsGenreService;
    }

    @Provides
    @Singleton
    TicketsSearchRepository provideTicketsSearchRepository() {
        return new TicketsSearchRepositoryImpl(provideTicketsSearchService());
    }

    static TicketsSearchService provideTicketsSearchService() {
        if (ticketsSearchService == null) {
            ticketsSearchService = getRetrofitInstance("https://app.ticketmaster.com").create(TicketsSearchService.class);
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
            merchService = getRetrofitInstance("http://svcs.ebay.com").create(MerchService.class);
        }
        return merchService;
    }

    //BrowseActivity

    @Provides
    @Singleton
    BrowseRepository provideBrowseRepository() { return new BrowseRepositoryImpl(); }

    //ArtistActivity

    @Provides
    @Singleton
    TopVideosRepository provideTopVideosRepository() { return new TopVideosRepositoryImpl(); }

    @Provides
    @Singleton
    ArtistVideosRepository provideArtistVideosRepository() { return new ArtistVideosRepositoryImpl(); }

    @Provides
    @Singleton
    ArtistShowsRepository provideArtistShowsRepository() {
        return new ArtistShowsRepositoryImpl(provideArtistShowsService());
    }

    static ArtistShowsService provideArtistShowsService() {
        if (artistShowsService == null) {
            artistShowsService = getShowsRetrofitInstance("http://api.setlist.fm").create(ArtistShowsService.class);
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
            artistAlbumsService = getRetrofitInstance("https://api.spotify.com").create(ArtistAlbumsService.class);
        }
        return artistAlbumsService;
    }

    static Retrofit getRetrofitInstance(String baseUrl) {
        Retrofit.Builder retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return retrofit.build();
    }

    static Retrofit getShowsRetrofitInstance(String baseUrl) {
        Retrofit.Builder retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(buildGsonConverter())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return retrofit.build();
    }

    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Adding custom deserializers
        gsonBuilder.registerTypeAdapter(ArtistShowsModel.class, new ArtistShowsDeserializer());
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);
    }
}
