package com.example.prithviv.wallhavenapp.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prithviv.wallhavenapp.HttpRequest.RetrofitServer;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.models.Data;
import com.example.prithviv.wallhavenapp.models.Wallpaper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedWallpaperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedWallpaperFragment extends Fragment {
    public static final String ARG_POSITION = "position";
    public static final String ARG_ID = "ID";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int selectedWallpaperPosition;
    private String selectedWallpaperID;
    private SimpleDraweeView mSimpleDraweeView;
    private RetrofitServer retrofitServer;
    private Data selectedWallpaperData;

    public SelectedWallpaperFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectedWallpaperFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectedWallpaperFragment newInstance(String param1, String param2) {
        SelectedWallpaperFragment fragment = new SelectedWallpaperFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedWallpaperPosition = getArguments().getInt(ARG_POSITION);
            selectedWallpaperID = getArguments().getString(ARG_ID);
        }
        Log.d(TAG, "position = " + selectedWallpaperPosition);
        Log.d(TAG, "ID = " + selectedWallpaperID);

        retrofitServer = new RetrofitServer();
        getWallpaperData(retrofitServer.getSelectedWallpaper(selectedWallpaperID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View selectedWallpaperView = inflater.inflate(R.layout.fragment_selected_wallpaper, container, false);
        mSimpleDraweeView = selectedWallpaperView.findViewById(R.id.selected_wallpaper_view);

        // Inflate the layout for this fragment
        return selectedWallpaperView;
    }

    private void getWallpaperData(Call<Wallpaper> call) {
        call.enqueue(new Callback<Wallpaper>() {
            @Override
            public void onResponse(Call<Wallpaper> call, retrofit2.Response<Wallpaper> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.toString());
                    assert response.body() != null;

                    selectedWallpaperData = response.body().getData();
                    setImageView(selectedWallpaperData);
                }
                retrofitServer.setIsWallpaperLoading(false);
            }

            @Override
            public void onFailure(Call<Wallpaper> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void setImageView(Data wallpaper) {
        String pathURL = wallpaper.getPath();

        final ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(pathURL))
                        .build();
        mSimpleDraweeView.setImageRequest(imageRequest);

    }
}
