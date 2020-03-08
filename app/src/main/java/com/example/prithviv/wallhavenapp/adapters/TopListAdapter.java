package com.example.prithviv.wallhavenapp.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.models.Wallpaper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;
import java.util.Random;


public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ViewHolder> {

    private List<Wallpaper> mData;
    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener mClickListener;

    public TopListAdapter(Context context, List<Wallpaper> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Inflates row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_toplist, viewGroup, false);
        return new ViewHolder(view);
    }

    // Binds the data to the TextView in each row
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
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
