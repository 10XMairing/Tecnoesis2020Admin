package com.github.tenx.tecnoesis20admin.ui.main.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.NotificationBody;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CustomViewHolder> {

    private Context context;
    private List<NotificationBody> list;

    public NotificationAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        NotificationBody model = list.get(position);
        Glide.with(context).load(model.getImage()).into(holder.civNotificationitemImage);
        holder.tvNotificationitemSenderName.setText(model.getSender());
        String sub = "Sub : " + model.getTitle();
        holder.tvNotificationitemSubject.setText(sub);
        holder.tvNotificationitemMessage.setText(model.getMessage());
        holder.tvNotificationitemDate.setText(model.getDate());
        Timber.d(model.toString());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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

    public void setList(List<NotificationBody> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}