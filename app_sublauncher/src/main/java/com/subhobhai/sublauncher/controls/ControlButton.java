package com.subhobhai.sublauncher.controls;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Virtual on-screen input control button: drawable with label, action, and key binding.
 */
public class ControlButton {
    public final String label;
    public final String keycode; // e.g. "W", "SPACE"
    public final float relX, relY; // as % of width/height (0-1)
    public final float radiusPx;
    public boolean pressed;

    public ControlButton(String label, String keycode, float relX, float relY, float radiusPx) {
        this.label = label;
        this.keycode = keycode;
        this.relX = relX;
        this.relY = relY;
        this.radiusPx = radiusPx;
    }

    public void draw(Canvas canvas, int w, int h, Paint paint) {
        float cx = relX * w;
        float cy = relY * h;
        paint.setColor(pressed ? Color.argb(180,30,160,230) : Color.argb(110,50,50,50));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, radiusPx, paint);

        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(radiusPx * 0.72f);
        paint.setFakeBoldText(true);
        canvas.drawText(label, cx, cy + (radiusPx * 0.26f), paint);
    }

    public boolean handleTouch(MotionEvent e, int w, int h) {
        float cx = relX * w;
        float cy = relY * h;
        float dx = e.getX() - cx;
        float dy = e.getY() - cy;
        boolean inside = (dx * dx + dy * dy) <= (radiusPx * radiusPx);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (inside) pressed = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                pressed = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!inside) pressed = false;
                break;
        }
        return inside;
    }
}
