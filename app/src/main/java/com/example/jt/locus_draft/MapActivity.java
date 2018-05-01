package com.example.jt.locus_draft;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener,
        SavedFragment.OnFragmentInteractionListener,
        GridFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private Location mCurrentLocation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment;
            switch (item.getItemId()) {
                case R.id.navigation_map:
//                    mTextMessage.setText(R.string.title_home);
                    selectedFragment = MainFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
                    break;
                case R.id.navigation_saved:
//                    mTextMessage.setText(R.string.title_dashboard);
                    selectedFragment = SavedFragment.newInstance("a", "b");
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
                    break;
                case R.id.navigation_grid:
//                    mTextMessage.setText(R.string.title_notifications);
                    selectedFragment = GridFragment.newInstance(mCurrentLocation);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //set frame layout to map fragment
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, MainFragment.newInstance()).commit();

    }

    //receive the current location from map activity
    @Override
    public void onLocationChange(Location location) {
        mCurrentLocation = location;
    }

    //get rid of this
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
