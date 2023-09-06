package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MemoryActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;
    private List<String> wordList;
    private int trialCount;

    private TextView promptTextView;
    private EditText inputEditText;
    private Button speakButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        promptTextView = findViewById(R.id.promptTextView);
        inputEditText = findViewById(R.id.inputEditText);
        speakButton = findViewById(R.id.speakButton);
        submitButton = findViewById(R.id.submitButton);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTrials();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmission();
            }
        });

        textToSpeech = new TextToSpeech(this, this);
        wordList = new ArrayList<>();
        wordList.add("FACE");
        wordList.add("VELVET");
        wordList.add("CHURCH");
        wordList.add("DAISY");
        wordList.add("RED");

        trialCount = 0;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or not supported.
                // You can handle the error as per your requirement.
            }
        } else {
            // Text-to-speech initialization failed.
            // You can handle the error as per your requirement.
        }
    }

    private void startTrials() {
        if (trialCount < 2) {
            // Reset UI and input
            promptTextView.setText("Listen carefully and enter the words you hear:");
            inputEditText.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.VISIBLE);
            speakButton.setEnabled(false);
            inputEditText.setText("");

            // Calculate the start and end index of words for the current trial
            int startIndex = trialCount * wordList.size();
            int endIndex = Math.min((trialCount + 1) * wordList.size(), wordList.size());

            // Create the expected array of words for comparison
            String[] expectedArray = wordList.subList(startIndex, endIndex).toArray(new String[0]);

            // Speak the words
            for (String word : expectedArray) {
                textToSpeech.speak(word, TextToSpeech.QUEUE_ADD, null, null);
                // Add delay if needed between words
            }
        } else {
            // Trials completed, perform any necessary cleanup or go to the next activity.
        }
        Intent intent = new Intent(MemoryActivity.this, NumbersGameActivity.class);
        intent.putStringArrayListExtra("wordList", new ArrayList<>(wordList));
        intent.putExtra("result4", 0);
        startActivity(intent);
    }



    private void handleSubmission() {
        String userResponse = inputEditText.getText().toString().toUpperCase().trim();

        // Compare user's response with the expected word list
        List<String> expectedWords = wordList.subList(0, (trialCount + 1) * wordList.size());
        String[] expectedArray = expectedWords.toArray(new String[expectedWords.size()]);
        String[] userArray = userResponse.split("\\s+");

        boolean isCorrect = expectedArray.length == userArray.length;
        for (int i = 0; i < Math.min(expectedArray.length, userArray.length); i++) {
            if (!expectedArray[i].equals(userArray[i])) {
                isCorrect = false;
                break;
            }
        }

        // Display result
        promptTextView.setText(isCorrect ? "Correct!" : "Incorrect! Try again.");
        inputEditText.setText("");
        inputEditText.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        speakButton.setEnabled(true);

        // Increment trial count and start next trial
        trialCount++;
        if (trialCount < 2) {
            // Prompt user to listen again
            promptTextView.setText("Listen carefully and enter the words you hear:");
            speakButton.performClick(); // Simulate button click to start next trial
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
