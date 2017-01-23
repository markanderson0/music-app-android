package app.application.tickets.toptickets;

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

import app.application.shared.GridItem;
import app.application.shared.GridViewAdapter;
import app.application.R;
import app.application.dagger.DaggerInjector;
import app.application.tickets.ticketssearch.TicketsSearchActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Activity that displays a grid of the most popular tickets.
 */
public class TopTicketsFragment extends Fragment implements TopTicketsContract.View {

    @BindView(R.id.grid_view)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_results)
    TextView noResults;

    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;

    @Inject
    TopTicketsPresenter topTicketsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_grid_view, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        topTicketsPresenter.attachView(this);
        mGridData = new ArrayList<>();
        topTicketsPresenter.getArtists(getContext());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        topTicketsPresenter.detachView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, mGridData, gridView);
            gridView.setAdapter(mGridAdapter);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            mGridAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, mGridData, gridView);
            gridView.setAdapter(mGridAdapter);
        }
    }

    @Override
    public void showTopTickets(ArrayList<GridItem> gridData) {
        mGridData = gridData;
        mGridAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, mGridData, gridView);
        mGridAdapter.setGridData(mGridData);
        gridView.setAdapter(mGridAdapter);
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
    public void onItemClick(View view) {
        TextView mGenreLabel = ButterKnife.findById(view, R.id.grid_item_title);
        Intent ticketsSearchIntent = new Intent(getActivity(), TicketsSearchActivity.class);
        ticketsSearchIntent.putExtra("search", mGenreLabel.getText().toString());
        startActivity(ticketsSearchIntent);
    }
}
