package com.gokhanbarisaker.osapissample;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.gokhanbarisaker.osapis.utility.DeviceUtilities;
import com.gokhanbarisaker.osapissample.service.FlickrService;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * Created by gokhanbarisaker on 12/16/14.
 */
public class Application extends android.app.Application
{
    public static final Scheduler SCHEDULER_BACKGROUND = Schedulers.from(DeviceUtilities.getCurrentDevice().getBackgroundThreadExecutor());

    public static Context context = null;
    public static Picasso picasso = null;
    public static Observer flickrServiceObserver = null;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        picasso = Picasso.with(getApplicationContext());
//        getFlickrService().subscribe(new Action1<FlickrService>() {
//            @Override public void call(FlickrService flickrService) {}});
//
//        getFlickrService().subscribe(new Action1<FlickrService>() {
//            @Override public void call(FlickrService flickrService) {}});
//
//        getFlickrService().subscribe(new Action1<FlickrService>() {
//            @Override public void call(FlickrService flickrService) {}});
    }

    public static synchronized Observable<FlickrService> getFlickrService()
    {
        return Observable.create(new OnFlickrServiceSubscribe());
    }

    private static class OnFlickrServiceSubscribe implements Observable.OnSubscribe<FlickrService>
    {
        @Override
        public void call(final Subscriber<? super FlickrService> subscriber)
        {
            Log.e("Flickr", "Subscription received. Binding service...");

            bindFlickrService(new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {

                    FlickrService.FlickrServiceBinder binder = (FlickrService.FlickrServiceBinder) service;
                    FlickrService flickrService = binder.getService();

                    Log.e("Flickr", "Service connected with instance: " + flickrService);

                    subscriber.onNext(flickrService);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                    Log.e("Flickr", "Service connection failed! Will retry...");

                    bindFlickrService(this);
                }
            });
        }

        private void bindFlickrService(ServiceConnection connection)
        {
            Intent intent = new Intent(Application.context, FlickrService.class);
            Application.context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }


//    private static class OnFlickrServiceSubscribe implements Observable.OnSubscribe<FlickrService> {
//
//        public FlickrService flickrService;
//        private ServiceConnection connection = new FlickrServiceConnection();
//
//        private void bindFlickrService()
//        {
//            Intent intent = new Intent(Application.this, FlickrService.class);
//            bindService(intent, connection, Context.BIND_AUTO_CREATE);
//        }
//
//        private void unbindFlickrService()
//        {
//            if (flickrService != null)
//            {
//                unbindService(connection);
//            }
//        }
//
//        class FlickrServiceConnection implements ServiceConnection
//        {
//            Subscriber subscriber;
//
//            FlickrServiceConnection(Subscriber subscriber)
//            {
//                this.subscriber = subscriber;
//            }
//
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                FlickrService.FlickrServiceBinder binder = (FlickrService.FlickrServiceBinder) service;
//                flickrService = binder.getService();
//
//                subscriber.onNext(flickrService);
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//                flickrService = null;
//
//                // Try reconnection
//                // bindFlickrService();
//            }
//        }
//    }
}
