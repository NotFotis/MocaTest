package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class NumbersGameActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;
    private EditText userInput;
    private Button beginButton;

    private int[] numbers = {2, 1, 8, 5, 4};
    private int currentNumberIndex = 0;

    private int[] additionalNumbers = {2, 4, 7};

    private int currentAdditionalNumberIndex = additionalNumbers.length - 1;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_game);

        userInput = findViewById(R.id.editTextUserInput);
        beginButton = findViewById(R.id.buttonBegin);

        // Initialize the TextToSpeech engine
        textToSpeech = new TextToSpeech(this, this);

        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginButton.setEnabled(false);
                speakNumbers();
            }
        });
    }

    private void speakNumbers() {
        if (currentNumberIndex < numbers.length) {
            int number = numbers[currentNumberIndex];
            String text = String.valueOf(number);

            // Speak the number
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            currentNumberIndex++;

            // Wait for the number to finish speaking
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    speakNumbers();
                }
            }, 1000);
        } else {
            // All numbers have been spoken
            promptUserInput();
        }
    }



    private void promptUserInput() {
        if (currentNumberIndex == numbers.length) {
            // User input for initial numbers
            userInput.setVisibility(View.VISIBLE);
            userInput.setText("");

            userInput.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkNumberInput();
                    return true;
                }
                return false;
            });
        } else {
            // User input for additional numbers
            userInput.setVisibility(View.VISIBLE);
            userInput.setText("");

            userInput.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkAdditionalNumberInput();
                    return true;
                }
                return false;
            });
        }
    }



    private int[] userNumberInput;

    private void checkNumberInput() {
        String input = userInput.getText().toString().trim();

        if (input.equals("")) {
            Toast.makeText(this, "Please enter the numbers", Toast.LENGTH_SHORT).show();
        } else {
            userNumberInput = convertStringToArray(input);

            // Check if the entered numbers match
            boolean numbersMatch = checkNumbersMatch(userNumberInput);

            if (numbersMatch) {
                Toast.makeText(this, "Numbers match!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Numbers do not match", Toast.LENGTH_SHORT).show();
            }
            userInput.setVisibility(View.INVISIBLE);
            userInput.setText("");
            speakAdditionalNumbers();
        }
    }

    private void speakAdditionalNumbers() {
        if (currentAdditionalNumberIndex >= 0) {
            int number = additionalNumbers[currentAdditionalNumberIndex];
            String text = String.valueOf(number);

            // Speak the additional number
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            currentAdditionalNumberIndex--;

            // Wait for the additional number to finish speaking
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    speakAdditionalNumbers();
                }
            }, 1000);
        } else {
            // All additional numbers have been spoken
            promptAdditionalNumberInput();
        }
    }




    private void promptAdditionalNumberInput() {
        userInput.setVisibility(View.VISIBLE);
        userInput.setText("");

        userInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkAdditionalNumberInput();
                return true;
            }
            return false;
        });
    }

    private void checkAdditionalNumberInput() {
        String input = userInput.getText().toString().trim();

        if (input.equals("")) {
            Toast.makeText(this, "Please enter the additional numbers", Toast.LENGTH_SHORT).show();
        } else {
            int[] userNumbers = convertStringToArray(input);

            // Check if the entered additional numbers match
            boolean additionalNumbersMatch = checkAdditionalNumbersMatch(userNumbers);

            int score = 0;

            if (checkNumbersMatch(userNumberInput)) {
                score++;
            }
            if (additionalNumbersMatch) {
                score++;
            }

            Toast.makeText(this, "Score: " + score, Toast.LENGTH_SHORT).show();
            // Go to the next activity or reset the game as per your requirement
        }
    }




    private int[] convertStringToArray(String input) {
        String[] stringArray = input.split(" ");
        int[] intArray = new int[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }

        return intArray;
    }

    private boolean checkNumbersMatch(int[] userNumbers) {
        if (userNumbers.length != numbers.length) {
            return false;
        }

        for (int i = 0; i < numbers.length; i++) {
            if (userNumbers[i] != numbers[i]) {
                return false;
            }
        }

        return true;
    }


    private boolean checkAdditionalNumbersMatch(int[] userNumbers) {
        if (userNumbers.length != additionalNumbers.length) {
            return false;
        }

        for (int i = 0; i < additionalNumbers.length; i++) {
            if (userNumbers[i] != additionalNumbers[i]) {
                return false;
            }
        }

        return true;
    }
    private void resetGame() {
        currentNumberIndex = 0;
        currentAdditionalNumberIndex = additionalNumbers.length - 1;
        userInput.setVisibility(View.INVISIBLE);
        userInput.setText("");
        beginButton.setEnabled(true);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale greekLocale = new Locale("el", "GR");
            int result = textToSpeech.setLanguage(greekLocale);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Text to Speech not supported in Greek language", Toast.LENGTH_SHORT).show();
            } else {
                beginButton.setEnabled(true);
            }
        } else {
            Toast.makeText(this, "Text to Speech initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        // Remove any pending handler messages
        handler.removeCallbacksAndMessages(null);
    }
}
