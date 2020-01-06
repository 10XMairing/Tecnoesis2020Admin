package com.github.tenx.tecnoesis20admin.ui.main.feeds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.FeedResponseBody;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.CustomViewHolder> {


    private Context context;
    private List<FeedResponseBody> list;


    public FeedAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        FeedResponseBody model = list.get(position);

        holder.tvFeedText.setText(model.getText());
        if(model.getDate() != null){
            holder.tvFeedDate.setText(model.getDate());
        }

        Glide.with(context).load(model.getImage()).placeholder(R.drawable.placeholder_image).into(holder.ivFeedImage);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_feed, parent, false);
        return new CustomViewHolder(v);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_feed_image)
        ImageView ivFeedImage;
        @BindView(R.id.tv_feed_text)
        TextView tvFeedText;

        @BindView(R.id.tv_feed_date)
        TextView tvFeedDate;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }

    public void setList(List<FeedResponseBody> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}