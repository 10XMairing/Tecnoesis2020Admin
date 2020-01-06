package com.github.tenx.tecnoesis20admin.ui.auth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.AdminResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminsAdapter extends RecyclerView.Adapter<AdminsAdapter.CustomVH> {


    private Context context;
    private List<AdminResponse> list;


    public AdminsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }


    @NonNull
    @Override
    public CustomVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_admin, parent, false);

        return new CustomVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomVH holder, int position) {
            holder.tvEmail.setText(list.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class CustomVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_email)
        TextView tvEmail;
        public CustomVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }

    public void setList(List<AdminResponse> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
