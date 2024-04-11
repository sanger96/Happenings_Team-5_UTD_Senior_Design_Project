package com.example.happeningsapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.PendingIntent;
import android.content.IntentSender;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.content.pm.PackageManager;
//Google Maps imports
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.happeningsapp.databinding.ActivityMapsBinding;
//location request imports
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

//manifest permissions
import android.Manifest;

import android.os.Looper;
import android.util.Log;
import android.webkit.WebViewClient;
import android.webkit.WebView;
import android.webkit.WebSettings;
//toast import
import android.webkit.WebViewClient;
import android.widget.Toast;
//fused location provider imports
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
//task imports
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnSuccessListener;
//location import
import android.location.Location;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener { //GoogleMap.OnMapLongClickListener is used for testing purposes
    private static final int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 100;
    private static final int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    //public Location currentLocation;
    // Fused Location Provider
    public FusedLocationProviderClient fusedLocationClient;
    //Geofencing Provider
    public GeofencingClient geofencingClient;
    private GeoFenceHelper geoFenceHelper;
    private String GEOFENCE_ID = "SOME_GEOFENCE_ID";
    private float GEOFENCE_RADIUS = 200; // probably in pixel size, double check
    //private int GEOFENCE_EXPIRATION = Geofence.NEVER_EXPIRE;

    // create a statically defined HashMap with <key= buildingName, node = LatLng>
    // <soccor field, LatLng>
    HashMap<String, LatLng> buildingLatLng = new HashMap<String, LatLng>();
    //This list may not be all of them since there could be ones for a soccer field or something and not a building
    public void createBuildingLatLng() {
        buildingLatLng.put("AB", new LatLng(32.9851, -96.7494));
        buildingLatLng.put("AD", new LatLng(32.9895, -96.7480));
        buildingLatLng.put("AH1", new LatLng(32.9878, -96.7521));
        buildingLatLng.put("AH2", new LatLng(32.9878, -96.7518));
        buildingLatLng.put("BE", new LatLng(32.9877, -96.7509));
        buildingLatLng.put("BSB", new LatLng(32.9914, -96.7496));
        buildingLatLng.put("CR", new LatLng(32.9924, -96.7487));
        buildingLatLng.put("CRA", new LatLng(32.9917, -96.7484));
        buildingLatLng.put("GR", new LatLng(32.9884, -96.7479));
        buildingLatLng.put("CB", new LatLng(32.9875, -96.7518));
        buildingLatLng.put("CB3", new LatLng(32.9878, -96.7514));
        buildingLatLng.put("DGA", new LatLng(32.9859, -96.7461));
        buildingLatLng.put("ATC", new LatLng(32.9862, -96.7476));
        buildingLatLng.put("ECSN", new LatLng(32.9869, -96.7504));
        buildingLatLng.put("ECSS", new LatLng(32.9861, -96.7507));
        buildingLatLng.put("ECSW", new LatLng(32.9860, -96.7515));
        buildingLatLng.put("JO", new LatLng(32.9889, -96.7488));
        buildingLatLng.put("MC", new LatLng(32.9872, -96.7476));
        buildingLatLng.put("FM", new LatLng(32.9924, -96.7458));
        buildingLatLng.put("FA", new LatLng(32.9877, -96.7499));
        buildingLatLng.put("FO", new LatLng(32.9877, -96.7491));
        buildingLatLng.put("FN", new LatLng(32.9881, -96.7495));
        buildingLatLng.put("HH", new LatLng(32.9869, -96.7516));
        buildingLatLng.put("ML1", new LatLng(32.9868, -96.7526));
        buildingLatLng.put("PS3", new LatLng(32.9905, -96.7501));
        buildingLatLng.put("PHA", new LatLng(32.9897, -96.7501));
        buildingLatLng.put("PHY", new LatLng(32.9893, -96.7507));
        buildingLatLng.put("PD", new LatLng(32.9916, -96.7456));
        buildingLatLng.put("ROC", new LatLng(32.9861, -96.7573));
        buildingLatLng.put("ROW", new LatLng(32.9863, -96.7584));
        buildingLatLng.put("SG", new LatLng(32.9816, -96.7527));
        buildingLatLng.put("SLC", new LatLng(32.9881, -96.7504));
        buildingLatLng.put("SCI", new LatLng(32.9887, -96.7507));
        buildingLatLng.put("SB", new LatLng(32.9913, -96.7463));
        buildingLatLng.put("SSB", new LatLng(32.9859, -96.7487));
        buildingLatLng.put("SSA", new LatLng(32.9861, -96.7496));
        buildingLatLng.put("SU", new LatLng(32.9869, -96.7489));
        buildingLatLng.put("SPN", new LatLng(32.9947, -96.7529));
        buildingLatLng.put("SP2", new LatLng(32.9951, -96.7535));
        buildingLatLng.put("TH", new LatLng(32.9884, -96.7486));
        buildingLatLng.put("VCB", new LatLng(32.9847, -96.7497));
        buildingLatLng.put("WSTC", new LatLng(32.9884, -96.7565));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(MapsActivity.this);

        // Fused Location Provider
        fusedLocationClient = (FusedLocationProviderClient) LocationServices.getFusedLocationProviderClient(this);

        //Geofencing Provider
        geofencingClient = LocationServices.getGeofencingClient(this);
        geoFenceHelper = new GeoFenceHelper(this);


        // Run a for loop to call alternate version of handleMapLongClick( ) that doesn't do map.clear()
        // make it acccept both building name and Latlng so that geofence name and point are both set.

        // run method to create markers for events, use addMarker(LatLng) pass the latitude and logitude.
        // when creating marker for event, call HashMap to get LatLng for building where event is, then pass to create marker

    }

    /**
     * Created by Google Maps Interface
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //   ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                            int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationTask = fusedLocationClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(loc).title("Your Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 17));
                    mMap.setMyLocationEnabled(true);

                } else {
                    Toast.makeText(MapsActivity.this, "Can't Fetch your current location", Toast.LENGTH_SHORT).show();
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, "Can't Fetch your current location", Toast.LENGTH_SHORT).show();
            }
        });
        mMap.setMyLocationEnabled(true);
        enableUserLocation();
        mMap.setOnMapLongClickListener(this);
    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                //We do not have the permission..
            }
        }
        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Toast.makeText(this, "You can add geofences...", Toast.LENGTH_SHORT).show();
            } else {
                //We do not have the permission..
                Toast.makeText(this, "Background location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addGeofence(LatLng latLng, float radius, String name) {
        //The GEOFENCE_ID is the name of the geofence, set these to the names of the builds
        Geofence geofence = geoFenceHelper.getGeofence(name, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geoFenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geoFenceHelper.getPendingIntent();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Geofence Added...");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geoFenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure: " + errorMessage);
                    }
                });
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }

    private void addCircle(LatLng latLng, float radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0)); // circle border
        circleOptions.fillColor(Color.argb(64, 255, 0, 0)); // circle fill color
        circleOptions.strokeWidth(4); // width in screen pixel for the border
        mMap.addCircle(circleOptions);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (Build.VERSION.SDK_INT >= 29) {
            //We need background permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                handleMapLongClick(latLng);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    //We show a dialog and ask for permission
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                }
            }

        } else {
            handleMapLongClick(latLng);
        }
    }

    // This is where we will statically define the geofences
    private void handleMapLongClick(LatLng latLng) {
        mMap.clear();
        addMarker(latLng); // center of the circle
        addCircle(latLng, GEOFENCE_RADIUS); // this is how to add the circle, this is just a visual representation of the geofence.
        addGeofence(latLng, GEOFENCE_RADIUS, "GEOFENCE_ID"); // this sets the geofence, note that its invisible thats why we draw the circle above.
    }

    // This is where we will statically define the geofences
    private void createGeofences(String name, LatLng latLng) {
        addMarker(latLng); // center of the circle
        addCircle(latLng, GEOFENCE_RADIUS); // this is how to add the circle, this is just a visual representation of the geofence.
        addGeofence(latLng, GEOFENCE_RADIUS, name); // this sets the geofence, note that its invisible thats why we draw the circle above.
    }
}