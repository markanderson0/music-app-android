package app.application.browse;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

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
 * Activity that displays artists from the selected genre.
 */
public class BrowseGenreActivity extends DrawerActivity implements BrowseContract.View, View.OnClickListener {

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
    private int pageNum;
    private String genre;
    private BootstrapButton loadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);
        browsePresenter.attachView(this);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View footerView = layoutInflater.inflate(R.layout.load_more, null);
        loadBtn = ButterKnife.findById(footerView, R.id.browse_load_btn);
        loadBtn.setOnClickListener(this);
        gridView.addFooterView(footerView);

        pageNum = 0;
        loadBtn.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        genre = extras.getString("genre");

        browsePresenter.getArtists(this, "mock-data/playlists/" + genre + "/" + genre + String.valueOf(pageNum) + ".json");
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
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, gridData, gridView);
            gridView.setAdapter(gridAdapter);
        }
    }

    @Override
    public void showGridData(ArrayList<GridItem> gridData) {
        this.gridData = gridData;
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, this.gridData, gridView);
        gridView.setAdapter(gridAdapter);
        gridAdapter.setGridData(this.gridData);
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
    public void onItemClick(View view, int position) {
        TextView genreTextView = ButterKnife.findById(view, R.id.grid_item_title);
        Intent artistIntent = new Intent(getActivity(), ArtistActivity.class);
        artistIntent.putExtra("artist", genreTextView.getText().toString());
        artistIntent.putExtra("image", gridData.get(position).getImage());
        startActivity(artistIntent);
    }

    @Override
    public void onClick(View v) {
        pageNum++;
        if (pageNum > 3) {
            loadBtn.setVisibility(View.GONE);
        }
        browsePresenter.getArtists(this, "mock-data/playlists/" + genre + "/" + genre + String.valueOf(pageNum) + ".json");
    }
}
