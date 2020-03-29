package com.example.prithviv.wallhavenapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SearchableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = intent.getStringExtra("search_value");
            Log.d("Query", "Query in Searchable: " + query);
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
