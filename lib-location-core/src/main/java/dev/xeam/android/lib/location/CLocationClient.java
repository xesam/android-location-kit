package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public interface CLocationClient {
    void requestSingleUpdate(CLocationOption option, CLocationListener locationListener);

    void requestLocationUpdates(CLocationOption option, CLocationListener locationListener);

    void removeUpdates();

    void shutdown();

}
