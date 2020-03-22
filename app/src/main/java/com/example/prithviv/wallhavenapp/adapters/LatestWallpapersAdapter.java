package com.example.prithviv.wallhavenapp.adapters;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prithviv.wallhavenapp.ContextProvider;
import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.fragments.SelectedWallpaperFragment;
import com.example.prithviv.wallhavenapp.models.Wallpaper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;
import java.util.Random;


public class LatestWallpapersAdapter extends RecyclerView.Adapter<LatestWallpapersAdapter.ViewHolder> {

    private List<Wallpaper> mData;
    private LayoutInflater mInflater;
    private final ContextProvider mContextProvider;

    public LatestWallpapersAdapter(ContextProvider contextProvider, List<Wallpaper> data) {
        this.mData = data;
        this.mContextProvider = contextProvider;
        this.mInflater = LayoutInflater.from(mContextProvider.getContext());
    }

    // Inflates row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_toplist, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(viewHolder);

        return viewHolder;
    }

    // Binds the data to the SimpleDraweeView in each row
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String urlThumbOriginal = mData.get(position).getThumbsOriginal();
        final ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlThumbOriginal))
                        .build();
        viewHolder.mSimpleDraweeView.setImageRequest(imageRequest);

    }

    // Total number of rows
    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        } else {
            return mData.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Random sRandom = new Random();
        private SimpleDraweeView mSimpleDraweeView;

        ViewHolder(View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.my_image_view);
            mSimpleDraweeView.getHierarchy().setPlaceholderImage(new ColorDrawable(sRandom.nextInt()));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            //Log.d(TAG, "position = " + this.getAdapterPosition());
            Wallpaper selectedWallpaper = mData.get(getAdapterPosition());
            //Log.d("Selected Wallpaper", selectedWallpaper.getURL());
            Context context = mContextProvider.getContext();

            launchSelectedWallpaperFragment(selectedWallpaper, context);
        }

        private void launchSelectedWallpaperFragment(Wallpaper selectedWallpaper, Context context) {
            Fragment selectedWallpaperFragment = new SelectedWallpaperFragment();
            Bundle args = new Bundle();
            args.putInt(SelectedWallpaperFragment.ARG_POSITION, getAdapterPosition());
            args.putString(SelectedWallpaperFragment.ARG_ID, selectedWallpaper.getID());
            selectedWallpaperFragment.setArguments(args);

            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, selectedWallpaperFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
