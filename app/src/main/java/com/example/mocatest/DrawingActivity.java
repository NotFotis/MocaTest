package com.example.mocatest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DrawingActivity extends AppCompatActivity {

    private ImageView canvasImageView;
    private Button compareButton;
    private TextView scoreTextView;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint userPaint;
    private Path userPath;
    private RectF expectedRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        canvasImageView = findViewById(R.id.canvasImageView);
        compareButton = findViewById(R.id.compareButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        userPath = new Path();
        expectedRect = new RectF(100, 100, 500, 500); // Example: Assuming the shape of a cube with a specific rectangular area

        userPaint = new Paint();
        userPaint.setColor(Color.RED);
        userPaint.setStyle(Paint.Style.STROKE);
        userPaint.setStrokeWidth(5f);

        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float score = compareShapes();
                scoreTextView.setText("Score: " + score);
                Intent intent = new Intent(DrawingActivity.this, ClockActivity.class);
                int fullName = intent.getIntExtra("FULL_NAME", 0);
                intent.putExtra("DrawingScore", score);
                startActivity(intent);
            }

        });

        canvasImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        userPath.moveTo(x, y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        userPath.lineTo(x, y);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        return false;
                }

                canvasImageView.invalidate();
                return true;
            }
        });

        canvasImageView.post(new Runnable() {
            @Override
            public void run() {
                int width = canvasImageView.getWidth();
                int height = canvasImageView.getHeight();
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                canvasImageView.setImageBitmap(bitmap);
                clearCanvas(); // Clear the canvas when it is ready
            }
        });
    }

    private float compareShapes() {
        // Logic to compare user's drawing with the shape of a cube using geometric calculations

        // Check if the user's path intersects with the expected rectangle
        RectF userRect = new RectF();
        userPath.computeBounds(userRect, true);

        if (RectF.intersects(expectedRect, userRect)) {
            // Calculate the intersection area
            RectF intersection = new RectF();
            if (intersection.setIntersect(expectedRect, userRect)) {
                float expectedArea = expectedRect.width() * expectedRect.height();
                float intersectionArea = intersection.width() * intersection.height();

                float score = intersectionArea / expectedArea * 2; // Normalize score to range from 0 to 2

                return Math.max(score, 0f); // Ensure the score is not negative
            }
        }

        return 0f; // No intersection, score is 0
    }

    private void clearCanvas() {
        if (canvas != null) {
            userPath.reset();
            canvas.drawColor(Color.WHITE);
            canvasImageView.invalidate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up resources if necessary
        bitmap.recycle();
    }

}
