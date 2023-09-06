package com.example.mocatest;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SpeechActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private EditText userInputEditText;
    private Button submitButton;

    private String[] phrases = {
            "Ξέρω μόνο ότι ο Γιάννης είναι αυτός που θα βοηθήσει σήμερα",
            "Η γάτα κρυβόταν πάντα κάτω από τον καναπέ όταν ήταν σκυλιά στο δωμάτιο"
    };

    private int currentIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        userInputEditText = findViewById(R.id.userInputEditText);
        submitButton = findViewById(R.id.submitButton);

        initializeTextToSpeech();

        // Set the initial phrase
        sayPhrase(phrases[currentIndex]);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userResponse = userInputEditText.getText().toString();
                calculateScore(userResponse);
                userInputEditText.getText().clear();

                if (currentIndex < phrases.length - 1) {
                    currentIndex++;
                    sayPhrase(phrases[currentIndex]);
                } else {
                    String scoreMessage = "Your score is: " + score + " out of " + phrases.length;
                    Toast.makeText(SpeechActivity.this, scoreMessage, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locale = new Locale("el", "GR");
                    int result = textToSpeech.setLanguage(locale);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(SpeechActivity.this, "Text to Speech language is not supported.", Toast.LENGTH_SHORT).show();
                    } else {
                        sayPhrase(phrases[currentIndex]);  // Speak the initial phrase after successful initialization
                    }
                } else {
                    Toast.makeText(SpeechActivity.this, "Text to Speech initialization failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // Nothing to do on start
            }

            @Override
            public void onDone(String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        submitButton.setEnabled(true);
                    }
                });
            }

            @Override
            public void onError(String utteranceId) {
                // Handle any errors during speech synthesis
            }
        });
    }



    private void sayPhrase(String phrase) {
        submitButton.setEnabled(false);
        String utteranceId = String.valueOf(currentIndex);
        textToSpeech.setOnUtteranceProgressListener(null);
        textToSpeech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // Nothing to do on start
            }

            @Override
            public void onDone(String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        submitButton.setEnabled(true);
                    }
                });
            }

            @Override
            public void onError(String utteranceId) {
                // Handle any errors during speech synthesis
            }
        });
    }



    private void calculateScore(String userResponse) {
        String currentPhrase = phrases[currentIndex];

        if (currentPhrase.equalsIgnoreCase(userResponse)) {
            score++;
        }
        Intent intent = new Intent(SpeechActivity.this, WordActivity.class);
        intent.putExtra("result8", score);
        startActivity(intent);
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
