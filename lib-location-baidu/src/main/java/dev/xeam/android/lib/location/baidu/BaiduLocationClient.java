package dev.xeam.android.lib.location.baidu;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import dev.xeam.android.lib.location.CLocationClient;
import dev.xeam.android.lib.location.CLocationListener;
import dev.xeam.android.lib.location.CLocationMode;
import dev.xeam.android.lib.location.CLocationOption;
import dev.xeam.android.lib.location.CLocationType;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public class BaiduLocationClient implements CLocationClient {

    private Context mContext;
    private LocationClient mUpdatesClient;
    private LocationClient mSingleUpdateClient;
    private NormalLocationListener mLocationListener;

    private String mCoorType = "bd09ll";

    public BaiduLocationClient(Context context) {
        mContext = context.getApplicationContext();
    }

    public BaiduLocationClient(Context context, String coorType) {
        this(context);
        mCoorType = coorType;
    }

    private LocationClientOption parseOption(CLocationOption option) {
        LocationClientOption bOption = new LocationClientOption();

        switch (option.getLocationMode()) {
            case CLocationMode.MODE_BATTERY_SAVING:
                bOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
                break;
            case CLocationMode.MODE_DEVICE_SENSOR:
                bOption.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
                break;
            default:
                bOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
                break;
        }

        bOption.setCoorType(mCoorType);
        if (option.isOnce()) {
            bOption.setScanSpan(0);
        } else {
            bOption.setScanSpan((int) option.getLocationInterval());
        }
        bOption.setIsNeedAddress(option.isNeedAddress());
        bOption.setOpenGps(true);
        bOption.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        bOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        return bOption;
    }

    private NormalLocationListener mSingleListener;

    @Override
    public CLocationType getType() {
        return new BaiduType();
    }

    @Override
    public void requestSingleUpdate(CLocationOption option, CLocationListener locationListener) {
        option.setLocationOnce(true);
        final boolean isFirst = mSingleUpdateClient == null || mSingleListener == null;
        if (isFirst) {
            mSingleUpdateClient = new LocationClient(mContext);
            mSingleListener = new NormalLocationListener(this, null, new Runnable() {
                @Override
                public void run() {
                    mSingleListener.detach();
                }
            });
            mSingleUpdateClient.registerLocationListener(mSingleListener);
        }

        mSingleListener.attach(locationListener);
        mSingleUpdateClient.setLocOption(parseOption(option));
        if (isFirst) {
            mSingleUpdateClient.start();
        } else {
            mSingleUpdateClient.requestLocation();
        }
    }

    public void removeSingle() {

        if (mSingleUpdateClient != null) {
            mSingleUpdateClient.unRegisterLocationListener(mSingleListener);
            mSingleUpdateClient = null;
            mSingleListener = null;
        }
    }

    @Override
    public void requestLocationUpdates(CLocationOption option, CLocationListener locationListener) {

        // timer update is conflict with single update
        removeSingle();

        if (mUpdatesClient == null || mLocationListener == null) {
            mUpdatesClient = new LocationClient(mContext);
            mLocationListener = new NormalLocationListener(this, locationListener);
            mUpdatesClient.registerLocationListener(mLocationListener);
        } else {
            mUpdatesClient.unRegisterLocationListener(mLocationListener);
            mUpdatesClient.stop();
        }

        mUpdatesClient.setLocOption(parseOption(option));
        mUpdatesClient.start();
    }

    @Override
    public void removeUpdates() {
        if (mUpdatesClient != null) {
            mUpdatesClient.stop();
            mUpdatesClient.unRegisterLocationListener(mLocationListener);
            mUpdatesClient = null;
        }
    }

    @Override
    public void shutdown() {
        if (mSingleUpdateClient != null) {
            mSingleUpdateClient.stop();
            mSingleUpdateClient = null;
        }
        if (mUpdatesClient != null) {
            mUpdatesClient.stop();
            mUpdatesClient.unRegisterLocationListener(mLocationListener);
            mUpdatesClient = null;
        }
    }

}
