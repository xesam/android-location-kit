package dev.xesam.libgaodelocation;

import android.content.Context;
import android.content.Intent;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Locale;

import dev.xesam.android.logtools.L;
import dev.xesam.liblocation.CLocationClient;
import dev.xesam.liblocation.CLocationConstant;
import dev.xesam.liblocation.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public class GaodeLocationClient implements CLocationClient {
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {

                    String dateString = new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(amapLocation.getTime());

                    StringBuffer sb = new StringBuffer()
                            .append(amapLocation.getLocationType()).append(",")
                            .append(dateString).append(",")
                            .append(amapLocation.getLatitude()).append(",")
                            .append(amapLocation.getLongitude());

                    Intent intent = new Intent();
                    intent.setAction(GaodeLocationReceiver.ACTION_LOCATION);
                    intent.putExtra(GaodeLocationReceiver.EXTRA_LOCATION, sb.toString());
                    mContext.sendBroadcast(intent);
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    L.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    private Context mContext;

    public void onCreate(Context context) {
        mContext = context.getApplicationContext();
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);
        initLocation();
    }

    private void initLocation() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(CLocationConstant.LOCATION_INTERVAL);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    @Override
    public void requestSingleUpdate(CLocationListener locationListener) {

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
