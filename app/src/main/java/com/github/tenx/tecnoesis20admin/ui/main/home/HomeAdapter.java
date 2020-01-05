package com.github.tenx.tecnoesis20admin.ui.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.NotificationBody;
import com.github.tenx.tecnoesis20admin.data.models.UpcomingEvents;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class HomeAdapter extends FirebaseRecyclerAdapter<NotificationBody, HomeAdapter.CustomViewHolder> {

    private Context context;

    public HomeAdapter(@NonNull FirebaseRecyclerOptions<NotificationBody> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CustomViewHolder holder, int position, @NonNull NotificationBody model) {

        Glide.with(context).load(model.getImage()).into(holder.civNotificationitemImage);
        holder.tvNotificationitemSenderName.setText(model.getSender());
        String sub = "Sub : " + model.getTitle();
        holder.tvNotificationitemSubject.setText(sub);
        holder.tvNotificationitemMessage.setText(model.getMessage());
        holder.tvNotificationitemDate.setText(model.getDate());
        Timber.d(model.toString());

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_notifications_body, parent, false);
        this.context = parent.getContext();

        return new CustomViewHolder(v);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_notificationitem_image)
        CircleImageView civNotificationitemImage;
        @BindView(R.id.tv_notificationitem_sender_name)
        TextView tvNotificationitemSenderName;
        @BindView(R.id.tv_notificationitem_date)
        TextView tvNotificationitemDate;
        @BindView(R.id.tv_notificationitem_subject)
        TextView tvNotificationitemSubject;
        @BindView(R.id.tv_notificationitem_message)
        TextView tvNotificationitemMessage;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this , itemView);
        }
    }

}