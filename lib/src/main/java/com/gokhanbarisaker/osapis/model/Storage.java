package com.gokhanbarisaker.osapis.model;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.os.EnvironmentCompat;

import java.io.File;

/**
 * Created by gokhanbarisaker on 22/07/14.
 */
public class Storage
{
    private File mountPoint = null;

    public Storage (File mountPoint)
    {
        setMountPoint(mountPoint);
    }


    /*******************************************
     * Accessors
     */

    /**
     * @return File instance with storage mount point.
     */
    public File getMountPoint() {
        return mountPoint;
    }

    /**
     * @param mountPoint New mount point for storage
     */
    public void setMountPoint(File mountPoint) {
        this.mountPoint = mountPoint;
    }

    public StatFs getStatFs()
    {
        return new StatFs(getMountPoint().getPath());
    }

    /**
     * @return Available internal storage size in bytes
     */
    public long getAvailableBytes()
    {
        StatFs stat = getStatFs();

        long blockSize = 0L;
        long availableBlocks = 0L;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        }
        else
        {
            //noinspection deprecation
            blockSize = stat.getBlockSize();
            //noinspection deprecation
            availableBlocks = stat.getAvailableBlocks();
        }

        return availableBlocks * blockSize;
    }

    /**
     * @return Total internal storage size in bytes
     */
    public long getTotalBytes()
    {
        StatFs stat = getStatFs();

        long blockSize = 0L;
        long totalBlocks = 0L;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        }
        else
        {
            //noinspection deprecation
            blockSize = stat.getBlockSize();
            //noinspection deprecation
            totalBlocks = stat.getBlockCount();
        }

        return totalBlocks * blockSize;
    }

    /**
     * Checks whether current storage is available with read &amp; write permissions
     * TODO: If necessary, we might add read only storage check within another method
     *
     * @return True, if current storage mount point is available. False, otherwise.
     */
    public boolean isAvailable()
    {
        return EnvironmentCompat.getStorageState(getMountPoint()).equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public String toString()
    {
        return new StringBuffer()
                .append("Storage is ")
                .append((isAvailable())?"available":"not available")
                .append(", ")
                .append(getAvailableBytes())
                .append('/')
                .append(getTotalBytes())
                .append(" bytes")
                .toString();
    }
}
