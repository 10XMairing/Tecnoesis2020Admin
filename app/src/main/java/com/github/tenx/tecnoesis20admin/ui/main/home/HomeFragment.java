package com.github.tenx.tecnoesis20admin.ui.main.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.NotificationBody;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class HomeFragment extends Fragment {
    @BindView(R.id.tv_designation)
    TextInputEditText tvDesignation;
    @BindView(R.id.tv_notification_title)
    TextInputEditText tvNotificationTitle;
    @BindView(R.id.tv_notification_message)
    TextInputEditText tvNotificationMessage;
    @BindView(R.id.tl_desig)
    TextInputLayout tlDesig;
    @BindView(R.id.tl_title)
    TextInputLayout tlTitle;
    @BindView(R.id.tl_message)
    TextInputLayout tlMessage;
    @BindView(R.id.rl_parent_home)
    RelativeLayout rlParentHome;
    @BindView(R.id.btn_home_send)
    MaterialButton btnHomeSend;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.recycler_notifications)
    RecyclerView recyclerNotifications;

    private HomeAdapter adapter;

//    google fragment lifecycle or https://developer.android.com/guide/components/fragments if you are unsure about how to use fragment lifecycle

    private HomeViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, parent);
        mViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter(getActivity());

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();

        mViewModel.getOnSuccessNotification().observe(this, data -> {

            enableInputs();
            if (data) {
//                successfullt sent
                Snackbar.make(rlParentHome, "Notification Sent Successfully", Snackbar.LENGTH_SHORT).show();

                clearEditTexts();


            } else {
//                failed
                Snackbar.make(rlParentHome, "Error sending notification", Snackbar.LENGTH_SHORT).show();
            }
        });

//        @TODO how to call view model demo
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }


    @OnClick(R.id.btn_home_send)
    void attemptSend(View v) {

        disableInputs();
        String desig = tvDesignation.getText().toString();
        String title = tvNotificationTitle.getText().toString();
        String message = tvNotificationMessage.getText().toString();


        if (desig.isEmpty()) {
            tlDesig.setError("Designation cannot be empty");
            enableInputs();
            return;
        }
        if (title.isEmpty()) {
            tlDesig.setError("Title cannot be empty");
            enableInputs();
            return;
        }
        if (message.isEmpty()) {
            tlDesig.setError("Message cannot be empty");
            enableInputs();
            return;
        }


        mViewModel.sendNotification(desig, title, message);

    }

    private void clearEditTexts() {
        tvNotificationMessage.setText("");
        tvDesignation.setText("");
        tvNotificationTitle.setText("");
    }


    private void disableInputs() {

        tvNotificationTitle.setEnabled(false);
        tvDesignation.setEnabled(false);
        tvNotificationMessage.setEnabled(false);
        btnHomeSend.setEnabled(false);
    }

    private void enableInputs() {
        tvNotificationTitle.setEnabled(true);
        tvDesignation.setEnabled(true);
        tvNotificationMessage.setEnabled(true);
        btnHomeSend.setEnabled(true);
    }

    private void initAdapter(Context ctx) {
//        as latest notification is at last
        LinearLayoutManager llman = new LinearLayoutManager(ctx);
        llman.setReverseLayout(true);
        recyclerNotifications.setLayoutManager(llman);


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("notifications")
                .limitToLast(50);
        FirebaseRecyclerOptions<NotificationBody> options =
                new FirebaseRecyclerOptions.Builder<NotificationBody>()
                        .setQuery(query, NotificationBody.class)
                        .build();
        adapter = new HomeAdapter(options);

        recyclerNotifications.setAdapter(adapter);
    }


}
