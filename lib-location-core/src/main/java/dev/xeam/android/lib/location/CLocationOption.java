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
    private boolean reuse = false; // 复用 client，可以节省流量

    public int getLocationMode() {
        return locationMode;
    }

    public CLocationOption setLocationMode(int locationMode) {
        this.locationMode = locationMode;
        return this;
    }

    public long getLocationInterval() {
        return locationInterval;
    }

    public CLocationOption setLocationInterval(long locationInterval) {
        if (locationInterval < LOCATION_INTERVAL_DEFAULT) {
            Log.w(TAG, "locationInterval is too small,locationInterval=" + locationInterval);
        }
        this.locationInterval = locationInterval;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    public CLocationOption setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public boolean isNeedAddress() {
        return needAddress;
    }

    public CLocationOption setNeedAddress(boolean needAddress) {
        this.needAddress = needAddress;
        return this;
    }

    public CLocationOption setLocationOnce(boolean locationOnce) {
        this.locationOnce = locationOnce;
        return this;
    }

    public boolean isOnce() {
        return locationOnce || locationInterval < LOCATION_INTERVAL_DEFAULT;
    }

    public boolean isReuse() {
        return reuse;
    }

    public void setReuse(boolean reuse) {
        this.reuse = reuse;
    }
}
