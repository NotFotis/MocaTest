package com.example.mocatest;

import android.content.ClipData;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
// Find the submit button
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate the score
                int score = calculateScore();
                // Do something with the score (e.g., display it)
                Toast.makeText(ClockActivity.this, "Score: " + score, Toast.LENGTH_SHORT).show();
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
        setDragListener(R.id.imageMinuteHand);

        // Get the rect of the clock contour
        ImageView contourView = findViewById(R.id.imageClockContour);
        contourView.bringToFront();
        contourRect = new Rect();
        contourView.getHitRect(contourRect);
    }

    private void setDragListener(int viewId) {
        final ImageView view = findViewById(viewId);

        // Get the rect of the clock contour
        ImageView contourView = findViewById(R.id.imageClockContour);
        contourRect = new Rect();
        contourView.getHitRect(contourRect);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData clipData = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(clipData, shadowBuilder, v, 0);
                    return true;
                }
                return false;
            }
        });

        contourView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // Check if the dragged view is a number
                        if (event.getLocalState() instanceof ImageView) {
                            // No need to hide the dragged view
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        // Check if the dragged view is a number and is inside the contour
                        if (event.getLocalState() instanceof ImageView && v == contourView) {
                            ImageView draggedNumber = (ImageView) event.getLocalState();
                            // Show visual feedback indicating the correct drop position
                            // For example, change the background color of the contour
                            contourView.setBackgroundColor(Color.GREEN);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        // Check if the dragged view is a number and is inside the contour
                        if (event.getLocalState() instanceof ImageView && v == contourView) {
                            ImageView draggedNumber = (ImageView) event.getLocalState();
                            // Reset the visual feedback
                            // For example, change the background color of the contour back to its original color
                            contourView.setBackgroundColor(Color.TRANSPARENT);
                        }
                        break;
                    case DragEvent.ACTION_DROP:
                        // Check if the dragged view is a number and is dropped inside the contour
                        if (event.getLocalState() instanceof ImageView && v == contourView) {
                            ImageView draggedNumber = (ImageView) event.getLocalState();
                            int centerX = (int) (event.getX() - draggedNumber.getWidth() / 2);
                            int centerY = (int) (event.getY() - draggedNumber.getHeight() / 2);
                            // Set the position of the dragged number to the dropped position on the contour
                            draggedNumber.setX(centerX);
                            draggedNumber.setY(centerY);
                            // Reset the visual feedback
                            contourView.setBackgroundColor(Color.TRANSPARENT);
                            return true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        // Check if the dragged view is a number
                        if (event.getLocalState() instanceof ImageView) {
                            ImageView draggedNumber = (ImageView) event.getLocalState();
                            // No need to change the visibility of the dragged view
                        }
                        break;
                }
                return true;
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
        ImageView hand2 = findViewById(R.id.imageMinuteHand);
        // Add your logic to check if the hands are inside the contour
        boolean areHandsInsideContour = isViewInsideContour(hand1, contourView) && isViewInsideContour(hand2, contourView);
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
