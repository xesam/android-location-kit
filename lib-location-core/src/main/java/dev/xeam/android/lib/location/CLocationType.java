package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public enum CLocationType {
    BAIDU("百度"),
    GAODE("高德"),
    ANDROID("Android");

    public String desc;

    CLocationType(String desc) {
        this.desc = desc;
    }
}
