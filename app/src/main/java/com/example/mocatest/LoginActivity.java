package com.example.mocatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private Spinner sexSpinner;
    private EditText educationEditText;
    private DatePicker dateOfBirthDatePicker;
    private TextView currentDateTextView;
    private Button registerButton;

    private UserDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fullNameEditText = findViewById(R.id.full_name);
        sexSpinner = findViewById(R.id.sex_spinner);
        educationEditText = findViewById(R.id.education);
        dateOfBirthDatePicker = findViewById(R.id.date_of_birth);
        currentDateTextView = findViewById(R.id.current_date);
        registerButton = findViewById(R.id.register_button);

        databaseHelper = new UserDatabaseHelper(this);

        // Get the current date and display it in the TextView
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        currentDateTextView.setText(formattedDate);

        // Set up a listener for the Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect the user information
                String fullName = fullNameEditText.getText().toString();
                String sex = sexSpinner.getSelectedItem().toString();
                String education = educationEditText.getText().toString();
                int year = dateOfBirthDatePicker.getYear();
                int month = dateOfBirthDatePicker.getMonth();
                int day = dateOfBirthDatePicker.getDayOfMonth();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                long dateOfBirth = calendar.getTimeInMillis() / 1000; // Convert to Unix timestamp
                long dateRegistered = currentDate.getTime() / 1000; // Convert to Unix timestamp
// Convert Unix timestamp to Date object for dateOfBirth
                Date dateOfBirthDate = new Date(dateOfBirth * 1000);

// Convert Unix timestamp to Date object for dateRegistered
                Date dateRegisteredDate = new Date(dateRegistered * 1000);
                // Create SimpleDateFormat objects for formatting dates
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

// Format dateOfBirthDate as string without time
                String dateOfBirthString = dateFormat.format(dateOfBirthDate);

// Format dateRegisteredDate as string without time
                String dateRegisteredString = dateFormat.format(dateRegisteredDate);


                // Store the user in the database
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UserDatabaseHelper.COLUMN_FULL_NAME, fullName);
                values.put(UserDatabaseHelper.COLUMN_SEX, sex);
                values.put(UserDatabaseHelper.COLUMN_EDUCATION, education);
                values.put(UserDatabaseHelper.COLUMN_DATE_OF_BIRTH, dateOfBirthString);
                values.put(UserDatabaseHelper.COLUMN_DATE_REGISTERED, dateRegisteredString);
                long userId = db.insert(UserDatabaseHelper.TABLE_USERS, null, values);

                // Close the database
                db.close();

                // Show a toast message indicating success
                Toast.makeText(LoginActivity.this, "User registered with ID " + userId, Toast.LENGTH_SHORT).show();
                // Redirect to DrawingActivity
                Intent intent = new Intent(LoginActivity.this, DrawingActivity.class);
                startActivity(intent);
            }
        });
    }

}