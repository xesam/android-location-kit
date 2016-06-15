package dev.xesam.liblocationandroid;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;

import dev.xesam.liblocation.CLocationClient;
import dev.xesam.liblocation.CLocationConstant;
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

    private Criteria getDefaultOption() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //精度高
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT); //电量消耗
        criteria.setAltitudeRequired(false); //不需要海拔
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true); //不需要费用
        return criteria;
    }

    @Override
    public void requestSingleUpdate(CLocationListener locationListener) {
        Criteria criteria = getDefaultOption();
        mLocationManager.requestSingleUpdate(criteria, new NormalLocationListener(this, locationListener), null);
    }

    @Override
    public void startLocation() {
        Criteria criteria = getDefaultOption();
        mLocationManager.requestLocationUpdates(
                CLocationConstant.LOCATION_INTERVAL,
                0,
                criteria,
                new NormalLocationListener(this),
                null);
    }

    @Override
    public void stopLocation() {

    }
}
