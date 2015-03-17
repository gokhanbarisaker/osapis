package com.gokhanbarisaker.osapissample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gokhanbarisaker.osapis.utility.DeviceUtilities;
import com.gokhanbarisaker.osapis.utility.ShareUtilities;
import com.gokhanbarisaker.osapissample.Application;
import com.gokhanbarisaker.osapissample.R;
import com.gokhanbarisaker.osapissample.adapter.ShareFragmentStatePagerAdapter;
import com.gokhanbarisaker.osapissample.model.Photo;
import com.gokhanbarisaker.osapissample.service.FlickrService;
import com.gokhanbarisaker.osapissample.utilities.FlickrUtilities;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class ShareActivity extends ActionBarActivity {

    private static final String KEY_BUNDLE_PAGER = "shareactivity.bundle.pager";

    private ViewPager pager = null;
    private Subscription flickrSubscription = null;

    private int currentPhotoIndex = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        findViewById(R.id.share_tweet_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = getCurrentPhoto();

                if (photo == null)
                {
                    Toast.makeText(v.getContext(), "No photo found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new ShareUtilities().createShareIntentForTwitter(v.getContext(), DeviceUtilities.getCurrentDevice(), photo.getTitle(), photo.getImageUrl());
                    ShareActivity.this.startActivity(intent);
                }
            }
        });

        findViewById(R.id.share_facebook_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = getCurrentPhoto();

                if (photo == null)
                {
                    Toast.makeText(v.getContext(), "No photo found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new ShareUtilities().createShareIntentForFacebook(v.getContext(), DeviceUtilities.getCurrentDevice(), photo.getTitle(), photo.getImageUrl());
                    ShareActivity.this.startActivity(intent);
                }
            }
        });

        findViewById(R.id.share_googleplus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = getCurrentPhoto();

                if (photo == null)
                {
                    Toast.makeText(v.getContext(), "No photo found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new ShareUtilities().createShareIntentForGooglePlus(v.getContext(), photo.getTitle(), photo.getImageUrl());
                    ShareActivity.this.startActivity(intent);
                }

            }
        });

        findViewById(R.id.share_whatsapp_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = getCurrentPhoto();

                if (photo == null)
                {
                    Toast.makeText(v.getContext(), "No photo found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new ShareUtilities().createShareIntentForWhatsapp(v.getContext(), DeviceUtilities.getCurrentDevice(), photo.getTitle(), photo.getImageUrl());
                    ShareActivity.this.startActivity(intent);
                }
            }
        });

        findViewById(R.id.share_instagram_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = getCurrentPhoto();

                if (photo == null)
                {
                    Toast.makeText(v.getContext(), "No photo found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new ShareUtilities().createShareIntentForInstagram(v.getContext(), DeviceUtilities.getCurrentDevice(), photo.getTitle(), photo.getImageUrl());
                    ShareActivity.this.startActivity(intent);
                }
            }
        });

        findViewById(R.id.share_pinterest_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = getCurrentPhoto();

                if (photo == null)
                {
                    Toast.makeText(v.getContext(), "No photo found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new ShareUtilities().createShareIntentForPinterest(v.getContext(), DeviceUtilities.getCurrentDevice(), photo.getTitle(), photo.getImageUrl(), "1442292");
                    ShareActivity.this.startActivity(intent);
                }
            }
        });

        pager = (ViewPager) findViewById(R.id.share_pager);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                currentPhotoIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        pager.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_BUNDLE_PAGER));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (pager.getAdapter() == null)
        {
            flickrSubscription =
            FlickrUtilities.getSharedInstance().fetchPhotoList().subscribe(new Subscriber<List<Photo>>() {

                @Override
                public void onCompleted() {
                    Log.e("Foo", "Tier 3");
                    flickrSubscription = null;
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Foo", "Tier 4", e);
                    flickrSubscription = null;
                }

                @Override
                public void onNext(final List<Photo> photoList) {
                    Log.e("Foo", "Tier 5: " + photoList.toString());

                    pager.post(new Runnable() {
                        @Override
                        public void run() {
                            ShareFragmentStatePagerAdapter adapter = new ShareFragmentStatePagerAdapter(getSupportFragmentManager(), photoList);
                            pager.setAdapter(adapter);
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        Subscription subscription = flickrSubscription;

        if (subscription != null)
        {
            subscription.unsubscribe();
            flickrSubscription = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_BUNDLE_PAGER, pager.onSaveInstanceState());
    }

    private Photo getCurrentPhoto()
    {
        ShareFragmentStatePagerAdapter adapter = (ShareFragmentStatePagerAdapter) pager.getAdapter();

        if (adapter != null)
        {
            List<Photo> photoList = adapter.getPhotoList();

            if (photoList != null)
            {
                return photoList.get(currentPhotoIndex);
            }
        }

        return null;
    }
}
