package dev.xesam.liblocationandroid;

import android.content.Context;
import android.location.LocationManager;

import dev.xesam.liblocation.CLocationClient;
import dev.xesam.liblocation.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-6-14.
 */
public class AndroidLocationClient implements CLocationClient {

    private Context mContext;
    private LocationManager mLocationManager;

    public AndroidLocationClient(Context context) {
        this.mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void requestSingleUpdate(CLocationListener locationListener) {

    }

    @Override
    public void startLocation() {
//        mLocationManager.requestLocationUpdates();
//        mLocationManager.requestSingleUpdate();
    }

    @Override
    public void stopLocation() {

    }
}
