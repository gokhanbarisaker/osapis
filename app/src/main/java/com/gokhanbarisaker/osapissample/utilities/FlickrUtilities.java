package com.gokhanbarisaker.osapissample.utilities;

import android.net.Uri;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gokhanbarisaker.osapissample.Application;
import com.gokhanbarisaker.osapissample.model.Photo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by gokhanbarisaker on 12/29/14.
 */
public class FlickrUtilities
{
    private static final String API_KEY = "f01e745e13b4499e737599d06d839315";

    private static final String KEY_JSON_PHOTOS = "photos";
    private static final String KEY_JSON_PHOTO = "photo";

    public static final String METHOD_INTERESTINGNESS_LIST = "flickr.interestingness.getList";

    private static Observable<List<Photo>> photoListObservable = null;

    private static FlickrUtilities sharedInstance = null;



    public synchronized static FlickrUtilities getSharedInstance()
    {
        if (sharedInstance == null)
        {
            sharedInstance = new FlickrUtilities();
        }

        return sharedInstance;
    }

    public Observable<List<Photo>> fetchPhotoList()
    {
        return fetchPhotoList(Application.client, Application.mapper);
    }

    public synchronized Observable<List<Photo>> fetchPhotoList(final OkHttpClient client, final ObjectMapper mapper)
    {
        if (photoListObservable == null)
        {
            photoListObservable =
                    generateUri(METHOD_INTERESTINGNESS_LIST)
                            .observeOn(Application.SCHEDULER_BACKGROUND)
                            .flatMap(new Func1<Uri, Observable<String>>() {
                                @Override
                                public Observable<String> call(final Uri uri) {
                                    return read(client, uri.toString());
                                }
                            })
                            .flatMap(new Func1<String, Observable<Photo>>() {
                                @Override
                                public Observable<Photo> call(final String s) {
                                    return parsePhotos(mapper, s);
                                }
                            })
                            .toList()
                            .cache()
                            .subscribeOn(AndroidSchedulers.mainThread());
        }

        return photoListObservable;
    }

    private Observable<String> read(final OkHttpClient client, final String url)
    {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ;
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

    private Observable<Photo> parsePhotos(final ObjectMapper mapper, final String jsonString)
    {
        return Observable.create(new Observable.OnSubscribe<Photo>() {
            @Override
            public void call(Subscriber<? super Photo> subscriber) {

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
                            for (JsonNode node : photoListNode)
                            {
                                Photo photo = Photo.fromJson(node);

                                if (photo != null)
                                {
                                    subscriber.onNext(photo);
                                }
                            }
                        }
                    }
                }

                if (subscriber.isUnsubscribed()) { return; }
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
}
