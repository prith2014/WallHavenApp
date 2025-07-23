package com.example.prithviv.wallhavenapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.fragments.LatestFragment;
import com.example.prithviv.wallhavenapp.fragments.SearchFragment;
import com.example.prithviv.wallhavenapp.fragments.SettingsFragment;
import com.example.prithviv.wallhavenapp.fragments.ToplistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements  LatestFragment.OnFragmentInteractionListener,
                    SearchFragment.OnFragmentInteractionListener,
                    ToplistFragment.OnFragmentInteractionListener {

    public static final String LATEST_FRAGMENT_TAG = "LATEST_FRAGMENT_TAG";
    public static final String TOPLIST_FRAGMENT_TAG = "TOPLIST_FRAGMENT_TAG";

    final Fragment fragmentLatest = new LatestFragment();
    final Fragment fragmentToplist = new ToplistFragment();
    Fragment fragmentSearch = new SearchFragment();
    final Fragment fragmentSettings = new SettingsFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment active = fragmentLatest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_latest:
                    fragmentManager.beginTransaction().hide(active).show(fragmentLatest).commit();
                    active = fragmentLatest;
                    return true;
                case R.id.search_dashboard:
                    fragmentManager.beginTransaction().hide(active).show(fragmentSearch).commit();
                    active = fragmentSearch;
                    return true;
                case R.id.navigation_toplist:
                    fragmentManager.beginTransaction().hide(active).show(fragmentToplist).commit();
                    active = fragmentToplist;
                    return true;
                case R.id.navigation_settings:
                    fragmentManager.beginTransaction().hide(active).show(fragmentSettings).commit();
                    active = fragmentSettings;
                    return true;
            }
            return false;
        });

        //Fragments
        fragmentManager.beginTransaction().add(R.id.main_container, fragmentSettings, "fragSettings").hide(fragmentSettings).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, fragmentSearch, "fragSearch").hide(fragmentSearch).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, fragmentToplist, TOPLIST_FRAGMENT_TAG).hide(fragmentToplist).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, fragmentLatest, LATEST_FRAGMENT_TAG).commit();

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("Query", "Query in Main: " + query);

            SearchFragment tempSearchFragment = SearchFragment.newInstance(query);
            fragmentManager.beginTransaction().remove(fragmentSearch).add(R.id.main_container, tempSearchFragment, "fragSearch").commit();
            fragmentSearch = tempSearchFragment;
            active = fragmentSearch;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
