package com.example.mocatest;

import android.graphics.Bitmap;
import android.graphics.Color;

public class DHash {

    private static final int HASH_SIZE = 8; // Desired hash size

    // Resize the bitmap to a fixed size (HASH_SIZE x HASH_SIZE)
    private static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    // Convert the bitmap to grayscale
    private static int[] convertToGrayscale(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int[] grayscalePixels = new int[width * height];
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int alpha = Color.alpha(pixel);
            int red = Color.red(pixel);
            int green = Color.green(pixel);
            int blue = Color.blue(pixel);

            // Check if the pixel is transparent
            if (alpha == 0) {
                grayscalePixels[i] = Color.WHITE; // Set transparent pixels to white
            } else {
                int gray = (red + green + blue) / 3;
                grayscalePixels[i] = Color.argb(alpha, gray, gray, gray);
            }
        }

        return grayscalePixels;
    }


    // Calculate the dHash value for a grayscale image
    public static long calculateDHash(int[] grayscalePixels, int width, int height) {
        // Resize the image to HASH_SIZE x HASH_SIZE + 1
        Bitmap resizedBitmap = resizeBitmap(Bitmap.createBitmap(grayscalePixels, width, height, Bitmap.Config.ARGB_8888), HASH_SIZE + 1, HASH_SIZE);
        int resizedWidth = resizedBitmap.getWidth();
        int resizedHeight = resizedBitmap.getHeight();

        // Calculate the dHash value
        long hash = 0L;
        for (int y = 0; y < HASH_SIZE; y++) {
            for (int x = 0; x < HASH_SIZE; x++) {
                int pixel = resizedBitmap.getPixel(x, y);
                int gray = Color.red(pixel);
                if (gray <= 128) { // Consider only black pixels (grayscale <= 128)
                    hash |= (1L << (y * HASH_SIZE + x));
                }
            }
        }

        return hash;
    }



    // Calculate the Hamming distance between two dHash values
    public static int calculateHammingDistance(long hash1, long hash2) {
        long diff = hash1 ^ hash2;
        int distance = 0;
        while (diff != 0) {
            distance++;
            diff &= (diff - 1);
        }
        return distance;
    }

    // Calculate the similarity score between two bitmaps using dHash
    public static double calculateSimilarityScore(Bitmap bitmap1, Bitmap bitmap2) {
        // Check if either of the bitmaps is empty
        if (bitmap1 == null || bitmap2 == null) {
            return 0.0; // Default score when either bitmap is empty
        }

        // Convert bitmaps to grayscale
        int[] pixels1 = convertToGrayscale(bitmap1);
        int[] pixels2 = convertToGrayscale(bitmap2);

        // Calculate dHash values
        long hash1 = calculateDHash(pixels1, bitmap1.getWidth(), bitmap1.getHeight());
        long hash2 = calculateDHash(pixels2, bitmap2.getWidth(), bitmap2.getHeight());

        // Calculate Hamming distance and similarity score
        int distance = calculateHammingDistance(hash1, hash2);

        double similarityScore = 1.0 - (double) distance / (HASH_SIZE * HASH_SIZE - 1);

        // Map the similarity score from range 0-1 to range 0-5
        similarityScore *= 5.0;

        return similarityScore;
    }


}
