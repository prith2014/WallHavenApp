package com.example.prithviv.wallhavenapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.adapters.TopListAdapter;
import com.example.prithviv.wallhavenapp.models.Wallpaper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // Top List URL does not give the uploaders or tags of each wallpaper
    private static final String topListURL = "https://wallhaven.cc/api/v1/search";

    private OnFragmentInteractionListener mListener;
    private RequestQueue queue;
    private RecyclerView homeRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> topListURLs;
    private List<Wallpaper> topListWallpapers = new ArrayList<>();
    private String topListJSONString;
    private ImageView imageView;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // Inflate the layout for this fragment
        View homeView =  inflater.inflate(R.layout.fragment_home, container, false);
        // RecyclerView
        homeRecyclerView = homeView.findViewById(R.id.my_recycler_view);
        homeRecyclerView.setHasFixedSize(true);
        // Linear layout manager
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        homeRecyclerView.setLayoutManager(layoutManager);

        topListURLs = new ArrayList<>();

        getTopList();

        /*
        try {
            //set time in mili
            Thread.sleep(3000);

        }catch (Exception e){
            e.printStackTrace();
        }
        */

        // RecyclerView List Adapter
        //mAdapter = new TopListAdapter(getActivity().getApplicationContext(), topListURLs);
        //mAdapter = new TopListAdapter(getActivity().getApplicationContext(), topListWallpapers);
        //homeRecyclerView.setAdapter(mAdapter);

        return homeView;
    }

    private void getTopList() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, topListURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("JSON", response.getJSONArray("data").toString());
                    Gson gson = new Gson();

                    topListWallpapers = gson.fromJson(response.getJSONArray("data").toString(), new TypeToken<List<Wallpaper>>(){}.getType() );
                    //Log.d("URL", topListWallpapers.get(1).getURL());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setRecyclerViewAdapter(topListWallpapers);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    public void setRecyclerViewAdapter(List<Wallpaper> wallpapers) {
        TopListAdapter myTopListAdapter = new TopListAdapter(getActivity(), wallpapers);
        homeRecyclerView.setAdapter(myTopListAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

