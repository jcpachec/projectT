package com.example.maquinte.whereisthepedo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import org.json.JSONObject;

public class MainActivity extends FragmentActivity {

	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	@SuppressWarnings("unused")
	private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9002;
	GoogleMap mMap;

	@SuppressWarnings("unused")
	private static final double SEATTLE_LAT = 47.60621,
	SEATTLE_LNG =-122.33207, 
	SYDNEY_LAT = -33.867487,
	SYDNEY_LNG = 151.20699, 
	NEWYORK_LAT = 40.714353, 
	NEWYORK_LNG = -74.005973;
	private static final float DEFAULTZOOM = 15;
	@SuppressWarnings("unused")
	private static final String LOGTAG = "Maps";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (servicesOK()) {
			setContentView(R.layout.activity_map);

			if (initMap()) {
				mMap.setMyLocationEnabled(true);

				try {
					initPunktData();
				} catch (Exception e) {


				}
			}
			else {
				Toast.makeText(this, "Map not available!", Toast.LENGTH_SHORT).show();
			}
		}
		else {
			setContentView(R.layout.activity_main);
		}

	}

	private void initPunktData(){

		mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
			@Override
			public void onMapLoaded() {
				new RestClient().execute("http://aldonarreola-001-site1.smarterasp.net/api/geoData");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean servicesOK() {
		int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		}
		else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		}
		else {
			Toast.makeText(this, "Can't connect to Google Play services", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	private boolean initMap() {
		if (mMap == null) {
			SupportMapFragment mapFrag =
					(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			mMap = mapFrag.getMap();
		}
		return (mMap != null);
	}

	@SuppressWarnings("unused")
	private void gotoLocation(double lat, double lng) {
		LatLng ll = new LatLng(lat, lng);
		CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
		mMap.moveCamera(update);
	}

	private void gotoLocation(double lat, double lng,
			float zoom) {
		LatLng ll = new LatLng(lat, lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
		mMap.moveCamera(update);
	}

	public void geoLocate(View v) throws IOException {
		hideSoftKeyboard(v);

		EditText et = (EditText) findViewById(R.id.editText1);
		String location = et.getText().toString();

		Geocoder gc = new Geocoder(this);
		List<Address> list = gc.getFromLocationName(location, 1);
		Address add = list.get(0);
		String locality = add.getLocality();
		Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

		double lat = add.getLatitude();
		double lng = add.getLongitude();

		gotoLocation(lat, lng, DEFAULTZOOM);

	}

	private void hideSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.mapTypeNone:
			mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
			break;
		case R.id.mapTypeNormal:
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.mapTypeSatellite:
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.mapTypeTerrain:
			mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case R.id.mapTypeHybrid:
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case R.id.gotoCurrentLocation:
			gotoCurrentLocation();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		super.onStop();
		MapStateManager mgr = new MapStateManager(this);
		mgr.saveMapState(mMap);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MapStateManager mgr = new MapStateManager(this);
		CameraPosition position = mgr.getSavedCameraPosition();
		if (position != null) {
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
			mMap.moveCamera(update);
			//			This is part of the answer to the code challenge
			mMap.setMapType(mgr.getSavedMapType());
		}
	}

	protected void gotoCurrentLocation() {
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
				mMap.addMarker(marker);
				builder.include(marker.getPosition());
			}
			int padding = 0; // offset from edges of the map in pixels33
			LatLngBounds bounds = builder.build();
			CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
			mMap.animateCamera(cu);
		}
	}


}
