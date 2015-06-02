package com.example.maquinte.whereisthepedo;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.util.Log;
public class MainActivity extends FragmentActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_fragment);
        addMapFragment();
    }

    private void addMapFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFragment fragment = new MapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();
    }









/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment)fragment;

        if(null!= mapFragment) {
            Log.w("whereIsThePedo", "mapFragment is not null");
            GoogleMap supportMap = mapFragment.getMap();
        }
        else {
            Log.w("whereIsThePedo", "mapFragment is null");
        }



      /*
   map = mapFragment.getMap();
      SupportMapFragment mapFragment = (SupportMapFragment)fragment;
        map = mapFragment.getMap();
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Hello world"));
    }

*/
}