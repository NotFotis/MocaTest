package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SimilarityActivity extends AppCompatActivity {
    private TextView promptTextView;
    private EditText similarityEditText;
    private Button submitButton;

    private String currentPrompt;
    private String[] prompts = {
            "τραίνο - ποδήλατο = ?",
            "ρολόι - χάρακας = ?"
    };

    private int currentPromptIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similarity);

        promptTextView = findViewById(R.id.promptTextView);
        similarityEditText = findViewById(R.id.similarityEditText);
        submitButton = findViewById(R.id.submitButton);

        showNextPrompt();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSimilarity();
            }
        });
    }

    private void showNextPrompt() {
        if (currentPromptIndex < prompts.length) {
            currentPrompt = prompts[currentPromptIndex];
            promptTextView.setText(currentPrompt);
            similarityEditText.setText("");
        } else {
            promptTextView.setText("Όλα τα μηνύματα ολοκληρώθηκαν! Σκορ: " + score);
            similarityEditText.setEnabled(false);
            submitButton.setEnabled(false);
        }
    }

    private void checkSimilarity() {
        String userInput = similarityEditText.getText().toString().trim().toLowerCase();
        String expectedAnswer = getExpectedAnswer(currentPromptIndex);

        if (userInput.equals(expectedAnswer)) {
            // User provided correct similarity
            score += 1; // Increment score by 1
            Toast.makeText(this, "Correct! Score: " + score, Toast.LENGTH_SHORT).show();
        } else {
            // User provided incorrect similarity
            Toast.makeText(this, "Incorrect! Score: " + score, Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(SimilarityActivity.this, WordInputActivity.class);
        int ClockScore = intent.getIntExtra("ClockScore", 0);
        int DrawingScore = intent.getIntExtra("DrawingScore", 0);
        int AnimalQuizScore= intent.getIntExtra("AnimalQuizScore", 0);
        int LetterGameScore = intent.getIntExtra("score",0);
        int SubtractionScore = intent.getIntExtra("finalScore",0);
        int SpeechScore = intent.getIntExtra("score",0);
        int WordScore = intent.getIntExtra("score",0);
        intent.putExtra("AnimalQuizScore", AnimalQuizScore); // Pass the score as an extra with the intent
        intent.putExtra("DrawingScore", DrawingScore);
        intent.putExtra("ClockScore", ClockScore);
        intent.putExtra("score", LetterGameScore);
        intent.putExtra("finalScore", SubtractionScore);
        intent.putExtra("score", SpeechScore);
        intent.putExtra("score", WordScore);
        intent.putExtra("score", score);
        startActivity(intent);
        currentPromptIndex++;
        showNextPrompt();
    }

    private String getExpectedAnswer(int promptIndex) {
        switch (promptIndex) {
            case 0:
                return "μεταφορα";
            case 1:
                return "μετρηση";
            default:
                return "";
        }
    }
}
