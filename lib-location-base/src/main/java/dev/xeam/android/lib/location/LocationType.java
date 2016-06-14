package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public enum LocationType {
    BAIDU("百度"),
    GAODE("高德"),
    ANDROID("原生");

    public final String desc;

    LocationType(String desc) {
        this.desc = desc;
    }
}
