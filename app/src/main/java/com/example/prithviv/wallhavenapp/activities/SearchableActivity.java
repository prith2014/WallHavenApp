package com.example.prithviv.wallhavenapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class SearchableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    /**
     * Performs a search and passes the results to the container
     * Activity that holds your Fragments.
     */
    public void doMySearch(String query) {
        // TODO: implement this
    }
}
