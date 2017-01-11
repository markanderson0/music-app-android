package app.application.artist.albums;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.application.R;
import app.application.artist.albums.data.models.artistalbumtracksmodel.Album;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Assigns the album data to the view
 */
public class ArtistAlbumsGridViewAdapter extends ArrayAdapter<Album> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Album> gridData = new ArrayList<Album>();
    private GridViewWithHeaderAndFooter gridView;
    View row;
    ViewHolder holder;
    int scrollPosition;

    public ArtistAlbumsGridViewAdapter(Context context, int layoutResourceId, ArrayList<Album> gridData, GridViewWithHeaderAndFooter gridView) {
        super(context, layoutResourceId, gridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.gridData = gridData;
        this.gridView = gridView;
        this.scrollPosition = gridView.getFirstVisiblePosition();
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param gridData list of albums
     */
    public void setGridData(ArrayList<Album> gridData) {
        this.gridData = gridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.albumDetails = (LinearLayout) row.findViewById(R.id.artist_albums_details);
            holder.albumCover = (ImageView) row.findViewById(R.id.album_cover);
            holder.albumCover.setAdjustViewBounds(true);
            holder.albumName = (TextView) row.findViewById(R.id.album_name);
            holder.releaseDate = (TextView) row.findViewById(R.id.album_release_date);
            holder.albumTracks = (LinearLayout) row.findViewById(R.id.album_tracks);
            holder.gridView = this.gridView;
            holder.gridView.setSelection(scrollPosition);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Album item = gridData.get(position);
        holder.albumName.setText(item.getName());
        holder.releaseDate.setText(item.getReleaseDate().substring(0, 4));

        //TODO: Percentage Layout?
        Configuration configuration = context.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp /2;
        int screenDensity = configuration.densityDpi / 145;
        holder.albumCover.getLayoutParams().height = screenWidthDp * screenDensity;
        holder.albumCover.getLayoutParams().width = screenWidthDp * screenDensity;

        if(item.getImages().size() > 1) {
            Log.i("AlbumCover", item.getImages().get(1).getUrl());
            Log.i("AlbumCoverWidth", String.valueOf(screenWidthDp));
            Picasso.with(context).load(item.getImages().get(1).getUrl()).resize(screenWidthDp, screenWidthDp).into(holder.albumCover);
        } else if(item.getImages().size() == 1) {
            Log.i("AlbumCover", item.getImages().get(0).getUrl());
            Picasso.with(context).load(item.getImages().get(0).getUrl()).resize(screenWidthDp, screenWidthDp).into(holder.albumCover);
        } else {
            Log.i("AlbumCover", "http://freetwitterheaders.com/wp-content/uploads/2012/09/Gradient_02.jpg");
            Picasso.with(context).load("http://freetwitterheaders.com/wp-content/uploads/2012/09/Gradient_02.jpg").fit().into(holder.albumCover);
        }

        if(item.getTracks().getItems().size() > 0){
            holder.albumTracks.removeAllViews();
            for(int i = 0; i < item.getTracks().getItems().size(); i++) {
                TextView track = new TextView(context);
                track.setText(item.getTracks().getItems().get(i).getTrackNumber() + ". " + item.getTracks().getItems().get(i).getName());
                holder.albumTracks.addView(track);
            }
        }

        return row;
    }

    static class ViewHolder {
        ImageView albumCover;
        TextView albumName, releaseDate;
        LinearLayout albumTracks, albumDetails;
        GridView gridView;
    }
}
