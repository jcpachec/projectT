package com.example.maquinte.whereisthepedo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        addChronometer();
        //addMapFragment();


        Log.i("Julio", "Here");
        setContentView(R.layout.activity_map_fragment);
        addMapFragment();


    }
    private void addChronometer() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ChronometerFragment fragment = new ChronometerFragment();
        transaction.add(R.id.fragment_container, fragment, "chronometer");
        transaction.commit();

    }
    private void addMapFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFragment fragment = new MapFragment();
        transaction.add(R.id.fragment_container, fragment, "map");
        transaction.commit();
    }
}