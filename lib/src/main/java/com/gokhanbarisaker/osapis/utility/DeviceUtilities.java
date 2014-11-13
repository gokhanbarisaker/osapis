package com.gokhanbarisaker.osapis.utility;

import com.gokhanbarisaker.osapis.model.Device;

/**
 * Created by gokhanbarisaker on 22/07/14.
 */
public class DeviceUtilities
{
    /******************************************
     * Variables
     */

    private static Device currentDevice = null;


    /*******************************************
     * Instance providers
     */

    public static synchronized Device getCurrentDevice()
    {
        if (currentDevice == null)
        {
            currentDevice = new Device();
        }

        return currentDevice;
    }
}
