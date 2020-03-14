package com.example.prithviv.wallhavenapp.activities;

import android.net.Uri;
import android.os.Bundle;

import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.fragments.HomeFragment;
import com.example.prithviv.wallhavenapp.fragments.SearchFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements  HomeFragment.OnFragmentInteractionListener,
                    SearchFragment.OnFragmentInteractionListener {

    //private TextView mTextMessage;
    final Fragment fragmentHome = new HomeFragment();
    final Fragment fragmentSearch = new SearchFragment();
    final FragmentManager fm = getSupportFragmentManager();

    Fragment active = fragmentHome;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_latest:
                    //mTextMessage.setText(R.string.title_home);
                    fm.beginTransaction().hide(active).show(fragmentHome).commit();
                    active = fragmentHome;
                    return true;
                case R.id.search_dashboard:
                    //mTextMessage.setText(R.string.title_search);
                    fm.beginTransaction().hide(active).show(fragmentSearch).commit();
                    active = fragmentSearch;
                    return true;
                case R.id.navigation_toplist:
                    //mTextMessage.setText(R.string.title_notifications);
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
        //mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Fragments
        fm.beginTransaction().add(R.id.main_container, fragmentSearch, "fragSearch").hide(fragmentSearch).commit();
        fm.beginTransaction().add(R.id.main_container, fragmentHome, "fragHome").commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
