package dev.xesam.libgaodelocation;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import dev.xesam.android.logtools.L;
import dev.xesam.liblocation.CLocation;
import dev.xesam.liblocation.CLocationException;
import dev.xesam.liblocation.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
class NormalLocationListener implements AMapLocationListener {

    private GaodeLocationClient mLocationClient;
    private CLocationListener mCLocationListener;

    public NormalLocationListener(GaodeLocationClient locationClient) {
        this(locationClient, null);
    }

    public NormalLocationListener(GaodeLocationClient locationClient, CLocationListener c) {
        this.mLocationClient = locationClient;
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

                if (mCLocationListener != null) {
                    mCLocationListener.onLocateSuccess(mLocationClient, location);
                }
                Log.e("gaode_changed", location.toString());
            } else {
                CLocationException exception = new CLocationException(rawLocation.getErrorCode(), rawLocation.getErrorInfo());
                if (mCLocationListener != null) {
                    mCLocationListener.onLocateFail(mLocationClient, exception);
                }
                L.e("gaode_error", exception.toString());
            }
        }
    }
}
