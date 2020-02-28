package com.example.testservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class PackageReplacedReceiver extends BroadcastReceiver {

    private static final String TAG = PackageReplacedReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        final View overlay = LayoutInflater.from(context).inflate(R.layout.overlay, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Log.d(TAG, "show overlay");
            TestService.getInstance().showAccessibilityOverlay(overlay);
        } else {
            Log.e(TAG, "wrong build version");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "hide overlay");
                TestService.getInstance().hideAccessibilityOverlay(overlay);

            }
        }).start();
    }

}
