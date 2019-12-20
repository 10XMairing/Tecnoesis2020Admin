package com.github.tenx.tecnoesis20.ui.main.home;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.tenx.tecnoesis20.R;


import java.util.ArrayList;

public class HomeEventAdapter extends RecyclerView.Adapter<HomeEventAdapter.HomeEventViewHolder>{

    private ArrayList<String> mImageUrl = new ArrayList<>();
    private ArrayList<String> mTitleName = new ArrayList<>();
    private ArrayList<String> mDate = new ArrayList<>();
    private Context mContext;

    public HomeEventAdapter(Context mContext,ArrayList<String> mImageUrl, ArrayList<String> mTitleName, ArrayList<String> mDate) {
        this.mImageUrl = mImageUrl;
        this.mTitleName = mTitleName;
        this.mDate = mDate;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HomeEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home,parent,false);
        HomeEventViewHolder holder = new HomeEventViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeEventViewHolder holder, int position) {
        Glide.with(mContext)
        .asBitmap()
        .load(mImageUrl.get(position))
        .into(holder.posterImageView);
        holder.titleTextView.setText(mTitleName.get(position));
        holder.descriptionTextView.setText(mDate.get(position));
        holder.expandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.expandableView.getVisibility() == View.GONE ){
                    TransitionManager.beginDelayedTransition(holder.eventCard,new AutoTransition());
                    holder.expandableView.setVisibility(View.VISIBLE);
                    holder.expandView.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
                else{
                    TransitionManager.beginDelayedTransition(holder.eventCard,new AutoTransition());
                    holder.expandableView.setVisibility(View.GONE);
                    holder.expandView.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitleName.size();
    }

    public class HomeEventViewHolder extends RecyclerView.ViewHolder{

        ImageView posterImageView;
        TextView titleTextView;
        TextView descriptionTextView;
        Button expandView;
        ConstraintLayout expandableView;
        CardView eventCard;
        public HomeEventViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.card_home_img_poster);
            titleTextView = itemView.findViewById(R.id.card_home_tv_title);
            descriptionTextView = itemView.findViewById(R.id.card_home_tv_description);
            expandView = itemView.findViewById(R.id.card_home_btn_arrDown);
            expandableView = itemView.findViewById(R.id.card_home_constraint_expandableView);
            eventCard = itemView.findViewById(R.id.card_home_cv_eventCard);
        }
    }
}


