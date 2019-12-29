package com.github.tenx.tecnoesis20admin.ui.main.events;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tenx.tecnoesis20admin.R;

import butterknife.ButterKnife;

public class EventsFragment extends Fragment {

    private EventsViewModel mViewModel;
    private EventsAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View parent =  inflater.inflate(R.layout.fragment_events, container, false);
        mRecyclerView = parent.findViewById(R.id.rview_events);
        ButterKnife.bind(this, parent);

        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        mViewModel.init();
        mViewModel.getEvents().observe(this, nicePlaces -> mAdapter.notifyDataSetChanged());

        initRecyclerView();
    }

    private void initRecyclerView(){
        mAdapter = new EventsAdapter(getActivity(),mViewModel.getEvents().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
}
