package app.application.artist.shows;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.ButtonMode;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.R;
import app.application.artist.shows.data.model.Setlist;
import app.application.dagger.DaggerInjector;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

import static java.lang.Integer.parseInt;

/**
 * Activity that displays the previous shows of the selected artst.
 */
public class ArtistShowsFragment extends Fragment implements View.OnClickListener, ArtistShowsContract.View {

    @BindView(R.id.gridView)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_tickets)
    TextView noTickets;

    @Inject
    ArtistShowsPresenter artistShowsPresenter;

    private BootstrapButton prevBtn, nextBtn;
    private LinearLayout pagesGrid;
    private String artist;
    private int pageNum, totalPages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tickets_search, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        artistShowsPresenter.attachView(this);

        artist = getArguments().getString("artist");
        pageNum = 1;

        View footerView = inflater.inflate(R.layout.artist_shows_footer, null);
        prevBtn = ButterKnife.findById(footerView, R.id.prev_btn);
        nextBtn = ButterKnife.findById(footerView, R.id.next_btn);
        pagesGrid = ButterKnife.findById(footerView, R.id.page_btns);
        prevBtn.setVisibility(View.INVISIBLE);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        gridView.addFooterView(footerView);

        artistShowsPresenter.getArtistName(artist);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        artistShowsPresenter.detachView();
    }

    @Override
    public void showShows(ArrayList<Setlist> shows) {
        ArtistShowsGridViewAdapter gridAdapter = new ArtistShowsGridViewAdapter(getContext(), R.layout.artist_shows_row, shows, gridView);
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

    @Override
    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
        setPages();
    }

    @Override
    public void setCurrentPage(String page){
        pageNum = parseInt(page);
    }

    /**
     * Creates a numbered button for each page. When clicked the shows corresponding with the
     * selected page number are loaded.
     */
    @Override
    public void setPages() {
        for(int i = 0; i < totalPages; i++){
            final BootstrapButton pageButtonn = new BootstrapButton(getContext());
            pageButtonn.setText(String.valueOf(i + 1));
            pageButtonn.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
            pageButtonn.setBootstrapSize(DefaultBootstrapSize.SM);
            pageButtonn.setButtonMode(ButtonMode.REGULAR);
            pageButtonn.setShowOutline(i == 0 ? false : true);
            pageButtonn.setOnClickListener((View v) -> {
                configurePaginationButtons(Integer.parseInt(pageButtonn.getText().toString()));
                artistShowsPresenter.getArtistShows(String.valueOf(pageNum));
            });
            pagesGrid.addView(pageButtonn);
        }
    }

    /**
     * Creates prev and next buttons which are shown/hidden when on the first/last page.
     *
     * @param selectedPageNumber the currently selected page number
     */
    @Override
    public void configurePaginationButtons(int selectedPageNumber){
        BootstrapButton prevSelectedPageButton = (BootstrapButton) pagesGrid.getChildAt(pageNum - 1);
        prevSelectedPageButton.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
        prevSelectedPageButton.setShowOutline(true);
        BootstrapButton selectedPageButton = (BootstrapButton) pagesGrid.getChildAt(selectedPageNumber - 1);
        selectedPageButton.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
        selectedPageButton.setShowOutline(false);

        pageNum = selectedPageNumber;

        if(pageNum == 1) {
            nextBtn.setVisibility(View.VISIBLE);
            prevBtn.setVisibility(View.INVISIBLE);
        } else if (pageNum == totalPages) {
            nextBtn.setVisibility(View.INVISIBLE);
            prevBtn.setVisibility(View.VISIBLE);
        } else if (totalPages == 1) {
            nextBtn.setVisibility(View.INVISIBLE);
            prevBtn.setVisibility(View.INVISIBLE);
        } else {
            nextBtn.setVisibility(View.VISIBLE);
            prevBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.prev_btn:
                configurePaginationButtons(pageNum - 1);
                gridView.setScrollY(0);
                artistShowsPresenter.getArtistShows(String.valueOf(pageNum));
                break;
            case R.id.next_btn:
                prevBtn.setVisibility(View.VISIBLE);
                configurePaginationButtons(pageNum + 1);
                gridView.setScrollY(0);
                artistShowsPresenter.getArtistShows(String.valueOf(pageNum));
                break;
        }
    }

    public ArtistShowsFragment getFragment(String artist) {
        Bundle bundle = new Bundle();
        bundle.putString("artist", artist);
        this.setArguments(bundle);
        return this;
    }
}
