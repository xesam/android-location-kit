package dev.xesam.liblocation;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
public class CLocation {
    public final long time;
    public final int locateMode;
    public final float accuracy;
    public final double latitude;
    public final double longitude;

    public CLocation(long time, int locateMode, float accuracy, double latitude, double longitude) {
        this.time = time;
        this.locateMode = locateMode;
        this.accuracy = accuracy;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CLocation{" +
                "time=" + time +
                ", locateMode=" + locateMode +
                ", accuracy=" + accuracy +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
