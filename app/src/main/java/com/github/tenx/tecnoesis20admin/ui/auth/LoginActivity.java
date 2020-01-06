package com.github.tenx.tecnoesis20admin.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.ui.main.MainActivity;
import com.github.tenx.tecnoesis20admin.ui.views.OtpEditText;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.et_login_email)
    TextInputEditText etLoginEmail;
    @BindView(R.id.et_otp)
    OtpEditText etLoginOtp;
    @BindView(R.id.btn_login)
    MaterialButton btnLogin;
    @BindView(R.id.btn_login_clear)
    MaterialButton btnLoginClear;
    @BindView(R.id.btn_login_otp)
    MaterialButton btnLoginOtp;
    @BindView(R.id.tl_1)
    TextInputLayout textInputLEmail;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.cl_1)
    MaterialCardView cl1;



    @BindView(R.id.toggle_admins)
    MaterialButton toggleAdmins;
    @BindView(R.id.recycler_admins)
    RecyclerView recyclerAdmins;

    @BindView(R.id.tv_info_1)
    TextView tvInfo1;


    private AdminsAdapter adminsAdapter;

    private boolean isAdminsVisible = true;
    private LoginActivityViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginActivityViewModel.class);
        ButterKnife.bind(this);


        initAdminsList(this);
        hideLoginButtons();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getSendOtpSuccess().observe(this, data -> {
            if (data) {
//                success
                showLoginButtons();
                textInputLEmail.setError("");
            } else {
//                failed
                enableOtpButtons();
                textInputLEmail.setError("Error occurred. try again");
            }
        });

        viewModel.loadAdmins();


        viewModel.getAdminsList().observe(this , data -> {
            adminsAdapter.setList(data);
        });


        viewModel.getLoginResponse().observe(this, data -> {

            if (data == null) {
//                failed
                btnLogin.setEnabled(true);
            } else {
                textInputLEmail.setError("");
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @OnClick(R.id.btn_login_otp)
    void onOtpClick(View v) {

        String emailString = etLoginEmail.getText().toString();

        try {
            if (emailString.isEmpty()) {
                textInputLEmail.setError("Email cannot be empty");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                textInputLEmail.setError("must be a valid email");
                return;
            }

            viewModel.requestNewOtp(emailString);
            disableOtpButtons();


        } catch (Error e) {
            Timber.e(e);
        }
    }


    @OnClick(R.id.btn_login_clear)
    void onClear(View v) {
        enableOtpButtons();
        hideLoginButtons();
    }


    @OnClick(R.id.btn_login)
    void onLogin(View v) {
        String emailString = etLoginEmail.getText().toString();
        String otpString = etLoginOtp.getText().toString();
        int otp = Integer.parseInt(otpString);
        try {
            if (emailString.isEmpty()) {
                textInputLEmail.setError("Email cannot be empty");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                textInputLEmail.setError("must be a valid email");
                return;
            }

            if (otpString.length() < 4) {
                textInputLEmail.setError("OTP must be 4 digits");
                return;
            }

            viewModel.loginWithOtp(emailString, otp);
            btnLogin.setEnabled(false);


        } catch (Error e) {
            Timber.e(e);
        }
    }


    private void showLoginButtons() {
        etLoginOtp.setEnabled(true);
        btnLoginOtp.setVisibility(View.GONE);
        etLoginOtp.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
        btnLoginClear.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(true);
        btnLoginClear.setEnabled(true);
        tvInfo.setVisibility(View.VISIBLE);

    }


    private void disableOtpButtons() {
        btnLoginOtp.setEnabled(false);
        etLoginEmail.setEnabled(false);

    }

    private void enableOtpButtons() {
        btnLoginOtp.setEnabled(true);
        btnLoginOtp.setVisibility(View.VISIBLE);
        etLoginEmail.setEnabled(true);
    }


    private void hideLoginButtons() {
        etLoginOtp.setEnabled(false);
        etLoginOtp.setVisibility(View.GONE);
        btnLogin.setVisibility(View.GONE);
        btnLoginClear.setVisibility(View.GONE);
        btnLogin.setEnabled(false);
        btnLoginClear.setEnabled(false);
        tvInfo.setVisibility(View.GONE);
    }



    @OnClick(R.id.toggle_admins)
    void toggleVisibility(View v){
        isAdminsVisible = !isAdminsVisible;
       if(isAdminsVisible){
           recyclerAdmins.setVisibility(View.VISIBLE);
           tvInfo1.setVisibility(View.VISIBLE);
       }else {
           recyclerAdmins.setVisibility(View.GONE);
           tvInfo1.setVisibility(View.GONE);
       }
    }


        private void initAdminsList(Context context){
        adminsAdapter = new AdminsAdapter(context);
        recyclerAdmins.setLayoutManager(new LinearLayoutManager(context));
        recyclerAdmins.setAdapter(adminsAdapter);
        }
}
