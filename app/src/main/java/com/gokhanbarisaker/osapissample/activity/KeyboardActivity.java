package com.gokhanbarisaker.osapissample.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gokhanbarisaker.osapis.utility.DeviceUtilities;
import com.gokhanbarisaker.osapissample.R;

public class KeyboardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        findViewById(R.id.edittext_showbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceUtilities.getCurrentDevice().getKeyboard().show(v);
            }
        });

        findViewById(R.id.edittext_hidebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceUtilities.getCurrentDevice().getKeyboard().hide(v);
            }
        });
    }
}
