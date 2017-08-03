package dev.xeam.android.lib.location.fw;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;

import dev.xeam.android.lib.location.CLocationClient;
import dev.xeam.android.lib.location.CLocationListener;
import dev.xeam.android.lib.location.CLocationOption;
import dev.xeam.android.lib.location.CLocationType;

/**
 * Created by xesamguo@gmail.com on 16-6-14.
 */
// TODO: 17-2-23
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

    public void requestSingleUpdate(CLocationListener locationListener) {
        Criteria criteria = getDefaultOption();
        mLocationManager.requestSingleUpdate(criteria, new NormalLocationListener(this, locationListener), null);
    }

    public void startLocation() {
        Criteria criteria = getDefaultOption();
        mLocationManager.requestLocationUpdates(
                1000,
                0,
                criteria,
                new NormalLocationListener(this),
                null);
    }

    public void stopLocation() {

    }

    @Override
    public CLocationType getType() {
        return new FwType();
    }

    @Override
    public void requestSingleUpdate(CLocationOption option, CLocationListener locationListener) {

    }

    @Override
    public void requestLocationUpdates(CLocationOption option, CLocationListener locationListener) {

    }

    @Override
    public void removeUpdates() {

    }

    @Override
    public void shutdown() {

    }
}
