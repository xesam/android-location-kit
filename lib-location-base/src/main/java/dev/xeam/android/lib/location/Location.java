package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-6-13.
 */
public class Location {
    public final long locationTime;
    public final int locationType;
    public final float accuracy;
    public final double latitude;
    public final double longitude;

    public Location(long locationTime, int locationType, float accuracy, double latitude, double longitude) {
        this.locationTime = locationTime;
        this.locationType = locationType;
        this.accuracy = accuracy;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationTime=" + locationTime +
                ", locationType=" + locationType +
                ", accuracy=" + accuracy +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
