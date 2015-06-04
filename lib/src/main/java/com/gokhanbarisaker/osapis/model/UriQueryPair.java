package com.gokhanbarisaker.osapis.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.gokhanbarisaker.osapis.utility.UriQueryParser;

import java.util.Map;

/**
 * Created by gokhanbarisaker on 6/4/15.
 */
public class UriQueryPair implements Map.Entry<String, String>, Parcelable
{
    // == Variables ================================================================================

    private String key;
    private String value;


    // == Constructors =============================================================================

    public UriQueryPair(@NonNull String key, @NonNull String value) {
        this.key = key;
        this.value = value;
    }


    // == Accessors ================================================================================

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String setValue(@NonNull String value) {
        String artifact = this.value;
        this.value = value;
        return artifact;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UriQueryPair)) {
            return false;
        }

        return ((UriQueryPair) o).getKey().equals(getKey()) &&
                ((UriQueryPair) o).getValue().equals(getValue());
    }

    @Override
    public String toString() {
        return "{\"" + key + "\":\"" + value + "\"}";
    }

    // == Parcel stuff =============================================================================

    protected UriQueryPair(Parcel in) {
        key = in.readString();
        value = in.readString();new UriQueryParser().parse(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UriQueryPair> CREATOR = new Parcelable.Creator<UriQueryPair>() {
        @Override
        public UriQueryPair createFromParcel(Parcel in) {
            return new UriQueryPair(in);
        }

        @Override
        public UriQueryPair[] newArray(int size) {
            return new UriQueryPair[size];
        }
    };
}
