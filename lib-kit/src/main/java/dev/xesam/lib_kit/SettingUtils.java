package dev.xesam.lib_kit;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Settings;

/**
 * 设置
 * <p>
 * Created by xesamguo@gmail.com on 11/17/15.
 */
public final class SettingUtils {
    /**
     * GPS是否已打开
     */
    public static boolean isGpsOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 跳转至Gps设置
     */
    public static void toGpsSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * WIFI是否已打开
     */
    public static boolean isWifiEnabled(Context context) {
        boolean enabled = false;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            enabled = wifiManager.isWifiEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enabled;
    }

    /**
     * 跳转至wifi设置
     */
    public static void toWifiSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 设置wifi
     */
    public static boolean setWifiEnable(Context context, boolean enable) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            return wifiManager.setWifiEnabled(enable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean enableWifi(Context context) {
        return setWifiEnable(context, true);
    }

    public static boolean disableWifi(Context context) {
        return setWifiEnable(context, false);
    }

    /**
     * 设置屏幕亮度
     */
    public static int getBrightness(Context context) {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }


    public static final int MAX_BRIGHTNESS = 255;
    public static final int MIN_BRIGHTNESS = 0;

    /**
     * 设置屏幕亮度
     */
    public static void setBrightness(Context context, int brightness) {
        try {
            if (brightness < MIN_BRIGHTNESS) {
                brightness = MIN_BRIGHTNESS;
            }
            if (brightness > MAX_BRIGHTNESS) {
                brightness = MAX_BRIGHTNESS;
            }
            ContentResolver resolver = context.getContentResolver();
            Uri uri = Settings.System
                    .getUriFor(Settings.System.SCREEN_BRIGHTNESS);
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS,
                    brightness);
            resolver.notifyChange(uri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取屏幕亮度
     */
    public static int getBrightnessMode(Context context) {
        int brightnessMode = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
        try {
            brightnessMode = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brightnessMode;
    }

    /**
     * 设置屏幕亮度模式
     * <p>
     * 1 auto, 0 manual
     */
    public static void setBrightnessMode(Context context, int brightnessMode) {
        try {
            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE, brightnessMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
