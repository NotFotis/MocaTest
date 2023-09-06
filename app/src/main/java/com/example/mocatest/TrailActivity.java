package com.example.mocatest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class TrailActivity extends AppCompatActivity {
    private TextView currentNumberTextView;
    private TrailDrawing drawingView;
    private Button clearButton;
    private int currentNumber = 1;
    private List<Point> drawnPath = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail);

        currentNumberTextView = findViewById(R.id.currentNumberTextView);
        drawingView = findViewById(R.id.drawingView);
        clearButton = findViewById(R.id.clearButton);

        updateCurrentNumber();

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPath();
            }
        });
    }

    private void updateCurrentNumber() {
        currentNumberTextView.setText(String.valueOf(currentNumber));
    }

    private void clearPath() {
        drawnPath.clear();
        drawingView.invalidate();
    }

    private void checkPath() {
        if (drawnPath.size() >= 2) {
            if (currentNumber == 1) {
                // First number
                currentNumber++;
                updateCurrentNumber();
                clearPath();
            } else if (drawnPathIsValid()) {
                currentNumber++;
                updateCurrentNumber();
                clearPath();
            } else {
                clearPath();
            }
        } else {
            clearPath();
        }
    }

    private boolean drawnPathIsValid() {
        if (drawnPath.size() < 2) {
            return false;
        }

        Point startPoint = drawnPath.get(0);
        Point endPoint = drawnPath.get(drawnPath.size() - 1);

        int startX = (int) startPoint.x;
        int startY = (int) startPoint.y;
        int endX = (int) endPoint.x;
        int endY = (int) endPoint.y;

        // Calculate the distance between start and end points
        double distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));

        // Define a threshold for a valid connection
        int threshold = 100;

        // Check if the drawn path is within the threshold of a straight line
        return distance <= threshold;
    }

    public int getDrawnPathSize() {
        return drawnPath.size();
    }

    public Point getDrawnPathPoint(int index) {
        return drawnPath.get(index);
    }
}
