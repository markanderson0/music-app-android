package app.application.user.profile.videos;

import android.graphics.Paint;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.List;

import app.application.shared.HorizontalGridAdapter;
import app.application.R;
import app.application.user.profile.videos.data.model.Show;
import app.application.user.profile.videos.data.model.Videos;
import butterknife.ButterKnife;

public class ShowViewHolder extends ChildViewHolder {
    private TextView locationTextView;
    private HorizontalGridView gridView;

    public ShowViewHolder(final View itemView) {
        super(itemView);
        locationTextView = ButterKnife.findById(itemView, R.id.list_item_artist_name);
        gridView = ButterKnife.findById(itemView, R.id.gridView);
    }

    public void onBind(Show show) {
        locationTextView.setText(show.getLocation());
    }

    public void setLocation(String name) {
        locationTextView.setText(name);
        locationTextView.setPaintFlags(locationTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void setVideos(List<Videos> videos) {
        HorizontalGridAdapter gridAdapter = new HorizontalGridAdapter(itemView.getContext(), videos);
        gridView.setAdapter(gridAdapter);
        HorizontalGridView.LayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        gridView.setLayoutManager(layoutManager);
        gridView.setHasFixedSize(true);
    }
}
