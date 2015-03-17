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

    /**
     * Singleton instance
     */
    private static Device currentDevice = null;


    /*******************************************
     * Instance providers
     */

    /**
     * Singleton device instance provider
     *
     * @return Device instance for current process
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
