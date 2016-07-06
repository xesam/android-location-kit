package dev.xeam.android.lib.location;

import android.util.Log;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
public final class CLocationOption {

    public static final String TAG = "CLocationOption";

    public static final long LOCATION_INTERVAL_DEFAULT = 2 * 1000L;

    private int locationMode;
    private long locationInterval;
    private long timeout = 30 * 1000;
    private boolean needAddress = false;
    private boolean locationOnce = false;

    public int getLocationMode() {
        return locationMode;
    }

    public void setLocationMode(int locationMode) {
        this.locationMode = locationMode;
    }

    public long getLocationInterval() {
        return locationInterval;
    }

    public void setLocationInterval(long locationInterval) {
        if (locationInterval < LOCATION_INTERVAL_DEFAULT) {
            Log.w(TAG, "locationInterval is too small,locationInterval=" + locationInterval);
        }
        this.locationInterval = locationInterval;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isNeedAddress() {
        return needAddress;
    }

    public void setNeedAddress(boolean needAddress) {
        this.needAddress = needAddress;
    }

    public void setLocationOnce(boolean locationOnce) {
        this.locationOnce = locationOnce;
    }

    public boolean isOnce() {
        return locationOnce || locationInterval < LOCATION_INTERVAL_DEFAULT;
    }
}
