package com.github.tenx.tecnoesis20admin.ui.main.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.EventResponse;
import com.github.tenx.tecnoesis20admin.data.models.UpcomingEvents;
import com.github.tenx.tecnoesis20admin.ui.user.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
//    google fragment lifecycle or https://developer.android.com/guide/components/fragments if you are unsure about how to use fragment lifecycle

    private HomeViewModel mViewModel;
    private HomeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Button bLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent =  inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = parent.findViewById(R.id.rview_upcoming_events);
        bLogin = parent.findViewById(R.id.btn_login);
        ButterKnife.bind(this, parent);

        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mViewModel.init();
        mViewModel.getUpcomingEvents().observe(this, new Observer<List<UpcomingEvents>>() {
            @Override
            public void onChanged(@Nullable List<UpcomingEvents> nicePlaces) {
                mAdapter.notifyDataSetChanged();
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView(){
        mAdapter = new HomeAdapter(getActivity(),mViewModel.getUpcomingEvents().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();


//        @TODO how to call view model demo
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
}
