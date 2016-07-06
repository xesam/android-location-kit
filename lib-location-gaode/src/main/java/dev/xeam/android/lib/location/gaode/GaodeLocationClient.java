package dev.xeam.android.lib.location.gaode;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;

import dev.xeam.android.lib.location.CLocationClient;
import dev.xeam.android.lib.location.CLocationListener;
import dev.xeam.android.lib.location.CLocationMode;
import dev.xeam.android.lib.location.CLocationOption;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public class GaodeLocationClient implements CLocationClient {

    private Context mContext;
    private AMapLocationClient mUpdatesClient;
    private AMapLocationClient mSingleUpdateClient;
    private NormalLocationListener mLocationListener;

    public GaodeLocationClient(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private AMapLocationClientOption getDefaultOption() {

        //初始化定位参数
        AMapLocationClientOption option = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否强制刷新WIFI，默认为强制刷新
        option.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        option.setMockEnable(false);
        return option;
    }

    private AMapLocationClientOption parseOption(CLocationOption option) {
        AMapLocationClientOption aOption = new AMapLocationClientOption();
        switch (option.getLocationMode()) {
            case CLocationMode.MODE_BATTERY_SAVING:
                aOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
                break;
            case CLocationMode.MODE_DEVICE_SENSOR:
                aOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
                break;
            default:
                aOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                break;
        }
        if (option.isOnce()) {
            aOption.setOnceLocation(true);
        } else {
            aOption.setInterval(option.getLocationInterval());
        }
        aOption.setWifiActiveScan(true);
        aOption.setMockEnable(false);
        aOption.setNeedAddress(option.isNeedAddress());
        aOption.setHttpTimeOut(option.getTimeout());
        return aOption;
    }

    @Override
    public void requestSingleUpdate(CLocationListener locationListener) {
        final AMapLocationClient aMapLocationClient = new AMapLocationClient(mContext);
        AMapLocationClientOption option = getDefaultOption();
        option.setOnceLocation(true);
        aMapLocationClient.setLocationOption(option);
        aMapLocationClient.setLocationListener(new NormalLocationListener(this, locationListener, new Runnable() {
            @Override
            public void run() {
                aMapLocationClient.onDestroy();
            }
        }));
        aMapLocationClient.startLocation();
    }

    private void requestSingleUpdate(AMapLocationClient client, CLocationOption option, NormalLocationListener listener) {
        AMapLocationClientOption aOption = parseOption(option);
        client.setLocationOption(aOption);
        client.setLocationListener(listener);
        listener.onLocateStart(this);
        client.startLocation();
    }

    public void requestSingleUpdate(CLocationOption option, CLocationListener locationListener) {
        option.setLocationOnce(true);
        if (option.isReuse()) {
            if (mSingleUpdateClient == null) {
                mSingleUpdateClient = new AMapLocationClient(mContext.getApplicationContext());
            }
            NormalLocationListener listener = new NormalLocationListener(this, locationListener);
            requestSingleUpdate(mSingleUpdateClient, option, listener);
        } else {
            final AMapLocationClient aMapLocationClient = new AMapLocationClient(mContext);
            NormalLocationListener listener = new NormalLocationListener(this, locationListener, new Runnable() {
                @Override
                public void run() {
                    aMapLocationClient.onDestroy();
                }
            });
            listener.onLocateStart(this);
            requestSingleUpdate(aMapLocationClient, option, listener);
        }
    }

    public void requestLocationUpdates(CLocationOption option, CLocationListener locationListener) {
        if (mUpdatesClient == null || mLocationListener == null) {
            mUpdatesClient = new AMapLocationClient(mContext);
            mLocationListener = new NormalLocationListener(this);
            mUpdatesClient.setLocationListener(mLocationListener);
        } else {
            mUpdatesClient.stopLocation();
        }

        mLocationListener.attach(locationListener);
        AMapLocationClientOption aOption = parseOption(option);
        mUpdatesClient.setLocationOption(aOption);
        mLocationListener.onLocateStart(this);
        mUpdatesClient.startLocation();
    }

    public void shutdown() {
        if (mSingleUpdateClient != null) {
            mSingleUpdateClient.stopLocation();
            mSingleUpdateClient.onDestroy();
            mSingleUpdateClient = null;
        }

        if (mUpdatesClient != null) {
            mUpdatesClient.stopLocation();
            mUpdatesClient.onDestroy();
            mUpdatesClient = null;
        }
        if (mLocationListener != null) {
            mLocationListener.onLocateStop(this);
            mLocationListener = null;
        }
    }

    @Override
    public void startLocation() {

    }

    @Override
    public void stopLocation() {

    }
}
