package com.example.prithviv.wallhavenapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.prithviv.wallhavenapp.ContextProvider;
import com.example.prithviv.wallhavenapp.MySingleton;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.adapters.LatestWallpapersAdapter;
import com.example.prithviv.wallhavenapp.models.Meta;
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
 * {@link LatestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LatestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LatestFragment extends Fragment {
    // Top List URL does not give the uploaders or tags of each wallpaper
    private static final String latestWallpapersURL = "https://wallhaven.cc/api/v1/search";
    // Page 2: "https://wallhaven.cc/api/v1/search?page=2"
    private static final String topListWallpapersURL = "https://wallhaven.cc/api/v1/search?toplist";

    private OnFragmentInteractionListener mListener;
    private RequestQueue queue;
    private RecyclerView latestRecyclerView;
    private LatestWallpapersAdapter myLatestWallpapersAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Wallpaper> latestWallpapers = new ArrayList<>();
    private Meta latestWallpapersMeta;
    private int pageNumber = 0;
    private boolean wallpapersLoading;

    protected Handler handler;

    public LatestFragment() {
        // Required empty public constructor
    }


    public static LatestFragment newInstance(String param1, String param2) {
        LatestFragment fragment = new LatestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = MySingleton.getInstance(getActivity()).getRequestQueue();
        handler = new Handler();
        getLatestWallpapers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View latestView =  inflater.inflate(R.layout.fragment_latest, container, false);
        // RecyclerView
        latestRecyclerView = latestView.findViewById(R.id.my_recycler_view);
        latestRecyclerView.setHasFixedSize(true);
        // Linear layout manager
        linearLayoutManager = new LinearLayoutManager(getActivity());
        latestRecyclerView.setLayoutManager(linearLayoutManager);

        setRecyclerViewAdapter(latestWallpapers);

        setScrollListener(latestRecyclerView);

        return latestView;
    }

    private void setRecyclerViewAdapter(List<Wallpaper> wallpapers) {
        myLatestWallpapersAdapter = new LatestWallpapersAdapter(new ContextProvider() {
            @Override
            public Context getContext() {
                return getActivity();
                //return MyActivity.this;       // For activities
            }
        }, wallpapers);
        latestRecyclerView.setAdapter(myLatestWallpapersAdapter);
    }

    private void setScrollListener(RecyclerView mRecyclerView) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (wallpapersLoading)
                    return;

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                int fiveItemsBeforeEnd = totalItemCount - 5;

                if (pastVisibleItems + visibleItemCount >= fiveItemsBeforeEnd) {
                    //Four Items before end of list
                    getLatestWallpapers();
                }
            }
        });
    }

    private void getLatestWallpapers() {
        setWallpapersLoading(true);
        String latestWallpapersURLPage = getLatestWallpapersNextPageNumberURL();
        Log.d("URL", latestWallpapersURLPage);
        JsonObjectRequest request = getNextPageLatestWallpapers(latestWallpapersURLPage);

        queue.add(request);
    }

    private String getLatestWallpapersNextPageNumberURL() {
        pageNumber++;
        return latestWallpapersURL + "?page=" + pageNumber;
    }

    private JsonObjectRequest getNextPageLatestWallpapers(String latestWallpapersURLPage) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, latestWallpapersURLPage, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Log.d("JSON", response.getJSONArray("data").toString());
                    //Log.d("meta", response.getJSONObject("meta").toString());

                    Gson gson = new Gson();
                    List<Wallpaper> tempLatestWallpapers = gson.fromJson(response.getJSONArray("data").toString(), new TypeToken<List<Wallpaper>>(){}.getType() );
                    latestWallpapersMeta = gson.fromJson(response.getJSONObject("meta").toString(), Meta.class);

                    latestWallpapers.addAll(tempLatestWallpapers);
                    pageNumber = latestWallpapersMeta.getCurrentPage();

                    //Log.d("URL", latestWallpapers.get(1).getThumbsOriginal());
                    Log.d("meta", Integer.toString(latestWallpapersMeta.getCurrentPage()));

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myLatestWallpapersAdapter.notifyDataSetChanged();
                        }
                    });
                    setWallpapersLoading(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        return request;
    }

    private void setWallpapersLoading(boolean input) {
        wallpapersLoading = input;
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

