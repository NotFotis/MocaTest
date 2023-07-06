package com.example.mocatest;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class WordActivity extends AppCompatActivity {

    private EditText userInputEditText;
    private TextView timerTextView;
    private Button startButton;
    private Set<String> dictionary;
    private int wordCount;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        userInputEditText = findViewById(R.id.userInputEditText);
        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        dictionary = loadDictionary();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    // If the timer is already running, stop it
                    stopTimer();
                } else {
                    // If the timer is not running, start it
                    startTimer();
                }
            }
        });
    }
    private Set<String> loadDictionary() {
        Set<String> wordSet = new HashSet<>();
        Resources resources = getResources();

        try {
            InputStream inputStream = resources.openRawResource(R.raw.directory);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                wordSet.add(line.trim().toLowerCase());
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordSet;
    }
    private void startTimer() {
        wordCount = 0;
        isTimerRunning = true;

        startButton.setText("Stop");
        userInputEditText.setEnabled(true);

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timerTextView.setText("Time remaining: " + secondsRemaining + "s");
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;

                startButton.setText("Start");
                userInputEditText.setEnabled(false);

                int score = (wordCount > 10) ? 1 : 0;
                Intent intent = new Intent(WordActivity.this, SimilarityActivity.class);
                int ClockScore = intent.getIntExtra("ClockScore", 0);
                int DrawingScore = intent.getIntExtra("DrawingScore", 0);
                int AnimalQuizScore= intent.getIntExtra("AnimalQuizScore", 0);
                int LetterGameScore = intent.getIntExtra("LetterGameScore",0);
                int SubtractionScore = intent.getIntExtra("SubtractionScore",0);
                int SpeechScore = intent.getIntExtra("score",0);
                intent.putExtra("AnimalQuizScore", AnimalQuizScore); // Pass the score as an extra with the intent
                intent.putExtra("DrawingScore", DrawingScore);
                intent.putExtra("ClockScore", ClockScore);
                intent.putExtra("LetterGameScore", LetterGameScore);
                intent.putExtra("SubtractionScore", SubtractionScore);
                intent.putExtra("score", SpeechScore);
                intent.putExtra("score", score);
                startActivity(intent);
                String result = "Word count: " + wordCount + "\nScore: " + score;
                timerTextView.setText(result);
            }

        }.start();
    }

    private void stopTimer() {
        isTimerRunning = false;

        startButton.setText("Start");
        userInputEditText.setEnabled(false);
        timerTextView.setText("Timer stopped");
    }

    public void checkWord(View view) {
        if (isTimerRunning) {
            String userInput = userInputEditText.getText().toString().trim().toLowerCase();

            if (!userInput.isEmpty() && userInput.charAt(0) == 'φ' || userInput.charAt(0) == 'Φ'  && dictionary.contains(userInput)) {
                wordCount++;
            }

            userInputEditText.getText().clear();
        }
    }

}
