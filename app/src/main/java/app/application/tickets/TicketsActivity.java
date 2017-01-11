package app.application.tickets;

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
import app.application.tickets.ticketsgenre.TicketsGenreFragment;
import app.application.tickets.ticketssearch.TicketsSearchActivity;
import app.application.tickets.toptickets.TopTicketsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that contains the tabs for each ticket genre
 */
public class TicketsActivity extends DrawerActivity {

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
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        TicketsActivity.ViewPagerAdapter adapter = new TicketsActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopTicketsFragment(), "Top");
        adapter.addFragment(new TicketsGenreFragment().getFragment("alt", "alternative rock"), "Alt / Indie");
        adapter.addFragment(new TicketsGenreFragment().getFragment("elect", "dance / electronic"), "Clubs / Dance");
        adapter.addFragment(new TicketsGenreFragment().getFragment("folk", "country"), "Country / Folk");
        adapter.addFragment(new TicketsGenreFragment().getFragment("fest", "festival"), "Festivals");
        adapter.addFragment(new TicketsGenreFragment().getFragment("hard-rock", "metal"), "Hard Rock / Metal");
        adapter.addFragment(new TicketsGenreFragment().getFragment("blues", "jazz / blues"), "Jazz / Blues");
        adapter.addFragment(new TicketsGenreFragment().getFragment("rnb", "r&b / soul"), "R&B / Urban");
        adapter.addFragment(new TicketsGenreFragment().getFragment("rap", "rap / hip hop"), "Rap / Hip-Hop");
        adapter.addFragment(new TicketsGenreFragment().getFragment("rock", "rock / pop"), "Rock / Pop");
        adapter.addFragment(new TicketsGenreFragment().getFragment("world", "world"), "World");
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
