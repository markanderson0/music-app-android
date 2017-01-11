package app.application.artist.videos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import app.application.R;
import app.application.user.profile.videos.ShowViewHolder;
import app.application.user.profile.videos.data.model.Playlist;
import app.application.user.profile.videos.data.model.Show;

public class ArtistVideosModelAdapter extends ExpandableRecyclerViewAdapter<ArtistVideosModelViewHolder, ShowViewHolder> {

    public ArtistVideosModelAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ArtistVideosModelViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_videos_list_parent, parent, false);
        return new ArtistVideosModelViewHolder(view);
    }

    @Override
    public ShowViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_list_child, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ShowViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        final Playlist playlist = ((Show) group).getItems().get(childIndex);
            holder.setLocation("Uploaded By " + playlist.getUser());
            holder.setVideos(playlist.getVideos());
    }

    @Override
    public void onBindGroupViewHolder(ArtistVideosModelViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setTitles(group);
    }
}
