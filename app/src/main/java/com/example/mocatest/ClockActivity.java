package com.example.mocatest;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ClockActivity extends AppCompatActivity {

    private Rect contourRect;
    private float lastTouchX, lastTouchY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        Intent intent = getIntent();
        int fullName = intent.getIntExtra("FULL_NAME", 0);
        int DrawingScore = intent.getIntExtra("score", 0);
// Find the submit button
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate the score
                int ClockScore = calculateScore();

                // Create an Intent to start the next activity
                Intent intent = new Intent(ClockActivity.this, AnimalQuizActivity.class);
                intent.putExtra("FULL_NAME", fullName);
                intent.putExtra("DrawingScore", DrawingScore);
                intent.putExtra("ClockScore", ClockScore); // Pass the score as an extra with the intent
                startActivity(intent);
            }
        });

        // Set the drag listeners for the numbers and hands
        setDragListener(R.id.imageNumberOne);
        setDragListener(R.id.imageNumberTwo);
        setDragListener(R.id.imageNumberThree);
        setDragListener(R.id.imageNumberFour);
        setDragListener(R.id.imageNumberFive);
        setDragListener(R.id.imageNumberSix);
        setDragListener(R.id.imageNumberSeven);
        setDragListener(R.id.imageNumberEight);
        setDragListener(R.id.imageNumberNine);
        setDragListener(R.id.imageNumberTen);
        setDragListener(R.id.imageNumberEleven);
        setDragListener(R.id.imageNumberTwelve);

        setDragListener(R.id.imageHourHand);


        // Get the rect of the clock contour
        ImageView contourView = findViewById(R.id.imageClockContour);
        contourView.bringToFront();
        contourRect = new Rect();
        contourView.getHitRect(contourRect);
    }

    private void setDragListener(int viewId) {
        final ImageView view = findViewById(viewId);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        lastTouchX = event.getRawX();
                        lastTouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float dx = event.getRawX() - lastTouchX;
                        float dy = event.getRawY() - lastTouchY;

                        float newX = view.getX() + dx;
                        float newY = view.getY() + dy;

                        // Update the position of the dragged view
                        view.setX(newX);
                        view.setY(newY);

                        lastTouchX = event.getRawX();
                        lastTouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:
                        // Perform any necessary actions when the dragging is completed
                        return true;
                }

                return false;
            }
        });
    }
    private int calculateScore() {
        int score = 0;

        // Check the correctness of the contour
        ImageView contourView = findViewById(R.id.imageClockContour);
        // Add your logic to check if the contour is correct
        boolean isContourCorrect = true /* Check if the contour is correct */;
        if (isContourCorrect) {
            score += 1;
        }

        // Check the correctness of the hands
        ImageView hand1 = findViewById(R.id.imageHourHand);

        // Add your logic to check if the hands are inside the contour
        boolean areHandsInsideContour = isViewInsideContour(hand1, contourView);
        if (areHandsInsideContour) {
            score += 1;
        }

        // Check the correctness of the numbers
        // Add your logic to check if the numbers are correct
        ImageView number1 = findViewById(R.id.imageNumberOne);
        ImageView number2 = findViewById(R.id.imageNumberTwo);
        ImageView number3 = findViewById(R.id.imageNumberThree);
        ImageView number4 = findViewById(R.id.imageNumberFour);
        ImageView number5 = findViewById(R.id.imageNumberFive);
        ImageView number6 = findViewById(R.id.imageNumberSix);
        ImageView number7 = findViewById(R.id.imageNumberSeven);
        ImageView number8 = findViewById(R.id.imageNumberEight);
        ImageView number9 = findViewById(R.id.imageNumberNine);
        ImageView number10 = findViewById(R.id.imageNumberTen);
        ImageView number11 = findViewById(R.id.imageNumberEleven);
        ImageView number12 = findViewById(R.id.imageNumberTwelve);

        boolean areNumbersCorrect = isViewInsideContour(number1,contourView) && isViewInsideContour(number2,contourView) &&
                isViewInsideContour(number3,contourView) && isViewInsideContour(number4,contourView) &&
                isViewInsideContour(number5,contourView) && isViewInsideContour(number6,contourView) &&
                isViewInsideContour(number7,contourView) && isViewInsideContour(number8,contourView) &&
                isViewInsideContour(number9,contourView) && isViewInsideContour(number10,contourView) &&
                isViewInsideContour(number11,contourView) && isViewInsideContour(number12,contourView) /* Check if the numbers are correct */;
        if (areNumbersCorrect) {
            score += 1;
        }

        return score;
    }
    private boolean isViewInsideContour(View view, View contourView) {
        Rect contourRect = new Rect();
        contourView.getHitRect(contourRect);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        return contourRect.contains(viewX, viewY);
    }

}
