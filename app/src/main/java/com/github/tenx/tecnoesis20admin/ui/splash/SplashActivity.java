package com.github.tenx.tecnoesis20admin.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.ui.auth.LoginActivity;
import com.github.tenx.tecnoesis20admin.ui.main.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.act_splash_mbtn_next)
    MaterialButton mbtnNext;

    @BindView(R.id.act_splash_progress)
    ProgressBar progress;

    private SplashViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        mbtnNext.setOnClickListener(v -> {
//            go to next screen
            showProgress();
            FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(task -> {
                if(viewModel.hasToken()){
                    viewModel.verifyToken();
                }else {
                    initActivity(LoginActivity.class);
                }
            });
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getLdTokenStatus().observe(this , data -> {
            Timber.d("data changed");

            if(data){
//                has token and logged in
                initActivity(MainActivity.class);
            }else {
                initActivity(LoginActivity.class);
            }
        });

    }

    public void showProgress(){
        progress.setVisibility(View.VISIBLE);
        mbtnNext.setEnabled(false);
    }

    public void hideProgress(){
        progress.setVisibility(View.INVISIBLE);
        mbtnNext.setEnabled(true);
    }

    private void  initActivity(Class activity){
        Intent i = new Intent(SplashActivity.this, activity);
        hideProgress();
        startActivity(i);
    }
}
