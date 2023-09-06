package com.example.mocatest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    private Paint paint;
    private Bitmap canvasBitmap;
    private Canvas drawCanvas;
    private float previousX, previousY;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        previousX = -1;
        previousY = -1;

        // Set a reasonable default size for canvasBitmap
        int width = 2000;  // Adjust as needed
        int height = 2000; // Adjust as needed

        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (previousX != -1) {
                    drawCanvas.drawLine(previousX, previousY, touchX, touchY, paint);
                }
                previousX = touchX;
                previousY = touchY;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                drawCanvas.drawLine(previousX, previousY, touchX, touchY, paint);
                previousX = touchX;
                previousY = touchY;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                previousX = -1;
                previousY = -1;
                break;
            default:
                return false;
        }

        return true;
    }

    public Bitmap getCanvasBitmap() {
        return canvasBitmap;
    }

    public void clearCanvas() {
        drawCanvas.drawColor(Color.WHITE);
        invalidate();
    }
}
