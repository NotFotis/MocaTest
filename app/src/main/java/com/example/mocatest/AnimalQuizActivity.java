package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AnimalQuizActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText animalEditText;
    private Button prevButton;
    private Button nextButton;
    private Button submitButton;
    private ImageView imageView;

    private String[] animalNames = {"λιονταρι", "ρινοκερος", "καμηλα"};
    private String[] animalImages = {"lion", "rhino", "camel"};
    private String[] userAnswers = new String[animalNames.length]; // Array to store user's answers
    private int currentAnimalIndex = 0;
    private int AnimalQuizScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_quiz);

        animalEditText = findViewById(R.id.animalEditText);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        submitButton = findViewById(R.id.submitButton);
        imageView = findViewById(R.id.imageView);

        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        updateAnimalImage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevButton:
                showPreviousAnimal();
                break;
            case R.id.nextButton:
                saveUserAnswer();
                showNextAnimal();
                break;
            case R.id.submitButton:
                saveUserAnswer();
                calculateScore();
                break;
        }
    }

    private void showPreviousAnimal() {
        if (currentAnimalIndex > 0) {
            currentAnimalIndex--;
            updateAnimalImage();
        }
    }

    private void showNextAnimal() {
        if (currentAnimalIndex < animalNames.length - 1) {
            currentAnimalIndex++;
            updateAnimalImage();
        } else {
            showScore();
        }
    }

    private void updateAnimalImage() {
        int resourceId = getResources().getIdentifier(animalImages[currentAnimalIndex], "drawable", getPackageName());
        imageView.setImageResource(resourceId);
        animalEditText.setText(userAnswers[currentAnimalIndex]);
        animalEditText.setHint("Enter animal name");
        animalEditText.requestFocus();

        // Hide/show previous and next buttons based on the current animal index
        if (currentAnimalIndex == 0) {
            prevButton.setVisibility(View.INVISIBLE);
        } else {
            prevButton.setVisibility(View.VISIBLE);
        }

        if (currentAnimalIndex == animalNames.length - 1) {
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    private void saveUserAnswer() {
        userAnswers[currentAnimalIndex] = animalEditText.getText().toString().trim();
    }

    private void calculateScore() {
        AnimalQuizScore = 0;

        for (int i = 0; i < animalNames.length; i++) {
            String userAnswer = userAnswers[i];
            String correctAnswer = animalNames[i];

            if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                AnimalQuizScore++;
            }
        }

        Toast.makeText(this, "Your score: " + AnimalQuizScore + "/" + animalNames.length, Toast.LENGTH_SHORT).show();
    }

    private void showScore() {
        calculateScore();
        AnimalQuizScore = 0; // Reset the score for a new quiz

        // Create an Intent to start the next activity
        Intent intent = new Intent(AnimalQuizActivity.this, MemoryActivity.class);
        int fullName = intent.getIntExtra("FULL_NAME", 0);
        int ClockScore = intent.getIntExtra("ClockScore", 0);
        int DrawingScore = intent.getIntExtra("DrawingScore", 0);
        intent.putExtra("FULL_NAME",fullName);
        intent.putExtra("AnimalQuizScore", AnimalQuizScore); // Pass the score as an extra with the intent
        intent.putExtra("DrawingScore", DrawingScore);
        intent.putExtra("ClockScore", ClockScore);
        startActivity(intent);
    }
}
