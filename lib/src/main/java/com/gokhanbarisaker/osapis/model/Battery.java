package com.gokhanbarisaker.osapis.model;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by gokhanbarisaker on 22/07/14.
 */
public class Battery
{
    /**
     * Provider for available battery percentage that is broadcast-ed within most recent sticky intent
     *
     * @param context Context instance
     * @return A value between 1 and 0 (1>= percentage >=0), where 1 is full and 0 is empty.
     */
    public float getCurrentLevel(Context context)
    {
        Intent batteryStatus = getRecentBatteryStatusIntent(context);

        float level = (float) batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        float scale = (float) batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return level / scale;
    }

    /**
     * @param context Context instance
     * @return see constants with prefix {@code BatteryManager}.BATTERY_STATUS for return values
     */
    public int getChargeStatus(final Context context)
    {
        Intent batteryStatus = getRecentBatteryStatusIntent(context);
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
    }

    /**
     * @param context Context instance
     * @return see constants with prefix {@code BatteryManager}.BATTERY_HEALTH for return values
     */
    public int getHealthStatus(final Context context)
    {
        Intent batteryStatus = getRecentBatteryStatusIntent(context);
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
    }

    /**
     * @param context Context instance
     * @return see constants with prefix {@code BatteryManager}.BATTERY_PLUGGED for return values
     */
    public int getPlugStatus(final Context context)
    {
        Intent batteryStatus = getRecentBatteryStatusIntent(context);
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
    }

    private Intent getRecentBatteryStatusIntent(final Context context)
    {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        return context.registerReceiver(null, ifilter);
    }
}
