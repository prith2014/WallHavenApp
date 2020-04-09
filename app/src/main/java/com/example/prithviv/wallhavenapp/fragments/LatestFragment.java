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

import com.example.prithviv.wallhavenapp.ContextProvider;
import com.example.prithviv.wallhavenapp.HttpRequest.RetrofitServer;
import com.example.prithviv.wallhavenapp.HttpRequest.WallhavenAPI;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.adapters.WallpapersAdapter;
import com.example.prithviv.wallhavenapp.models.Data;
import com.example.prithviv.wallhavenapp.models.Meta;
import com.example.prithviv.wallhavenapp.models.WallpaperList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


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
    private static final String WALLHAVEN_API_URL = "https://wallhaven.cc/api/v1/";
    private static final String LATEST_WALLPAPER_GET_REQUEST_URL = "https://wallhaven.cc/api/v1/search";
    // Page 2: "https://wallhaven.cc/api/v1/search?page=2"

    public static final String LATEST_FRAGMENT_TAG = "LATEST_FRAGMENT_TAG";

    private OnFragmentInteractionListener mListener;
    private RecyclerView latestRecyclerView;
    private WallpapersAdapter myWallpapersAdapter;
    private LinearLayoutManager linearLayoutManager;
    private WallpaperList wallpaperList;
    private Meta latestWallpapersMeta;
    private List<Data> latestWallpapersList;
    private int pageNumber = 0;
    private boolean wallpapersLoading;
    private Handler handler;
    private RetrofitServer retrofitServer;
    private WallhavenAPI wallhavenAPI;

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
        latestWallpapersList = new ArrayList<>();
        handler = new Handler();

        retrofitServer = new RetrofitServer();
        wallhavenAPI = retrofitServer.getRetrofitInstance().create(WallhavenAPI.class);

        getLatestWallpapers(latestWallpapersList);
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

        setRecyclerViewAdapter(latestWallpapersList);
        setScrollListener(latestRecyclerView);

        return latestView;
    }

    private void setRecyclerViewAdapter(List<Data> wallpapers) {
        myWallpapersAdapter = new WallpapersAdapter(new ContextProvider() {
            @Override
            public Context getContext() {
                return getActivity();
                //return MyActivity.this;       // For activities
            }
        }, wallpapers);

        latestRecyclerView.setAdapter(myWallpapersAdapter);
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
                    //Five Items before end of list
                    getLatestWallpapers(latestWallpapersList);
                }
            }
        });
    }

    private void getLatestWallpapers(List<Data> wallpapers) {
        setWallpapersLoading(true);

        Call<WallpaperList> retroCall = wallhavenAPI.listLatestWallpapers(getNextPageNumber());

        retroCall.enqueue(new Callback<WallpaperList>() {
            @Override
            public void onResponse(Call<WallpaperList> call, retrofit2.Response<WallpaperList> response) {
                if (response.isSuccessful()) {
                    //Log.d("JSON", response.toString());
                    WallpaperList wallpaperList = response.body();
                    assert wallpaperList != null;

                    //Log.d("JSON", wallpaper.getData().get(0).getUrl());
                    wallpapers.addAll(wallpaperList.getData());
                    latestWallpapersMeta = wallpaperList.getMeta();
                    //Log.d("JSON", latestWallpapersList.get(0).getUrl());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myWallpapersAdapter.notifyDataSetChanged();
                        }
                    });
                    setWallpapersLoading(false);
                }
            }

            @Override
            public void onFailure(Call<WallpaperList> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private int getNextPageNumber() {
        pageNumber++;
        //Log.d("Page", Integer.toString(pageNumber));
        return pageNumber;
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

