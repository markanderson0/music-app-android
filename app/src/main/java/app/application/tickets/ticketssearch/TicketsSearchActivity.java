package app.application.tickets.ticketssearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.R;
import app.application.dagger.DaggerInjector;
import app.application.drawer.DrawerActivity;
import app.application.tickets.ticketssearch.data.model.TicketsSearchModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Activity that displays the available tickets for the selected artist.
 */
public class TicketsSearchActivity extends DrawerActivity implements TicketsSearchContract.View {

    @BindView(R.id.grid_view)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_tickets)
    TextView noTickets;

    @Inject
    TicketsSearchPresenter ticketsSearchPresenter;

    TicketsSearchGridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets_search);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);
        ticketsSearchPresenter.attachView(this);
        Bundle extras = getIntent().getExtras();
        String search = extras.getString("search");
        setTitle(search + " Tickets");
        ticketsSearchPresenter.getSearchArtists(search);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticketsSearchPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tickets_search_map_menu, menu);
        final MenuItem searchActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                Intent ticketsSearchIntent = new Intent(getActivity(), TicketsSearchActivity.class);
                ticketsSearchIntent.putExtra("search", query);
                startActivity(ticketsSearchIntent);
                searchActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        final MenuItem mapMenuItem = menu.findItem(R.id.map_icon);
        mapMenuItem.setOnMenuItemClickListener((MenuItem menuItem) -> {
                if(gridView.getVisibility() == View.VISIBLE) {
                    Intent ticketsMapIntent = new Intent(TicketsSearchActivity.this, TicketsMapActivity.class);
                    Bundle ticketsMapBundle = new Bundle();
                    ticketsMapBundle.putParcelableArrayList("tickets", gridAdapter.getGridData());
                    ticketsMapIntent.putExtras(ticketsMapBundle);
                    ticketsMapIntent.putExtra("search", getTitle());
                    startActivity(ticketsMapIntent);
                }
                return false;
        });

        final MenuItem listMenuItem = menu.findItem(R.id.list_icon);
        listMenuItem.setVisible(false);
        return true;
    }

    @Override
    public void showTableArtists(ArrayList<TicketsSearchModel> tickets) {
        gridAdapter = new TicketsSearchGridViewAdapter(this, R.layout.tickets_search_row, tickets, gridView);
        gridView.setAdapter(gridAdapter);
        gridAdapter.setGridData(tickets);
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

}
