package com.gokhanbarisaker.osapissample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.gokhanbarisaker.osapissample.service.FlickrService;
import com.squareup.picasso.Picasso;

import rx.Subscriber;

/**
 * Created by gokhanbarisaker on 12/16/14.
 */
public class Application extends android.app.Application
{
    public static FlickrService flickrService;
    public static Picasso picasso = null;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FlickrService.FlickrServiceBinder binder = (FlickrService.FlickrServiceBinder) service;
            flickrService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            flickrService = null;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        bindFlickrService();

        picasso = Picasso.with(getApplicationContext());
    }

    private void bindFlickrService()
    {
        Intent intent = new Intent(this, FlickrService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void unbindFlickrService()
    {
        if (flickrService != null)
        {
            unbindService(connection);
        }
    }
}
