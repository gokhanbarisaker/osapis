package com.gokhanbarisaker.osapis.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.gokhanbarisaker.osapis.utility.StringHelper;

import java.io.File;

/**
 * Created by gokhanbarisaker on 18/07/14.
 */
public class Device
{
    /********************************************
     * Accessors
     */

    /**
     * @return Device name, that defined in Build environment
     */
    public String getName()
    {
        return getName(new StringHelper());
    }

    /**
     * @param helper Utility for string operations
     * @return Device name, that defined in Build environment
     */
    public String getName(StringHelper helper)
    {
        String deviceName = null;

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer))
        {
            deviceName = helper.capitalize(model);
        }
        else
        {
            deviceName = helper.capitalize(manufacturer) + " " + model;
        }

        return deviceName;
    }

    /**
     * @return {@code DisplayMetrics} instance for current system.
     */
    public DisplayMetrics getDisplayMetrics()
    {
        return Resources.getSystem().getDisplayMetrics();
    }

    /**
     * @return Battery
     */
    public Battery getBattery()
    {
        return new Battery();
    }

    public Storage getInternalStorage()
    {
        File mountPoint = Environment.getDataDirectory();
        return new Storage(mountPoint);
    }

    public Storage getExternalStorage()
    {
        File mountPoint = Environment.getExternalStorageDirectory();
        return new Storage(mountPoint);
    }

    public boolean isAppInstalled(Context context, String packageName)
    {
        PackageManager pm = context.getPackageManager();
        boolean appInstalled = false;
        try
        {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            appInstalled = false;
        }
        return appInstalled;
    }

    public String getCarrierName(Context context)
    {
        TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getSimOperator();
        return carrierName.toLowerCase();
    }
}
