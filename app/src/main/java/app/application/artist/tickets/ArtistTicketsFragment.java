package app.application.artist.tickets;

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
import app.application.dagger.DaggerInjector;
import app.application.tickets.ticketssearch.TicketsSearchContract;
import app.application.tickets.ticketssearch.TicketsSearchGridViewAdapter;
import app.application.tickets.ticketssearch.TicketsSearchPresenter;
import app.application.tickets.ticketssearch.data.model.TicketsSearchModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Fragment that displays tickets for the seelcted artist.
 */
public class ArtistTicketsFragment extends Fragment implements TicketsSearchContract.View {

    @BindView(R.id.grid_view)
    GridViewWithHeaderAndFooter mGridView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_tickets)
    TextView noTickets;

    @Inject
    TicketsSearchPresenter ticketsSearchPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tickets_search, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        ticketsSearchPresenter.attachView(this);
        String search = getArguments().getString("artist");
        ticketsSearchPresenter.getSearchArtists(search);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ticketsSearchPresenter.detachView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        }
    }

    @Override
    public void showTableArtists(ArrayList<TicketsSearchModel> tickets) {
        TicketsSearchGridViewAdapter mGridAdapter = new TicketsSearchGridViewAdapter(getContext(), R.layout.tickets_search_row, tickets, mGridView);
        mGridView.setAdapter(mGridAdapter);
        mGridAdapter.setGridData(tickets);
    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        mGridView.setVisibility(View.GONE);
        noTickets.setText(message);
        noTickets.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        mGridView.setVisibility(View.GONE);
        noTickets.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        mGridView.setVisibility(View.VISIBLE);
        noTickets.setVisibility(View.GONE);
    }

    public ArtistTicketsFragment getFragment(String artist) {
        Bundle bundle = new Bundle();
        bundle.putString("artist", artist);
        this.setArguments(bundle);
        return this;
    }
}
