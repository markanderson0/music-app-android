package app.application.artist.merch;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.R;
import app.application.dagger.DaggerInjector;
import app.application.merch.MerchContract;
import app.application.merch.MerchGridViewAdapter;
import app.application.merch.MerchPresenter;
import app.application.merch.data.models.MerchItem;
import app.application.merch.merchproduct.MerchProductActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Fragment that displays merch for the seelcted artist.
 */
public class ArtistMerchFragment extends Fragment implements View.OnClickListener, MerchContract.View {

    @BindView(R.id.gridView)
    GridViewWithHeaderAndFooter gridView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_merch)
    TextView noMerch;

    @Inject
    MerchPresenter merchPresenter;

    private MerchGridViewAdapter gridAdapter;
    private ArrayList<MerchItem> merchItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.merch_category, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        merchPresenter.attachView(this);

        View footerView = inflater.inflate(R.layout.load_more, null);
        BootstrapButton mLoadBtn = (BootstrapButton) footerView.findViewById(R.id.browse_load_btn);
        mLoadBtn.setOnClickListener(this);
        gridView.addFooterView(footerView);

        merchPresenter.getMerch(getArguments().getString("artist"), getArguments().getString("categoryId"), getArguments().getString("sortValue"));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        merchPresenter.detachView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridAdapter = new MerchGridViewAdapter(getContext(), R.layout.merch_grid_item, merchItems, gridView);
            gridView.setAdapter(gridAdapter);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridAdapter = new MerchGridViewAdapter(getContext(), R.layout.merch_grid_item, merchItems, gridView);
            gridView.setAdapter(gridAdapter);
        }
    }

    @Override
    public void showMerch(ArrayList<MerchItem> merchItems) {
        this.merchItems = merchItems;
        gridAdapter = new MerchGridViewAdapter(getContext(), R.layout.merch_grid_item, this.merchItems, gridView);
        gridView.setAdapter(gridAdapter);
        gridAdapter.setGridData(this.merchItems);
    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        gridView.setVisibility(View.GONE);
        noMerch.setText(message);
        noMerch.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        noMerch.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        noMerch.setVisibility(View.GONE);
    }

    @OnItemClick(R.id.gridView)
    public void onClick(View view) {
        if(view.getId() == R.id.browse_load_btn) {
            merchPresenter.getMerch(getArguments().getString("artist"), getArguments().getString("categoryId"), getArguments().getString("sortValue"));
        } else {
            TextView mProductName = ButterKnife.findById(view, R.id.merch_grid_item_name);
            TextView mProductPrice = ButterKnife.findById(view, R.id.merch_grid_item_price);
            Intent merchProductIntent = new Intent(getContext(), MerchProductActivity.class);
            merchProductIntent.putExtra("image", merchPresenter.getMerchItemImage(mProductName.getText().toString()));
            merchProductIntent.putExtra("name", mProductName.getText().toString());
            merchProductIntent.putExtra("price", mProductPrice.getText().toString());
            startActivity(merchProductIntent);
        }
    }

    public ArtistMerchFragment getFragment(String artist, String categoryId, String sortValue) {
        Bundle bundle = new Bundle();
        bundle.putString("artist", artist);
        bundle.putString("categoryId", categoryId);
        bundle.putString("sortValue", sortValue);
        this.setArguments(bundle);
        return this;
    }
}
