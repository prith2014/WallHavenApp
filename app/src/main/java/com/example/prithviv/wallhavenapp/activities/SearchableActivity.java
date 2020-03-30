package com.example.prithviv.wallhavenapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.prithviv.wallhavenapp.R;

public class SearchableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIntent(getIntent());

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = intent.getStringExtra("search_value");
            Log.d("Query", "Query in Searchable: " + query);
            doMySearch(query);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
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
