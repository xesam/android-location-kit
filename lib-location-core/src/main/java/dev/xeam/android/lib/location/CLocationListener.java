package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-6-14.
 */
public interface CLocationListener {

    void onLocateStart(CLocationClient locationClient);

    void onLocateSuccess(CLocationClient locationClient, CLocation location);

    void onLocateFail(CLocationClient locationClient, CLocationException e);

}
