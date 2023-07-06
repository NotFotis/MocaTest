package com.example.mocatest;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ShapeDetector {
    public Shape detectFromBitmap(Bitmap bitmap) {
        // Convert the bitmap to OpenCV Mat format
        Mat image = new Mat();
        Utils.bitmapToMat(bitmap, image);

        // Preprocess the image
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        // Apply edge detection
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 50, 150);

        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Find the largest contour
        MatOfPoint largestContour = null;
        double maxArea = 0;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                largestContour = contour;
            }
        }

        // Approximate the contour to a polygon
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        MatOfPoint2f contour2f = new MatOfPoint2f(largestContour.toArray());
        double epsilon = 0.02 * Imgproc.arcLength(contour2f, true);
        Imgproc.approxPolyDP(contour2f, approxCurve, epsilon, true);

        // Get the number of corners to determine the shape
        int numCorners = approxCurve.rows();

        // Create a Shape object based on the number of corners
        Shape shape;
        switch (numCorners) {
            case 3:
                shape = new Shape.Triangle();
                break;
            case 4:
                shape = new Shape.Rectangle();
                break;
            case 5:
                shape = new Shape.Pentagon();
                break;
            case 6:
                shape = new Shape.Hexagon();
                break;
            default:
                shape = new Shape.Unknown();
                break;
        }

        // Draw the detected shape on the image (for visualization purposes)
        Imgproc.drawContours(image, contours, -1, new Scalar(0, 255, 0), 3);

        // Convert the image back to Bitmap format
        Bitmap resultBitmap = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(image, resultBitmap);

        // Set the result image and shape in the Shape object
        shape.setImage(resultBitmap);

        return shape;
    }

    public static abstract class Shape {
        private Bitmap image;

        public void setImage(Bitmap image) {
            this.image = image;
        }

        public Bitmap getImage() {
            return image;
        }

        public static class Triangle extends Shape {
            // Triangle-specific properties
            private int baseLength;
            private int height;

            // Triangle-specific methods
            public void setBaseLength(int baseLength) {
                this.baseLength = baseLength;
            }

            public int getBaseLength() {
                return baseLength;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getHeight() {
                return height;
            }
        }

        public static class Rectangle extends Shape {
            // Rectangle-specific properties
            private int width;
            private int height;

            // Rectangle-specific methods
            public void setWidth(int width) {
                this.width = width;
            }

            public int getWidth() {
                return width;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getHeight() {
                return height;
            }
        }

        public static class Pentagon extends Shape {
            // Pentagon-specific properties
            private int sideLength;

            // Pentagon-specific methods
            public void setSideLength(int sideLength) {
                this.sideLength = sideLength;
            }

            public int getSideLength() {
                return sideLength;
            }
        }

        public static class Hexagon extends Shape {
            // Hexagon-specific properties
            private int sideLength;

            // Hexagon-specific methods
            public void setSideLength(int sideLength) {
                this.sideLength = sideLength;
            }

            public int getSideLength() {
                return sideLength;
            }
        }

        public static class Unknown extends Shape {
            // Unknown shape-specific properties
            private String description;

            // Unknown shape-specific methods
            public void setDescription(String description) {
                this.description = description;
            }

            public String getDescription() {
                return description;
            }
        }

    }
}
