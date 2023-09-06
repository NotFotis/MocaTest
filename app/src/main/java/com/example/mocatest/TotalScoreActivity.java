package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class TotalScoreActivity extends AppCompatActivity {
    private TextView fullNameTextView;
    private TextView totalScoreTextView;

    private ActivityResultLauncher<Intent> drawingLauncher;
    private ActivityResultLauncher<Intent> clockLauncher;
    private ActivityResultLauncher<Intent> animalQuizLauncher;
    private ActivityResultLauncher<Intent> MemoryLauncher;
    private ActivityResultLauncher<Intent> NumbersGameLauncher;
    private ActivityResultLauncher<Intent> LetterGameLauncher;
    private ActivityResultLauncher<Intent> SubtractionLauncher;
    private ActivityResultLauncher<Intent> SpeechLauncher;
    private ActivityResultLauncher<Intent> WordLauncher;
    private ActivityResultLauncher<Intent> SimilarityLauncher;
    private ActivityResultLauncher<Intent> WordInputLauncher;
    private ActivityResultLauncher<Intent> OrientationLauncher;

    // Add launchers for other activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_score);

        fullNameTextView = findViewById(R.id.full_name_text_view);
        totalScoreTextView = findViewById(R.id.total_score_text_view);

        // Retrieve the full name from LoginActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("FULL_NAME")) {
            String fullName = intent.getStringExtra("FULL_NAME");
            fullNameTextView.setText(fullName);
        }

        // Initialize ActivityResultLaunchers
        drawingLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result1")
        );
        clockLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result2")
        );
        animalQuizLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result3")
        );
        MemoryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result4")
        );
        NumbersGameLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result5")
        );
        LetterGameLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result6")
        );
        SubtractionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result7")
        );
        SpeechLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result8")
        );
        WordLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result9")
        );
        SimilarityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result10")
        );
        WordInputLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result11")
        );
        OrientationLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result, "result12")
        );


        // Initialize launchers for other activities

        // Start the activities in sequence
        startDrawingActivity();


    }

    private void startDrawingActivity() {
        Intent intent = new Intent(this, DrawingActivity.class);
        drawingLauncher.launch(intent);
    }

    private void startClockActivity() {
        Intent intent = new Intent(this, ClockActivity.class);
        clockLauncher.launch(intent);
    }
    private void startAnimalQuizActivity() {
        Intent intent = new Intent(this, AnimalQuizActivity.class);
        animalQuizLauncher.launch(intent);
    }
    private void startMemoryActivity() {
        Intent intent = new Intent(this, MemoryActivity.class);
        MemoryLauncher.launch(intent);
    }

    private void startNumberQuizActivity() {
        Intent intent = new Intent(this, NumbersGameActivity.class);
        NumbersGameLauncher.launch(intent);
    }
    private void startLetterGameActivity() {
        Intent intent = new Intent(this, LetterGame.class);
        LetterGameLauncher.launch(intent);
    }
    private void startSubtractionActivity() {
        Intent intent = new Intent(this, SubtractionActivity.class);
        SubtractionLauncher.launch(intent);
    }

    private void startSpeechActivity() {
        Intent intent = new Intent(this, SpeechActivity.class);
        SpeechLauncher.launch(intent);
    }
    private void startWordActivity() {
        Intent intent = new Intent(this, WordActivity.class);
        WordLauncher.launch(intent);
    }
    private void startSimilarityActivity() {
        Intent intent = new Intent(this, SimilarityActivity.class);
        SimilarityLauncher.launch(intent);
    }

    private void startWordInputActivity() {
        Intent intent = new Intent(this, WordInputActivity.class);
        WordInputLauncher.launch(intent);
    }
    private void startOrientationActivity() {
        Intent intent = new Intent(this, OrientationActivity.class);
        OrientationLauncher.launch(intent);
    }

    // Implement similar methods for other activities

    private void handleActivityResult(ActivityResult result, String extraKey) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            int activityScore = data.getIntExtra(extraKey, 0);

            // Calculate the total score based on the returned score
            double totalScore = calculateTotalScore(); // Pass other scores
            totalScoreTextView.setText(String.valueOf(totalScore));
        }
    }
    private int calculateTotalScore() {
        int totalScore = 0;
        Intent intent = getIntent();

        int ClockScore = intent.getIntExtra("result2", 0);
        int DrawingScore = intent.getIntExtra("result1", 0);
        int AnimalQuizScore= intent.getIntExtra("result4", 0);
        int LetterGameScore = intent.getIntExtra("result5",0);
        int SubtractionScore = intent.getIntExtra("result6",0);
        int SpeechScore = intent.getIntExtra("result7",0);
        int WordScore = intent.getIntExtra("result8",0);
        int SimilarityScore = intent.getIntExtra("result9",0);
        int WordInputScore = intent.getIntExtra("result10",0);
        int OrientationScore = intent.getIntExtra("result11",0);
        totalScore += ClockScore;
        totalScore += DrawingScore;
        totalScore += AnimalQuizScore;
        totalScore += LetterGameScore;
        totalScore += SubtractionScore;
        totalScore += SpeechScore;
        totalScore += WordScore;
        totalScore += SimilarityScore;
        totalScore += WordInputScore;
        totalScore += OrientationScore;


        return totalScore;
    }
}
