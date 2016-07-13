package dev.xesam.applocationall;

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
        vConsole.setText("requestLocationUpdates:onLocateStart");
    }

    @Override
    public void onLocateSuccess(CLocationClient locationClient, CLocation location) {
        vConsole.setText("requestLocationUpdates:" + location.toString());
    }

    @Override
    public void onLocateFail(CLocationClient locationClient, CLocationException e) {
        vConsole.setText("requestLocationUpdates:" + e.toString());
    }
}
