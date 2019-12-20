package com.github.tenx.tecnoesis20.ui.main.home;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.tenx.tecnoesis20.R;
import com.github.tenx.tecnoesis20.data.models.EventResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
//    google fragment lifecycle or https://developer.android.com/guide/components/fragments if you are unsure about how to use fragment lifecycle

    private HomeViewModel mViewModel;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();


    @BindView(R.id.frag_home_rv_card)
    RecyclerView cardRecyclerView;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, parent);
        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel this is for demo delete this later
        mViewModel.loadEvents();


    }

    @Override
    public void onStart() {
        super.onStart();

//        @TODO how to call view model demo

        mViewModel.getEvents().observe(this, eventsResponse -> {
//            display result
            for (EventResponse res : eventsResponse) {
                mImageUrls.add(res.getImageUrl());
                mDates.add(res.getDate());
                mTitles.add(res.getName());
            }
            initRecyclerView();
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    private void initRecyclerView() {
        HomeEventAdapter adapter = new HomeEventAdapter(getActivity(), mImageUrls, mTitles, mDates);
        cardRecyclerView.setAdapter(adapter);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
