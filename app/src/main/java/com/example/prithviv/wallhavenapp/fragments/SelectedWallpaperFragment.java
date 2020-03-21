package com.example.prithviv.wallhavenapp.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.prithviv.wallhavenapp.MySingleton;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.models.Wallpaper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedWallpaperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedWallpaperFragment extends Fragment {
    public static final String ARG_POSITION = "position";
    public static final String ARG_ID = "ID";
    private static final String WALLHAVEN_GET_REQUEST = "https://wallhaven.cc/api/v1/w/";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int selectedWallpaperPosition;
    private String selectedWallpaperID;
    private Wallpaper selectedWallpaper;
    private SimpleDraweeView mSimpleDraweeView;
    private Handler handler;
    private Boolean wallpaperLoading;
    private RequestQueue queue;

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
        queue = MySingleton.getInstance(getActivity()).getRequestQueue();
        getWallpaperData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View selectedWallpaperView = inflater.inflate(R.layout.fragment_selected_wallpaper, container, false);
        mSimpleDraweeView = selectedWallpaperView.findViewById(R.id.selected_wallpaper_view);

        // Inflate the layout for this fragment
        return selectedWallpaperView;
    }

    private void getWallpaperData() {
        setWallpaperLoading(true);
        queue.add(getWallpaperRequest(createWallpaperGetRequest(selectedWallpaperID)));
    }

    private String createWallpaperGetRequest(String id) {
        return WALLHAVEN_GET_REQUEST + id;
    }

    private JsonObjectRequest getWallpaperRequest(String URL) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Gson gson = new Gson();
                    //Log.d("JSON", response.getJSONObject("data").toString());

                    selectedWallpaper = gson.fromJson(response.getJSONObject("data").toString(), Wallpaper.class);
                    //Log.d(TAG, selectedWallpaper.getPath());

                    setImageView(selectedWallpaper);

                    setWallpaperLoading(false);
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

    private void setWallpaperLoading(boolean input) {
        wallpaperLoading = input;
    }

    private void setImageView(Wallpaper wallpaper) {
        String pathURL = selectedWallpaper.getPath();
        final ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(pathURL))
                        .build();
        mSimpleDraweeView.setImageRequest(imageRequest);
    }
}
