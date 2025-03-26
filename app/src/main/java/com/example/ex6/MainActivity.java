package com.example.ex6;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Set up the button to get current location
        Button btnGetLocation = findViewById(R.id.btnGetLocation);
        btnGetLocation.setOnClickListener(v -> getLocation());
    }

    private void getLocation() {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // If permission is granted, check if location services are enabled
            checkLocationServices();
        }
    }

    private void checkLocationServices() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // If location services are not enabled, show an alert and direct to location settings
            showLocationServicesAlert();
        } else {
            // Fetch location if services are enabled
            fetchLocation();
        }
    }

    private void showLocationServicesAlert() {
        // Show an alert to the user to enable location services
        new AlertDialog.Builder(this)
                .setMessage("Location services are disabled. Would you like to enable them?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Open location settings if user clicks "Yes"
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Cannot access location without enabling services.", Toast.LENGTH_SHORT).show();
                })
                .create()
                .show();
    }

    private void fetchLocation() {
        // Create a LocationRequest object for location updates (non-deprecated)
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);  // 10 seconds interval
        locationRequest.setFastestInterval(5000); // 5 seconds fastest update interval
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);  // High accuracy

        // Set up the LocationCallback to handle location updates
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        // Show the location in an AlertDialog
                        showLocationAlert(location.getLatitude(), location.getLongitude());
                    }
                }
            }
        };

        // Request location updates
        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } catch (SecurityException e) {
            // Handle the exception if location permission is not granted
            Toast.makeText(this, "Permission denied to access location", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLocationAlert(double latitude, double longitude) {
        // Display the location in an alert dialog
        new AlertDialog.Builder(this)
                .setTitle("Current Location")
                .setMessage("Latitude: " + latitude + "\nLongitude: " + longitude)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If permission granted, check location services
                checkLocationServices();
            } else {
                // If permission denied, show a message
                Toast.makeText(this, "Permission denied. Cannot fetch location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates when the activity is paused to save resources
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
