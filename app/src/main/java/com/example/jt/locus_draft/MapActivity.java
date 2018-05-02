package com.example.jt.locus_draft;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MapActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener,
        SavedFragment.OnFragmentInteractionListener,
        GridFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment;
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    selectedFragment = MainFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment, "MAP").commit();
                    break;
                case R.id.navigation_saved:
                    selectedFragment = SavedFragment.newInstance("a", "b");
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment, "SAVED").commit();
                    break;
                case R.id.navigation_grid:
                    selectedFragment = GridFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment, "GRID").commit();
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
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println(getFragmentManager().getFragments());
    }
}
