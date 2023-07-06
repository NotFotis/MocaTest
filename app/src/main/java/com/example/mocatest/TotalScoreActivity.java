package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TotalScoreActivity extends AppCompatActivity {
    private TextView fullNameTextView;
    private TextView totalScoreTextView;

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

        // Calculate the total score from 11 other activities
        int totalScore = calculateTotalScore();
        totalScoreTextView.setText(String.valueOf(totalScore));
    }

    private int calculateTotalScore() {
        int totalScore = 0;
        Intent intent = getIntent();
        int ClockScore = intent.getIntExtra("ClockScore", 0);
        int DrawingScore = intent.getIntExtra("DrawingScore", 0);
        int AnimalQuizScore= intent.getIntExtra("AnimalQuizScore", 0);
        int LetterGameScore = intent.getIntExtra("score",0);
        int SubtractionScore = intent.getIntExtra("finalScore",0);
        int SpeechScore = intent.getIntExtra("score",0);
        int WordScore = intent.getIntExtra("score",0);
        int SimilarityScore = intent.getIntExtra("score",0);
        int WordInputScore = intent.getIntExtra("score",0);
        int OrientationScore = intent.getIntExtra("score",0);
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
