package com.gokhanbarisaker.osapissample.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gokhanbarisaker.osapis.utility.DeviceUtilities;
import com.gokhanbarisaker.osapissample.R;

import rx.Subscription;


public class MainActivity extends AppCompatActivity {

    Subscription subs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_keyboardbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KeyboardActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.main_statusbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeviceStatusActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.main_sharebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.main_camerabutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.main_dialogfragmentbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DialogFragmentActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
