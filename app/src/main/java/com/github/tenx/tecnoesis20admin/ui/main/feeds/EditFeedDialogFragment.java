package com.github.tenx.tecnoesis20admin.ui.main.feeds;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.models.FeedResponseBody;
import com.github.tenx.tecnoesis20admin.ui.main.MainActivity;
import com.github.tenx.tecnoesis20admin.ui.main.MainViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class EditFeedDialogFragment extends DialogFragment {


    @BindView(R.id.iv_update_image)
    ImageView ivUpdateImage;

    @BindView(R.id.et_text_feed)
    EditText etFeed;

    @BindView(R.id.tv_update_filename)
    TextView tvUpdateFilename;

    @BindView(R.id.btn_select_image)
    MaterialButton btnSelectImage;

    @BindView(R.id.rl_1)
    RelativeLayout rl1;

    @BindView(R.id.btn_update)
    MaterialButton btnUpdate;

    @BindView(R.id.btn_delete)
    MaterialButton btnDelete;

    @BindView(R.id.prog_update_progress)
    ProgressBar progUpdateProgress;
    @BindView(R.id.ll_parent_dialog)
    LinearLayout llParentDialog;


    private FeedResponseBody feedData;
    private String feedKey;
    private MainViewModel vm;


    static EditFeedDialogFragment newInstance(String feedkey, FeedResponseBody data) {
        EditFeedDialogFragment f = new EditFeedDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("key", feedkey);
        args.putParcelable("data", data);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_update_feed, container, false);
        ButterKnife.bind(this, v);


        btnSelectImage.setVisibility(View.GONE);
        // retrieve display dimensions
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        v.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
        v.setMinimumHeight((int)(displayRectangle.height() * 0.9f));



        return v;
    }



    @Override
    public void onStart() {
        super.onStart();
        try {
            feedData = getArguments().getParcelable("data");
            feedKey = getArguments().getString("key");
            initData(feedData, getActivity());
        } catch (NullPointerException e) {
            Timber.e("An error occured while parsing data");
            this.dismiss();

        }

        vm = ((MainActivity) getActivity()).getVm();


        vm.getOnUpdatedFeed().observe(getActivity(), data -> {
            if (data) {
//                uploaded successfully
                hideProgress();
                this.dismiss();
            } else {
                hideProgress();
            }
        });

        vm.getOnDeleteFeed().observe(getActivity(), data -> {
            if (data) {
//                deleted successfully
                hideProgress();
                this.dismiss();
            } else {
                hideProgress();
            }
        });

    }


    private void initData(FeedResponseBody data, Context context) {
        etFeed.setText(data.getText());
        Glide.with(context).load(data.getImage()).into(ivUpdateImage);

    }

    @OnClick(R.id.btn_update)
    void onClickUpdate(View v) {
        String text = etFeed.getText().toString();

        Map<String, Object> data = new HashMap<>();

        if (TextUtils.isEmpty(text)) {
            Toast.makeText(v.getContext(), "feed text cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        } else {
            data.put("text", text);
        }


        vm.updateFeed(data, feedKey);
        showProgress();


    }


    @OnClick(R.id.btn_delete)
    void onClickDelete(View v) {
        Snackbar.make(llParentDialog, "Confirm Delete?", Snackbar.LENGTH_LONG).setAction("Confirm", v1-> {
            showProgress();
            vm.deleteFeed(feedKey);
        }).show();
    }


    private void showProgress() {
        this.setCancelable(false);
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnSelectImage.setEnabled(true);
        etFeed.setEnabled(true);
        progUpdateProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        this.setCancelable(true);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnSelectImage.setEnabled(false);
        etFeed.setEnabled(false);
        progUpdateProgress.setVisibility(View.GONE);
    }
}
