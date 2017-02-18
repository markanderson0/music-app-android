package app.application.user.profile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import app.application.drawer.DrawerActivity;
import app.application.R;
import app.application.user.profile.favourites.FavouritesFragment;
import app.application.user.profile.following.FollowingFragment;
import app.application.user.profile.videos.VideosFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that contains the tabs for each category of the users profile
 */
public class ProfileActivity extends DrawerActivity {

    @BindView(R.id.profile_tabs)
    TabLayout tabLayout;
    @BindView(R.id.profile_viewpager)
    ViewPager viewPager;
    @BindView(R.id.profile_image)
    ImageView profileImageView;
    @BindView(R.id.profile_pic)
    ImageView profilePicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullLayoutId(R.layout.profile_toolbar);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);
        setTitle("testUser");
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        profileImageView.setImageDrawable(getDrawableImage("img/sky.jpg"));
        profilePicImageView.setImageDrawable(getDrawableImage("img/profile.png"));
    }

    private Drawable getDrawableImage(String imagePath) {
        InputStream ims = null;
        try {
            ims = this.getAssets().open(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Drawable.createFromStream(ims, null);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new VideosFragment(), "Videos");
        adapter.addFragment(new FollowingFragment(), "Following");
        adapter.addFragment(new FavouritesFragment(), "Favourites");
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
