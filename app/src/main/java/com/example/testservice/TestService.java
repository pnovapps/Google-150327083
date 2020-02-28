package com.example.testservice;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.RequiresApi;

public class TestService extends AccessibilityService {

    private static final String TAG = TestService.class.getSimpleName();

    private static TestService thisInstance;

    public static TestService getInstance() {
        return thisInstance;
    }

    public TestService() {
        super();
        Log.d(TAG, "TestService");
        thisInstance = this;
    }

    @Override
    protected void onServiceConnected() {
        Log.d(TAG, "onServiceConnected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, event.toString());
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @SuppressLint("InflateParams")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void showAccessibilityOverlay(View accessibilityOverlay) {

        Log.d(TAG, "showAccessibilityOverlay");

        WindowManager windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);

        if(windowManager == null) {
            return;
        }

        final WindowManager.LayoutParams layoutParams
                = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.OPAQUE);

        // If the update fails to restart the accessibility service correctly an exception
        // is thrown as below
        //
        // 02-26 09:55:48.146  5800  6759 W WindowManager: Attempted to add Accessibility overlay window with unknown token null.  Aborting.
        // 02-26 09:55:48.147 29577 29577 E MainActivity: android.view.WindowManager$BadTokenException: Unable to add window -- token null is not valid; is your activity running?
        windowManager.addView(accessibilityOverlay, layoutParams);

    }

    public void hideAccessibilityOverlay(View accessibilityOverlay) {

        Log.d(TAG, "hideAccessibilityOverlay " + accessibilityOverlay);

        WindowManager windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);

        if(windowManager == null) {
            return;
        }

        windowManager.removeView(accessibilityOverlay);

    }

}
