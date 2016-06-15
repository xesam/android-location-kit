package dev.xesam.libgaodelocation;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;

import dev.xesam.liblocation.CLocationClient;
import dev.xesam.liblocation.CLocationConstant;
import dev.xesam.liblocation.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public class GaodeLocationClient implements CLocationClient {

    private Context mContext;
    public AMapLocationClient mLocationClient = null;
    private NormalLocationListener mNormalLocationListener;

    public GaodeLocationClient(Context context) {
        this.mContext = context.getApplicationContext();
        mNormalLocationListener = new NormalLocationListener(this);
        init(context);
    }

    public void init(Context context) {
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        mLocationClient.setLocationListener(mNormalLocationListener);
        mLocationClient.setLocationOption(getDefaultOption());
    }

    private AMapLocationClientOption getDefaultOption() {
        //初始化定位参数
        AMapLocationClientOption option = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否只定位一次,默认为false
        option.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        option.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        option.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        option.setInterval(CLocationConstant.LOCATION_INTERVAL);
        return option;
    }

    @Override
    public void requestSingleUpdate(CLocationListener locationListener) {
        final AMapLocationClient aMapLocationClient = new AMapLocationClient(mContext.getApplicationContext());
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

    @Override
    public void startLocation() {
        mLocationClient.startLocation();
    }

    @Override
    public void stopLocation() {
        mLocationClient.stopLocation();
    }
}
