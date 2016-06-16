package dev.xesam.libbaidulocatioin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public abstract class BaiduLocationReceiver extends BroadcastReceiver {
    public static final String ACTION_LOCATION = "dev.action.baidu.location";
    public static final String EXTRA_LOCATION = "dev.extra.baidu.location";

    IntentFilter intentFilter;

    public BaiduLocationReceiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_LOCATION);
    }

    public void register(Context context) {
        context.registerReceiver(this, intentFilter);
    }

    public void unRegister(Context context) {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String locationString = intent.getStringExtra(EXTRA_LOCATION);
        onReceiveLocation(locationString);

    }

    protected abstract void onReceiveLocation(String locationString);
}
