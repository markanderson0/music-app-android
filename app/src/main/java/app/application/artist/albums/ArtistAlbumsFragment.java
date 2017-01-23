package app.application.artist.albums;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.R;
import app.application.artist.albums.data.models.artistalbumtracksmodel.Album;
import app.application.dagger.DaggerInjector;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Fragment that displays the albums of the selected artist.
 */
public class ArtistAlbumsFragment extends Fragment implements ArtistAlbumsContract.View {

    @BindView(R.id.grid_view)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_tickets)
    TextView noTickets;

    @Inject
    ArtistAlbumsPresenter artistAlbumsPresenter;

    private ArrayList<Album> artistAlbums;
    private ArtistAlbumsGridViewAdapter gridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tickets_search, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        artistAlbumsPresenter.attachView(this);
        String artist = getArguments().getString("artist");
        artistAlbums = new ArrayList<>();
        artistAlbumsPresenter.getAlbums(artist, "artist", "1");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        artistAlbumsPresenter.detachView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridAdapter = new ArtistAlbumsGridViewAdapter(getContext(), R.layout.artist_albums_row, artistAlbums, gridView);
            gridView.setAdapter(gridAdapter);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridAdapter = new ArtistAlbumsGridViewAdapter(getContext(), R.layout.artist_albums_row, artistAlbums, gridView);
            gridView.setAdapter(gridAdapter);
        }
    }

    @Override
    public void showAlbums(List<Album> albums){
        artistAlbums = (ArrayList) albums;
        gridAdapter = new ArtistAlbumsGridViewAdapter(getContext(), R.layout.artist_albums_row, artistAlbums, gridView);
        gridView.setAdapter(gridAdapter);
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

    public ArtistAlbumsFragment getFragment(String artist) {
        Bundle bundle = new Bundle();
        bundle.putString("artist", artist);
        this.setArguments(bundle);
        return this;
    }
}
