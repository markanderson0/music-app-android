package app.application.user.profile.favourites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import app.application.R;
import app.application.user.profile.favourites.data.model.FavouritesModel;
import app.application.user.profile.videos.VideosModelViewHolder;

import static android.view.LayoutInflater.from;

public class FavouritesModelAdapter extends ExpandableRecyclerViewAdapter<VideosModelViewHolder, FavouritesVideoViewHolder> {

    public FavouritesModelAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public VideosModelViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = from(parent.getContext()).inflate(R.layout.videos_list_parent, parent, false);
        return new VideosModelViewHolder(view);
    }

    @Override
    public FavouritesVideoViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_list_child, parent, false);
        return new FavouritesVideoViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(FavouritesVideoViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        final FavouritesModel artist = (FavouritesModel) group;
        holder.setVideo(artist.getVideos());
    }

    @Override
    public void onBindGroupViewHolder(VideosModelViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        FavouritesModel videosModel = (FavouritesModel) group;
        holder.setArtistNameTitle(group);
        holder.setArtistImage(videosModel.getImage());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
