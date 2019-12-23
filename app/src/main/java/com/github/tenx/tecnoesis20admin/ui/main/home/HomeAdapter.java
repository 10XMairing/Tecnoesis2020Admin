package com.github.tenx.tecnoesis20admin.ui.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.UpcomingEvents;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<UpcomingEvents> upcomingEvents;
    private LayoutInflater inflater;
    private Context mContext;

    public HomeAdapter(Context context, List<UpcomingEvents> upcomingEvents) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.upcomingEvents = upcomingEvents;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_event, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        ((MyViewHolder)holder).moduleName.setText(upcomingEvents.get(i).getModuleName());
        ((MyViewHolder)holder).eventName.setText(upcomingEvents.get(i).getEventName());
        ((MyViewHolder)holder).eventDate.setText(upcomingEvents.get(i).getEventDate());
        ((MyViewHolder)holder).eventTime.setText(upcomingEvents.get(i).getEventTime());
    }

    @Override
    public int getItemCount() {
        return upcomingEvents.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView moduleName;
        private TextView eventName;
        private TextView eventDate;
        private TextView eventTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleName= itemView.findViewById(R.id.tv_module_name);
            eventName= itemView.findViewById(R.id.tv_event_name);
            eventDate= itemView.findViewById(R.id.tv_dayndate);
            eventTime= itemView.findViewById(R.id.tv_time);

        }
    }
}