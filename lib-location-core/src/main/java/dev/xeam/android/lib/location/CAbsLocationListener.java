package dev.xeam.android.lib.location;

import android.util.Log;

/**
 * Created by xesamguo@gmail.com on 16-7-13.
 */
public abstract class CAbsLocationListener<C> implements CLocationListener {

    public final String TAG;

    protected final C mLocationClient;
    private CLocationListener mCLocationListener;
    private Runnable mCallback;

    public CAbsLocationListener(C locationClient, CLocationListener c, Runnable callback) {
        TAG = getClass().getSimpleName();
        this.mLocationClient = locationClient;
        this.mCLocationListener = c;
        this.mCallback = callback;
    }

    public void attach(CLocationListener c) {
        this.mCLocationListener = c;
    }

    public void detach() {
        this.mCLocationListener = null;
    }

    @Override
    public void onLocateStart(CLocationClient locationClient) {
        Log.d(TAG, "onLocateStart");
        if (mCLocationListener != null) {
            mCLocationListener.onLocateStart(locationClient);
        }
    }

    @Override
    public void onLocateSuccess(CLocationClient locationClient, CLocation location) {
        Log.d(TAG, "onLocateSuccess:" + location.toString());
        if (mCLocationListener != null) {
            mCLocationListener.onLocateSuccess(locationClient, location);
        }
        if (mCallback != null) {
            mCallback.run();
        }
    }

    @Override
    public void onLocateFail(CLocationClient locationClient, CLocationException e) {
        Log.d(TAG, "onLocateFail:" + e.toString());
        if (mCLocationListener != null) {
            mCLocationListener.onLocateFail(locationClient, e);
        }
        if (mCallback != null) {
            mCallback.run();
        }
    }
}
