package com.example.jt.locus_draft;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


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
                    selectedFragment = MainFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
                    break;
                case R.id.navigation_saved:
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

        String filename = "saved.ser";
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<PhotoLoc>());
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChange(Location location) {
        mCurrentLocation = location;
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
