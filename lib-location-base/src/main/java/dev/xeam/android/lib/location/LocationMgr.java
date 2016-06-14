package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-6-13.
 */
public final class LocationMgr {

    private static LocationMgr sLocationMgr;

    public static LocationMgr instance() {
        if (sLocationMgr == null) {
            sLocationMgr = new LocationMgr();
        }
        return sLocationMgr;
    }

    public void startLocation() {

    }

    public void stopLocation() {

    }
}
