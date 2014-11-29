package com.bharathmg.bookmyride;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bharathmg.bookmyride.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RideDetails extends FragmentActivity {

    private GoogleMap mMap;
    private TextView dropPointView, carView, pickUpPlace;
    private Button rideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);


        Bundle extras = getIntent().getExtras();
        String carName = extras.getString("car");
        String pickUp = extras.getString("pickUp");
        final String dropPoint = extras.getString("drop");
        double lat = extras.getDouble("lat");
        double lon = extras.getDouble("lon");

        carView = (TextView) findViewById(R.id.carName);
        dropPointView = (TextView) findViewById(R.id.dropPointView);
        pickUpPlace = (TextView) findViewById(R.id.pickUpName);

        carView.setText(carName);
        dropPointView.setText(dropPoint);
        pickUpPlace.setText(pickUp);

        setUpMapIfNeeded();

        if (lat != 0 && lon != 0) {
            LatLng latlng = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(latlng).title(dropPoint));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }

        rideButton = (Button) findViewById(R.id.ride);
        rideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("BookMyRidePrefs", MODE_PRIVATE);
                if (prefs.contains("Phone")) {
                    String number = prefs.getString("Phone", "");
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(number, null, "Ride confirmed. Drop point " + dropPoint, null, null);
                    Toast.makeText(RideDetails.this, "SMS Sent to " + number, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RideDetails.this, "No phone number is saved. Please save a number by choosing Save PhoneNUmber menu option", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                mMap.setTrafficEnabled(true);
                mMap.setBuildingsEnabled(true);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ride_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, UserDetails.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
