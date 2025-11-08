package com.subhobhai.sublauncher.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom overlay view for Minecraft controls: WASD, Jump, Crouch, etc.
 * Draws virtual controls and handles input dispatch via touch.
 */
public class ControlsOverlayView extends View {
    private final List<ControlButton> buttons = new ArrayList<>();
    private final Paint paint = new Paint();

    public ControlsOverlayView(Context context) {
        super(context);
        init();
    }
    public ControlsOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        // WASD D-Pad
        buttons.add(new ControlButton("W", "UP", 0.15f, 0.70f, 56));
        buttons.add(new ControlButton("S", "DOWN", 0.15f, 0.85f, 56));
        buttons.add(new ControlButton("A", "LEFT", 0.08f, 0.78f, 56));
        buttons.add(new ControlButton("D", "RIGHT", 0.22f, 0.78f, 56));
        // Jump (Space)
        buttons.add(new ControlButton("⤴", "SPACE", 0.85f, 0.78f, 60));
        // Crouch (Shift)
        buttons.add(new ControlButton("⬇", "LSHIFT", 0.75f, 0.88f, 56));
        // Inventory (E)
        buttons.add(new ControlButton("☰", "E", 0.25f, 0.93f, 50));
        // (More buttons could be added here)
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ControlButton btn : buttons) {
            btn.draw(canvas, getWidth(), getHeight(), paint);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consumed = false;
        for (ControlButton btn : buttons) {
            if (btn.handleTouch(event, getWidth(), getHeight())) {
                // TODO: Dispatch JNI event: btn.keycode/label, event.action
                consumed = true;
            }
        }
        invalidate(); // Redraw pressed state, etc.
        return consumed ? true : super.onTouchEvent(event);
    }
}
