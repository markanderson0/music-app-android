package app.application.user.profile.favourites;

import android.view.View;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;
import java.util.List;

import app.application.R;
import app.application.user.profile.favourites.data.model.FavouritesModel;
import app.application.user.profile.favourites.data.model.Video;
import butterknife.ButterKnife;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

public class FavouritesVideoViewHolder extends ChildViewHolder {
    GridViewWithHeaderAndFooter gridView;

    public FavouritesVideoViewHolder(View itemView) {
        super(itemView);
        gridView = ButterKnife.findById(itemView, R.id.grid_view);
    }

    public void onBind(FavouritesModel favourite) {
        FavouritesGridViewAdapter gridAdapter = new FavouritesGridViewAdapter(gridView.getContext(), R.layout.favourites_layout, (ArrayList) favourite.getVideos(), gridView);
        gridView.setAdapter(gridAdapter);
    }

    public void setVideo(List<Video> videos) {
        FavouritesGridViewAdapter gridAdapter = new FavouritesGridViewAdapter(gridView.getContext(), R.layout.favourites_layout, (ArrayList) videos, gridView);
        gridView.setAdapter(gridAdapter);
    }
}
