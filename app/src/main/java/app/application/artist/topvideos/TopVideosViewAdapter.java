package app.application.artist.topvideos;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import app.application.R;
import app.application.artist.topvideos.data.model.TopVideosModel;
import butterknife.ButterKnife;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Assigns the video data to the view
 */
public class TopVideosViewAdapter extends ArrayAdapter<TopVideosModel> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<TopVideosModel> gridData = new ArrayList<TopVideosModel>();
    private GridViewWithHeaderAndFooter gridView;
    View row;
    ViewHolder holder;
    private int width;
    private double columns;
    private boolean tablet;
    private int scrollPosition;

    public TopVideosViewAdapter(Context context, int layoutResourceId, ArrayList<TopVideosModel> gridData, GridViewWithHeaderAndFooter gridView) {
        super(context, layoutResourceId, gridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.gridData = gridData;
        this.gridView = gridView;
        this.width = getWidth();
        this.scrollPosition = gridView.getFirstVisiblePosition();
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param gridData list of top videos
     */
    public void setGridData(ArrayList<TopVideosModel> gridData) {
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
            holder.userTextView = ButterKnife.findById(row, R.id.video_upload_user);
            holder.ratingTextView = ButterKnife.findById(row, R.id.video_rating);
            holder.timeTextView = ButterKnife.findById(row, R.id.video_time);
            holder.viewsTextView = ButterKnife.findById(row, R.id.video_views);
            holder.songsTextView = ButterKnife.findById(row, R.id.video_songs);
            holder.imageView = ButterKnife.findById(row, R.id.video_image);
            holder.videoDetails = ButterKnife.findById(row, R.id.video_details);
            holder.gridView = this.gridView;
            holder.gridView.setSelection(scrollPosition);
            setGridWidths();
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        TopVideosModel item = gridData.get(position);
        holder.userTextView.setText("Uploaded By " + item.user);
        holder.userTextView.setPaintFlags(holder.userTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.ratingTextView.setMarkdownText(" {fa_volume_off} " + String.valueOf(item.getAudio()) + " {fa_video_camera} " + String.valueOf(item.video));
        holder.timeTextView.setText(item.time);
        holder.viewsTextView.setMarkdownText(String.valueOf(item.views) + " {fa_eye} ");
        holder.songsTextView.setText(item.songsString);
        try {
            InputStream ims = context.getAssets().open(item.getImage());
            Drawable d = Drawable.createFromStream(ims, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Picasso.with(context).load(item.image).fit().into(holder.imageView);

        return row;
    }

    public void setGridWidths() {
        if (tablet) {
            holder.imageView.setAdjustViewBounds(true);
            holder.gridView.setColumnWidth(width * 2);
            holder.imageView.getLayoutParams().height = 400;
            holder.imageView.getLayoutParams().width = (width * 2);
            holder.videoDetails.getLayoutParams().width = (width * 2);
            holder.imageView.setMaxWidth(width * 2);
            holder.songsTextView.setMaxWidth(width * 3);

            Resources r = getContext().getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 182, r.getDisplayMetrics());
            float rightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());

            RelativeLayout.LayoutParams viewsParams = (RelativeLayout.LayoutParams) holder.viewsTextView.getLayoutParams();
            viewsParams.setMargins(0, (int) px, (int) rightPx, 0);
            holder.viewsTextView.setLayoutParams(viewsParams);

            RelativeLayout.LayoutParams timeParams = (RelativeLayout.LayoutParams) holder.timeTextView.getLayoutParams();
            timeParams.setMargins(0, (int) px, 0, 0);
            holder.timeTextView.setLayoutParams(timeParams);

        } else {
            holder.imageView.setAdjustViewBounds(true);
            holder.gridView.setNumColumns((int) columns);
            holder.gridView.setColumnWidth(width * 2);
            holder.imageView.setMaxWidth(width * 2);
            holder.userTextView.setMaxWidth(width * 3);
            holder.songsTextView.setMaxWidth(width * 3);
        }
    }

    private int getWidth() {
        Configuration configuration = context.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        columns = Math.ceil(screenWidthDp / 210.00);
        if (columns > 5) {
            tablet = true;
            columns = Math.ceil(screenWidthDp / 400.00);
            return (screenWidthDp - (5 * (int) columns)) / (int) columns;
        } else {
            tablet = false;
            return (screenWidthDp - (5 * (int) columns)) / (int) columns;
        }
    }

    static class ViewHolder {
        TextView songsTextView, timeTextView, userTextView;
        AwesomeTextView ratingTextView, viewsTextView;
        ImageView imageView;
        RelativeLayout videoDetails;
        GridViewWithHeaderAndFooter gridView;
    }
}
