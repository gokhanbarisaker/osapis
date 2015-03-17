package com.gokhanbarisaker.osapis.model;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by gokhanbarisaker on 12/30/14.
 */
public class Display
{
    /**
     * @return {@code DisplayMetrics} instance for current system.
     */
    public DisplayMetrics getMetrics()
    {
        return Resources.getSystem().getDisplayMetrics();
    }

    /**
     * @return Status bar
     */
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getLayout()
    {
        return Resources.getSystem().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }
}
