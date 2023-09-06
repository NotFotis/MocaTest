package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;

import java.time.Clock;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OpenCVLoader.initDebug();
        // Get a reference to the login button in the layout
        Button loginButton = findViewById(R.id.login_button);
        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        // Set a click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to launch the LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });
        // Set a click listener for the login button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to launch the LoginActivity
                Intent intent = new Intent(MainActivity.this, NumbersGameActivity.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to launch the LoginActivity
                Intent intent = new Intent(MainActivity.this, LetterGame.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to launch the LoginActivity
                Intent intent = new Intent(MainActivity.this, DrawingActivity.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to launch the LoginActivity
                Intent intent = new Intent(MainActivity.this, TrailActivity.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });
    }
}
