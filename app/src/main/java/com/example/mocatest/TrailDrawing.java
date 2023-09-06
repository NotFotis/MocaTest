package com.example.mocatest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

public class TrailDrawing extends View {
    private Paint paint;
    private TrailActivity trailActivity;

    public TrailDrawing(Context context) {
        super(context);
        trailActivity = (TrailActivity) context;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < trailActivity.getDrawnPathSize() - 1; i++) {
            Point p1 = trailActivity.getDrawnPathPoint(i);
            Point p2 = trailActivity.getDrawnPathPoint(i + 1);
            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
        }
    }
}
