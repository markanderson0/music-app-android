package app.application.user.upload.uploadvideo;

import java.util.ArrayList;
import java.util.List;

import app.application.artist.shows.data.model.Artist;
import app.application.artist.shows.data.model.ArtistShowsModel;
import app.application.artist.shows.data.model.City;
import app.application.artist.shows.data.model.Coords;
import app.application.artist.shows.data.model.Country;
import app.application.artist.shows.data.model.Set;
import app.application.artist.shows.data.model.Setlist;
import app.application.artist.shows.data.model.Setlists;
import app.application.artist.shows.data.model.Sets;
import app.application.artist.shows.data.model.Song;
import app.application.artist.shows.data.model.Venue;
import app.application.user.upload.uploadvideo.data.UploadVideoService;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Mock implementation of the {@link UploadVideoService}
 */
public class MockUploadVideoServiceImpl implements UploadVideoService {
    @Override
    public Observable<ArtistShowsModel> getEvent(@Query("artistMbid") String artistMbid, @Query("year") String year, @Query("p") String p) {
        return Observable.just(getArtistShowsModel());
    }

    @Override
    public Observable<ArtistShowsModel> getSongs(@Path("eventId") String eventId) {
        return Observable.just(getArtistShowsModel());
    }

    private ArtistShowsModel getArtistShowsModel() {
        return new ArtistShowsModel(getSetlists());
    }

    private Setlists getSetlists() {
        return new Setlists(getSetlistList(), "1", "1");
    }

    private List<Setlist> getSetlistList() {
        List<Setlist> setlists = new ArrayList<>();
        setlists.add(new Setlist(
                "123",
                "tour",
                "01-01-2017",
                getVenue(),
                getSetlistArtist(),
                getArtist1Sets()
        ));
        setlists.add(new Setlist(
                "234",
                "tour",
                "02-01-2017",
                getVenue(),
                getSetlistArtist(),
                getArtist2Sets()
        ));
        return setlists;
    }

    private Venue getVenue() {
        return new Venue("venue", getCity());
    }

    private City getCity() {
        return new City(
                "city",
                "cityState",
                new Country("country"),
                new Coords("1", "1")
        );
    }

    private Artist getSetlistArtist() {
        return new Artist("artist1", "sortName", "mbid");
    }

    private Sets getArtist1Sets() {
        return new Sets(getArtist1SetList());
    }

    private List<Set> getArtist1SetList() {
        List<Set> setList = new ArrayList<>();
        setList.add(new Set(getArtist1Songs(), ""));
        return setList;
    }

    private List<Song> getArtist1Songs() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("song1", null, null, ""));
        songs.add(new Song("song2", null, null, ""));
        songs.add(new Song("song3", null, null, ""));
        return songs;
    }

    private Sets getArtist2Sets() {
        return new Sets(getArtist2SetList());
    }

    private List<Set> getArtist2SetList() {
        List<Set> setList = new ArrayList<>();
        setList.add(new Set(getArtist2Songs(), ""));
        return setList;
    }

    private List<Song> getArtist2Songs() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("song4", null, null, ""));
        songs.add(new Song("song5", null, null, ""));
        return songs;
    }
}
