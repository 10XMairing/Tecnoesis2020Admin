package com.github.tenx.tecnoesis20admin.ui.main.feeds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.FeedResponseBody;
import com.github.tenx.tecnoesis20admin.ui.main.MainActivity;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.CustomViewHolder> {


    private Context context;
    private List<DataSnapshot> list;
    private FragmentManager fm;

    public FeedAdapter(Context context, FragmentManager fm) {
        this.context = context;
        list = new ArrayList<>();
        this.fm = fm;

    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {



        DataSnapshot snap = list.get(position);

        FeedResponseBody model = list.get(position).getValue(FeedResponseBody.class);



        holder.tvFeedText.setText(model.getText());
        if(model.getDate() != null){
            holder.tvFeedDate.setText(model.getDate());
        }



        Glide.with(context).load(model.getImage()).placeholder(R.drawable.placeholder_image).into(holder.ivFeedImage);



        boolean isOwner = ((MainActivity) context).getVm().isOwner(model.getEmail());


        if(isOwner){
            holder.btnEdit.setVisibility(View.VISIBLE);

        }else {
            holder.btnEdit.setVisibility(View.GONE);
        }

        holder.btnEdit.setOnClickListener(v -> {
            showDialog(fm, snap.getKey(), model);
        });

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

        @BindView(R.id.btn_edit)
        MaterialButton btnEdit;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }

    public void setList(List<DataSnapshot> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    private void showDialog(FragmentManager fm, String feedKey, FeedResponseBody data){
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
            ft.commit();
            fm.executePendingTransactions();
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        EditFeedDialogFragment newFragment = EditFeedDialogFragment.newInstance(feedKey, data);
        newFragment.show(ft, "dialog");
    }
}