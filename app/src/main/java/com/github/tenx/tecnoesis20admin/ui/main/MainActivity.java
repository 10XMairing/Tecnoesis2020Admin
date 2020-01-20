package com.github.tenx.tecnoesis20admin.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.ui.main.feeds.FeedFragment;
import com.github.tenx.tecnoesis20admin.ui.main.feeds.UploadStatusWatcher;
import com.github.tenx.tecnoesis20admin.ui.main.notifications.NotificationFragment;
import com.github.tenx.tecnoesis20admin.ui.splash.SplashActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UploadStatusWatcher {


    @BindView(R.id.act_main_bnv)
    BottomNavigationView botNav;



    //    frag mans
    private FragmentManager fm;

    private MainViewModel vm;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    private Boolean isUploadingFeed = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        vm = ViewModelProviders.of(this).get(MainViewModel.class);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        initBotNav();
        initNavHeader();

        //        initialize home fragment in main activity

        loadFragment(new FeedFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id  = item.getItemId();

      if (id == R.id.nav_contact) {

                sendMail();


        } else if (id == R.id.nav_logout) {
                vm.deleteUserData();
                Intent i = new Intent(MainActivity.this , SplashActivity.class);
                startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //    handle bottom navigation clicks

    private void loadFragment(Fragment frag){
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.act_main_fl_container, frag).commit();


    }

    private void initBotNav(){
        botNav.setOnNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            Fragment frag;

            if(isUploadingFeed){
                Snackbar.make(drawer , "Feed upload in status. Wait for completion", Snackbar.LENGTH_SHORT).show();
                return false;
            }
            switch (id){
                case R.id.nav_home:
                    frag = new FeedFragment();
                    break;
                case R.id.nav_notifications:


                    frag = new NotificationFragment();

                    break;
                default:
                    return  false;
            }

            loadFragment(frag);
            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        vm.loadFeeds();
        vm.loadNotifications();
    }

    public MainViewModel getVm() {
        return vm;
    }

    private void  sendMail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","tenx.devs@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tecnoesis2020 Report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }



    private void initNavHeader(){
        View headerView = navigationView.getHeaderView(0);
        TextView tvEmail = headerView.findViewById(R.id.tv_nav_header_email);
        tvEmail.setText(vm.getEmail());
    }

    public boolean isOwner(String email){
            return vm.isOwner(email);
    }

    @Override
    public void onGoingUpload(Boolean status) {
            isUploadingFeed = status;
    }
}
