package app.application.artist.shows;

import app.application.artist.shows.data.model.ArtistShowsModel;

/**
 * Utility class to get an artists MBID.
 */
public class ArtistShowsUtil {

    /**
     * Returns the artists MBID. Matches the searched artists name with the artist names that
     * are contained in the search results. If the names match the MBID is extracted and returned.
     *
     * @param searchArtist response from the setlist.fm api call as an object
     * @param artistName the artists name
     * @return the artists MBID
     */
    public static String getMbid(ArtistShowsModel searchArtist, String artistName) {
        String mbid = "";
        for (int i = 0; i < searchArtist.getSetlists().getSetlist().size(); i++) {
            if (artistName.equals(searchArtist.getSetlists().getSetlist().get(i).getArtist().getName()) ||
                    artistName.equals(searchArtist.getSetlists().getSetlist().get(i).getArtist().getSortName())) {
                mbid = searchArtist.getSetlists().getSetlist().get(i).getArtist().getMbid();
                break;
            }
        }
        return mbid;
    }
}
