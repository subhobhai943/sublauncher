package com.subhobhai.sublauncher.controls;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;

import com.subhobhai.sublauncher.NativeBridge;

import java.util.HashMap;
import java.util.Map;

/**
 * TouchController: sends key press events to native input bridge on virtual press/release.
 */
public class TouchController {
    private final Map<String, Boolean> keyDown = new HashMap<>();
    private final Handler handler;
    private final Context context;

    public TouchController(Context ctx) {
        this.context = ctx;
        handler = new Handler(Looper.getMainLooper());
    }

    public void onButtonEvent(String keycode, boolean pressed) {
        // Avoid repeats (only send on state change)
        if (keyDown.containsKey(keycode) && keyDown.get(keycode) == pressed) return;
        keyDown.put(keycode, pressed);
        // Send input via JNI/Native bridge (stub)
        if (pressed) NativeBridge.sendKeyDown(keycode);
        else NativeBridge.sendKeyUp(keycode);
    }

    // Optional: expose for ControlsOverlayView onTouch integration
    public boolean onTouch(MotionEvent e, String keycode) {
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                onButtonEvent(keycode, true); return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                onButtonEvent(keycode, false); return true;
        }
        return false;
    }
}
