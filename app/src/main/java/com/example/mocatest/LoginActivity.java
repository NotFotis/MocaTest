package com.example.mocatest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private Spinner sexSpinner;
    private EditText educationEditText;
    private TextView dateOfBirthTextView;
    private Button datePickerButton;
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
        dateOfBirthTextView = findViewById(R.id.date_of_birth_text);
        datePickerButton = findViewById(R.id.date_picker_button);
        currentDateTextView = findViewById(R.id.current_date);
        registerButton = findViewById(R.id.register_button);

        databaseHelper = new UserDatabaseHelper(this);

        // Get the current date and display it in the TextView
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);
        currentDateTextView.setText(formattedDate);

        // Set up a listener for the date picker button
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set up a listener for the Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect the user information
                String fullName = fullNameEditText.getText().toString();
                String sex = sexSpinner.getSelectedItem().toString();
                String education = educationEditText.getText().toString();
                String dateOfBirth = dateOfBirthTextView.getText().toString(); // Get the selected date

                // Get the current date
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = dateFormat.format(currentDate);

                // Store the user in the database
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UserDatabaseHelper.COLUMN_FULL_NAME, fullName);
                values.put(UserDatabaseHelper.COLUMN_SEX, sex);
                values.put(UserDatabaseHelper.COLUMN_EDUCATION, education);
                values.put(UserDatabaseHelper.COLUMN_DATE_OF_BIRTH, dateOfBirth);
                values.put(UserDatabaseHelper.COLUMN_DATE_REGISTERED, formattedDate);
                long userId = db.insert(UserDatabaseHelper.TABLE_USERS, null, values);

                // Close the database
                db.close();

                // Show a toast message indicating success
                Toast.makeText(LoginActivity.this, "User registered with ID " + userId, Toast.LENGTH_SHORT).show();

                // Redirect to DrawingActivity
                Intent intent = new Intent(LoginActivity.this, TotalScoreActivity.class);
                intent.putExtra("FULL_NAME", fullName);
                startActivity(intent);
            }
        });
}

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Set the Greek locale
        Locale greekLocale = new Locale("el", "GR");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", greekLocale);

        DatePickerDialog datePickerDialog = new DatePickerDialog(LoginActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date in the TextView
                        String selectedDate = String.format(greekLocale, "%04d/%02d/%02d", year, monthOfYear + 1, dayOfMonth);
                        dateOfBirthTextView.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

}
