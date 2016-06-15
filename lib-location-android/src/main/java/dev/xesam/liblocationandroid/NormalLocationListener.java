package dev.xesam.liblocationandroid;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

import dev.xesam.liblocation.CLocation;
import dev.xesam.liblocation.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
class NormalLocationListener implements LocationListener {

    private AndroidLocationClient mLocationClient;
    private CLocationListener mCLocationListener;
    private Runnable mCallback;

    public NormalLocationListener(AndroidLocationClient locationClient) {
        this(locationClient, null);
    }

    public NormalLocationListener(AndroidLocationClient locationClient, CLocationListener c) {
        this(locationClient, c, null);
    }

    public NormalLocationListener(AndroidLocationClient locationClient, CLocationListener c, Runnable callback) {
        this.mLocationClient = locationClient;
        this.mCLocationListener = c;
        this.mCallback = callback;
    }

    @Override
    public void onLocationChanged(Location rawLocation) {

        CLocation location = new CLocation(
                rawLocation.getTime(),
                -1,
                rawLocation.getAccuracy(),
                rawLocation.getLatitude(),
                rawLocation.getLongitude()
        );

        if (mCLocationListener != null) {
            mCLocationListener.onLocateSuccess(mLocationClient, location);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(rawLocation.getTime()).append(",")
                .append(new Date(rawLocation.getTime()).toString()).append(",")
                .append(rawLocation.getAccuracy()).append(",")
                .append(rawLocation.getProvider()).append(",")
                .append(rawLocation.getLatitude()).append(",")
                .append(rawLocation.getLongitude());
        Log.e(getClass().getSimpleName(), stringBuilder.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
