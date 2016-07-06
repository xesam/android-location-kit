package dev.xeam.android.lib.location.gaode;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import dev.xeam.android.lib.location.CLocation;
import dev.xeam.android.lib.location.CLocationClient;
import dev.xeam.android.lib.location.CLocationException;
import dev.xeam.android.lib.location.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
class NormalLocationListener implements AMapLocationListener, CLocationListener {

    public static final String TAG = NormalLocationListener.class.getSimpleName();

    private GaodeLocationClient mLocationClient;
    private CLocationListener mCLocationListener;
    private Runnable mCallback;

    public NormalLocationListener(GaodeLocationClient locationClient) {
        this(locationClient, null);
    }

    public NormalLocationListener(GaodeLocationClient locationClient, CLocationListener c) {
        this(locationClient, c, null);
    }

    public NormalLocationListener(GaodeLocationClient locationClient, CLocationListener c, Runnable callback) {
        this.mLocationClient = locationClient;
        this.mCLocationListener = c;
        this.mCallback = callback;
    }

    public void attach(CLocationListener c) {
        this.mCLocationListener = c;
    }

    @Override
    public void onLocationChanged(AMapLocation rawLocation) {
        if (rawLocation != null) {
            if (rawLocation.getErrorCode() == 0) {

                CLocation location = new CLocation(
                        rawLocation.getTime(),
                        rawLocation.getLocationType(),
                        rawLocation.getAccuracy(),
                        rawLocation.getLatitude(),
                        rawLocation.getLongitude()
                );

                onLocateSuccess(mLocationClient, location);
            } else {
                CLocationException exception = new CLocationException(rawLocation.getErrorCode(), rawLocation.getErrorInfo());
                onLocateFail(mLocationClient, exception);
            }
        }
        if (mCallback != null) {
            mCallback.run();
        }
    }

    @Override
    public void onLocateStart(CLocationClient locationClient) {
        Log.d(TAG, "onLocateStart");
        if (mCLocationListener != null) {
            mCLocationListener.onLocateStart(locationClient);
        }
    }

    @Override
    public void onLocateStop(CLocationClient locationClient) {
        Log.d(TAG, "onLocateStop");
        if (mCLocationListener != null) {
            mCLocationListener.onLocateStop(locationClient);
        }
    }

    @Override
    public void onLocateSuccess(CLocationClient locationClient, CLocation location) {
        Log.d(TAG, location.toString());
        if (mCLocationListener != null) {
            mCLocationListener.onLocateSuccess(locationClient, location);
        }
    }

    @Override
    public void onLocateFail(CLocationClient locationClient, CLocationException e) {
        Log.d(TAG, e.toString());
        if (mCLocationListener != null) {
            mCLocationListener.onLocateFail(locationClient, e);
        }

    }
}
