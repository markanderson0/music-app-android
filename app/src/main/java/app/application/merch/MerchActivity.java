package app.application.merch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import app.application.drawer.DrawerActivity;
import app.application.R;
import app.application.merch.merchcategory.MerchCategoryFragment;
import app.application.merch.merchsearch.MerchSearchActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that contains the tabs for each merch category
 */
public class MerchActivity extends DrawerActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_viewpager);
        ButterKnife.bind(this);
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
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

    private void setupViewPager(ViewPager viewPager) {
        MerchActivity.ViewPagerAdapter adapter = new MerchActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MerchCategoryFragment().getFragment("band tshirt", "11450"), "Apparel");
        adapter.addFragment(new MerchCategoryFragment().getFragment("band merch", "2329"), "Accessories");
        adapter.addFragment(new MerchCategoryFragment().getFragment("cds and vinyl", "11233"), "Music");
        adapter.addFragment(new MerchCategoryFragment().getFragment("band posters", "158658"), "Art");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
