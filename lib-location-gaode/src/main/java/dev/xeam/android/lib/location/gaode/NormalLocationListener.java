package dev.xeam.android.lib.location.gaode;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import dev.xeam.android.lib.location.CLocation;
import dev.xeam.android.lib.location.CLocationException;
import dev.xeam.android.lib.location.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
class NormalLocationListener implements AMapLocationListener {

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

                if (mCLocationListener != null) {
                    mCLocationListener.onLocateSuccess(mLocationClient, location);
                }
                Log.e("gaode_changed", location.toString());
            } else {
                CLocationException exception = new CLocationException(rawLocation.getErrorCode(), rawLocation.getErrorInfo());
                if (mCLocationListener != null) {
                    mCLocationListener.onLocateFail(mLocationClient, exception);
                }
                Log.e("gaode_error", exception.toString());
            }
        }
        if (mCallback != null) {
            mCallback.run();
        }
    }
}
