package com.example.prithviv.wallhavenapp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.prithviv.wallhavenapp.R;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final String GENERAL_CATEGORY = "com.example.wallhavenapp.generalcategory";
    private final String ANIME_CATEGORY = "com.example.wallhavenapp.animecategory";
    private final String PEOPLE_CATEGORY = "com.example.wallhavenapp.peoplecategory";

    //private SwitchPreference generalSwitchPreference;
    //private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.main_preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(GENERAL_CATEGORY)) {
            Preference pref = findPreference(key);
        }
    }
}

