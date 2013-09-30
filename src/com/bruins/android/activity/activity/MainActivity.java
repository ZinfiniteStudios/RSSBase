package com.bruins.android.activity.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.actionbarsherlock.app.ActionBar;
import com.bruins.android.activity.fragment.ScheduleFragment;
import com.crashlytics.android.Crashlytics;
import com.crittercism.app.Crittercism;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.bruins.android.activity.adapter.ArticleListAdapter;
import com.bruins.android.activity.db.DbAdapter;
import com.bruins.android.activity.fragment.AboutFragment;
import com.bruins.android.activity.fragment.ArticleDetailFragment;
import com.bruins.android.activity.fragment.ArticleListFragment;
import com.bruins.android.activity.fragment.FacebookFragment;
import com.bruins.android.activity.fragment.LocationsFragment;
import com.bruins.android.activity.rss.domain.Article;
import com.bruins.android.activity.widget.NavDrawerItem;
import roboguice.RoboGuice;
import roboguice.inject.InjectView;
import android.content.Context;
import com.actionbarsherlock.view.Window;
import android.view.View;
import android.view.ActionProvider;
import android.view.SubMenu;
import com.actionbarsherlock.view.MenuItem;
import roboguice.inject.RoboInjector;
import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import com.bruins.android.v2.R;


/**
 * Created with IntelliJ IDEA.
 * User: david.hodge
 * Date: 9/20/13
 * Time: 1:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends RoboSherlockFragmentActivity implements ArticleListFragment.Callbacks{

    public static final int NAV_ID_FEED = R.id.drawer_feed;
    public static final int NAV_ID_LOCATION = R.id.drawer_location;
    public static final int NAV_ID_FACEBOOK = R.id.drawer_facebook;
    public static final int NAV_ID_TWITTER = R.id.drawer_twitter;
    public static final int NAV_ID_ABOUT = R.id.drawer_about;

    @InjectView(R.id.drawer_root) DrawerLayout mDrawerLayout;
    @InjectView(R.id.fragment_container) FrameLayout mContent;
    @InjectView(R.id.drawer_feed) NavDrawerItem drawerFeed;
    @InjectView(R.id.drawer_location) NavDrawerItem drawerLocation;
    @InjectView(R.id.drawer_facebook) NavDrawerItem drawerFacebook;
    @InjectView(R.id.drawer_twitter) NavDrawerItem drawerTwitter;
    @InjectView(R.id.drawer_about) NavDrawerItem drawerAbout;
    @InjectView(R.id.nav_drawer_footer) ImageView navFooter;

    ActionBarDrawerToggle mActionBarDrawerToggle;
    Context mContext;
    NavDrawerItem curNavDrawerItem;
    FragmentManager mfragmentManager;
    private boolean mTwoPane;
    private DbAdapter dba;
    private PullToRefreshAttacher mPullToRefreshAttacher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);

        Crittercism.init(getApplicationContext(), getResources().getString(R.string.crittercism_id));
        setTheme(R.style.Theme_Bruins);
        setContentView(R.layout.main_activity);

        dba = new DbAdapter(this);
        if (findViewById(R.id.article_detail_container) != null) {
            mTwoPane = true;
            ((ArticleListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container))
                    .setActivateOnItemClick(true);
        }

        mContext = this;
        mfragmentManager = getSupportFragmentManager();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
        setSupportProgressBarIndeterminateVisibility(false);

        navFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.main_url))));
            }
        });

        initDrawerLayout();
        if(savedInstanceState == null){
            setContentFragment(NAV_ID_FEED);
        }
    }

    public void initDrawerLayout(){
        mDrawerLayout.setDrawerShadow(getResources().getDrawable(R.drawable.drawer_shadow), GravityCompat.START);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.ic_navigation_drawer,
                R.string.drawer_open,
                R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void navigationDrawerItemClick(View v) {
        setSupportProgressBarIndeterminateVisibility(false);
        switch (v.getId()) {
            default:
                if (curNavDrawerItem != v) {
                    setContentFragment(v.getId());
                    mDrawerLayout.closeDrawers();
                }
        }
    }

    private void setContentFragment(int fragID) {
        RoboInjector injector = RoboGuice.getInjector(this);
        Fragment fragment;
        Bundle args = new Bundle();
        switch (fragID) {
            case NAV_ID_FEED:
                fragment = injector.getInstance(ArticleListFragment.class);
                getSupportActionBar().setTitle(getResources().getString(R.string.ab_feed_title));
                break;
            case NAV_ID_LOCATION:
                fragment = injector.getInstance(LocationsFragment.class);
                getSupportActionBar().setTitle(getResources().getString(R.string.ab_location_title));
                break;
            case NAV_ID_FACEBOOK:
                fragment = injector.getInstance(FacebookFragment.class);
                getSupportActionBar().setTitle(getResources().getString(R.string.ab_facebook));
                break;
            case NAV_ID_TWITTER:
                fragment = injector.getInstance(ScheduleFragment.class);
                getSupportActionBar().setTitle("Schedule");
                break;
            case NAV_ID_ABOUT:
                fragment = injector.getInstance(AboutFragment.class);
                getSupportActionBar().setTitle(getResources().getString(R.string.ab_about));
                break;
            default:
                return;
        }
        if (fragment != null) {
            fragment.setArguments(args);
            fragment.setRetainInstance(true);
        }

            mDrawerLayout.closeDrawer(GravityCompat.START);
            FragmentTransaction ft = mfragmentManager.beginTransaction();
            ft.replace(R.id.fragment_container, fragment).addToBackStack("tag");
            ft.addToBackStack(null);
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.commit();

    }

    @Override
    public void onBackPressed() {
//        getFragmentManager().getBackStackEntryCount();
        if(mfragmentManager.getBackStackEntryCount() == 1){
            this.finish();
        }
        mfragmentManager.popBackStack();
    }

    @Override
    public void onItemSelected(String id) {
        Article selected = (Article) ((ArticleListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).getListAdapter().getItem(Integer.parseInt(id));

        //mark article as read
        dba.openToWrite();
        dba.markAsRead(selected.getGuid());
        dba.close();
        selected.setRead(true);
        ArticleListAdapter adapter = (ArticleListAdapter) ((ArticleListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).getListAdapter();
        adapter.notifyDataSetChanged();
        Log.e("CHANGE", "Changing to read: ");


//        //load article details to main panel
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putSerializable (Article.KEY, selected);

            ArticleDetailFragment fragment = new ArticleDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.article_detail_container, fragment)
                    .commit();

        } else {
//            Intent detailIntent = new Intent(this, ArticleDetailActivity.class);
//            Bundle arguments = new Bundle();
//            arguments.putSerializable (Article.KEY, selected);
//            Log.d("LINK", selected.getGuid());
//            detailIntent.putExtra(ArticleDetailFragment.ARG_ITEM_ID, id);
//            Log.d("LINK", id);
//            startActivity(detailIntent);
            Intent articleIntent = new Intent(this, ViewArticle.class);
            articleIntent.putExtra("URL", selected.getGuid());
            startActivity(articleIntent);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(getMenuItem(item))) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private android.view.MenuItem getMenuItem(final MenuItem item) {
        return new android.view.MenuItem() {
            @Override
            public int getItemId() {
                return item.getItemId();
            }

            public boolean isEnabled() {
                return true;
            }

            @Override
            public boolean collapseActionView() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean expandActionView() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public ActionProvider getActionProvider() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public View getActionView() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public char getAlphabeticShortcut() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int getGroupId() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public Drawable getIcon() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Intent getIntent() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ContextMenu.ContextMenuInfo getMenuInfo() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public char getNumericShortcut() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int getOrder() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public SubMenu getSubMenu() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public CharSequence getTitle() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public CharSequence getTitleCondensed() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean hasSubMenu() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isActionViewExpanded() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isCheckable() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isChecked() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isVisible() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public android.view.MenuItem setActionProvider(ActionProvider actionProvider) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setActionView(View view) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setActionView(int resId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setAlphabeticShortcut(char alphaChar) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setCheckable(boolean checkable) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setChecked(boolean checked) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setEnabled(boolean enabled) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setIcon(Drawable icon) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setIcon(int iconRes) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setIntent(Intent intent) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setNumericShortcut(char numericChar) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setShortcut(char numericChar, char alphaChar) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void setShowAsAction(int actionEnum) {
                // TODO Auto-generated method stub

            }

            @Override
            public android.view.MenuItem setShowAsActionFlags(int actionEnum) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setTitle(CharSequence title) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setTitle(int title) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setTitleCondensed(CharSequence title) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public android.view.MenuItem setVisible(boolean visible) {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance().activityStart(this); // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance().activityStop(this); // Add this method.
    }
}
