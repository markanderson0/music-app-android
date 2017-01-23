package app.application.user.upload.myuploads;

import android.graphics.Paint;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.List;

import app.application.R;
import app.application.user.profile.videos.data.model.Show;
import app.application.user.profile.videos.data.model.Videos;

public class MyUploadsVideosViewHolder extends ChildViewHolder {
    private TextView childTextView;
    private HorizontalGridView gridView;

    public MyUploadsVideosViewHolder(final View itemView) {
        super(itemView);
        childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
        gridView = (HorizontalGridView) itemView.findViewById(R.id.grid_view);
    }

    public void onBind(Show show) {
        childTextView.setText(show.getLocation());
    }

    public void setLocation(String name) {
        childTextView.setText(name);
        childTextView.setPaintFlags(childTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void setVideos(List<Videos> videos) {
        MyUploadsHorizontalGridAdapter gridAdapter = new MyUploadsHorizontalGridAdapter(itemView.getContext(), videos);
        gridView.setAdapter(gridAdapter);
        HorizontalGridView.LayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        gridView.setLayoutManager(layoutManager);
        gridView.setHasFixedSize(true);
    }
}
