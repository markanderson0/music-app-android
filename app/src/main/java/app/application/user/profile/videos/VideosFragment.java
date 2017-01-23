package app.application.user.profile.videos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.R;
import app.application.dagger.DaggerInjector;
import app.application.user.profile.videos.data.model.VideosModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that displays the videos that a user has uploaded.
 */
public class VideosFragment extends Fragment implements VideosContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_results)
    TextView noResults;

    @Inject
    VideosPresenter videosPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_videos, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        videosPresenter.attachView(this);
        videosPresenter.getVideos(getContext());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videosPresenter.detachView();
    }

    @Override
    public void showVideos(ArrayList<VideosModel> videos){
        VideosModelAdapter adapter = new VideosModelAdapter(videos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        noResults.setText(message);
        noResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        noResults.setVisibility(View.GONE);
    }
}
