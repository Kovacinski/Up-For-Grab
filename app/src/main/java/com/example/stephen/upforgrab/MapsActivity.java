package com.example.stephen.upforgrab;

import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationServices;

import com.parse.Parse;
import com.parse.ParseAnalytics;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener,  GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
   Location local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if(status != ConnectionResult.SUCCESS)
        {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }
        else
        {
            FragmentManager fm;
            fm = getSupportFragmentManager();
            // Getting GoogleMap object from the fragment
            mMap = ((SupportMapFragment)fm.findFragmentById(R.id.map)).getMap();

            // Enabling MyLocation Layer of Google Map
            mMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(new Criteria(), true);

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location!=null){

                onMyLocationChange(location);
            }
        }
        Parse.initialize(this, "5h9nDf0DGXlK1LmtMKsljhGvMbZbk0OuoAbDUGeQ", "0ja8tV2dzEenGtQglDt8h5yBaTdS89G3vI7oZjaF");





    }

    private void onProviderDisabled(String provider) {
    }


    private boolean isGooglePlay(){

        int available =  GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(available == ConnectionResult.SUCCESS)
            return true;
        else {
            return false;
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

   /* @Override
    public void onMyLocationChange(Location location) {
        local = location;
        TextView tvLocation = (TextView) findViewById(R.id.textView1);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        tvLocation.setText("latitude " + latitude+ ", longitude "+ longitude);
    }*/

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
        local = location;

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
