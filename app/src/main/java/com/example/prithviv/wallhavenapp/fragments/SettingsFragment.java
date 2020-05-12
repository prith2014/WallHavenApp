package com.example.prithviv.wallhavenapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.prithviv.wallhavenapp.ContextProvider;
import com.example.prithviv.wallhavenapp.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    private final String GENERAL_CATEGORY = "com.example.wallhavenapp.generalcategory";
    private final String ANIME_CATEGORY = "com.example.wallhavenapp.animecategory";
    private final String PEOPLE_CATEGORY = "com.example.wallhavenapp.peoplecategory";

    private SwitchPreference generalSwitchPreference;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.main_preferences, rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.wallhavenapp", Context.MODE_PRIVATE);
        //boolean generalCategory = sharedPreferences.getBoolean(GENERAL_CATEGORY, true);
        //boolean animeCategory = sharedPreferences.getBoolean(ANIME_CATEGORY, true);
        //boolean peopleCategory = sharedPreferences.getBoolean(PEOPLE_CATEGORY, true);

        generalSwitchPreference = getPreferenceManager().findPreference("general_switch_key");
        generalSwitchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                sharedPreferences.edit().putBoolean(GENERAL_CATEGORY, preference.).apply();
                Log.v("Settings", "General toggle is " + preference.isEnabled());
                return true;
            }
        });
        */
    }

}

