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
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.adapters.WallpapersAdapter;
import com.example.prithviv.wallhavenapp.models.Data;
import com.example.prithviv.wallhavenapp.models.Meta;
import com.example.prithviv.wallhavenapp.models.WallpaperList;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String TOPLIST_FRAGMENT_TAG = "TOPLIST_FRAGMENT_TAG";

    private OnFragmentInteractionListener mListener;
    private RecyclerView toplistRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private WallpapersAdapter myToplistWallpapersAdapter;

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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToplistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToplistFragment newInstance(String param1, String param2) {
        ToplistFragment fragment = new ToplistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        topListWallpapersArrayList = new ArrayList<>();
        handler = new Handler();

        retrofitServer = new RetrofitServer(new ContextProvider() {
            @Override
            public Context getContext() {
                return getActivity();
            }
        });
        getToplistWallpapers(retrofitServer.getToplistWallpapersCall());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View toplistView =  inflater.inflate(R.layout.fragment_toplist, container, false);
        // Recycler view
        toplistRecyclerView = toplistView.findViewById(R.id.toplist_recycler_view);
        toplistRecyclerView.setHasFixedSize(true);
        // Linear layout manager
        linearLayoutManager = new LinearLayoutManager(getActivity());
        toplistRecyclerView.setLayoutManager(linearLayoutManager);

        setRecyclerViewAdapter(topListWallpapersArrayList);
        setScrollListener(toplistRecyclerView);

        return toplistView;
    }

    private void setRecyclerViewAdapter(List<Data> wallpapers) {
        myToplistWallpapersAdapter = new WallpapersAdapter(new ContextProvider() {
            @Override
            public Context getContext() {
                return getActivity();
                //return MyActivity.this;       // For activities
            }
        }, wallpapers);

        toplistRecyclerView.setAdapter(myToplistWallpapersAdapter);
    }

    private void setScrollListener(RecyclerView mRecyclerView) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (retrofitServer.isWallpaperLoading())
                    return;

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                int fiveItemsBeforeEnd = totalItemCount - 5;

                if (pastVisibleItems + visibleItemCount >= fiveItemsBeforeEnd) {
                    //Five Items before end of list
                    getToplistWallpapers(retrofitServer.getToplistWallpapersCall());
                }
            }
        });
    }

    private void getToplistWallpapers(Call<WallpaperList> call) {
        call.enqueue(new Callback<WallpaperList>() {
            @Override
            public void onResponse(Call<WallpaperList> call, Response<WallpaperList> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.toString());
                    WallpaperList wallpaperList = response.body();
                    assert wallpaperList != null;

                    wallpaperList.parseResponse(topListWallpapersArrayList);
                    topListWallpapersMeta = wallpaperList.getMeta();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myToplistWallpapersAdapter.notifyDataSetChanged();
                        }
                    });
                    retrofitServer.setIsWallpaperLoading(false);
                }
            }
            @Override
            public void onFailure(Call<WallpaperList> call, Throwable t) {
                Log.d("Error Toplist", t.getMessage());
            }
        });
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
