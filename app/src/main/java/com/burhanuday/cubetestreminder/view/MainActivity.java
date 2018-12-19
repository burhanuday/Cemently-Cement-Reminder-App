package com.burhanuday.cubetestreminder.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.interfaces.AddLocationFabListener;
import com.burhanuday.cubetestreminder.interfaces.CubeDetailsFragmentListener;
import com.burhanuday.cubetestreminder.interfaces.CubeDetailsPressedListener;
import com.burhanuday.cubetestreminder.interfaces.LocationSaveButtonListener;
import com.burhanuday.cubetestreminder.interfaces.ShowDetailsListener;
import com.burhanuday.cubetestreminder.model.Cube;
import com.burhanuday.cubetestreminder.model.Location;
import com.burhanuday.cubetestreminder.util.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        AddLocationFabListener, LocationSaveButtonListener, ShowDetailsListener,
        CubeDetailsPressedListener, CubeDetailsFragmentListener {

    private Fragment fragment;
    private static final String TAG = "MainActivity";

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new UpcomingFragment());
    }

    @Override
    public void onBackPressed() {
        navigation.setVisibility(View.VISIBLE);
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_upcoming:
                fragment = new UpcomingFragment();
                break;
            case R.id.navigation_calendar:
                fragment = new CalendarFragment();
                break;
            case R.id.navigation_more:
                fragment = new MoreFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        Log.i(TAG, "loadFragment: " + fragment.getClass().getSimpleName());
        for (int i=0; i<getSupportFragmentManager().getBackStackEntryCount(); i++){
            if (fragment.getClass().getSimpleName().equals(getSupportFragmentManager().getBackStackEntryAt(i).getName())){
                getSupportFragmentManager().popBackStackImmediate(fragment.getClass().getSimpleName(), 0);
                return true;
            }
        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .replace(R.id.homeFrameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void addLocationFabClicked() {
        Fragment fragment = new AddLocationFragment();
        loadFragment(fragment);
        navigation.setVisibility(View.GONE);
        Log.i(TAG, "addLocationFabClicked: ");
    }

    @Override
    public void saveButtonClicked() {
        Fragment fragment = new UpcomingFragment();
        navigation.setVisibility(View.VISIBLE);
        loadFragment(fragment);
    }

    @Override
    public void showDetailsFragment(Location location) {
        navigation.setVisibility(View.GONE);
        Bundle bundle = new Bundle();
        bundle.putSerializable("location_object", location);

        LocationDetailsFragment locationDetailsFragment = new LocationDetailsFragment();
        locationDetailsFragment.setArguments(bundle);

        loadFragment(locationDetailsFragment);
    }

    @Override
    public void onShowCubeDetails(Cube cube, Fragment fragment) {
        navigation.setVisibility(View.GONE);
        Bundle bundle = new Bundle();
        bundle.putSerializable("cube_object", cube);

        CubeDetailsFragment cubeDetailsFragment = new CubeDetailsFragment();
        cubeDetailsFragment.setArguments(bundle);
        cubeDetailsFragment.setTargetFragment(fragment, 123);

        loadFragment(cubeDetailsFragment);
    }

    @Override
    public void onSaveButtonClicked() {
        navigation.setVisibility(View.GONE);
        LocationDetailsFragment locationDetailsFragment = new LocationDetailsFragment();
        loadFragment(locationDetailsFragment);
    }

    @Override
    public void onCancelButtonClicked() {

    }

    @Override
    public void onDeleteButtonClicked() {

    }
}