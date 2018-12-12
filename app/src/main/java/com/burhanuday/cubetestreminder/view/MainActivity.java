package com.burhanuday.cubetestreminder.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.util.ReminderManager;
import com.burhanuday.cubetestreminder.adapter.ViewPagerAdapter;
import com.burhanuday.cubetestreminder.util.DataFetch;
import com.burhanuday.cubetestreminder.util.DatabaseHelper;
import com.burhanuday.cubetestreminder.util.GlobalPrefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    private DemoFragment currentFragment;
    private ViewPagerAdapter adapter;
    // UI
    private AHBottomNavigationViewPager viewPager;
    private AHBottomNavigation bottomNavigation;
    AlertDialog alertDialog;
    GlobalPrefs globalPrefs;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initBottomNav();

        Handler permissionCheck = new Handler();
        permissionCheck.postDelayed(new Runnable() {
            @Override
            public void run() {
                askPermissions();
            }
        },500);

        DatabaseHelper dh = new DatabaseHelper(this);
        DataFetch df = new DataFetch(this);
        globalPrefs = new GlobalPrefs(this);
        String lastRefreshed = globalPrefs.getLastRefreshed();
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        if(df.getDays(lastRefreshed, today) != 0) {
            Log.i("compare", "running");
            globalPrefs.setRefreshed(today);
            dh.compare();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);

        // load nav menu header data
        TextView txtName = (TextView) navHeader.findViewById(R.id.nav_header_textView);
        txtName.setText("Cemently");

        // initializing navigation menu
        setUpNavigationView();
        setToolbarTitle("Today");
        drawer.openDrawer(Gravity.START);
        final Handler closeDrawerHandler = new Handler();
        closeDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(Gravity.START)){
                            drawer.closeDrawers();
                        }
                    }
                });
            }
        }, 1000);

    }

    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    private void setUpNavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_design_mix:
                        //startActivity(new Intent(MainActivity.this, DesignMix.class));
                        startActivity(new Intent(MainActivity.this, DesignMix.class));
                        break;
                    case R.id.nav_nominal_mix:
                        startActivity(new Intent(MainActivity.this, NominalMix.class));
                        break;
                    case R.id.nav_reminder:
                        drawer.closeDrawers();
                }
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }

    public void setAlarm(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute);
        ReminderManager reminderManager = new ReminderManager(MainActivity.this);
        reminderManager.setReminder(calendar);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(MainActivity.this, "This permission is required to store and backup data", Toast.LENGTH_SHORT).show();
                    askPermissions();
                }
                break;
            }


            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toasty.error(MainActivity.this, "This permission is required to store and backup data", Toast.LENGTH_LONG, true).show();
                    askPermissions();
                }
                break;

            }
        }
    }

    public void askPermissions(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

        } else {
            // Permission has already been granted
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WAKE_LOCK},
                    2);

        } else {
            // Permission has already been granted
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    3);

        } else {
            // Permission has already been granted
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECEIVE_BOOT_COMPLETED)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                    4);

        } else {
            // Permission has already been granted
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("com.burhanuday.cubetestreminder", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            setAlarm(10,0);
            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("RestrictedApi")
    public void initBottomNav(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }
        bottomNavigation = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Today", R.drawable.ic_today, R.color.light_gray);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Upcoming", R.drawable.ic_upcoming, R.color.light_gray);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Completed", R.drawable.ic_history, R.color.light_gray);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Settings", R.drawable.ic_settings, R.color.light_gray);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);
        bottomNavigation.setTranslucentNavigationEnabled(true);

        floatingActionButton.setVisibility(View.VISIBLE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddAlarm.class));
                finish();
            }
        });
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.light_blue));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.light_gray));
        bottomNavigation.setForceTint(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setColored(false);
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        setToolbarTitle("Today");
                        break;
                    case 1:
                        setToolbarTitle("Upcoming");
                        break;
                    case 2:
                        setToolbarTitle("History");
                        break;
                    case 3:
                        setToolbarTitle("Settings");
                        break;
                }

                if (currentFragment == null) {
                    currentFragment = adapter.getCurrentFragment();
                }

                if (wasSelected) {
                    currentFragment.refresh();
                    return true;
                }

                if (currentFragment != null) {
                    currentFragment.willBeHidden();
                }

                viewPager.setCurrentItem(position, false);

                if (currentFragment == null) {
                    return true;
                }

                currentFragment = adapter.getCurrentFragment();
                currentFragment.willBeDisplayed();
                return true;
            }
        });

        viewPager.setOffscreenPageLimit(4);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        currentFragment = adapter.getCurrentFragment();
    }



}
