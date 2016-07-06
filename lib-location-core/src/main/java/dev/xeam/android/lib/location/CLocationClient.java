package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public interface CLocationClient {

    void requestSingleUpdate(CLocationListener locationListener);

    void startLocation();

    void stopLocation();
}
