package com.example.prithviv.wallhavenapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import androidx.appcompat.widget.SearchView;

import com.example.prithviv.wallhavenapp.ContextProvider;
import com.example.prithviv.wallhavenapp.HttpRequest.RetrofitServer;
import com.example.prithviv.wallhavenapp.HttpRequest.WallhavenAPI;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.activities.MainActivity;
import com.example.prithviv.wallhavenapp.adapters.WallpapersAdapter;
import com.example.prithviv.wallhavenapp.models.Data;
import com.example.prithviv.wallhavenapp.models.Meta;
import com.example.prithviv.wallhavenapp.models.WallpaperList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public SearchView searchView;
    private List<Data> searchWallpapersList;
    private Meta searchWallpaperMeta;
    private Handler handler;
    private RetrofitServer retrofitServer;
    private WallhavenAPI wallhavenAPI;
    private RecyclerView searchRecyclerView;
    private SearchView searchBarView;
    private LinearLayoutManager linearLayoutManager;
    private WallpapersAdapter mySearchWallpapersAdapter;
    private boolean wallpapersLoading;
    private int pageNumber = 0;
    private String searchQuery;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String searchParam) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(SearchManager.QUERY, searchParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchWallpapersList = new ArrayList<>();
        handler = new Handler();
        retrofitServer = new RetrofitServer();
        wallhavenAPI = retrofitServer.getRetrofitInstance().create(WallhavenAPI.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View searchView = inflater.inflate(R.layout.fragment_search, container, false);
        searchBarView = searchView.findViewById(R.id.search_bar);
        // Recycler view
        searchRecyclerView = searchView.findViewById(R.id.search_recycler_view);
        searchRecyclerView.setHasFixedSize(true);
        // Linear layout manager
        linearLayoutManager = new LinearLayoutManager(getActivity());
        searchRecyclerView.setLayoutManager(linearLayoutManager);

        setRecyclerViewAdapter(searchWallpapersList);
        setScrollListener(searchRecyclerView);

        searchBarView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Search", "Search Query Submitted: " + query);
                searchQuery = query;

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(SearchManager.QUERY, query);
                intent.setAction(Intent.ACTION_SEARCH);
                searchBarView.clearFocus();
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Search", "Search Query: " + newText);
                return false;
            }
        });

        if (getArguments() != null) {
            searchQuery = getArguments().getString(SearchManager.QUERY);
            Log.d("Search", "Search this: " + searchQuery);
            getSearchWallpapers(searchWallpapersList, searchQuery);
            searchBarView.setQuery(searchQuery, false);
        }

        return searchView;
    }

    private void setRecyclerViewAdapter(List<Data> wallpapers) {
        mySearchWallpapersAdapter = new WallpapersAdapter(new ContextProvider() {
            @Override
            public Context getContext() {
                return getActivity();
                //return MyActivity.this;       // For activities
            }
        }, wallpapers);

        searchRecyclerView.setAdapter(mySearchWallpapersAdapter);
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
                    getSearchWallpapers(searchWallpapersList, searchQuery);
                }
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

    private void getSearchWallpapers(List<Data> wallpapers, String searchQuery) {
        setWallpapersLoading(true);

        Call<WallpaperList> retroCall = wallhavenAPI.listSearchWallpapers(searchQuery, getNextPageNumber());

        retroCall.enqueue(new Callback<WallpaperList>() {
            @Override
            public void onResponse(Call<WallpaperList> call, Response<WallpaperList> response) {
                if (response.isSuccessful()) {
                    //Log.d(TAG, response.toString());
                    WallpaperList wallpaperList = response.body();

                    assert wallpaperList != null;
                    wallpapers.addAll(wallpaperList.getData());
                    searchWallpaperMeta = wallpaperList.getMeta();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mySearchWallpapersAdapter.notifyDataSetChanged();
                        }
                    });
                    setWallpapersLoading(false);
                }
            }
            @Override
            public void onFailure(Call<WallpaperList> call, Throwable t) {
                Log.d("Error Toplist", t.getMessage());
            }
        });
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
