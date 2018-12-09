package com.burhanuday.cubetestreminder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    private DemoFragment currentFragment;
    private ViewPagerAdapter adapter;
    private Handler handler = new Handler();
    Handler adHandler1 = new Handler();
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

        if (!globalPrefs.isRatedByUser()) globalPrefs.incrementDaysPast();
       // runUpdateCheck();
        //Intent intent = new Intent();
        //intent.putExtra("key", 101);

        //onMessage(this, intent);

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

    public String calcDate(String doc, int afterDays){
        Log.i("DOC", doc);
        Date todayDate = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            todayDate = format.parse(doc);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("DATE", "error while parsing date");
        }
        Log.i("TODAY DAte", String.valueOf(todayDate));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(todayDate);
        calendar.add(Calendar.DAY_OF_YEAR, afterDays);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String afterDate = day + "-" + (month + 1) + "-" + year;
        Log.i("DATE ADDED:", afterDate);
        return afterDate;
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
        /*
        ComponentName receiver = new ComponentName(getActivity(), onBootReceiver.class);
        PackageManager pm = getActivity().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        */
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
        handler.removeCallbacksAndMessages(null);
        adHandler1.removeCallbacksAndMessages(null);
    }

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

// Add items
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

// Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

// Enable the translation of the FloatingActionButton
       // bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

// Change colors
      bottomNavigation.setAccentColor(getResources().getColor(R.color.light_blue));
      bottomNavigation.setInactiveColor(getResources().getColor(R.color.light_gray));

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

// Display color under navigation bar (API 21+)
// Don't forget these lines in your style-v21
// <item name="android:windowTranslucentNavigation">true</item>
// <item name="android:fitsSystemWindows">true</item>
        bottomNavigation.setTranslucentNavigationEnabled(true);

// Manage titles
       bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
       // bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
     //   bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

// Use colored navigation with circle reveal effect
       bottomNavigation.setColored(false);
// Set current item programmatically
        bottomNavigation.setCurrentItem(0);

// Customize notification (title, background, typeface)
   //     bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

// Add or remove notification for each item
    //    bottomNavigation.setNotification("1", 3);
// OR
    /*    AHNotification notification = new AHNotification.Builder()
                .setText("1")
                .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary))
                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                .build();
        bottomNavigation.setNotification(notification, 1);

        */

// Enable / disable item & set disable color
     /*   bottomNavigation.enableItemAtPosition(2);
        bottomNavigation.disableItemAtPosition(2);
        bottomNavigation.setItemDisableColor(Color.parseColor("#3A000000"));

        */

// Set listeners
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

		/*
		bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
			@Override public void onPositionChange(int y) {
				Log.d("DemoActivity", "BottomNavigation Position: " + y);
			}
		});
		*/

        viewPager.setOffscreenPageLimit(4);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();
/*
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setting custom colors for notification
                AHNotification notification = new AHNotification.Builder()
                        .setText(":)")
                        .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                        .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                        .build();
                bottomNavigation.setNotification(notification, 1);
                Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
                        Snackbar.LENGTH_SHORT).show();

            }
        }, 3000);

        */

        //bottomNavigation.setDefaultBackgroundResource(R.drawable.bottom_navigation_background);

        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }



}
