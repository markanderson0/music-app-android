package app.application.user.upload.myuploads;

import android.content.Context;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.application.R;
import app.application.user.profile.videos.data.model.Videos;
import butterknife.ButterKnife;

/**
 * Assigns the videos to the view
 */
public class MyUploadsHorizontalGridAdapter extends RecyclerView.Adapter<MyUploadsHorizontalGridAdapter.SimpleViewHolder>{
    private Context context;
    private List<Videos> videos;
    private MaterialDialog materialDialog;

    public MyUploadsHorizontalGridAdapter(Context context, List<Videos> videos){
        this.context = context;
        this.videos = videos;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView songsTextView, timeTextView;
        AwesomeTextView ratingTextView, viewsTextView;
        ImageView imageView;
        HorizontalGridView gridView;
        LinearLayout gridItem;

        public SimpleViewHolder(View view) {
            super(view);
            ratingTextView = ButterKnife.findById(view, R.id.video_rating);
            timeTextView = ButterKnife.findById(view, R.id.video_time);
            viewsTextView = ButterKnife.findById(view, R.id.video_views);
            songsTextView = ButterKnife.findById(view, R.id.video_songs);
            imageView = ButterKnife.findById(view, R.id.video_image);
            gridItem = ButterKnife.findById(view, R.id.grid_item);

        }
    }

    @Override
    public MyUploadsHorizontalGridAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.video_layout, parent, false);
        return new MyUploadsHorizontalGridAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyUploadsHorizontalGridAdapter.SimpleViewHolder holder, final int position) {
        final Context context = holder.imageView.getContext();
        final Videos item = videos.get(position);
        holder.ratingTextView.setMarkdownText(" {fa_volume_off} " + String.valueOf(item.getAudio()) + " {fa_video_camera} " + String.valueOf(item.video));
        holder.timeTextView.setText(item.getTime());
        holder.viewsTextView.setMarkdownText(String.valueOf(item.getViews()) + " {fa_eye} ");
        holder.songsTextView.setText(item.getSongsString());
        Picasso.with(context).load(item.getImage()).fit().into(holder.imageView);

        holder.gridItem.setOnClickListener((View view) -> {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View editView = layoutInflater.inflate(R.layout.myuploads_dialog, null);
                BootstrapButton deleteBtn = ButterKnife.findById(editView, R.id.my_uploads_delete_btn);
                deleteBtn.setOnClickListener((View v) -> {
                        materialDialog.dismiss();
                });

                materialDialog = new MaterialDialog.Builder(context)
                        .customView(editView, true)
                        .show();
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.videos.size();
    }
}
