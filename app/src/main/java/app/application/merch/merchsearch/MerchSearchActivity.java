package app.application.merch.merchsearch;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.drawer.DrawerActivity;
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
 * Activity that searches for merch and displays the results.
 */
public class MerchSearchActivity extends DrawerActivity implements View.OnClickListener, MerchContract.View {

    @BindView(R.id.gridView)
    GridViewWithHeaderAndFooter mGridView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_merch)
    TextView noMerch;

    @Inject
    MerchPresenter merchPresenter;

    private MerchGridViewAdapter mGridAdapter;
    private ArrayList<MerchItem> mMerchItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merch_category);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);
        merchPresenter.attachView(this);

        setTitle(getIntent().getExtras().getString("search"));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View footerView = layoutInflater.inflate(R.layout.load_more, null);
        BootstrapButton mLoadBtn = (BootstrapButton) footerView.findViewById(R.id.browse_load_btn);
        mLoadBtn.setOnClickListener(this);
        mGridView.addFooterView(footerView);

        merchPresenter.getMerch(getIntent().getExtras().getString("search"), "11450", "BestMatch");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        merchPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);
        final MenuItem searchActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                Intent ticketsSearchIntent = new Intent(getActivity(), MerchSearchActivity.class);
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
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridAdapter = new MerchGridViewAdapter(this, R.layout.merch_grid_item, mMerchItems, mGridView);
            mGridView.setAdapter(mGridAdapter);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mGridAdapter = new MerchGridViewAdapter(this, R.layout.merch_grid_item, mMerchItems, mGridView);
            mGridView.setAdapter(mGridAdapter);
        }
    }

    @Override
    public void showMerch(ArrayList<MerchItem> merchItems) {
        mMerchItems = merchItems;
        mGridAdapter = new MerchGridViewAdapter(this, R.layout.merch_grid_item, mMerchItems, mGridView);
        mGridView.setAdapter(mGridAdapter);
        mGridAdapter.setGridData(mMerchItems);
    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        mGridView.setVisibility(View.GONE);
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
        mGridView.setVisibility(View.VISIBLE);
        noMerch.setVisibility(View.GONE);
    }

    @OnItemClick(R.id.gridView)
    public void onClick(View view) {
        if(view.getId() == R.id.browse_load_btn) {
            merchPresenter.getMerch(getIntent().getExtras().getString("search"), "11450", "BestMatch");
        } else {
            TextView mProductName = ButterKnife.findById(view, R.id.merch_grid_item_name);
            TextView mProductPrice = ButterKnife.findById(view, R.id.merch_grid_item_price);
            Intent merchProductIntent = new Intent(MerchSearchActivity.this, MerchProductActivity.class);
            merchProductIntent.putExtra("image", merchPresenter.getMerchItemImage(mProductName.getText().toString()));
            merchProductIntent.putExtra("name", mProductName.getText().toString());
            merchProductIntent.putExtra("price", mProductPrice.getText().toString());
            startActivity(merchProductIntent);
        }
    }
}
