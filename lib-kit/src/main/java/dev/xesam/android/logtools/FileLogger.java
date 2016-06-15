package dev.xesam.android.logtools;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志控制台
 * <p>
 * Created by xesamguo@gmail.com on 16-3-8.
 */
public final class FileLogger {
    private static final int MSG_1 = 0X1;
    private static Handler writerHandler;
    private static Context writerContext;
    private static volatile FileWriter writer;

    private static boolean mEnable = false;

    private static HandlerThread handlerThread;

    synchronized public static void init(Context context) {
        if (mEnable) {
            throw new RuntimeException("init twice");
        }

        mEnable = true;
        writerContext = context.getApplicationContext();
        handlerThread = new HandlerThread("FileLogger") {
            @Override
            protected void onLooperPrepared() {
                super.onLooperPrepared();
                writerHandler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        switch (msg.what) {
                            case MSG_1: {
                                if (writer != null) {
                                    try {
                                        writer.write(msg.obj.toString() + "\n");
                                        writer.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                break;
                            }
                            default: {

                            }
                        }
                        return true;
                    }
                });
            }
        };
        handlerThread.start();
    }

    synchronized public static void recycle() {
        mEnable = false;
        writerContext = null;

        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer = null;

        if (handlerThread != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                handlerThread.quitSafely();
            } else {
                handlerThread.quit();
            }
        }
        handlerThread = null;
    }

    private static void createNewLog(File dir, String filename) {

        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            File file = new File(dir.getAbsolutePath() + File.separator + filename);
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新一轮日志记录
     */
    public synchronized static void newRound() {
        String dateTime = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA)).format(new Date());
        String filename = String.format(Locale.CHINA, "log-%s.txt", dateTime);
        newRound(filename);
    }

    /**
     * 新一轮日志记录
     */
    public synchronized static void newRound(String filename) {
        File dir = writerContext.getExternalFilesDir(null);
        if (dir != null) {
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new RuntimeException("create log file fail");
                }
            }
        }
        newRound(dir, filename);
    }

    /**
     * 新一轮日志记录
     */
    public synchronized static void newRound(File dir, String filename) {
        if (!mEnable) {
            return;
        }

        createNewLog(dir, filename);
    }

    public static void log(String data) {
        if (!mEnable) {
            return;
        }

        if (writer == null) {
            newRound();
        }

        if (writerHandler != null) {
            writerHandler.obtainMessage(MSG_1, data).sendToTarget();
        }
    }

}
