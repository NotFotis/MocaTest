package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SubtractionActivity extends AppCompatActivity {

    private TextView questionTextView;
    private EditText answerEditText;
    private Button submitButton;

    private int currentScore = 0;
    private int numSubtractions = 0;

    private int[] subtractionNumbers = {100, 93, 86, 79, 72};
    private int[] correctAnswers = {93, 86, 79, 72, 65};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);

        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        showNextQuestion();
    }

    private void showNextQuestion() {
        if (numSubtractions < subtractionNumbers.length) {
            int number = subtractionNumbers[numSubtractions];
            int answer = correctAnswers[numSubtractions];

            questionTextView.setText("Subtract 7 from " + number);
            answerEditText.setText("");

            numSubtractions++;
        } else {
            endGame();
        }
    }

    private void checkAnswer() {
        if (numSubtractions <= subtractionNumbers.length) {
            String userAnswerString = answerEditText.getText().toString().trim();

            if (!userAnswerString.isEmpty()) {
                int userAnswer = Integer.parseInt(userAnswerString);
                int correctAnswer = correctAnswers[numSubtractions - 1];

                if (userAnswer == correctAnswer) {
                    currentScore++;
                }

                showNextQuestion();
            } else {
                Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void endGame() {
        int finalScore = calculateScore();
        Intent intent = new Intent(SubtractionActivity.this, SpeechActivity.class);
        intent.putExtra("result7", finalScore);
        startActivity(intent);
        Toast.makeText(this, "Game Over! Your score: " + finalScore, Toast.LENGTH_LONG).show();
    }

    private int calculateScore() {
        if (currentScore >= 4) {
            return 3;
        } else if (currentScore >= 2) {
            return 2;
        } else if (currentScore >= 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
