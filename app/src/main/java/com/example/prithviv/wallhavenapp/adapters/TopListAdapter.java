package com.example.prithviv.wallhavenapp.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prithviv.wallhavenapp.R;
import com.example.prithviv.wallhavenapp.models.Wallpaper;

import java.io.InputStream;
import java.net.URL;
import java.util.List;


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
        // TODO: Load Thumbnail using Fresco
        Drawable wallpaperDrawable = loadImageFromWebOperations(urlThumbOriginal);

        viewHolder.myImageView.setImageDrawable(wallpaperDrawable);
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
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public static Drawable loadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
