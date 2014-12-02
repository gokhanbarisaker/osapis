package com.gokhanbarisaker.osapissample;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokhanbarisaker.osapis.utility.DeviceUtilities;
import com.gokhanbarisaker.osapis.utility.ShareUtilities;
import com.gokhanbarisaker.osapissample.R;
import com.squareup.picasso.Picasso;

public class ShareActivity extends ActionBarActivity {

    private ViewPager pager = null;

    ViewPager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        imageView = (ImageView) findViewById(R.id.share_image);
        textView = (TextView) findViewById(R.id.share_text);

        Picasso.with(this).load(imageUrl).into(imageView);
        textView.setText(text);

        findViewById(R.id.share_tweet_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new ShareUtilities().createShareIntentForTwitter(v.getContext(), DeviceUtilities.getCurrentDevice(), text, imageUrl);
                ShareActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.share_facebook_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new ShareUtilities().createShareIntentForFacebook(v.getContext(), DeviceUtilities.getCurrentDevice(), text, imageUrl);
                ShareActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.share_googleplus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new ShareUtilities().createShareIntentForGooglePlus(v.getContext(), text, imageUrl);
                ShareActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.share_whatsapp_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new ShareUtilities().createShareIntentForWhatsapp(v.getContext(), DeviceUtilities.getCurrentDevice(), text, imageUrl);
                ShareActivity.this.startActivity(intent);
            }
        });
    }
}
