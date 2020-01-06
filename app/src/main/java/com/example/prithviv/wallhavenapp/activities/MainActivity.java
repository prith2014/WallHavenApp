package com.example.prithviv.wallhavenapp.activities;

import android.net.Uri;
import android.os.Bundle;

import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.fragments.HomeFragment;
import com.example.prithviv.wallhavenapp.fragments.SearchFragment;
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

    private TextView mTextMessage;
    final Fragment fragmentHome = new HomeFragment();
    final Fragment fragmentSearch = new SearchFragment();
    final FragmentManager fm = getSupportFragmentManager();
    ImageView testImage;

    Fragment active = fragmentHome;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    fm.beginTransaction().hide(active).show(fragmentHome).commit();
                    active = fragmentHome;
                    return true;
                case R.id.search_dashboard:
                    mTextMessage.setText(R.string.title_search);
                    fm.beginTransaction().hide(active).show(fragmentSearch).commit();
                    active = fragmentSearch;
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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
        mTextMessage = findViewById(R.id.message);
        testImage = findViewById(R.id.testimage);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Fragments
        fm.beginTransaction().add(R.id.main_container, fragmentSearch, "fragSearch").hide(fragmentSearch).commit();
        fm.beginTransaction().add(R.id.main_container, fragmentHome, "fragHome").commit();
        final TextView textView = findViewById(R.id.text);

        // Request Queue
        /*
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://wallhaven.cc/api/v1/w/2e99dg";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //textView.setText("Response is: "+ response);
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                Log.d("Error.Response", error.toString());
            }
        });
        queue.add(stringRequest);
        */
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
