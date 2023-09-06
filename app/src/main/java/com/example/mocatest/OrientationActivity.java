package com.example.mocatest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrientationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private EditText editTextDate, editTextMonth, editTextYear, editTextDay, editTextPlace, editTextCity;
    private Button buttonCheck;
    private Location lastKnownLocation;
    private TextView textViewScore;
    private int score = 0;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);

        editTextDate = findViewById(R.id.editTextDate);
        editTextMonth = findViewById(R.id.editTextMonth);
        editTextYear = findViewById(R.id.editTextYear);
        editTextDay = findViewById(R.id.editTextDay);
        editTextPlace = findViewById(R.id.editTextPlace);
        editTextCity = findViewById(R.id.editTextCity);

        buttonCheck = findViewById(R.id.buttonCheck);
        textViewScore = findViewById(R.id.textViewScore);

        geocoder = new Geocoder(this, Locale.getDefault());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });
    }

    private void checkAnswers() {
        String enteredDate = editTextDate.getText().toString().trim();
        String enteredMonth = editTextMonth.getText().toString().trim();
        String enteredYear = editTextYear.getText().toString().trim();
        String enteredDay = editTextDay.getText().toString().trim();
        String enteredPlace = editTextPlace.getText().toString().trim();
        String enteredCity = editTextCity.getText().toString().trim();

        score = 0;

        // Check date, month, year, and day against the system's date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String systemDate = sdf.format(new Date());

        String[] parts = systemDate.split("-");
        String systemDay = parts[0];
        String systemMonth = parts[1];
        String systemYear = parts[2];

        if (enteredDate.equals(systemDate)) {
            score++;
        }

        if (enteredMonth.equals(getGreekMonth(systemMonth))) {
            score++;
        }

        if (enteredYear.equals(systemYear)) {
            score++;
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfDay = new SimpleDateFormat("EEEE", new Locale("el", "GR"));
        String systemDayOfWeek = sdfDay.format(calendar.getTime());

        if (enteredDay.equalsIgnoreCase(systemDayOfWeek)) {
            score++;
        }

        // Check place and city based on user's location
        if (checkLocationPermission()) {
            retrieveLocation();
            String systemPlace = getPlaceFromCoordinates();
            String systemCity = getCityFromCoordinates();

            String enteredPlaceTrimmed = enteredPlace.trim(); // Trim leading/trailing spaces
            String enteredCityTrimmed = enteredCity.trim(); // Trim leading/trailing spaces
            System.out.println("System place " + systemPlace);
            System.out.println("System place " + systemCity);

                // Compare the entered place and city with the retrieved location
                if (enteredPlaceTrimmed.equalsIgnoreCase(systemPlace) ) {
                    score++;
                }
                if ( enteredCityTrimmed.equalsIgnoreCase(systemCity)){
                    score++;
                }

        }
        Intent intent = new Intent(OrientationActivity.this, TotalScoreActivity.class);
        intent.putExtra("result12", score);
        startActivity(intent);
        textViewScore.setText("Score: " + score);
    }
    private String getGreekMonth(String month) {
        String[] monthNames = {
                "", // Empty placeholder to align with 1-based month index
                "Ιανουάριος", "Φεβρουάριος", "Μάρτιος", "Απρίλιος", "Μάιος", "Ιούνιος",
                "Ιούλιος", "Αύγουστος", "Σεπτέμβριος", "Οκτώβριος", "Νοέμβριος", "Δεκέμβριος"
        };
        int monthIndex = Integer.parseInt(month);
        String greekMonth = monthNames[monthIndex];

        return greekMonth;
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private void retrieveLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, get user's location
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                lastKnownLocation = location; // Set lastKnownLocation to the current location

                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                Geocoder geocoder = new Geocoder(getApplicationContext(), new Locale("el", "GR"));
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    if (addresses.size() > 0) {
                                        Address address = addresses.get(0);
                                        String city = address.getLocality();

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }



    private String getPlaceFromCoordinates() {
        String place = "";

        if (lastKnownLocation != null) {
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();

            try {
                Geocoder geocoder = new Geocoder(getApplicationContext(), new Locale("el", "GR"));
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    place = address.getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return place;
    }

    private String getCityFromCoordinates() {
        String city = "";

        if (lastKnownLocation != null) {
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();

            try {
                Geocoder geocoder = new Geocoder(getApplicationContext(), new Locale("el", "GR"));
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    city = address.getCountryName();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return city;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, retrieve location
                retrieveLocation();
            }
        }
    }
}
