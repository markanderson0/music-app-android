package app.application.search;

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
 * Activity that searches for artists and displays the results.
 */
public class SearchActivity extends DrawerActivity implements SearchContract.View {

    @BindView(R.id.gridView)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noResults)
    TextView noResults;

    @Inject
    SearchPresenter searchPresenter;

    private GridViewAdapter gridAdapter;
    private ArrayList<GridItem> gridData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);
        searchPresenter.attachView(this);
        gridData = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        String artistName = extras.getString("query");
        searchPresenter.searchArtists(artistName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.detachView();
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

    @OnItemClick(R.id.gridView)
    public void onItemClick(View view, int position) {
        TextView mGenreLabel = ButterKnife.findById(view, R.id.grid_item_title);
        Intent artistIntent = new Intent(SearchActivity.this, ArtistActivity.class);
        artistIntent.putExtra("artist", mGenreLabel.getText().toString());
        artistIntent.putExtra("image", gridData.get(position).getImage());
        startActivity(artistIntent);
    }

    @Override
    public void showResults(ArrayList<GridItem> gridData) {
        this.gridData = gridData;
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, this.gridData, gridView);
        gridAdapter.setGridData(this.gridData);
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
