package app.application.artist.shows;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import app.application.artist.shows.data.model.Cover;
import app.application.artist.shows.data.model.Set;
import app.application.artist.shows.data.model.Song;

/**
 * Utility class to get an artists MBID and get the songs from a setlist.
 *
 * @note This class is soon to be depricated and replaced with a custom GSON parser.
 */
public class ArtistShowsUtil {

    /**
     * Returns the artists MBID.
     *
     * @param searchArtist response from the setlist.fm api call as an object
     * @param artist the artists name
     * @return the artists MBID
     */
    public static String getMBID(Object searchArtist, String artist) {
        LinkedTreeMap artistShowsModelMap = (LinkedTreeMap) searchArtist;
        LinkedTreeMap setlistsMap = (LinkedTreeMap) artistShowsModelMap.get("setlists");
        String mbid = "";
        if (setlistsMap.get("setlist") instanceof ArrayList) {
            List<Object> setlists = (List<Object>) setlistsMap.get("setlist");
            for (int i = 0; i < setlists.size(); i++) {
                LinkedTreeMap setlistMap = (LinkedTreeMap) setlists.get(i);
                LinkedTreeMap artistMap = (LinkedTreeMap) setlistMap.get("artist");
                String artistName = (String) artistMap.get("@name");
                String artistSortName = (String) artistMap.get("@name");
                if (artistName.toLowerCase().equals(artist.toLowerCase()) ||
                        artistSortName.toLowerCase().equals(artist.toLowerCase())) {
                    mbid = (String) artistMap.get("@mbid");
                }
            }
        } else {
            LinkedTreeMap setlistMap = (LinkedTreeMap) setlistsMap.get("setlist");
            LinkedTreeMap artistMap = (LinkedTreeMap) setlistMap.get("artist");
            String artistName = (String) artistMap.get("@name");
            String artistSortName = (String) artistMap.get("@name");
            if (artistName.toLowerCase().equals(artist.toLowerCase()) ||
                    artistSortName.toLowerCase().equals(artist.toLowerCase())) {
                mbid = (String) artistMap.get("@mbid");
            }
        }

        return mbid;
    }

    /**
     * Returns the a list of songs played in a setlist.
     *
     * @param sets the set containing the songs from a setlist
     * @return a list of songs
     */
    public static ArrayList<Song> getSetlist(Object sets) {
        Set set = new Set();
        ArrayList<Song> songs = new ArrayList<>();
        if (sets instanceof LinkedTreeMap) {
            LinkedTreeMap setsMap = (LinkedTreeMap) sets;
            // If setlist has an encore
            if (setsMap.get("set") instanceof ArrayList) {
                ArrayList<LinkedTreeMap> setList = (ArrayList<LinkedTreeMap>) setsMap.get("set");
                for (int i = 0; i < setList.size(); i++) {
                    if(setList.get(i).get("song") instanceof ArrayList) {
                        ArrayList<LinkedTreeMap> songList = (ArrayList<LinkedTreeMap>) setList.get(i).get("song");
                        for(int j = 0; j < songList.size(); j++) {
                            LinkedTreeMap songMap = songList.get(j);
                            getSong(songMap, new Song(), songs);
                        }
                    } else {
                        LinkedTreeMap songMap = (LinkedTreeMap) setList.get(i).get("song");
                        getSong(songMap, new Song(), songs);
                    }
                }
                // If setlist doesnt have an encore
            } else if(setsMap.get("set") instanceof LinkedTreeMap) {
                LinkedTreeMap setMap = (LinkedTreeMap) setsMap.get("set");
                if(setMap.get("song") instanceof ArrayList) {
                    ArrayList<LinkedTreeMap> songList = (ArrayList<LinkedTreeMap>) setMap.get("song");
                    for(int j = 0; j < songList.size(); j++) {
                        LinkedTreeMap songMap = songList.get(j);
                        getSong(songMap, new Song(), songs);
                    }
                    // If a single song set
                } else {
                    LinkedTreeMap songMap = (LinkedTreeMap) setMap.get("song");
                    getSong(songMap, new Song(), songs);
                }
            }
            set.setSong(songs);
        }
        return songs;
    }

    /**
     * Maps the song from the api response into to the list of songs
     *
     * @param songMap songs from the api response
     * @param song new song instance
     * @param songs list of songs
     */
    private static void getSong(LinkedTreeMap songMap, Song song, ArrayList<Song> songs) {
        if (songMap.get("@tape") == null) {
            if (songMap.get("cover") != null) {
                Cover cover = new Cover();
                LinkedTreeMap coverMap = (LinkedTreeMap) songMap.get("cover");
                cover.setName((String) coverMap.get("@name"));
                song.setName(songMap.get("@name") + " (" + cover.getName() + " cover)");
                songs.add(song);
            } else {
                song.setName((String) songMap.get("@name"));
                songs.add(song);
            }
        }
    }
}
