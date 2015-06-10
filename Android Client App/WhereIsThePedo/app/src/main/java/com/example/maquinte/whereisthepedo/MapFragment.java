package com.example.maquinte.whereisthepedo;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;



/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment {

    MapView mMapView;
    GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.activity_map_fragment, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                new RestClient().execute("http://aldonarreola-001-site1.smarterasp.net/api/geoData");
            }
        });

        googleMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {

                    @Override
                    public boolean onMarkerClick(Marker arg0) {
                        Log.i("Markerinfo",Double.toString(arg0.getPosition().latitude));
                        Log.i("Markerinfo",Double.toString(arg0.getPosition().longitude));
                        return false;
                    }

                }
    );

            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

                @Override
                public void onMapLongClick(LatLng arg0) {

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(arg0.latitude, arg0.longitude))
                            .title("New Marker"));

                    new RestClientPost().execute("http://aldonarreola-001-site1.smarterasp.net/api/geoData", Double.toString(arg0.latitude), Double.toString(arg0.longitude));

                }
            });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    private class RestClientPost extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params){
            Vector v = new Vector();


            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonParam = new JSONObject();


                jsonParam.put("categoryId", "1");
                jsonParam.put("description", ".");
                jsonParam.put("latitude", params[1]);
                jsonParam.put("longitude",  params[2]);
                jsonParam.put("time", ".");
                jsonParam.put("remarks", ".");

                OutputStreamWriter out = new   OutputStreamWriter(conn.getOutputStream());
                out.write(jsonParam.toString());
                out.close();

                int HttpResult =conn.getResponseCode();
                if(HttpResult ==HttpURLConnection.HTTP_OK){

                    StringBuilder sb = new StringBuilder();

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            conn.getInputStream(),"utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    System.out.println(""+sb.toString());

                }else{
                    System.out.println(conn.getResponseMessage());
                }

                conn.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            catch (Exception e) {

                e.printStackTrace();

            }

            return "";
        }
    }



    private class RestClient extends AsyncTask<String, Void, Vector> {
        @Override
        protected Vector doInBackground(String... serverUrl){
            Vector v = new Vector();


            try {
                URL url = new URL(serverUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                StringBuilder builder = new StringBuilder();
                String aux = "";
                while ((aux = reader.readLine()) != null) {
                    builder.append(aux);
                }
                String jsonInString = builder.toString();
                final Gson gson = new Gson();
                GeoPoint[]points = gson.fromJson(jsonInString, GeoPoint[].class);
                for(GeoPoint point : points)
                    v.add(point);
                conn.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            catch (Exception e) {

                e.printStackTrace();

            }
            return v;
        }

        @Override
        protected void onPostExecute(Vector points) {
            Enumeration enumerator = points.elements();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            while (enumerator.hasMoreElements()) {
                GeoPoint pointToAdd = (GeoPoint) enumerator.nextElement();
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(pointToAdd.getLatitude(), pointToAdd.getLongitude())).title(pointToAdd.getDescription());
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                googleMap.addMarker(marker);
                builder.include(marker.getPosition());
            }
            int padding = 0; // offset from edges of the map in pixels33
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.animateCamera(cu);
        }
    }

}