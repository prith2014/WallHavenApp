package com.example.prithviv.wallhavenapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.prithviv.wallhavenapp.HttpRequest.RetrofitServer;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.adapters.WallpapersAdapter;
import com.example.prithviv.wallhavenapp.models.Data;
import com.example.prithviv.wallhavenapp.models.Meta;
import com.example.prithviv.wallhavenapp.models.WallpaperList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private OnFragmentInteractionListener mListener;
    private RecyclerView latestRecyclerView;
    private WallpapersAdapter myWallpapersAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Meta latestWallpapersMeta;
    private List<Data> latestWallpapersArrayList;
    private Handler handler;
    private RetrofitServer retrofitServer;

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
        latestWallpapersArrayList = new ArrayList<>();
        handler = new Handler();

        retrofitServer = new RetrofitServer(this::getActivity);
        getLatestWallpapers(retrofitServer.getLatestWallpapersCall());
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

        setRecyclerViewAdapter(latestWallpapersArrayList);
        setScrollListener(latestRecyclerView);

        SwipeRefreshLayout swipeRefreshLayout = latestView.findViewById(R.id.swipe_refresh_latest);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            refreshWallpapers();
            swipeRefreshLayout.setRefreshing(false);
        });

        FloatingActionButton refreshFloatingActionButton = latestView.findViewById(R.id.refresh_floatingActionButton);
        refreshFloatingActionButton.setOnClickListener(v -> refreshWallpapers());

        return latestView;
    }

    private void setRecyclerViewAdapter(List<Data> wallpapers) {
        //return MyActivity.this;       // For activities
        myWallpapersAdapter = new WallpapersAdapter(this::getActivity, wallpapers);

        latestRecyclerView.setAdapter(myWallpapersAdapter);
    }

    private void setScrollListener(RecyclerView mRecyclerView) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (retrofitServer.isWallpaperLoading())
                    return;

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                int fiveItemsBeforeEnd = totalItemCount - 5;

                if (pastVisibleItems + visibleItemCount >= fiveItemsBeforeEnd) {
                    //Five Items before end of list
                    getLatestWallpapers(retrofitServer.getLatestWallpapersCall());
                }
            }
        });
    }

    private void getLatestWallpapers(Call<WallpaperList> call) {
        call.enqueue(new Callback<WallpaperList>() {
            @Override
            public void onResponse(@NonNull Call<WallpaperList> call, @NonNull retrofit2.Response<WallpaperList> response) {
                if (response.isSuccessful()) {
                    //Log.d("JSON", response.toString());
                    WallpaperList wallpaperList = response.body();
                    assert wallpaperList != null;

                    //Log.d("JSON", wallpaper.getData().get(0).getUrl());
                    wallpaperList.parseResponse(latestWallpapersArrayList);
                    latestWallpapersMeta = wallpaperList.getMeta();
                    //Log.d("JSON", latestWallpapersList.get(0).getUrl());

                    handler.post(() -> myWallpapersAdapter.notifyDataSetChanged());
                    retrofitServer.setIsWallpaperLoading(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WallpaperList> call, @NonNull Throwable t) {
                if (t.getMessage() != null) {
                    Log.d("Error", t.getMessage());
                }
            }
        });
    }

    private void refreshWallpapers() {
        latestWallpapersArrayList.clear();
        retrofitServer.refreshPageNumber();
        getLatestWallpapers(retrofitServer.getLatestWallpapersCall());
    }

    @Override
    public void onAttach(@NonNull Context context) {
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

