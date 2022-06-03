package com.twinternet.druber;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback

{


    private SupportMapFragment mapFragment;
    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location userLastKnownLocation;
    private final float DEFAULT_ZOOM = 21;
    private Location myLocation;
    private Button findDoctor;








    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        getSupportActionBar().hide();

        findDoctor = findViewById(R.id.btn_findAvaibaleDoctors);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        findDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDoctor();

            }
        });

        //we cast our fragment into the SupportMapFragment
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_google_maps_id);
        mapFragment.getMapAsync(this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);




        Dexter.withContext(GoogleMapActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse)
                    {
                        gMap.setMyLocationEnabled(true);
                        //Show location
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(5000);
                        locationRequest.setFastestInterval(1000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

                        //requesting user to enable gps
                        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);


                        SettingsClient settingsClient = LocationServices.getSettingsClient(GoogleMapActivity.this);
                        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
                        task.addOnSuccessListener(GoogleMapActivity.this, new OnSuccessListener<LocationSettingsResponse>()
                        {
                            @Override
                            public void onSuccess(LocationSettingsResponse locationSettingsResponse)
                            {
                                getDeviceLocation();

                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse)
                    {
                        //location denied
                        Toast.makeText(GoogleMapActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken)
                    {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();







    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation()
    {
        gMap.setMyLocationEnabled(true);

        //getting the last known location
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task)
            {
                //if the task to get the lastKnowLocation is successful then we get the device location
                if(task.isSuccessful())
                {
                    userLastKnownLocation = task.getResult();

                    if(userLastKnownLocation != null)
                    {
                        LatLng currentUserLocation = new LatLng(userLastKnownLocation.getLatitude(), userLastKnownLocation.getLongitude());

                        //Move the camera to the current user location
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLocation, DEFAULT_ZOOM));
                    }

                    }

            }
        });
    }

    private void showDoctor()
    {
        //Displaying available doctors
        MarkerOptions doctor1 = new MarkerOptions();
        doctor1.position(new LatLng(-0.10343164265809346, 34.75222825756009));
        doctor1.title("Dr. D.A. Minai");
        gMap.addMarker(doctor1);
        gMap.animateCamera(CameraUpdateFactory.newLatLng(doctor1.getPosition()));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(doctor1.getPosition(), DEFAULT_ZOOM));
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker)
            {

                AlertDialog.Builder alert = new AlertDialog.Builder(GoogleMapActivity.this);
                alert.setTitle("D.A.Minai");
                alert.setMessage("Specialty:  Dentist \n Phone Number: 0721463369");
                alert.setNegativeButton("Cancel", null);
                alert.setPositiveButton("Select", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startActivity(new Intent(GoogleMapActivity.this, PatientDetailsActivity.class));

                    }
                });
                alert.show();

                return false;
            }
        });

        //Displaying the second Doctor
        //Displaying available doctors
        MarkerOptions doctor2 = new MarkerOptions();
        doctor2.position(new LatLng(-0.08386913210141354, 34.77418514003305));
        doctor2.title("Dr. Margaret A.M.");
        gMap.addMarker(doctor2);
        gMap.animateCamera(CameraUpdateFactory.newLatLng(doctor2.getPosition()));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(doctor2.getPosition(), DEFAULT_ZOOM));
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(GoogleMapActivity.this);
                alert.setTitle("Dr. Margaret A.M.");
                alert.setMessage("Specialty:  Pediatrician \n Phone Number: 0726066050");
                alert.setNegativeButton("Cancel", null);
                alert.setPositiveButton("Select", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startActivity(new Intent(GoogleMapActivity.this, PatientDetailsActivity.class));

                    }
                });
                alert.show();

                return false;
            }
        });


        //Displaying the third Doctor
        //Displaying available doctors
        MarkerOptions doctor3 = new MarkerOptions();
        doctor3.position(new LatLng(-0.10373205215404101, 34.753344058799975));
        doctor3.title("Dr. Obiero J.J.");
        gMap.addMarker(doctor3);
        gMap.animateCamera(CameraUpdateFactory.newLatLng(doctor3.getPosition()));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(doctor3.getPosition(), DEFAULT_ZOOM));
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(GoogleMapActivity.this);
                alert.setTitle("Dr. Obiero J.J.");
                alert.setMessage("Specialty:  Physiotherapist \n Phone Number: 0711621762");
                alert.setNegativeButton("Cancel", null);
                alert.setPositiveButton("Select", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startActivity(new Intent(GoogleMapActivity.this, PatientDetailsActivity.class));
                    }
                });
                alert.show();

                return false;
            }
        });


        //Displaying the fourth Doctor
        //Displaying available doctors
        MarkerOptions doctor4 = new MarkerOptions();
        doctor4.position(new LatLng(-0.10120005227706572, 34.75555424232755));
        doctor4.title("Dr. Wendy D.M");
        gMap.addMarker(doctor4);
        gMap.animateCamera(CameraUpdateFactory.newLatLng(doctor4.getPosition()));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(doctor4.getPosition(), DEFAULT_ZOOM));
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(GoogleMapActivity.this);
                alert.setTitle("Dr. Wendy D.M");
                alert.setMessage("Specialty:  Clinical Officer \n Phone Number: 0712203203");
                alert.setNegativeButton("Cancel", null);
                alert.setPositiveButton("Select", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startActivity(new Intent(GoogleMapActivity.this, PatientDetailsActivity.class));
                    }
                });
                alert.show();

                return false;
            }
        });


        //Displaying the fourth Doctor
        //Displaying available doctors
        MarkerOptions doctor5 = new MarkerOptions();
        doctor5.position(new LatLng(-0.10155410330594891, 34.755559606745656));
        doctor5.title("Dr. Patricia P.J.");
        gMap.addMarker(doctor5);
        gMap.animateCamera(CameraUpdateFactory.newLatLng(doctor5.getPosition()));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(doctor5.getPosition(), DEFAULT_ZOOM));
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(GoogleMapActivity.this);
                alert.setTitle("Dr. Patricia P.J.");
                alert.setMessage("Specialty:  (Emergency(ER) \n Phone Number: 0726686900");
                alert.setNegativeButton("Cancel", null);
                alert.setPositiveButton("Select", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startActivity(new Intent(GoogleMapActivity.this, PatientDetailsActivity.class));

                    }
                });
                alert.show();

                return false;
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 12)
        {
            if (resultCode == RESULT_OK)
            {
                getDeviceLocation();
            }
        }
    }
}

