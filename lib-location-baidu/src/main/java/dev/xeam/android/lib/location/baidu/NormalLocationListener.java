package dev.xeam.android.lib.location.baidu;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dev.xeam.android.lib.location.CAbsLocationListener;
import dev.xeam.android.lib.location.CLocation;
import dev.xeam.android.lib.location.CLocationException;
import dev.xeam.android.lib.location.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
class NormalLocationListener extends CAbsLocationListener<BaiduLocationClient> implements BDLocationListener, CLocationListener {

    private int mCount = 0;

    public NormalLocationListener(BaiduLocationClient locationClient) {
        this(locationClient, null);
    }

    public NormalLocationListener(BaiduLocationClient locationClient, CLocationListener c) {
        this(locationClient, c, null);
    }

    public NormalLocationListener(BaiduLocationClient locationClient, CLocationListener c, Runnable callback) {
        super(locationClient, c, callback);
    }

    @Override
    public void onReceiveLocation(BDLocation rawLocation) {
        mCount++;
        Log.d("onReceiveLocation", hashCode() + ":" + mCount);

        final int locType = rawLocation.getLocType();

        if (locType == BDLocation.TypeGpsLocation
//                || locType == BDLocation.TypeOffLineLocation
                || locType == BDLocation.TypeNetWorkLocation) {

            String timeString = rawLocation.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            long timestamp = -1;
            try {
                Date d = dateFormat.parse(timeString);
                timestamp = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CLocation location = new CLocation(timestamp, locType, rawLocation.getRadius(), rawLocation.getLatitude(), rawLocation.getLongitude());
            onLocateSuccess(mLocationClient, location);
        } else {
            CLocationException exception = new CLocationException(locType, rawLocation.getLocationDescribe());
            onLocateFail(mLocationClient, exception);
        }
    }
}
