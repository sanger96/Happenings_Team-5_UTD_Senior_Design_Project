package com.example.happeningsapp;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.happeningsapp.databinding.ActivityMapsBinding;

//added imports for location services and permissions
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    // LocationListener and Manager object to listen for location updates
    private LocationListener locationListener;
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Request permission to access the user's location
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in UTD and move the camera
        LatLng UTD = new LatLng(32.98580852229557, -96.7501997116368);
        mMap.addMarker(new MarkerOptions().position(UTD).title("The University of Texas at Dallas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UTD));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));

        // Create a LocationListener object to listen for location updates and what happens when the location changes
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Add a marker at the current location and move the camera to the current location
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {


            }
            @Override
            public void onProviderEnabled(String provider) {

            }
            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //Create a LocationManager object to request location updates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Request location updates (min_time = 1 sec, min_distance = 10 meters)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);

    }
}