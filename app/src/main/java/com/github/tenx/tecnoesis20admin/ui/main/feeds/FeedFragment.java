package com.github.tenx.tecnoesis20admin.ui.main.feeds;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.ui.main.MainActivity;
import com.github.tenx.tecnoesis20admin.ui.main.MainViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class FeedFragment extends Fragment {

    @BindView(R.id.iv_image_load)
    ImageView ivImageLoad;
    @BindView(R.id.tv_filename)
    TextView tvFilename;
    @BindView(R.id.btn_select_image)
    MaterialButton btnSelectImage;
    @BindView(R.id.tv_feed_text)
    TextInputEditText tvFeedText;
    @BindView(R.id.progress_circular)
    ProgressBar progressCircular;


    @BindView(R.id.recycler_feed)
    RecyclerView recyclerFeed;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;

    private UploadStatusWatcher uploadStatusWatcher;


    private EventsViewModel mViewModel;
    private MainViewModel parentViewModel;

    @BindView(R.id.btn_save_feed)
    MaterialButton btnSave;

    private Uri imageuri;
    private FeedAdapter adapter;


    public static final int LOAD_IMAGE_CODE = 123;


    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View parent = inflater.inflate(R.layout.fragment_feeds, container, false);
        ButterKnife.bind(this, parent);

        initFeedRecycler(getActivity());

        return parent;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        mViewModel.init();
        parentViewModel = ((MainActivity) getActivity()).getVm();
        parentViewModel.getListFeeds().observe(getActivity(), data -> {
            adapter.setList(data);
        });
        parentViewModel.getLdFeedSuccess().observe(getActivity(), data -> {
            Timber.d("Upload task completed");

            if (!data.isEmpty()) {
                uploadStatusWatcher.onGoingUpload(false);
                hideProgress();
                Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                parentViewModel.setLdFeedSuccess("");
                tvFeedText.setText("");
                ivImageLoad.setImageResource(R.drawable.placeholder_image);
                imageuri = null;
            }
        });

        parentViewModel.getLdFeedError().observe(getActivity(), data -> {
            if (!data.isEmpty()) {
                uploadStatusWatcher.onGoingUpload(false);
                hideProgress();
                Toast.makeText(getActivity(), "Failed to post feed. Try Again!", Toast.LENGTH_SHORT).show();
                parentViewModel.setLdFeedError("");
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof UploadStatusWatcher){
            uploadStatusWatcher = ( UploadStatusWatcher)  context;
        }else {
            Timber.e("Parent Activity must implement UploadStatusWatcher");
            throw new Error("Parent Activity must implement UploadStatusWatcher");
        }

    }


    @OnClick(R.id.btn_save_feed)
    void uploadFeed(View v) {


        String feed = tvFeedText.getText().toString();
        if (imageuri != null && !TextUtils.isEmpty(feed)) {
            showProgress();

            parentViewModel.uploadImage(imageuri, feed);
           uploadStatusWatcher.onGoingUpload(true);
        } else {
            Toast.makeText(getActivity(), "Select an image first!  and Some texts", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.btn_select_image)
    void selectImage(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == LOAD_IMAGE_CODE) {

                //Get ImageURi and load with help of picasso
                //Uri selectedImageURI = data.getData();
                Glide.with(getActivity()).load(data.getData()).into(ivImageLoad);
                imageuri = data.getData();


                tvFilename.setText(data.getData().getLastPathSegment());

            }

        }
    }


    private void hideProgress() {
        btnSave.setEnabled(true);
        btnSelectImage.setEnabled(true);
        progressCircular.setVisibility(View.GONE);
        tvFeedText.setEnabled(true);
    }

    private void showProgress() {
        btnSave.setEnabled(false);
        btnSelectImage.setEnabled(false);
        progressCircular.setVisibility(View.VISIBLE);
        tvFeedText.setEnabled(false);
    }


    private void initFeedRecycler(Context context) {
        adapter = new FeedAdapter(context);
        recyclerFeed.setLayoutManager(new LinearLayoutManager(context));
        recyclerFeed.setAdapter(adapter);
    }


}
