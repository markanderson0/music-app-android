package app.application.artist.topvideos;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.R;
import app.application.artist.topvideos.data.model.TopVideosModel;
import app.application.dagger.DaggerInjector;
import app.application.videoplayer.VideoPlayerActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Fragment that displays an artists top videos.
 */
public class TopVideosFragment extends Fragment implements TopVideosContract.View {

    @BindView(R.id.grid_view)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_results)
    TextView noResults;

    @Inject
    TopVideosPresenter topVideosPresenter;

    private TopVideosViewAdapter gridAdapter;
    private ArrayList<TopVideosModel> gridData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_grid_view, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        topVideosPresenter.attachView(this);
        gridData = new ArrayList<>();
        topVideosPresenter.getTopVideos(getContext());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        topVideosPresenter.detachView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridAdapter = new TopVideosViewAdapter(getContext(), R.layout.favourites_layout, gridData, gridView);
            gridView.setAdapter(gridAdapter);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gridAdapter = new TopVideosViewAdapter(getContext(), R.layout.favourites_layout, gridData, gridView);
            gridView.setAdapter(gridAdapter);
        }
    }

    @Override
    public void showTopVideos(ArrayList<TopVideosModel> topVideos) {
        gridData = topVideos;
        gridAdapter = new TopVideosViewAdapter(getContext(), R.layout.favourites_layout, gridData, gridView);
        gridView.setAdapter(gridAdapter);
        gridAdapter.setGridData(gridData);
    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        gridView.setVisibility(View.GONE);
        noResults.setText(message);
        noResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        noResults.setVisibility(View.GONE);
    }

    //TODO: Get video playlist from id
    @OnItemClick(R.id.grid_view)
    public void OnItemClick(View view, int position) {
        Intent videoPlayerIntent = new Intent(view.getContext(), VideoPlayerActivity.class);
        videoPlayerIntent.putExtra("videoId", gridData.get(position).getVideoId());
        videoPlayerIntent.putExtra("video", gridData.get(position).getFile());
        videoPlayerIntent.putExtra("title", gridData.get(position).getSongsString());
        videoPlayerIntent.putExtra("views", String.valueOf(gridData.get(position).getViews()));
        videoPlayerIntent.putExtra("audioRating", String.valueOf(gridData.get(position).getAudio()));
        videoPlayerIntent.putExtra("videoRating", String.valueOf(gridData.get(position).getVideo()));
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("videos", (ArrayList<? extends Parcelable>) gridData);
//        videoPlayerIntent.putExtras(bundle);
        view.getContext().startActivity(videoPlayerIntent);
    }
}
