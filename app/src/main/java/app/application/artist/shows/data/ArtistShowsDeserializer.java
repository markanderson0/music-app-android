package app.application.artist.shows.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.application.artist.shows.data.model.Artist;
import app.application.artist.shows.data.model.ArtistShowsModel;
import app.application.artist.shows.data.model.Set;
import app.application.artist.shows.data.model.Setlist;
import app.application.artist.shows.data.model.Setlists;
import app.application.artist.shows.data.model.Sets;
import app.application.artist.shows.data.model.Song;
import app.application.artist.shows.data.model.Venue;

/**
 * Class to deserialize the setlist.fm api response.
 */
public class ArtistShowsDeserializer implements JsonDeserializer<ArtistShowsModel> {
    @Override
    public ArtistShowsModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        ArtistShowsModel artistShowsModel = new ArtistShowsModel();
        Setlists setlists = new Setlists();
        List<Setlist> setlistList = new ArrayList<>();
        final JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonSetlists;
        JsonArray jsonSetlistList = new JsonArray();
        if(jsonObject.get("setlists") != null) {
            jsonSetlists = jsonObject.get("setlists");
            jsonSetlistList = jsonSetlists.getAsJsonObject().getAsJsonArray("setlist");
            setlists.setItemsPerPage(jsonSetlists.getAsJsonObject().get("@itemsPerPage").getAsString());
            setlists.setTotal(jsonSetlists.getAsJsonObject().get("@total").getAsString());
        } else {
            jsonSetlistList.add(jsonObject.get("setlist"));
        }
        //for every setlsit
        for (int i = 0; i < jsonSetlistList.size(); i++) {
            JsonElement jsonSetlistElement = jsonSetlistList.get(i);
            JsonElement jsonSets = jsonSetlistElement.getAsJsonObject().get("sets");
            List<Song> songs = new ArrayList<>();
            //if sets has at least one set
            if (!jsonSets.isJsonPrimitive()) {
                JsonElement jsonSet = jsonSets.getAsJsonObject().get("set");
                if (jsonSet.isJsonObject()) { //if only one set (no Encore)
                    songs = getSongs(jsonSet.getAsJsonObject().get("song"), gson);
                } else if (jsonSet.isJsonArray()) { //if multiple sets (Encores)
                    for (int j = 0; j < jsonSet.getAsJsonArray().size(); j++) {
                        songs.addAll(getSongs(jsonSet.getAsJsonArray().get(j).getAsJsonObject().get("song"), gson));
                    }
                }
            }
            Set set = new Set(songs, "");
            List<Set> setList = new ArrayList<>();
            setList.add(set);
            Sets sets = new Sets(setList);
            Setlist setlist = new Setlist();
            setlist.setSets(sets);
            setlist.setId(jsonSetlistElement.getAsJsonObject().get("@id").getAsString());
            setlist.setEventDate(jsonSetlistElement.getAsJsonObject().get("@eventDate").getAsString());
            setlist.setTour(jsonSetlistElement.getAsJsonObject().get("@tour") != null ?
                    jsonSetlistElement.getAsJsonObject().get("@tour").getAsString() : "");
            setlist.setArtist(getArtist(jsonSetlistElement, gson));
            setlist.setVenue(getVenue(jsonSetlistElement, gson));
            setlistList.add(setlist);
        }
        setlists.setSetlist(setlistList);
        artistShowsModel.setSetlists(setlists);
        return artistShowsModel;
    }

    private List<Song> getSongs(JsonElement jsonSongs, Gson gson) {
        List<Song> songs = new ArrayList<>();
        if(jsonSongs.isJsonArray()) {
            for (int j = 0; j < jsonSongs.getAsJsonArray().size(); j++) {
                Song song = gson.fromJson(jsonSongs.getAsJsonArray().get(j), Song.class);
                if(song.getTape() == null) {
                    songs.add(song);
                }
            }
        } else {
            Song song = gson.fromJson(jsonSongs.getAsJsonObject(), Song.class);
            if(song.getTape() == null) {
                songs.add(song);
            }
        }
        return songs;
    }

    private Artist getArtist(JsonElement jsonArtist, Gson gson) {
        Artist artist = gson.fromJson(jsonArtist.getAsJsonObject().get("artist"), Artist.class);
        return artist;
    }

    private Venue getVenue(JsonElement jsonVenue, Gson gson) {
        Venue venue = gson.fromJson(jsonVenue.getAsJsonObject().get("venue"), Venue.class);
        return venue;
    }
}
