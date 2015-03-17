package com.gokhanbarisaker.osapis.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.*;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.webkit.WebView;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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
        String deviceName = null;

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer))
        {
            deviceName = StringUtils.capitalize(model);
        }
        else
        {
            deviceName = StringUtils.capitalize(manufacturer) + " " + model;
        }

        return deviceName;
    }

    /**
     * @return {@code DisplayMetrics} instance for current system.
     */
    public Display getDisplay()
    {
        return new Display();
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

    public Keyboard getKeyboard()
    {
        return new Keyboard();
    }

    /**
     * TODO: Re-decide if we got to keep the method here, or not!
     * @param priority for running thread instances within executor.
     * @return Executor instance optimized for current device processors.
     */
    public Executor getOptimalThreadExecutor(final int priority)
    {
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        final ThreadFactory backgroundThreadFactory = new BasicThreadFactory.Builder()
                .priority(priority)
                .build();

        return Executors.newFixedThreadPool(availableProcessors, backgroundThreadFactory);
    }

    /**
     *
     * @return Executor instance, with background priority, optimized for current device processors.
     */
    public Executor getBackgroundThreadExecutor()
    {
        return getOptimalThreadExecutor(Process.THREAD_PRIORITY_BACKGROUND);
    }



    /**
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.43">W3 compatible</a>
     * a user agent string.
     *
     * @param label Build label
     * @param version Build version
     * @return a unique User-Agent http header value for current application.
     */
    public synchronized String getUserAgent(String label, String version)
    {
        return new StringBuffer()
                .append(label)
                .append("/")
                .append(version)
                .append(" (")
                .append(getName())
                .append("; Android ")
                .append(Build.VERSION.SDK_INT)
                .append("; Scale/")
                .append(String.format("%.2f", getDisplay().getMetrics().density))
                .append(")")
                .toString();
    }

    /**
     * Accessor for system-wide, {@code WebView} generated, user agent string.
     *
     * @param context Context
     * @return {@code WebView} generated user agent string.
     */
    public String getWebViewUserAgent(Context context)
    {
        if (Looper.myLooper() != Looper.getMainLooper())
        {
            throw new IllegalStateException("WebView user-agent must be extracted from main thread!");
        }

        return (context == null)?(null):(new WebView(context).getSettings().getUserAgentString());
    }
}
