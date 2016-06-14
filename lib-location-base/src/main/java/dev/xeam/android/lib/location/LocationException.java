package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-6-14.
 */
public class LocationException extends Exception {
    public int errorCode;
    public String errorInfo;

    public LocationException(int errorCode, String errorInfo) {
        super("[" + errorCode + "]" + errorInfo);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    @Override
    public String toString() {
        return "LocationException{" +
                "errorCode=" + errorCode +
                ", errorInfo='" + errorInfo + '\'' +
                '}';
    }
}
