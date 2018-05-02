package com.example.jt.locus_draft;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements OnMapReadyCallback {

    private OnFragmentInteractionListener mListener;

    private MapView mMapView;
    private GoogleMap mMap;

    private double berkeleyLat = 37.871826;
    private double berkeleyLng = -122.259824;
    private Location berkeleyLoc;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng berkeley = new LatLng(berkeleyLat, berkeleyLng);
        berkeleyLoc = new Location(LocationManager.GPS_PROVIDER);
        berkeleyLoc.setLatitude(berkeleyLat);
        berkeleyLoc.setLongitude(berkeleyLng);
        mListener.onLocationChange(berkeleyLoc);

        float zoomLevel = 15.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(berkeley, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(berkeley));

        mMap.getUiSettings().setZoomControlsEnabled(true);

        loadLocations();
        setOnCircleClickListener();
    }

    private void loadLocations() {
        DatabaseReference locsRef = FirebaseDatabase.getInstance().getReference().child("locs");

        //get all locations from firebase
        locsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //get values of location from database
                HashMap<String, String> locData = (HashMap<String, String>) dataSnapshot.getValue();

                //add circle at given latitude and longitude
                double lat = Double.parseDouble(locData.get("lat"));
                double lng = Double.parseDouble(locData.get("lng"));

                PhotoLoc pl = new PhotoLoc(locData.get("name"), lat, lng, locData.get("img"), dataSnapshot.getKey());

                Circle circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(lat, lng))
                        .radius(32)
                        .fillColor(Color.BLACK)
                        .clickable(true));

                circle.setClickable(true);
                circle.setTag(pl); //make a meaningful tag here
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //nothing
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //nothing
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //nothing
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //nothing
            }
        });
    }

    private void setOnCircleClickListener() {
        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                //do something with the tag here
                PhotoLoc pl = (PhotoLoc) circle.getTag();

                //send to location activity
                Intent intent = new Intent(getActivity(), PhotoLocationActivity.class);
                intent.putExtra("pl", pl);
                intent.putExtra("lat", berkeleyLat);
                intent.putExtra("lng", berkeleyLng);
                startActivity(intent);
            }
        });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(/*String param1, String param2*/) {
        MainFragment fragment = new MainFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // code from https://stackoverflow.com/questions/16536414/how-to-use-mapview-in-android-using-google-map-v2?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onLocationChange(Location location);
    }
}
