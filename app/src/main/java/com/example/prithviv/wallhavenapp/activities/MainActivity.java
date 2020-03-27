package com.example.prithviv.wallhavenapp.activities;

import android.net.Uri;
import android.os.Bundle;

import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.fragments.LatestFragment;
import com.example.prithviv.wallhavenapp.fragments.SearchFragment;
import com.example.prithviv.wallhavenapp.fragments.ToplistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements  LatestFragment.OnFragmentInteractionListener,
                    SearchFragment.OnFragmentInteractionListener,
                    ToplistFragment.OnFragmentInteractionListener {

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
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
