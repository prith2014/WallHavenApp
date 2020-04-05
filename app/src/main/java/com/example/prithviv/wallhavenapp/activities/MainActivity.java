package com.example.prithviv.wallhavenapp.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.prithviv.wallhavenapp.HttpRequest.RetrofitServer;
import com.example.prithviv.wallhavenapp.HttpRequest.WallhavenAPI;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.fragments.LatestFragment;
import com.example.prithviv.wallhavenapp.fragments.SearchFragment;
import com.example.prithviv.wallhavenapp.fragments.ToplistFragment;
import com.example.prithviv.wallhavenapp.models.Data;
import com.example.prithviv.wallhavenapp.models.WallpaperList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity
        implements  LatestFragment.OnFragmentInteractionListener,
                    SearchFragment.OnFragmentInteractionListener,
                    ToplistFragment.OnFragmentInteractionListener {

    private static final String TAG = "Main Activity TAG";
    final Fragment fragmentLatest = new LatestFragment();
    final Fragment fragmentSearch = new SearchFragment();
    final Fragment fragmentToplist = new ToplistFragment();
    final FragmentManager fm = getSupportFragmentManager();

    Fragment active = fragmentLatest;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_latest:
                    fm.beginTransaction().hide(active).show(fragmentLatest).commit();
                    active = fragmentLatest;
                    return true;
                case R.id.search_dashboard:
                    fm.beginTransaction().hide(active).show(fragmentSearch).commit();
                    active = fragmentSearch;
                    return true;
                case R.id.navigation_toplist:
                    fm.beginTransaction().hide(active).show(fragmentToplist).commit();
                    active = fragmentToplist;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Fragments
        fm.beginTransaction().add(R.id.main_container, fragmentSearch, "fragSearch").hide(fragmentSearch).commit();
        fm.beginTransaction().add(R.id.main_container, fragmentToplist, "fragToplist").hide(fragmentToplist).commit();
        fm.beginTransaction().add(R.id.main_container, fragmentLatest, "fragLatest").commit();

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
            //doMySearch(query);

            Bundle bundle = new Bundle();
            bundle.putString(SearchManager.QUERY, query);
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, searchFragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * Performs a search and passes the results to the container
     * Activity that holds your Fragments.
     */
    public void doMySearch(String query) {
        Log.d(TAG, "doMySearch: " + query);
        // Pass into search fragment
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
