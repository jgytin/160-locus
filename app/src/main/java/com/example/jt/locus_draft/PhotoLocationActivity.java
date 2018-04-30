package com.example.jt.locus_draft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class PhotoLocationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Photo> mPhotos = new ArrayList<Photo>(30);

    private int mLocationNumber;

    private DatabaseReference mLocRef;
    private String mImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_location);

        Intent intent = getIntent();
        mLocationNumber = intent.getIntExtra("ex", 0);
        getSupportActionBar().setTitle("Location " + mLocationNumber);

        mRecyclerView = findViewById(R.id.location_images);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mLocRef = FirebaseDatabase.getInstance().getReference("locs").child(mLocationNumber +  "");
        System.out.println(mLocRef);

        setPhotos();
        setAdapterAndUpdateData();
    }

    private void setPhotos() {
        System.out.println(mLocRef.child("img"));
        //get photo url from locs/[#]/img
        mLocRef.child("img").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //load the url into a new photo object to display
                System.out.println(dataSnapshot.getValue(String.class));
                mPhotos.add(new Photo(dataSnapshot.getValue(String.class)));
                setAdapterAndUpdateData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // don't worry about it
            }
        });

    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mPhotos array
        // this will "refresh" our recycler view
        mAdapter = new PhotoAdapter(this, mPhotos);
        mRecyclerView.setAdapter(mAdapter);
    }
}
