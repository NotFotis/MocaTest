package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LetterGame extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private String letters = "Φ Β Α Κ Μ Ν Α Α Α Ι Κ Λ Β Α Φ Α Κ Δ Ε Α Α Α Ι Α Μ Ο Φ Α Α Β ";
    private int totalA = 11;
    private int tappedA = 0;
    private boolean isStarted = false;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_game);

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLetterTapping();
            }
        });

        textToSpeech = new TextToSpeech(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private void startLetterTapping() {
        //totalA = countOccurrences(letters, 'A');
        tappedA = 0;
        isStarted = true;
        speakLetters();
        displayAButton();
    }

    private void speakLetters() {
        textToSpeech.setSpeechRate(0.7f); // Adjust the speech rate as needed
        textToSpeech.speak(letters, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void displayAButton() {
        LinearLayout buttonLayout = findViewById(R.id.button_layout);
        buttonLayout.removeAllViews();

        Button aButton = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(5, 5, 5, 5);
        aButton.setLayoutParams(params);
        aButton.setText("Α");
        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLetterATapped();
            }
        });

        buttonLayout.addView(aButton);

        Button submitButton = new Button(this);
        submitButton.setLayoutParams(params);
        submitButton.setText("Submit");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClicked();
            }
        });

        buttonLayout.addView(submitButton);
    }

    private void onLetterATapped() {
        if (!isStarted) {
            Toast.makeText(LetterGame.this, "Click Start to begin", Toast.LENGTH_SHORT).show();
            return;
        }

        tappedA++;
    }

    private void onSubmitClicked() {
        isStarted = false;
        calculateScore();
    }

    private void calculateScore() {
        double score;
        if (tappedA == totalA || tappedA <= 9 || tappedA >= 13) {
            score = 0.0;
        } else {
            score = 1.0;
        }
        Intent intent = new Intent(LetterGame.this, SubtractionActivity.class);
        int ClockScore = intent.getIntExtra("ClockScore", 0);
        int DrawingScore = intent.getIntExtra("DrawingScore", 0);
        int AnimalQuizScore= intent.getIntExtra("AnimalQuizScore", 0);
        int LetterGameScore = intent.getIntExtra("LetterGameScore",0);
        intent.putExtra("AnimalQuizScore", AnimalQuizScore); // Pass the score as an extra with the intent
        intent.putExtra("DrawingScore", DrawingScore);
        intent.putExtra("ClockScore", ClockScore);
        intent.putExtra("LetterGameScore", LetterGameScore);
        intent.putExtra("Score", score);
        startActivity(intent);
        Toast.makeText(LetterGame.this, "Score: " + score, Toast.LENGTH_SHORT).show();
    }

    private int countOccurrences(String str, char target) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == target) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(new Locale("el", "GR"));
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Greek language not supported", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Text-to-speech initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

}
