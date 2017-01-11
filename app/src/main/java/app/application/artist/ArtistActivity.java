package app.application.artist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.application.drawer.DrawerActivity;
import app.application.R;
import app.application.artist.albums.ArtistAlbumsFragment;
import app.application.artist.merch.ArtistMerchFragment;
import app.application.artist.shows.ArtistShowsFragment;
import app.application.artist.tickets.ArtistTicketsFragment;
import app.application.artist.topvideos.TopVideosFragment;
import app.application.artist.videos.ArtistVideosFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that contains the tabs for each category of the artists profile
 */
public class ArtistActivity extends DrawerActivity {

    @BindView(R.id.artist_tabs)
    TabLayout tabLayout;
    @BindView(R.id.artist_viewpager)
    ViewPager viewPager;
    @BindView(R.id.artist_image)
    ImageView artistImageView;

    String artistName, artistImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullLayoutId(R.layout.artist_toolbar);
        setContentView(R.layout.artist);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        artistName = extras.getString("artist");
        artistImage = extras.getString("image");
        setTitle(artistName);

        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        Picasso.with(this).load(artistImage).fit().into(artistImageView);
    }

    private void setupViewPager(ViewPager viewPager) {
        ArtistActivity.ViewPagerAdapter adapter = new ArtistActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopVideosFragment(), "Top Videos");
        adapter.addFragment(new ArtistVideosFragment(), "Videos");
        adapter.addFragment(new ArtistTicketsFragment().getFragment(artistName), "Tickets");
        adapter.addFragment(new ArtistMerchFragment().getFragment(artistName, "11450", "BestMatch"), "Merch");
        adapter.addFragment(new ArtistShowsFragment().getFragment(artistName), "Shows");
        adapter.addFragment(new ArtistAlbumsFragment().getFragment(artistName), "Albums");
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
