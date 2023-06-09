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

    private Bitmap referenceBitmap;
    private Bitmap drawingBitmap;
    private Canvas drawingCanvas;
    private Paint paint;
    private float lastTouchX;
    private float lastTouchY;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
    }

    public void setReferenceBitmap(Bitmap referenceBitmap) {
        this.referenceBitmap = referenceBitmap;
    }

    public Bitmap getBitmap() {
        return drawingBitmap;
    }

    public void clearDrawing() {
        drawingCanvas.drawColor(Color.WHITE);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        drawingBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawingCanvas = new Canvas(drawingBitmap);
        drawingCanvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(drawingBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = touchX;
                lastTouchY = touchY;
                return true;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                drawingCanvas.drawLine(lastTouchX, lastTouchY, touchX, touchY, paint);
                invalidate();
                lastTouchX = touchX;
                lastTouchY = touchY;
                return true;
        }
        return false;
    }
}
