package dev.xeam.android.lib.location;

/**
 * Created by xesamguo@gmail.com on 16-6-15.
 */
public class CLocationException extends Exception {
    public final int errorCode;
    public final String errorInfo;

    public CLocationException(int errorCode, String errorInfo) {
        super(errorInfo);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    @Override
    public String toString() {
        return "CLocationException{" +
                "errorCode=" + errorCode +
                ", errorInfo='" + errorInfo + '\'' +
                '}';
    }
}
