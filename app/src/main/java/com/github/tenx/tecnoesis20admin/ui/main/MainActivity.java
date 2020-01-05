package com.github.tenx.tecnoesis20admin.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.ui.main.about.AboutFragment;
import com.github.tenx.tecnoesis20admin.ui.main.events.EventsFragment;
import com.github.tenx.tecnoesis20admin.ui.main.home.HomeFragment;
import com.github.tenx.tecnoesis20admin.ui.splash.SplashActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.act_main_bnv)
    BottomNavigationView botNav;


    //    frags
    private HomeFragment fragHome;
    //    frag mans
    private FragmentManager fm;

    private MainViewModel vm;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    public MainViewModel getVm() {
        return vm;
    }

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
        if(fragHome == null){
            fragHome = new HomeFragment();
        }
        loadFragment(fragHome);
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
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

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
        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                int colorID;
                Fragment frag;
                switch (id){
                    case R.id.nav_home:
                        frag = new HomeFragment();
                        colorID = R.color.nav_home;
                        break;
                    case R.id.nav_events:
                        frag = new EventsFragment();
                        colorID = R.color.nav_events;
                        break;
                    case R.id.nav_about:
                        frag = new AboutFragment();
                        colorID = R.color.nav_about;
                        break;
                    default:
                        return  false;
                }

                botNav.setBackgroundColor(getResources().getColor(colorID));
                loadFragment(frag);
                return true;
            }
        });
    }


    private void initNavHeader(){
        View headerView = navigationView.getHeaderView(0);
        TextView tvEmail = headerView.findViewById(R.id.tv_nav_header_email);
        tvEmail.setText(vm.getEmail());
    }
}
