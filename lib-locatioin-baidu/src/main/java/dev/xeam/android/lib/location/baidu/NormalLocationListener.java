package dev.xeam.android.lib.location.baidu;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dev.xeam.android.lib.location.CLocation;
import dev.xeam.android.lib.location.CLocationClient;
import dev.xeam.android.lib.location.CLocationException;
import dev.xeam.android.lib.location.CLocationListener;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
class NormalLocationListener implements BDLocationListener, CLocationListener {

    public static final String TAG = NormalLocationListener.class.getSimpleName();

    private int mCount = 0;

    private BaiduLocationClient mLocationClient;
    private CLocationListener mCLocationListener;
    private Runnable mCallback;

    public NormalLocationListener(BaiduLocationClient locationClient) {
        this(locationClient, null);
    }

    public NormalLocationListener(BaiduLocationClient locationClient, CLocationListener c) {
        this(locationClient, c, null);
    }

    public NormalLocationListener(BaiduLocationClient locationClient, CLocationListener c, Runnable callback) {
        this.mLocationClient = locationClient;
        this.mCLocationListener = c;
        this.mCallback = callback;
    }

    public void attach(CLocationListener c) {
        this.mCLocationListener = c;
    }

    @Override
    public void onReceiveLocation(BDLocation rawLocation) {
        mCount++;
        Log.e("onReceiveLocation", hashCode() + ":" + mCount);

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
        StringBuffer sb = new StringBuffer(256);
        sb.append("\ncurrent time : ");
        sb.append(new Date().toString());
        sb.append("\nlcoate time : ");
        sb.append(rawLocation.getTime());
        sb.append("\ngetLocType : ");
//        sb.append(rawLocation.getLocType());
//        sb.append("\nlatitude : ");
//        sb.append(rawLocation.getLatitude());
//        sb.append("\nlontitude : ");
//        sb.append(rawLocation.getLongitude());
//        sb.append("\nradius : ");
//        sb.append(rawLocation.getRadius());
//        if (rawLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//            sb.append("\nspeed : ");
//            sb.append(rawLocation.getSpeed());// 单位：公里每小时
//            sb.append("\nsatellite : ");
//            sb.append(rawLocation.getSatelliteNumber());
//            sb.append("\nheight : ");
//            sb.append(rawLocation.getAltitude());// 单位：米
//            sb.append("\ndirection : ");
//            sb.append(rawLocation.getDirection());// 单位度
//            sb.append("\naddr : ");
//            sb.append(rawLocation.getAddrStr());
//            sb.append("\ndescribe : ");
//            sb.append("gps定位成功");
//
//        } else if (rawLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//            sb.append("\naddr : ");
//            sb.append(rawLocation.getAddrStr());
//            //运营商信息
//            sb.append("\noperationers : ");
//            sb.append(rawLocation.getOperators());
//            sb.append("\ndescribe : ");
//            sb.append("网络定位成功");
//        } else if (rawLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//            sb.append("\ndescribe : ");
//            sb.append("离线定位成功，离线定位结果也是有效的");
//        } else if (rawLocation.getLocType() == BDLocation.TypeServerError) {
//            sb.append("\ndescribe : ");
//            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//        } else if (rawLocation.getLocType() == BDLocation.TypeNetWorkException) {
//            sb.append("\ndescribe : ");
//            sb.append("网络不同导致定位失败，请检查网络是否通畅");
//        } else if (rawLocation.getLocType() == BDLocation.TypeCriteriaException) {
//            sb.append("\ndescribe : ");
//            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//        }
//        sb.append("\nlocationdescribe : ");
//        sb.append(rawLocation.getLocationDescribe());// 位置语义化信息
        Log.e("BaiduLocationApiDem", sb.toString());
        if (mCallback != null) {
            mCallback.run();
        }
    }

    @Override
    public void onLocateStart(CLocationClient locationClient) {
        Log.d(TAG, "onLocateStart");
        if (mCLocationListener != null) {
            mCLocationListener.onLocateStart(locationClient);
        }
    }

    @Override
    public void onLocateSuccess(CLocationClient locationClient, CLocation location) {
        Log.d(TAG, location.toString());
        if (mCLocationListener != null) {
            mCLocationListener.onLocateSuccess(locationClient, location);
        }
    }

    @Override
    public void onLocateFail(CLocationClient locationClient, CLocationException e) {
        Log.d(TAG, e.toString());
        if (mCLocationListener != null) {
            mCLocationListener.onLocateFail(locationClient, e);
        }

    }
}
