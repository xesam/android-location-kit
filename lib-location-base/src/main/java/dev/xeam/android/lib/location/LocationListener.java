package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-6-13.
 */
public interface LocationListener {

    void onLocationStart(LocationClient locationClient);

    void onLocationSuccess(LocationClient locationClient, Location location);

    void onLocationFail(LocationClient locationClient, LocationException e);

    void onLocationStop(LocationClient locationClient);
}
