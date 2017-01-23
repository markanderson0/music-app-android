package app.application.browse;

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
import app.application.dagger.DaggerInjector;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Activity that displays the available genres to browse.
 */
public class BrowseActivity extends DrawerActivity implements View.OnClickListener, BrowseContract.View {

    @BindView(R.id.grid_view)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_results)
    TextView noResults;

    @Inject
    BrowsePresenter browsePresenter;

    private GridViewAdapter gridAdapter;
    private ArrayList<GridItem> gridData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);
        browsePresenter.attachView(this);
        browsePresenter.getArtists(this, "browseNavigation");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        browsePresenter.detachView();
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

    @Override
    public void showGridData(ArrayList<GridItem> gridData) {
        this.gridData = gridData;
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, gridData, gridView);
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

    @OnItemClick(R.id.grid_view)
    public void onClick(View view) {
        TextView genreLabel = ButterKnife.findById(view, R.id.grid_item_title);
        Intent browseGenreIntent = new Intent(getActivity(), BrowseGenreActivity.class);
        browseGenreIntent.putExtra("genre", genreLabel.getText().toString().toLowerCase().replace(" ", ""));
        startActivity(browseGenreIntent);
    }
}
