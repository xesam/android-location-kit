package dev.xesam.android.logtools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * write crash log to file
 */
public class CrashLogger implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private Context mContext;

    private CrashLogger(Context context) {
        mContext = context.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static void register(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new CrashLogger(context));
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(thread, ex);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    private boolean handleException(Thread thread, Throwable ex) {
        if (ex == null) {
            return false;
        }

        String dateTime = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA)).format(new Date());
        String crashLogName = String.format("crash.%s.txt", dateTime);

        String appInfo = collectAppInfo(dateTime);
        String crashInfo = collectCrashInfo(ex);
        write(crashLogName, appInfo, crashInfo);
        return true;
    }

    private String collectAppInfo(String dateTime) {
        StringBuilder sb = new StringBuilder()
                .append("dateTime : ").append(dateTime).append("\n");
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "unknown" : pi.versionName;
                int versionCode = pi.versionCode;
                sb.append("versionName : ").append(versionName).append("\n")
                        .append("versionCode : ").append(versionCode + "").append("\n");
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return sb.append("\n").toString();
    }

    /**
     * 保存错误信息到文件中
     */
    private String collectCrashInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }

    private void write(String crashLogName, String appInfo, String crashInfo) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = mContext.getExternalFilesDir(null);
                if (file == null) {
                    return;
                }
                if (!file.exists()) {
                    file.mkdirs();
                }
                File logFile = new File(file.getAbsolutePath() + File.separator + crashLogName);
                FileOutputStream fos = new FileOutputStream(logFile);
                fos.write(appInfo.getBytes());
                fos.write(crashInfo.getBytes());
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}