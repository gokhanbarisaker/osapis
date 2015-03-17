package com.gokhanbarisaker.osapissample.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class FlickrService extends Service {

    private final IBinder binder = new FlickrServiceBinder();

    public FlickrService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class FlickrServiceBinder extends Binder {
        public FlickrService getService() {
            // Return this instance of LocalService so clients can call public methods
            return FlickrService.this;
        }
    }
}
