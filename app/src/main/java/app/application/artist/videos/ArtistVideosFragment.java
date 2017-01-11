package app.application.artist.videos;

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
import app.application.user.profile.videos.data.model.Show;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that displays an artists videos.
 */
public class ArtistVideosFragment extends Fragment implements ArtistVideosContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noResults)
    TextView noResults;

    @Inject
    ArtistVideosPresenter artistVideosPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_videos, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        artistVideosPresenter.attachView(this);
        artistVideosPresenter.getVideos(getContext());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        artistVideosPresenter.detachView();
    }

    @Override
    public void showVideos(ArrayList<Show> shows) {
        ArtistVideosModelAdapter adapter = new ArtistVideosModelAdapter(shows);
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

