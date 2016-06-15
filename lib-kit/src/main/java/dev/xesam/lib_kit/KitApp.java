package dev.xesam.lib_kit;

import android.app.Application;

import dev.xesam.android.logtools.CrashLogger;
import dev.xesam.android.logtools.FileLogger;
import dev.xesam.android.logtools.L;

/**
 * Created by xesamguo@gmail.com on 16-3-8.
 */
public class KitApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        L.enable(true);
        CrashLogger.register(this);
        FileLogger.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        FileLogger.recycle();
    }
}
