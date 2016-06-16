package dev.xesam.libbaidulocatioin;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import dev.xesam.liblocation.CLocationClient;
import dev.xesam.liblocation.CLocationConstant;
import dev.xesam.liblocation.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public class BaiduLocationClient implements CLocationClient {
    public com.baidu.location.LocationClient mLocationClient = null;

    private Context mContext;

    public BaiduLocationClient(Context context) {
        mContext = context.getApplicationContext();
        mLocationClient = new LocationClient(mContext);
        mLocationClient.registerLocationListener(new NormalLocationListener(this));
        mLocationClient.setLocOption(getDefaultOption());
    }

    private LocationClientOption getDefaultOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(CLocationConstant.LOCATION_INTERVAL);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        return option;
    }

    @Override
    public void requestSingleUpdate(CLocationListener locationListener) {
        final LocationClient bdLocationClient = new LocationClient(mContext);     //声明LocationClient类
        bdLocationClient.registerLocationListener(new NormalLocationListener(this, locationListener, new Runnable() {
            @Override
            public void run() {
                bdLocationClient.stop();
            }
        }));
        LocationClientOption option = getDefaultOption();
        option.setScanSpan(0);
        bdLocationClient.setLocOption(option);
        bdLocationClient.start();
    }

    @Override
    public void startLocation() {
        mLocationClient.start();
    }

    @Override
    public void stopLocation() {
        mLocationClient.stop();
    }
}
