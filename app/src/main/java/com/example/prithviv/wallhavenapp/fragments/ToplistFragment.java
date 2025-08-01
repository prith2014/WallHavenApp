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
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToplistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToplistFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView topListRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private WallpapersAdapter topListWallpapersAdapter;

    private List<Data> topListWallpapersArrayList;
    private Meta topListWallpapersMeta;
    private Handler handler;
    private RetrofitServer retrofitServer;


    public ToplistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ToplistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToplistFragment newInstance() {
        ToplistFragment fragment = new ToplistFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topListWallpapersArrayList = new ArrayList<>();
        handler = new Handler();

        retrofitServer = new RetrofitServer(this::getActivity);
        getTopListWallpapers(retrofitServer.getToplistWallpapersCall());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View topListView =  inflater.inflate(R.layout.fragment_toplist, container, false);

        // Recycler view
        topListRecyclerView = topListView.findViewById(R.id.toplist_recycler_view);
        topListRecyclerView.setHasFixedSize(true);

        // Linear layout manager
        linearLayoutManager = new LinearLayoutManager(getActivity());
        topListRecyclerView.setLayoutManager(linearLayoutManager);

        setRecyclerViewAdapter(topListWallpapersArrayList);
        setScrollListener(topListRecyclerView);

        SwipeRefreshLayout swipeRefreshLayout = topListView.findViewById(R.id.swipe_refresh_toplist);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            refreshWallpapers();
            swipeRefreshLayout.setRefreshing(false);
        });

        FloatingActionButton refreshFloatingActionButton = topListView.findViewById(R.id.refresh_floatingActionButton);
        refreshFloatingActionButton.setOnClickListener(v -> refreshWallpapers());

        return topListView;
    }

    private void setRecyclerViewAdapter(List<Data> wallpapers) {
        topListWallpapersAdapter = new WallpapersAdapter(this::getActivity, wallpapers);
        topListRecyclerView.setAdapter(topListWallpapersAdapter);
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
                    getTopListWallpapers(retrofitServer.getToplistWallpapersCall());
                }
            }
        });
    }

    // TODO: Does topListWallpapersArrayList have to be in Fragment?
    /*  It should be in Retrofit class if possible. Maybe put wallpapersArrayList in Retrofit instance
        and set wallpapers to recycleview adapter using getter function.
        Main reason toplistWallpapersArrayList is in fragment is because of Async nature of HTTP GET request
    */
    private void getTopListWallpapers(Call<WallpaperList> call) {
        call.enqueue(new Callback<WallpaperList>() {
            @Override
            public void onResponse(@NonNull Call<WallpaperList> call, @NonNull Response<WallpaperList> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.toString());
                    WallpaperList wallpaperList = response.body();
                    assert wallpaperList != null;

                    int prevSize = topListWallpapersArrayList.size();
                    wallpaperList.parseResponse(topListWallpapersArrayList);
                    topListWallpapersMeta = wallpaperList.getMeta();

                    handler.post(() -> topListWallpapersAdapter.notifyItemRangeInserted(prevSize - 1, wallpaperList.getData().size()));
                    retrofitServer.setIsWallpaperLoading(false);
                }
            }
            @Override
            public void onFailure(@NonNull Call<WallpaperList> call, @NonNull Throwable t) {
                if (t.getMessage() != null) {
                    Log.d("TOPLIST", "GET request error: " + t.getMessage());
                }
            }
        });
    }

    private void refreshWallpapers() {
        Log.d("TOPLIST", "Refreshing wallpapers");
        topListWallpapersAdapter.notifyItemRangeRemoved(0, topListWallpapersArrayList.size());
        topListWallpapersArrayList.clear();
        retrofitServer.refreshPageNumber();
        getTopListWallpapers(retrofitServer.getToplistWallpapersCall());
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
