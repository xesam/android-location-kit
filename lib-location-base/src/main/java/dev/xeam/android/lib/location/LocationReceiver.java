package dev.xeam.android.lib.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public abstract class LocationReceiver extends BroadcastReceiver {
    public static final String ACTION_LOCATION = "dev.xesam.action.location";
    public static final String EXTRA_LOCATION = "dev.xesam.extra.location";

    private IntentFilter intentFilter;

    public LocationReceiver() {
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

    }

    protected abstract void onReceiveLocation(Context context, Location location);
}
