package app.application.shared;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.application.R;
import app.application.user.profile.videos.data.model.Videos;
import app.application.videoplayer.VideoPlayerActivity;
import butterknife.ButterKnife;

/**
 * Assigns the grid data to the grid item view
 */
public class HorizontalGridAdapter extends RecyclerView.Adapter<HorizontalGridAdapter.SimpleViewHolder>{
    private Context context;
    private List<Videos> videos;
    private String videoId;

    public HorizontalGridAdapter(Context context, List<Videos> videos){
        this.context = context;
        this.videos = videos;
    }

    public HorizontalGridAdapter(Context context, List<Videos> videos, String videoId){
        this.context = context;
        this.videos = videos;
        this.videoId = videoId;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView songsTextView, timeTextView, videoPlaying;
        AwesomeTextView ratingTextView, viewsTextView;
        ImageView imageView;
        LinearLayout gridItem;

        public SimpleViewHolder(View view) {
            super(view);
            ratingTextView = ButterKnife.findById(view, R.id.video_rating);
            timeTextView = ButterKnife.findById(view, R.id.video_time);
            viewsTextView = ButterKnife.findById(view, R.id.video_views);
            songsTextView = ButterKnife.findById(view, R.id.video_songs);
            imageView = ButterKnife.findById(view, R.id.video_image);
            gridItem = ButterKnife.findById(view, R.id.grid_item);
            videoPlaying = ButterKnife.findById(view, R.id.video_playing);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.video_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        Context context = holder.imageView.getContext();
        final Videos item = videos.get(position);
        holder.ratingTextView.setMarkdownText(" {fa_volume_off} " + String.valueOf(item.getAudio()) + " {fa_video_camera} " + String.valueOf(item.video) + " ");
        holder.timeTextView.setText(item.getTime());
        holder.viewsTextView.setMarkdownText(String.valueOf(item.getViews()) + " {fa_eye} ");
        holder.songsTextView.setText(item.getSongsString());
        Picasso.with(context).load(item.getImage()).fit().into(holder.imageView);

        holder.gridItem.setOnClickListener((View view) -> {
                Intent videoPlayerIntent = new Intent(view.getContext(), VideoPlayerActivity.class);
                videoPlayerIntent.putExtra("videoId", item.getId());
                videoPlayerIntent.putExtra("video", item.getFile());
                videoPlayerIntent.putExtra("title", item.getSongsString());
                videoPlayerIntent.putExtra("views", String.valueOf(item.getViews()));
                videoPlayerIntent.putExtra("audioRating", String.valueOf(item.getAudio()));
                videoPlayerIntent.putExtra("videoRating", String.valueOf(item.getVideo()));
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("videos", (ArrayList<? extends Parcelable>) videos);
                videoPlayerIntent.putExtras(bundle);
                view.getContext().startActivity(videoPlayerIntent);
        });

        if(item.getId().equals(videoId)){
            holder.videoPlaying.setVisibility(View.VISIBLE);
        }
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
