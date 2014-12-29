package com.gokhanbarisaker.osapissample.listener;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by gokhanbarisaker on 12/28/14.
 */
public abstract class OnServiceSubscribe<T extends Service> implements Observable.OnSubscribe<T>
{
    private Context context = null;
    private Class serviceClass = null;

    protected OnServiceSubscribe(Context context, Class serviceClass)
    {
        this.context = context;
        this.serviceClass = serviceClass;
    }

    @Override
    public void call(final Subscriber<? super T> subscriber)
    {
        Log.e(getClass().getSimpleName(), "Subscription received. Binding " + serviceClass.getSimpleName() + " service...");

        bindService(context, serviceClass, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                T flickrService = getService(service);

                Log.e(getClass().getSimpleName(), "Service connected with instance: " + flickrService);

                subscriber.onNext(flickrService);
                subscriber.onCompleted();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                Log.e(getClass().getSimpleName(), "Service connection failed! Will retry...");

                bindService(context, serviceClass, this);
            }
        });
    }

    private void bindService(Context context, Class serviceClass, ServiceConnection connection)
    {
        Intent intent = new Intent(context, serviceClass);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    protected abstract T getService(IBinder binder);
}