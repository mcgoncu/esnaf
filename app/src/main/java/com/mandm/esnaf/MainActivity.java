package com.mandm.esnaf;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = PhotosFragment.newInstance();
                break;
            case 2:
                fragment = AboutFragment.newInstance();
                break;
            case 3:
                fragment = ContactFragment.newInstance();
                break;
            default:
                fragment = PlaceholderFragment.newInstance();
                break;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        if (position == 0) {
            while (fragmentManager.popBackStackImmediate());
        } else {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitle = title;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    private NavigationDrawerFragment getmNavigationDrawerFragment() {
        return mNavigationDrawerFragment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        int position = 0;
        if (count > 0) {
            String name = fragmentManager.getBackStackEntryAt(count - 1).getName();
            if (name.equals(PhotosFragment.class.getSimpleName()))
                position = 1;
            else if (name.equals(AboutFragment.class.getSimpleName()))
                position = 2;
            else if (name.equals(ContactFragment.class.getSimpleName()))
                position = 3;
        }
        getmNavigationDrawerFragment().setItemChecked(position);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        public static PlaceholderFragment newInstance() {
            return new PlaceholderFragment();
        }

        ImageView ivFacebook;
        ImageView ivTwitter;
        ImageView ivGPlus;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, container, false);
            ivFacebook = (ImageView) view.findViewById(R.id.imageViewFacebook);
            ivTwitter = (ImageView) view.findViewById(R.id.imageViewTwitter);
            ivGPlus = (ImageView) view.findViewById(R.id.imageViewGooglePlus);

            ivFacebook.setOnClickListener(this);
            ivTwitter.setOnClickListener(this);
            ivGPlus.setOnClickListener(this);

            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            getActivity().setTitle(R.string.app_name);
            ((MainActivity) getActivity()).restoreActionBar();
        }

        @Override
        public void onClick(View view) {
            String url = "";

            if (view == ivFacebook) {
                url = Constants.facebookUrl;
            } else if (view == ivTwitter) {
                url = Constants.twitterUrl;
            } else if (view == ivGPlus) {
                url = Constants.gPlusUrl;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }
}
