package com.gokhanbarisaker.osapissample;

import android.content.Context;
import android.os.IBinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gokhanbarisaker.osapis.utility.DeviceUtilities;
import com.gokhanbarisaker.osapissample.listener.OnServiceSubscribe;
import com.gokhanbarisaker.osapissample.service.FlickrService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by gokhanbarisaker on 12/16/14.
 */
public class Application extends android.app.Application
{
    public static final Scheduler SCHEDULER_BACKGROUND = Schedulers.from(DeviceUtilities.getCurrentDevice().getBackgroundThreadExecutor());

    public static Context context = null;
    public static Picasso picasso = null;
    public static final OkHttpClient client = new OkHttpClient();
    public static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        picasso = Picasso.with(getApplicationContext());
    }
}
