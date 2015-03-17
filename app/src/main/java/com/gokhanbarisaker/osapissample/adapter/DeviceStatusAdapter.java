package com.gokhanbarisaker.osapissample.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gokhanbarisaker.osapis.model.Device;
import com.gokhanbarisaker.osapis.utility.DeviceUtilities;
import com.gokhanbarisaker.osapissample.BuildConfig;
import com.gokhanbarisaker.osapissample.R;
import com.gokhanbarisaker.osapissample.utilities.RandomColorGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokhanbarisaker on 11/30/14.
 */
public class DeviceStatusAdapter extends RecyclerView.Adapter<DeviceStatusAdapter.ViewHolder>
{
    private List<DeviceStatus> deviceStatusList = null;


    public DeviceStatusAdapter(final Context context)
    {
        deviceStatusList = generateDeviceStatusList(context);
    }

    private List<DeviceStatus> generateDeviceStatusList(final Context context)
    {
        List<DeviceStatus> list = new ArrayList<>();

        Device device = DeviceUtilities.getCurrentDevice();

        list.add(new DeviceStatus("Name", device.getName()));
        list.add(new DeviceStatus("Display", device.getDisplay().getMetrics().toString()));
        list.add(new DeviceStatus("Display", "Layout: " + device.getDisplay().getLayout()));
        list.add(new DeviceStatus("Battery charge status", Integer.toString(device.getBattery().getChargeStatus(context))));
        list.add(new DeviceStatus("Battery health status", Integer.toString(device.getBattery().getHealthStatus(context))));
        list.add(new DeviceStatus("Battery plug status", Integer.toString(device.getBattery().getPlugStatus(context))));
        list.add(new DeviceStatus("Battery current level", new StringBuffer("% ").append(device.getBattery().getCurrentLevel(context)).toString()));
        list.add(new DeviceStatus("Internal storage", device.getInternalStorage().toString()));
        list.add(new DeviceStatus("External storage", device.getExternalStorage().toString()));
        list.add(new DeviceStatus("Carrier", device.getCarrierName(context)));
        list.add(new DeviceStatus("User-Agent, custom", device.getUserAgent(BuildConfig.APPLICATION_ID, BuildConfig.VERSION_NAME)));
        list.add(new DeviceStatus("User-Agent, webview", device.getWebViewUserAgent(context)));

        return list;
    }

    @Override
    public DeviceStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_devicestatus, parent, false);

        ViewHolder holder = new ViewHolder(rootView);

        holder.titleTextView = (TextView) rootView.findViewById(R.id.item_device_status_title_textview);
        holder.descriptionTextView = (TextView) rootView.findViewById(R.id.item_device_status_description_textview);

        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceStatusAdapter.ViewHolder holder, int position)
    {
        DeviceStatus status = deviceStatusList.get(position);

        holder.titleTextView.setText(status.title);
        holder.descriptionTextView.setText(status.description);

        holder.titleTextView.setBackgroundColor(RandomColorGenerator.generate());
    }

    @Override
    public int getItemCount() {
        return deviceStatusList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView titleTextView = null;
        TextView descriptionTextView = null;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class DeviceStatus
    {
        String title, description = null;

        DeviceStatus(String title, String description)
        {
            this.title = title;
            this.description = description;
        }
    }
}
