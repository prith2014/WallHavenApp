package com.example.prithviv.wallhavenapp.fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prithviv.wallhavenapp.ContextProvider;
import com.example.prithviv.wallhavenapp.HttpRequest.RetrofitServer;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.models.Data;
import com.example.prithviv.wallhavenapp.models.Tag;
import com.example.prithviv.wallhavenapp.models.Wallpaper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

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

    private int selectedWallpaperPosition;
    private String selectedWallpaperID;
    private SimpleDraweeView mSimpleDraweeView;
    private RetrofitServer retrofitServer;
    private Data selectedWallpaperData;
    private DownloadManager downloadManager;

    private TextView textViewCategory;
    private TextView textViewResolution;
    private TextView textViewSize;
    private TextView textViewViews;
    private TextView textViewRating;
    private TextView textViewTags;

    public SelectedWallpaperFragment(ContextProvider contextProvider) {
        this.downloadManager = (DownloadManager) contextProvider.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectedWallpaperFragment.
     */
    public static SelectedWallpaperFragment newInstance(ContextProvider mContextProvider) {
        SelectedWallpaperFragment fragment = new SelectedWallpaperFragment(mContextProvider);
        Bundle args = new Bundle();
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

        retrofitServer = new RetrofitServer(this::getActivity);
        getWallpaperData(retrofitServer.getSelectedWallpaper(selectedWallpaperID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View selectedWallpaperView = inflater.inflate(R.layout.fragment_selected_wallpaper, container, false);
        mSimpleDraweeView = selectedWallpaperView.findViewById(R.id.selected_wallpaper_view);

        FloatingActionButton downloadFloatingActionButton = selectedWallpaperView.findViewById(R.id.download_floatingActionButton);
        downloadFloatingActionButton.setOnClickListener(v -> {
            Log.v("DOWNLOAD", "Download button clicked");
            downloadWallpaper(selectedWallpaperData.getPath());
        });

        textViewCategory = selectedWallpaperView.findViewById(R.id.text_view_category);
        textViewResolution = selectedWallpaperView.findViewById(R.id.text_view_resolution);
        textViewSize = selectedWallpaperView.findViewById(R.id.text_view_size);
        textViewViews = selectedWallpaperView.findViewById(R.id.text_view_views);
        textViewRating = selectedWallpaperView.findViewById(R.id.text_view_purity);
        textViewTags = selectedWallpaperView.findViewById(R.id.text_view_tags);

        // Inflate the layout for this fragment
        return selectedWallpaperView;
    }

    private void getWallpaperData(Call<Wallpaper> call) {
        call.enqueue(new Callback<Wallpaper>() {
            @Override
            public void onResponse(@NonNull Call<Wallpaper> call, @NonNull retrofit2.Response<Wallpaper> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.toString());
                    assert response.body() != null;

                    selectedWallpaperData = response.body().getData();
                    setImageView(selectedWallpaperData);
                    setImageDetails(selectedWallpaperData);
                }
                retrofitServer.setIsWallpaperLoading(false);
            }

            @Override
            public void onFailure(@NonNull Call<Wallpaper> call, @NonNull Throwable t) {
                if (t.getMessage() != null) {
                    Log.d("Error", t.getMessage());
                }
            }
        });
    }

    private void setImageView(Data wallpaper) {
        String pathURL = wallpaper.getPath();

        final ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(pathURL))
                        .build();
        mSimpleDraweeView.setImageRequest(imageRequest);
        mSimpleDraweeView.setAspectRatio(Float.parseFloat(selectedWallpaperData.getRatio()));
    }

    private void setImageDetails(Data wallpaper) {
        textViewCategory.setText(wallpaper.getCategory());
        textViewResolution.setText(wallpaper.getResolution());
        textViewSize.setText(String.format(Locale.getDefault(), "%d", wallpaper.getFileSize()));
        textViewViews.setText(String.format(Locale.getDefault(), "%d", wallpaper.getViews()));
        textViewRating.setText(wallpaper.getPurity());

        List<Tag> tags = wallpaper.getTags();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            sb.append(tag.getName());

            if (i < tags.size() - 1) {
                sb.append(", ");
            }
        }

        textViewTags.setText(sb.toString());
    }

    private void downloadWallpaper(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        request.setTitle("Wallpaper " + selectedWallpaperID);
        request.setDescription("Downloading");
        request.allowScanningByMediaScanner();
        request.setMimeType(selectedWallpaperData.getFileType());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, selectedWallpaperID);

        downloadManager.enqueue(request);
        Log.v("DOWNLOAD", "Download request enqueued");
    }
}
