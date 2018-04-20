package com.example.jt.locus_draft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class PhotoLocationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Photo> mPhotos = new ArrayList<Photo>(30);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_location);

        Intent intent = getIntent();
        getSupportActionBar().setTitle("Location " + intent.getIntExtra("ex", 0));

        mRecyclerView = findViewById(R.id.location_images);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        setPhotos();
        setAdapterAndUpdateData();
    }

    // add some default photos for now
    private void setPhotos() {
        for (int i = 0; i < 9; i++) {
            mPhotos.add(new Photo());
        }
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mPhotos array
        // this will "refresh" our recycler view
        mAdapter = new PhotoAdapter(this, mPhotos);
        mRecyclerView.setAdapter(mAdapter);

        // scroll to the last comment
        if (mPhotos.size() != 0) mRecyclerView.smoothScrollToPosition(mPhotos.size() - 1);
    }
}
