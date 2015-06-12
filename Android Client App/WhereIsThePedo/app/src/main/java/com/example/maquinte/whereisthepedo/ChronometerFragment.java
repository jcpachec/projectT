package com.example.maquinte.whereisthepedo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.TimeUnit;


/**
 * A fragment that launches other parts of the demo application.
 */
public class ChronometerFragment extends Fragment {


    TextView chronometer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.chronometer, container,
                false);
        chronometer = (TextView)v.findViewById(R.id.chronometer);
        new RestClient().execute("http://aldonarreola-001-site1.smarterasp.net/api/nexttime");


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    private class RestClient extends AsyncTask<String, Void, DateTime> {
        @Override
        protected DateTime doInBackground(String... serverUrl){
            DateTime date = null;


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
                Date javaDate = gson.fromJson(jsonInString, Date.class);
                date = new DateTime(javaDate);
                conn.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            catch (Exception e) {

                e.printStackTrace();

            }
            return date;
        }

        @Override
        protected void onPostExecute(DateTime date) {
            //mMapView = (MapView) v.findViewById(R.id.mapView);
            Interval interval = new Interval(new DateTime(), date);
            final Period period = interval.toPeriod();
            new CountDownTimer(period.toStandardDuration().getMillis(), 1000) {

                public void onTick(long millisUntilFinished) {
                    long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                    StringBuilder sb = new StringBuilder(64);
                    sb.append(hours);
                    sb.append(" Hours ");
                    sb.append(minutes);
                    sb.append(" Minutes ");
                    sb.append(seconds);
                    sb.append(" Seconds");

                    chronometer.setText(sb.toString());
                }

                public void onFinish() {
                    chronometer.setText("done!");
            }
            }.start();
        }
    }
}