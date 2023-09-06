package com.example.mocatest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class DrawingActivity extends AppCompatActivity {

    private DrawingView drawView;
    private ImageView referenceImageView;
    private Button compareButton;
    private TextView similarityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        drawView = findViewById(R.id.drawView);
        referenceImageView = findViewById(R.id.referenceImageView);
        compareButton = findViewById(R.id.compareButton);
        similarityTextView = findViewById(R.id.similarityTextView);

        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user-drawn bitmap
                Bitmap userBitmap = drawView.getCanvasBitmap();

                Bitmap referenceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.reference_image);
                Mat referenceMat = new Mat();
                Utils.bitmapToMat(referenceBitmap, referenceMat);
                Imgproc.cvtColor(referenceMat, referenceMat, Imgproc.COLOR_BGR2GRAY);

// Convert user's drawing to Mat (assuming drawingMat contains the user's drawing)
                Mat drawingMat = new Mat();
                Utils.bitmapToMat(userBitmap, drawingMat);
                Imgproc.cvtColor(drawingMat, drawingMat, Imgproc.COLOR_BGR2GRAY);

// Apply edge detection (Canny) to both images
                Mat referenceEdges = new Mat();
                Imgproc.Canny(referenceMat, referenceEdges, 100, 200);

                Mat drawingEdges = new Mat();
                Imgproc.Canny(drawingMat, drawingEdges, 100, 200);

// Find contours in both images
                List<MatOfPoint> referenceContours = new ArrayList<>();
                Imgproc.findContours(referenceEdges, referenceContours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

                List<MatOfPoint> drawingContours = new ArrayList<>();
                Imgproc.findContours(drawingEdges, drawingContours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);


                // Calculate similarity score (using SSIM for example)
                double similarityScore = calculateSSIM(drawingMat, referenceMat);

                // Display similarity score
                similarityTextView.setText("Similarity Score: " + similarityScore);
            }
        });
    }

    public static double calculateSSIM(Mat mat1, Mat mat2) {
        // Convert the images to grayscale if they are not already
        Mat mat1Gray = new Mat();
        Mat mat2Gray = new Mat();
        Imgproc.cvtColor(mat1, mat1Gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(mat2, mat2Gray, Imgproc.COLOR_BGR2GRAY);

        // Calculate SSIM
        double ssim = calculateSSIMValue(mat1Gray, mat2Gray);
        return ssim;
    }

    private static double calculateSSIMValue(Mat img1, Mat img2) {
        int width = img1.cols();
        int height = img1.rows();

        Mat img1Squared = new Mat();
        Mat img2Squared = new Mat();
        Core.multiply(img1, img1, img1Squared);
        Core.multiply(img2, img2, img2Squared);

        Mat imgProduct = new Mat();
        Core.multiply(img1, img2, imgProduct);

        Mat mu1 = new Mat();
        Mat mu2 = new Mat();
        Imgproc.GaussianBlur(img1, mu1, new Size(11, 11), 1.5);
        Imgproc.GaussianBlur(img2, mu2, new Size(11, 11), 1.5);

        Mat mu1Squared = new Mat();
        Mat mu2Squared = new Mat();
        Core.multiply(mu1, mu1, mu1Squared);
        Core.multiply(mu2, mu2, mu2Squared);

        Mat muProduct = new Mat();
        Core.multiply(mu1, mu2, muProduct);

        Mat sigma1Squared = new Mat();
        Mat sigma2Squared = new Mat();
        Core.subtract(img1Squared, mu1Squared, sigma1Squared);
        Core.subtract(img2Squared, mu2Squared, sigma2Squared);

        Mat sigma12 = new Mat();
        Core.subtract(imgProduct, muProduct, sigma12);

        Mat ssimMap = new Mat();
        Core.multiply(sigma12, new Scalar(2), ssimMap);
        Core.add(sigma1Squared, sigma2Squared, sigma1Squared);
        Core.add(sigma1Squared, new Scalar(0.0001), sigma1Squared); // Small constant to avoid division by zero
        Core.divide(ssimMap, sigma1Squared, ssimMap);

        Scalar mssim = Core.mean(ssimMap);

        return (mssim.val[0] + mssim.val[1] + mssim.val[2]) / 3.0;
    }
}

