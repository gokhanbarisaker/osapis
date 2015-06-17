package com.gokhanbarisaker.osapis;

import com.gokhanbarisaker.osapis.model.UriQueryPair;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gokhanbarisaker on 6/4/15.
 */
public class UriQueryPairTest
{
    // TODO: add before constructor!
    // TODO: add parcellable tests.

    private static final String KEY = "sample.key";
    private static final String VALUE1 = "sample.value.1";
    private static final String VALUE2 = "sample.value.2";

    @Test
    public void accessors() {
        UriQueryPair pair = new UriQueryPair(KEY, VALUE1);

        Assert.assertEquals("It should provide same key used in constructor", pair.getKey(), KEY);
        Assert.assertEquals("It should provide same value used in constructor", pair.getValue(), VALUE1);

        pair.setValue(VALUE2);
        Assert.assertEquals("It should provide value updated with setter", pair.getValue(), VALUE2);
    }

//    @Test public void parcellable() {
//        UriQueryPair pairOrigin = new UriQueryPair(KEY, VALUE1);
//
//        Parcel parcel = Parcel.obtain();
//        parcel.writeParcelable(pairOrigin, 0);
//        parcel.setDataPosition(0);
//
//        UriQueryPair pairClone = UriQueryPair.CREATOR.createFromParcel(parcel);
//
//        Assert.assertEquals("It should read&write to parcel as expected", pairOrigin, pairClone);
//    }
}
