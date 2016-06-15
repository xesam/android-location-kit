package dev.xesam.libgaodelocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by xesamguo@gmail.com on 16-3-14.
 */
public abstract class GaodeLocationReceiver extends BroadcastReceiver {
    public static final String ACTION_LOCATION = "dev.action.gaode.location";
    public static final String EXTRA_LOCATION = "dev.extra.gaode.location";

    IntentFilter intentFilter;

    public GaodeLocationReceiver() {
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
