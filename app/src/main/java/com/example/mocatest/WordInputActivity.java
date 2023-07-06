package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class WordInputActivity extends AppCompatActivity {

    private TextView resultTextView;
    private EditText wordEditText;
    private Button submitButton;

    private List<String> wordList = Arrays.asList("FACE", "VELVET", "CHURCH", "DAISY", "RED");
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_input);

        resultTextView = findViewById(R.id.resultTextView);
        wordEditText = findViewById(R.id.wordEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredWord = wordEditText.getText().toString().toUpperCase().trim();

                if (wordList.contains(enteredWord)) {
                    score++;
                }

                wordEditText.getText().clear();

                if (score == 5) {
                    resultTextView.setText("Perfect score! You got all the words right!");
                } else {
                    resultTextView.setText("Your score: " + score + " out of 5");
                }
                Intent intent = new Intent(WordInputActivity.this, OrientationActivity.class);
                int ClockScore = intent.getIntExtra("ClockScore", 0);
                int DrawingScore = intent.getIntExtra("DrawingScore", 0);
                int AnimalQuizScore= intent.getIntExtra("AnimalQuizScore", 0);
                int LetterGameScore = intent.getIntExtra("score",0);
                int SubtractionScore = intent.getIntExtra("finalScore",0);
                int SpeechScore = intent.getIntExtra("score",0);
                int WordScore = intent.getIntExtra("score",0);
                int SimilarityScore = intent.getIntExtra("score",0);
                intent.putExtra("AnimalQuizScore", AnimalQuizScore); // Pass the score as an extra with the intent
                intent.putExtra("DrawingScore", DrawingScore);
                intent.putExtra("ClockScore", ClockScore);
                intent.putExtra("score", LetterGameScore);
                intent.putExtra("finalScore", SubtractionScore);
                intent.putExtra("score", SpeechScore);
                intent.putExtra("score", WordScore);
                intent.putExtra("score", SimilarityScore);
                intent.putExtra("score", score);
                startActivity(intent);
                resultTextView.setVisibility(View.VISIBLE);
            }
        });
    }
}
