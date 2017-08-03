package dev.xesam.applocationall;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextSwitcher;

import dev.xeam.android.lib.location.CLocation;
import dev.xeam.android.lib.location.CLocationClient;
import dev.xeam.android.lib.location.CLocationException;
import dev.xeam.android.lib.location.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-7-13.
 */
public class SimpleLocationListener implements CLocationListener {

    private TextSwitcher vConsole;

    public SimpleLocationListener(TextSwitcher view) {
        this.vConsole = view;
    }

    @Override
    public void onLocateStart(CLocationClient locationClient) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                vConsole.setText("requestLocationUpdates:onLocateStart");
            }
        });
    }

    @Override
    public void onLocateSuccess(CLocationClient locationClient, final CLocation location) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                vConsole.setText("requestLocationUpdates:" + location.toString());
            }
        });
    }

    @Override
    public void onLocateFail(CLocationClient locationClient, final CLocationException e) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                vConsole.setText("requestLocationUpdates:" + e.toString());
            }
        });
    }
}
