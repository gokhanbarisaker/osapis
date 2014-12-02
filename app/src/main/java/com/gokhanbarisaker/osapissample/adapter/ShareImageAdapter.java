package com.gokhanbarisaker.osapissample.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by gokhanbarisaker on 11/30/14.
 */
public class ShareImageAdapter extends PagerAdapter
{
    private static final String imageUrl = "https://www.google.com/images/srpr/logo11w.png";

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
