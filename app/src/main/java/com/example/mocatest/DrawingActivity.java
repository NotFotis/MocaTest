package com.example.mocatest;



import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.features2d.SIFT;
import org.opencv.imgproc.Imgproc;

import java.util.List;


public class DrawingActivity extends AppCompatActivity {

    private DrawingView drawingView;
    private ImageView referenceImageView;
    private Button submitButton;
private Button clearButton;
    private Bitmap referenceBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        referenceImageView = findViewById(R.id.reference_image_view);
        submitButton = findViewById(R.id.submit_button);
        clearButton = findViewById(R.id.clear_button);

        // Load the reference image from resources
        referenceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.reference_image);
        referenceImageView.setImageBitmap(referenceBitmap);

        drawingView = findViewById(R.id.drawing_view);
        drawingView.setReferenceBitmap(referenceBitmap);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capture the drawn image as a bitmap
                Bitmap drawnBitmap = drawingView.getBitmap();

                // Calculate the similarity score between the drawn image and the reference image
                double similarityScore = DHash.calculateSimilarityScore(drawnBitmap, referenceBitmap);

                // Display the similarity score to the user
                Toast.makeText(DrawingActivity.this, "Similarity Score: " + similarityScore, Toast.LENGTH_SHORT).show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the drawing
                drawingView.clearDrawing();
            }
        });
    }





}
