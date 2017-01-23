package app.application.user.following;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.drawer.DrawerActivity;
import app.application.shared.GridItem;
import app.application.shared.GridViewAdapter;
import app.application.R;
import app.application.artist.ArtistActivity;
import app.application.dagger.DaggerInjector;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Activity that displays the artists that a user is following.
 */
public class FollowingActivity extends DrawerActivity implements FollowingContract.View {

    @BindView(R.id.grid_view)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_results)
    TextView noResults;

    @Inject
    FollowingPresenter followingPresenter;

    private GridViewAdapter gridAdapter;
    private ArrayList<GridItem> gridData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);
        followingPresenter.attachView(this);
        gridData = new ArrayList<>();
        followingPresenter.getArtists(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        followingPresenter.detachView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, gridData, gridView);
            gridView.setAdapter(gridAdapter);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, gridData, gridView);
            gridView.setAdapter(gridAdapter);
        }
    }

    @OnItemClick(R.id.grid_view)
    public void onItemClick(View view, int position) {
        TextView mGenreLabel = ButterKnife.findById(view, R.id.grid_item_title);
        Intent artistIntent = new Intent(getActivity(), ArtistActivity.class);
        artistIntent.putExtra("artist", mGenreLabel.getText().toString());
        artistIntent.putExtra("image", gridData.get(position).getImage());
        startActivity(artistIntent);
    }

    @Override
    public void showResults(ArrayList<GridItem> gridData){
        this.gridData = gridData;
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, gridData, gridView);
        gridAdapter.setGridData(gridData);
        gridView.setAdapter(gridAdapter);
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
}
