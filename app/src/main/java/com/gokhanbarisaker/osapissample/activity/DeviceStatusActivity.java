package com.gokhanbarisaker.osapissample.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.gokhanbarisaker.osapis.utility.DeviceUtilities;
import com.gokhanbarisaker.osapissample.R;
import com.gokhanbarisaker.osapissample.adapter.DeviceStatusAdapter;

public class DeviceStatusActivity extends ActionBarActivity {

    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_status);

        recyclerView = (RecyclerView) findViewById(R.id.device_status_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new DeviceStatusAdapter(this));
    }
}
