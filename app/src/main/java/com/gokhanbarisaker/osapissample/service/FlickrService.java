package com.gokhanbarisaker.osapissample.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gokhanbarisaker.osapissample.Application;
import com.gokhanbarisaker.osapissample.model.Photo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.observables.ViewObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FlickrService extends Service {

    private static final String API_KEY = "f01e745e13b4499e737599d06d839315";

    private static final String KEY_JSON_PHOTOS = "photos";
    private static final String KEY_JSON_PHOTO = "photo";

    public static final String METHOD_INTERESTINGNESS_LIST = "flickr.interestingness.getList";

    private final IBinder binder = new FlickrServiceBinder();
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private static Observable<List<Photo>> photoListObservable = null;

//    private final Observable observable = null;

    public FlickrService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public synchronized Observable<List<Photo>> fetchPhotoList()
    {
        return fetchPhotoList(Application.SCHEDULER_BACKGROUND, AndroidSchedulers.mainThread());
    }

    public synchronized Observable<List<Photo>> fetchPhotoList(Scheduler observeOn, Scheduler subscribeOn)
    {
        if (photoListObservable == null)
        {
            photoListObservable =
                    generateUri(METHOD_INTERESTINGNESS_LIST)
                            .observeOn(observeOn)
                            .flatMap(new Func1<Uri, Observable<String>>() {
                                @Override
                                public Observable<String> call(Uri uri) {return read(uri.toString());}
                            })
                            .flatMap(new Func1<String, Observable<Photo>>() {
                        @Override
                        public Observable<Photo> call(String s) {return parsePhotos(s);
                        }
                    })
                            .toList()
                            .cache()
                            .subscribeOn(subscribeOn)
                            .observeOn(observeOn);
        }

        return photoListObservable;
    }

    private Observable<String> read(final String url)
    {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = null;
                String output = null;

                try
                {
                    response = client.newCall(request).execute();
                    output = response.body().string();

                    if (subscriber.isUnsubscribed()) { return; }
                    subscriber.onNext(output);
                    subscriber.onCompleted();
                }
                catch (IOException e)
                {
                    if (!subscriber.isUnsubscribed())
                    {
                        subscriber.onError(e);
                    }
                }
            }
        });
    }

    private Observable<Photo> parsePhotos(final String jsonString)
    {
        return Observable.create(new Observable.OnSubscribe<Photo>() {
            @Override
            public void call(Subscriber<? super Photo> subscriber) {

//                List<Photo> photoList = null;
                ObjectNode root = null;

                try {
                    root = (ObjectNode) mapper.readTree(jsonString);
                } catch (Exception e) {}

                if (root != null)
                {
                    JsonNode photoListWrapperNode = root.get("photos");

                    if (photoListWrapperNode != null)
                    {
                        JsonNode photoListNode = photoListWrapperNode.get("photo");

                        if (photoListNode != null)
                        {
//                            photoList = new ArrayList<>(photoListNode.size());

                            for (JsonNode node : photoListNode)
                            {
                                Photo photo = Photo.fromJson(node);

                                if (photo != null)
                                {
//                                    photoList.add(photo);
                                    subscriber.onNext(photo);
                                }
                            }
                        }
                    }
                }

                if (subscriber.isUnsubscribed()) { return; }
                //subscriber.onNext(photoList);
                subscriber.onCompleted();
            }
        });
    }

    // https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=4cbbe7d0909a7353907c85dcb6451e55&format=json&nojsoncallback=1
    private Observable<Uri> generateUri(final String method)
    {
        // TODO: Simplifying with Func0


        return Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {

                subscriber.onNext(new Uri.Builder()
                        .scheme("https")
                        .authority("api.flickr.com")
                        .appendPath("services")
                        .appendPath("rest")
                        .appendPath("")
                        .appendQueryParameter("method", method)
                        .appendQueryParameter("api_key", API_KEY)
                        .appendQueryParameter("format", "json")
                        .appendQueryParameter("nojsoncallback", String.valueOf(1))
                        .build());
                subscriber.onCompleted();
            }
        });
    }

//    class FlickerPhotoListFetcher extends AsyncTask<Object, Object, List<Photo>>
//    {
//
//
//
//        private Semaphore lock = new Semaphore(1);
//        private Subscriber<? super List<Photo>> subscriber;
//        private String method;
//
//
//        FlickerPhotoListFetcher(Subscriber<? super List<Photo>> subscriber, String method)
//        {
//            this.subscriber = subscriber;
//            this.method = method;
//        }
//
//        @Override
//        protected List<Photo> doInBackground(Object... params)
//        {
//            if (!subscriber.isUnsubscribed())
//            {
//                subscriber.onStart();
//            }
//
//
//            Uri uri = generateUri(method);
//            String jsonString = read(uri.toString());
//            List<Photo> photoList = parse(jsonString);
//
//            return photoList;
//        }
//
//        @Override
//        protected void onPostExecute(List<Photo> photoList)
//        {
//            if (subscriber != null && !subscriber.isUnsubscribed())
//            {
//                if (photoList == null)
//                {
//                    subscriber.onError(new Exception("ADSFASDFAA"));
//                }
//                else
//                {
//                    subscriber.onNext(photoList);
//                    subscriber.onCompleted();
//                }
//            }
//        }
//
//        private String read(final String url)
//        {
//            Request request = new Request.Builder()
//                    .url(url)
//                    .build();
//
//            Response response = null;
//            String output = null;
//
//            try
//            {
//                response = client.newCall(request).execute();
//                output = response.body().string();
//            }
//            catch (IOException e) { /* Ignored */ }
//
//            return output;
//        }
//
//        private List<Photo> parse(final String jsonString)
//        {
//            List<Photo> photoList = null;
//            ObjectNode root = null;
//
//            try {
//                root = (ObjectNode) mapper.readTree(jsonString);
//            } catch (Exception e) {}
//
//            if (root != null)
//            {
//                JsonNode photoListWrapperNode = root.get("photos");
//
//                if (photoListWrapperNode != null)
//                {
//                    JsonNode photoListNode = photoListWrapperNode.get("photo");
//
//                    if (photoListNode != null)
//                    {
//                        photoList = new ArrayList<>(photoListNode.size());
//
//                        for (JsonNode node : photoListNode)
//                        {
//                            Photo photo = Photo.fromJson(node);
//
//                            if (photo != null)
//                            {
//                                photoList.add(photo);
//                            }
//                        }
//                    }
//                }
//            }
//
//            return photoList;
//        }
//
//        // https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=4cbbe7d0909a7353907c85dcb6451e55&format=json&nojsoncallback=1
//        private Uri generateUri(final String method)
//        {
//            return new Uri.Builder()
//                    .scheme("https")
//                    .authority("api.flickr.com")
//                    .appendPath("services")
//                    .appendPath("rest")
//                    .appendPath("")
//                    .appendQueryParameter("method", method)
//                    .appendQueryParameter("api_key", API_KEY)
//                    .appendQueryParameter("format", "json")
//                    .appendQueryParameter("nojsoncallback", String.valueOf(1))
//                    .build();
//        }
//    }

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
