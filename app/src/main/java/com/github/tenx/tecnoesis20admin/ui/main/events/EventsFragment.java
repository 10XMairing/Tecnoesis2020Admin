package com.github.tenx.tecnoesis20admin.ui.main.events;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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

public class EventsFragment extends Fragment {

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


    private EventsViewModel mViewModel;
    private MainViewModel parentViewModel;

    @BindView(R.id.btn_save_feed)
    MaterialButton btnSave;

    private Uri imageuri;

    public static final int LOAD_IMAGE_CODE = 123;


    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View parent = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this, parent);

        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        mViewModel.init();
        parentViewModel = ((MainActivity) getActivity()).getVm();

      parentViewModel.getLdDownloadUrl().observe(getActivity() , data -> {
          Timber.d("Upload task completed");
            if(!data){
                Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                hideProgress();

            }else {
//                image uploaded
                Timber.e("image uploaded : "+data);

                Toast.makeText(getActivity(), "Feed posted successfully", Toast.LENGTH_SHORT).show();
                hideProgress();
            }
      });
    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @OnClick(R.id.btn_save_feed)
    void uploadFeed(View v) {


        String feed = tvFeedText.getText().toString();
            if(imageuri != null && !TextUtils.isEmpty(feed)){
                showProgress();
                parentViewModel.uploadImage(imageuri , feed);
            }else {
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


    private void hideProgress(){
        btnSave.setEnabled(true);
        btnSelectImage.setEnabled(true);
        progressCircular.setVisibility(View.GONE);
        tvFeedText.setEnabled(true);
    }

    private void showProgress(){
        btnSave.setEnabled(false);
        btnSelectImage.setEnabled(false);
        progressCircular.setVisibility(View.VISIBLE);
        tvFeedText.setEnabled(false);
    }


}
