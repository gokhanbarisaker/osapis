package com.gokhanbarisaker.osapissample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.gokhanbarisaker.osapis.utility.DeviceUtilities;
import com.gokhanbarisaker.osapis.utility.ShareUtilities;
import com.gokhanbarisaker.osapissample.Application;
import com.gokhanbarisaker.osapissample.R;
import com.gokhanbarisaker.osapissample.adapter.ShareFragmentStatePagerAdapter;
import com.gokhanbarisaker.osapissample.model.Photo;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

public class ShareActivity extends ActionBarActivity {

    private static final String KEY_BUNDLE_PAGER = "shareactivity.bundle.pager";

    private ViewPager pager = null;
    private Subscription flickrSubscription = null;

    private int currentPhotoIndex = 0;

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
            Application.flickrService.fetchPhotoList().subscribe(new Subscriber<List<Photo>>() {
                @Override
                public void onCompleted() {
                    flickrSubscription = null;
                }

                @Override
                public void onError(Throwable e) {
                    flickrSubscription = null;
                }

                @Override
                public void onNext(List<Photo> photoList) {
                    ShareFragmentStatePagerAdapter adapter = new ShareFragmentStatePagerAdapter(getSupportFragmentManager(), photoList);
                    pager.setAdapter(adapter);
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
