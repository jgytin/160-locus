package com.example.jt.locus_draft;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PhotoLocationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Photo> mPhotos = new ArrayList<Photo>(30);

    private ImageButton saveButton;

    PhotoLoc pl;
    private int mLocationNumber;

    private DatabaseReference mLocRef;
    private String mImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_location);

        Intent intent = getIntent();
        pl = (PhotoLoc) intent.getSerializableExtra("pl");
        mLocationNumber = intent.getIntExtra("ex", 0);
        getSupportActionBar().setTitle("Location " + pl.name);

        saveButton = findViewById(R.id.save_button);

        mRecyclerView = findViewById(R.id.location_images);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        setupSaveButton();
        mLocRef = FirebaseDatabase.getInstance().getReference("locs").child(pl.index +  "");

        setPhotos();
        setAdapterAndUpdateData();
    }

    private void setupSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String filename = "saved.ser";

                ArrayList<PhotoLoc> pls = new ArrayList<PhotoLoc>();

                FileInputStream fis = null;
                ObjectInputStream ois = null;
                try {
                    fis = openFileInput(filename);
                    ois = new ObjectInputStream(fis);
                    pls.addAll((ArrayList<PhotoLoc>) ois.readObject());
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (pls.contains(pl)) {
                    pls.remove(pl);
                }
                else {
                    pls.add(pl);
                }

                FileOutputStream fos = null;
                ObjectOutputStream oos = null;
                try {
                    fos = openFileOutput(filename, Context.MODE_PRIVATE);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(pls);
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setPhotos() {
        //get photo url from locs/[#]/img
        mLocRef.child("img").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //load the url into a new photo object to display
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
