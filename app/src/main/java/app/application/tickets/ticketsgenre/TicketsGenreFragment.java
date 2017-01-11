package app.application.tickets.ticketsgenre;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.shared.GridItem;
import app.application.shared.GridViewAdapter;
import app.application.R;
import app.application.dagger.DaggerInjector;
import app.application.tickets.ticketssearch.TicketsSearchActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Fragment that displays the available tickets for the selected genre.
 */
public class TicketsGenreFragment extends Fragment implements TicketsGenreContract.View {

    @BindView(R.id.gridView)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_tickets)
    TextView noTickets;
    @BindView(R.id.tickets_genre_table)
    TableLayout tableLayout;
    @BindView(R.id.tickets_load_btn)
    BootstrapButton loadBtn;

    @Inject
    TicketsGenrePresenter ticketsGenrePresenter;

    private GridViewAdapter gridViewAdapter;
    private ArrayList<GridItem> gridItems;
    private String genreSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tickets_genre, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        ticketsGenrePresenter.attachView(this);

        String genre = getArguments().getString("genre");
        genreSearch = getArguments().getString("search");
        gridItems = new ArrayList<>();

        ticketsGenrePresenter.getGenreArtists(genreSearch);
        ticketsGenrePresenter.getGridArtists(getContext(), genre);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ticketsGenrePresenter.detachView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridViewAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, gridItems, gridView);
            gridView.setAdapter(gridViewAdapter);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gridViewAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, gridItems, gridView);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    @Override
    public void showGridArtists(ArrayList<GridItem> gridData) {
        gridItems = gridData;
        gridViewAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, gridItems, gridView);
        gridViewAdapter.setGridData(gridItems);
        gridView.setAdapter(gridViewAdapter);
    }

    /**
     * Adds the artists recieved from the presenter to the table. For every artists in the list
     * a new table row is added by inflating the required view. The artists name is displayed in
     * the TextView and the button is assigned an onClickListener which opens a new
     * {@link TicketsSearchActivity} querying from the respective artist.
     * @param tableArtists list of artists to be shown in the table
     */
    @Override
    public void showTableArtists(ArrayList<String> tableArtists) {
        for(int i = 0; i < tableArtists.size(); i++) {
            String artist = tableArtists.get(i);
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View tableRowView = layoutInflater.inflate(R.layout.tickets_genre_row, null);
            TextView artistnameTextView = ButterKnife.findById(tableRowView, R.id.artist_name);
            artistnameTextView.setText(artist);
            BootstrapButton findTicketsBtn = ButterKnife.findById(tableRowView, R.id.find_tickets_btn);
            findTicketsBtn.setOnClickListener((View v) -> {
                Intent ticketsSearchIntent = new Intent(getActivity(), TicketsSearchActivity.class);
                ticketsSearchIntent.putExtra("search", artist);
                startActivity(ticketsSearchIntent);
            });
            tableLayout.addView(tableRowView);
        }
    }

    @OnItemClick(R.id.gridView)
    public void onItemClick(View view) {
        TextView mGenreLabel = ButterKnife.findById(view, R.id.grid_item_title);
        Intent ticketsSearchIntent = new Intent(getActivity(), TicketsSearchActivity.class);
        ticketsSearchIntent.putExtra("search", mGenreLabel.getText().toString());
        startActivity(ticketsSearchIntent);
    }

    @OnClick(R.id.tickets_load_btn)
    public void onClick(View v) {
        ticketsGenrePresenter.getGenreArtists(genreSearch);
    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        gridView.setVisibility(View.GONE);
        noTickets.setText(message);
        noTickets.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
        noTickets.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        noTickets.setVisibility(View.GONE);
    }

    public TicketsGenreFragment getFragment(String genre, String search) {
        Bundle bundle = new Bundle();
        bundle.putString("genre", genre);
        bundle.putString("search", search);
        this.setArguments(bundle);
        return this;
    }
}
