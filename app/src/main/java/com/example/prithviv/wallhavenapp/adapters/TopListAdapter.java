package com.example.prithviv.wallhavenapp.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.prithviv.wallhavenapp.R;

import java.util.List;


public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener mClickListener;

    public TopListAdapter(Context context, List<String> data) {
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
        String url = mData.get(position);
        viewHolder.myTextView.setText(url);
    }

    // Total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}
