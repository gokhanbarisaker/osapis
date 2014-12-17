package com.gokhanbarisaker.osapissample.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gokhanbarisaker.osapissample.fragment.ShareFragment;
import com.gokhanbarisaker.osapissample.model.Photo;

import java.util.List;

/**
 * Created by gokhanbarisaker on 12/17/14.
 */
public class ShareFragmentStatePagerAdapter extends FragmentStatePagerAdapter
{
    List<Photo> photoList = null;

    public ShareFragmentStatePagerAdapter(FragmentManager fm, List<Photo> photoList)
    {
        super(fm);
        this.photoList = photoList;
    }

    @Override
    public Fragment getItem(int position)
    {
        Photo photo = photoList.get(position);
        return ShareFragment.newInstance(photo);
    }

    @Override
    public int getCount()
    {
        return (photoList == null)?(0):(photoList.size());
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }
}
