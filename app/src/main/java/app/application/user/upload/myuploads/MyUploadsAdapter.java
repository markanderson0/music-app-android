package app.application.user.upload.myuploads;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import app.application.R;
import app.application.user.profile.videos.data.model.Show;
import app.application.user.profile.videos.data.model.VideosModel;
import app.application.user.profile.videos.VideosModelViewHolder;

public class MyUploadsAdapter extends ExpandableRecyclerViewAdapter<VideosModelViewHolder, MyUploadsVideosViewHolder> {

    public MyUploadsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public VideosModelViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_list_parent, parent, false);
        return new VideosModelViewHolder(view);
    }

    @Override
    public MyUploadsVideosViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_list_child, parent, false);
        return new MyUploadsVideosViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(MyUploadsVideosViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        final Show artist = ((VideosModel) group).getItems().get(childIndex);
        if (artist.getPlaylist().size() > 0) {
            holder.setLocation(artist.getDate() + " | " + artist.getVenue() + " | " + artist.getLocation());
            for (int i = 0; i < artist.getPlaylist().size(); i++) {
                holder.setVideos(artist.getPlaylist().get(i).getVideos());
            }
        }
    }

    @Override
    public void onBindGroupViewHolder(VideosModelViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        VideosModel videosModel = (VideosModel) group;
        holder.setArtistNameTitle(group);
        holder.setArtistImage(videosModel.getImage());
    }
}

