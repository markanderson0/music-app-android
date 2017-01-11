package app.application.drawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.mikepenz.fastadapter.utils.RecyclerViewCacheUtil;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import app.application.R;
import app.application.base.BaseActivity;
import app.application.browse.BrowseActivity;
import app.application.merch.MerchActivity;
import app.application.tickets.TicketsActivity;
import app.application.user.following.FollowingActivity;
import app.application.user.profile.ProfileActivity;
import app.application.user.settings.SettingsActivity;
import app.application.user.upload.UploadActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity displays the drawer menu.
 */
public class DrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CoordinatorLayout fullLayout = null;
    LinearLayout frameLayout = null;
    Drawer result = null;
    Bundle savedInstanceState = null;
    Toolbar toolbar;
    int fullLayoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        fullLayoutId =  R.layout.app_bar_main;
        setContentView(R.layout.activity_main);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public Activity getActivity(){
        return this;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFullLayoutId(int fullLayoutId) {
        this.fullLayoutId = fullLayoutId;
    }

    public int getFullLayoutId() {
        return fullLayoutId;
    }

    @Override
    public void setContentView(int layoutResID) {
        fullLayout = (CoordinatorLayout) getLayoutInflater().inflate(getFullLayoutId(), null);
        frameLayout = (LinearLayout) fullLayout.findViewById(R.id.content_main);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(fullLayout);

        //Drawer content
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface font = Typeface.createFromAsset(getAssets(), "SourceSansPro-Regular.ttf");
        TypefaceProvider.registerDefaultIconSets();
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withHasStableIds(true)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .addDrawerItems(
                        new TitleDrawerItem().withTypeface(font),
                        new DividerDrawerItem(),
                        new ExpandableDrawerItem()
                                .withName("UserName")
                                .withIcon(FontAwesome.Icon.faw_user)
                                .withIdentifier(19)
                                .withSelectable(false)
                                .withSelectable(true)
                                .withTypeface(font)
                                .withTextColor(Color.WHITE)
                                .withIconColor(Color.WHITE)
                                .withSelectedColor(Color.LTGRAY)
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName("Profile")
                                                .withLevel(2)
                                                .withIcon(FontAwesome.Icon.faw_user)
                                                .withIdentifier(2000)
                                                .withSelectable(true)
                                                .withTypeface(font)
                                                .withTextColor(Color.WHITE)
                                                .withIconColor(Color.WHITE)
                                                .withSelectedColor(Color.LTGRAY),
                                        new SecondaryDrawerItem()
                                                .withName("Following")
                                                .withLevel(2)
                                                .withIcon(FontAwesome.Icon.faw_heart)
                                                .withIdentifier(2001)
                                                .withSelectable(true)
                                                .withTypeface(font)
                                                .withTextColor(Color.WHITE)
                                                .withIconColor(Color.WHITE)
                                                .withSelectedColor(Color.LTGRAY),
                                        new SecondaryDrawerItem()
                                                .withName("Upload")
                                                .withLevel(2)
                                                .withIcon(FontAwesome.Icon.faw_upload)
                                                .withIdentifier(2002)
                                                .withSelectable(true)
                                                .withTypeface(font)
                                                .withTextColor(Color.WHITE)
                                                .withIconColor(Color.WHITE)
                                                .withSelectedColor(Color.LTGRAY),
                                        new SecondaryDrawerItem()
                                                .withName("Settings")
                                                .withLevel(2)
                                                .withIcon(FontAwesome.Icon.faw_cog)
                                                .withIdentifier(2003)
                                                .withSelectable(true)
                                                .withTypeface(font)
                                                .withTextColor(Color.WHITE)
                                                .withIconColor(Color.WHITE)
                                                .withSelectedColor(Color.LTGRAY),
                                        new SecondaryDrawerItem()
                                                .withName("LogOut")
                                                .withLevel(2)
                                                .withIcon(FontAwesome.Icon.faw_sign_out)
                                                .withIdentifier(2004)
                                                .withSelectable(true)
                                                .withTypeface(font)
                                                .withTextColor(Color.WHITE)
                                                .withIconColor(Color.WHITE)
                                                .withSelectedColor(Color.LTGRAY)
                                ),
                        new AccountDrawerItem().withTypeface(font).withActivity(this),
                        new DividerDrawerItem(),
                        new SearchDrawerItem().withActivity(this),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem()
                                .withName("Browse")
                                .withIcon(FontAwesome.Icon.faw_bars)
                                .withIdentifier(1)
                                .withSelectable(true)
                                .withTypeface(font)
                                .withTextColor(Color.WHITE)
                                .withIconColor(Color.WHITE)
                                .withSelectedColor(Color.LTGRAY),
                        new PrimaryDrawerItem()
                                .withName("Tickets")
                                .withIcon(FontAwesome.Icon.faw_ticket)
                                .withIdentifier(2)
                                .withSelectable(true)
                                .withTypeface(font)
                                .withTextColor(Color.WHITE)
                                .withIconColor(Color.WHITE)
                                .withSelectedColor(Color.LTGRAY),
                        new PrimaryDrawerItem()
                                .withName("Merch")
                                .withIcon(FontAwesome.Icon.faw_shopping_cart)
                                .withIdentifier(3)
                                .withSelectable(true)
                                .withTypeface(font)
                                .withTextColor(Color.WHITE)
                                .withIconColor(Color.WHITE)
                                .withSelectedColor(Color.LTGRAY)
                )
                .withOnDrawerItemClickListener((View view, int position, IDrawerItem drawerItem) -> {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(DrawerActivity.this, BrowseActivity.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(DrawerActivity.this, TicketsActivity.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(DrawerActivity.this, MerchActivity.class);
                            } else if (drawerItem.getIdentifier() == 2000) {
                                intent = new Intent(DrawerActivity.this, ProfileActivity.class);
                            } else if (drawerItem.getIdentifier() == 2001) {
                                intent = new Intent(DrawerActivity.this, FollowingActivity.class);
                            } else if (drawerItem.getIdentifier() == 2002) {
                                intent = new Intent(DrawerActivity.this, UploadActivity.class);
                            } else if (drawerItem.getIdentifier() == 2003) {
                                intent = new Intent(DrawerActivity.this, SettingsActivity.class);
                            }
                            if (intent != null) {
                                DrawerActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        hideSoftKeyboard(DrawerActivity.this);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {}

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {}

                    public void hideSoftKeyboard(Activity activity) {
                        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .withSliderBackgroundDrawableRes(R.drawable.side_nav_bar)
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        //if you have many different types of DrawerItems you can magically pre-cache those items to get a better scroll performance
        //make sure to init the cache after the DrawerBuilder was created as this will first clear the cache to make sure no old elements are in
        //RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);
        new RecyclerViewCacheUtil<IDrawerItem>().withCacheSize(2).apply(result.getRecyclerView(), result.getDrawerItems());

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            result.setSelection(21, false);
        }
    }


}
