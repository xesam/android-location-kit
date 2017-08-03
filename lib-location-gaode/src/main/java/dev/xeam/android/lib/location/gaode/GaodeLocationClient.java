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
    private NormalLocationListener mSingleLocationListener;
    private NormalLocationListener mUpdatesLocationListener;

    public GaodeLocationClient(Context context) {
        this.mContext = context.getApplicationContext();
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
        aOption.setMockEnable(false);
        aOption.setNeedAddress(option.isNeedAddress());
        aOption.setHttpTimeOut(option.getTimeout());
        return aOption;
    }

    private void requestSingleUpdate(AMapLocationClient client, CLocationOption option, NormalLocationListener listener) {
        AMapLocationClientOption aOption = parseOption(option);
        client.setLocationOption(aOption);
        listener.onLocateStart(this);
        client.startLocation();
    }

    @Override
    public void requestSingleUpdate(CLocationOption option, CLocationListener locationListener) {
        option.setLocationOnce(true);
        if (option.isReuse()) {
            if (mSingleUpdateClient == null) {
                mSingleUpdateClient = new AMapLocationClient(mContext.getApplicationContext());
                mSingleLocationListener = new NormalLocationListener(this);
                mSingleUpdateClient.setLocationListener(mSingleLocationListener);
            }
            mSingleLocationListener.attach(locationListener);
            requestSingleUpdate(mSingleUpdateClient, option, mSingleLocationListener);
        } else {
            final AMapLocationClient tmp = new AMapLocationClient(mContext);
            NormalLocationListener listener = new NormalLocationListener(this, locationListener, new Runnable() {
                @Override
                public void run() {
                    tmp.onDestroy();
                }
            });
            tmp.setLocationListener(listener);
            requestSingleUpdate(tmp, option, listener);
        }
    }

    @Override
    public void requestLocationUpdates(CLocationOption option, CLocationListener locationListener) {
        if (mUpdatesClient == null || mUpdatesLocationListener == null) {
            mUpdatesClient = new AMapLocationClient(mContext);
            mUpdatesLocationListener = new NormalLocationListener(this);
            mUpdatesClient.setLocationListener(mUpdatesLocationListener);
        } else {
            mUpdatesClient.stopLocation();
        }

        mUpdatesLocationListener.attach(locationListener);
        AMapLocationClientOption aOption = parseOption(option);
        mUpdatesClient.setLocationOption(aOption);
        mUpdatesLocationListener.onLocateStart(this);
        mUpdatesClient.startLocation();
    }

    @Override
    public void removeUpdates() {
        if (mUpdatesClient != null) {
            mUpdatesClient.stopLocation();
            mUpdatesClient.onDestroy();
            mUpdatesClient = null;
        }
        if (mUpdatesLocationListener != null) {
            mUpdatesLocationListener.detach();
            mUpdatesLocationListener = null;
        }
    }

    @Override
    public void shutdown() {
        if (mSingleUpdateClient != null) {
            mSingleUpdateClient.stopLocation();
            mSingleUpdateClient.onDestroy();
            mSingleUpdateClient = null;
        }
        if (mSingleLocationListener != null) {
            mSingleLocationListener.detach();
            mSingleLocationListener = null;
        }
        if (mUpdatesClient != null) {
            mUpdatesClient.stopLocation();
            mUpdatesClient.onDestroy();
            mUpdatesClient = null;
        }
        if (mUpdatesLocationListener != null) {
            mUpdatesLocationListener.detach();
            mUpdatesLocationListener = null;
        }
    }
}
