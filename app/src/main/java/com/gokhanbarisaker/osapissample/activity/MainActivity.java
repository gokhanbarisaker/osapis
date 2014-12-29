package com.gokhanbarisaker.osapissample.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gokhanbarisaker.osapissample.Application;
import com.gokhanbarisaker.osapissample.R;
import com.gokhanbarisaker.osapissample.model.Photo;
import com.gokhanbarisaker.osapissample.service.FlickrService;
import com.gokhanbarisaker.osapissample.utilities.FlickrUtilities;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends ActionBarActivity {

    Subscription subs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.keyboard_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KeyboardActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.device_status_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeviceStatusActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
