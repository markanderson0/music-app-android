package app.application.user.profile.favourites;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.application.R;
import app.application.user.profile.favourites.data.model.Video;

/**
 * Assigns the favourites to the view
 */
public class FavouritesGridViewAdapter extends ArrayAdapter<Video> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Video> gridData = new ArrayList<Video>();
    private GridView gridView;
    View row;
    ViewHolder holder;

    public FavouritesGridViewAdapter(Context context, int layoutResourceId, ArrayList<Video> gridData, GridView gridView) {
        super(context, layoutResourceId, gridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.gridData = gridData;
        this.gridView = gridView;
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param gridData list of videos
     */
    public void setGridData(ArrayList<Video> gridData) {
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
            holder.userTextView = (TextView) row.findViewById(R.id.video_upload_user);
            holder.ratingTextView = (AwesomeTextView) row.findViewById(R.id.video_rating);
            holder.timeTextView = (TextView) row.findViewById(R.id.video_time);
            holder.viewsTextView = (AwesomeTextView) row.findViewById(R.id.video_views);
            holder.songsTextView = (TextView) row.findViewById(R.id.video_songs);
            holder.imageView = (ImageView) row.findViewById(R.id.video_image);
            holder.gridView = this.gridView;
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Video item = gridData.get(position);
        holder.userTextView.setText("Uploaded By " + item.getUser());
        holder.userTextView.setPaintFlags(holder.userTextView.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        holder.ratingTextView.setMarkdownText(" {fa_volume_off} " + String.valueOf(item.getAudio()) + " {fa_video_camera} " + String.valueOf(item.video));
        holder.timeTextView.setText(item.getTime());
        holder.viewsTextView.setMarkdownText(String.valueOf(item.getViews()) + " {fa_eye} ");
        holder.songsTextView.setText(item.getSongsString());
        Picasso.with(context).load(item.getImage()).fit().into(holder.imageView);

        return row;
    }

    static class ViewHolder {
        TextView songsTextView, timeTextView, userTextView;
        AwesomeTextView ratingTextView, viewsTextView;
        ImageView imageView;
        GridView gridView;
    }
}
