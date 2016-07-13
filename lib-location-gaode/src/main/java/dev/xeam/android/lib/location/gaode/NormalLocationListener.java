package dev.xeam.android.lib.location.gaode;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import dev.xeam.android.lib.location.CAbsLocationListener;
import dev.xeam.android.lib.location.CLocation;
import dev.xeam.android.lib.location.CLocationException;
import dev.xeam.android.lib.location.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
class NormalLocationListener extends CAbsLocationListener<GaodeLocationClient> implements AMapLocationListener {

    public NormalLocationListener(GaodeLocationClient locationClient) {
        this(locationClient, null);
    }

    public NormalLocationListener(GaodeLocationClient locationClient, CLocationListener c) {
        this(locationClient, c, null);
    }

    public NormalLocationListener(GaodeLocationClient locationClient, CLocationListener c, Runnable callback) {
        super(locationClient, c, callback);
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
        } else {
            CLocationException exception = new CLocationException(-1, "onLocationChanged(null)");
            onLocateFail(mLocationClient, exception);
        }
    }
}
