package com.github.tenx.tecnoesis20admin.ui.main.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.Events;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private List<Events> events;
    private LayoutInflater inflater;
    private Context mContext;

    public EventsAdapter(Context context, List<Events> events) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.events = events;
    }

    @NonNull
    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_event_select, parent, false);
        EventsAdapter.MyViewHolder holder = new EventsAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.MyViewHolder holder, int i) {
        ((EventsAdapter.MyViewHolder)holder).moduleName.setText(events.get(i).getModuleName());
        ((EventsAdapter.MyViewHolder)holder).eventName.setText(events.get(i).getEventName());
        ((EventsAdapter.MyViewHolder)holder).eventDate.setText(events.get(i).getEventDate());
        ((EventsAdapter.MyViewHolder)holder).eventTime.setText(events.get(i).getEventTime());

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView moduleName;
        private TextView eventName;
        private TextView eventDate;
        private TextView eventTime;
        private CheckBox eventChoose;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleName= itemView.findViewById(R.id.tv_module_name_select);
            eventName= itemView.findViewById(R.id.tv_event_name_select);
            eventDate= itemView.findViewById(R.id.tv_dayndate_select);
            eventTime= itemView.findViewById(R.id.tv_time_select);
            eventChoose= itemView.findViewById(R.id.cb_choose);
        }
    }
}
