package com.gokhanbarisaker.osapissample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.gokhanbarisaker.osapissample.R;
import com.gokhanbarisaker.osapis.model.Keyboard;
import com.gokhanbarisaker.osapis.utility.DeviceUtilities;


public class MainActivity extends ActionBarActivity {

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
