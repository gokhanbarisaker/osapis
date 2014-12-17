package com.gokhanbarisaker.osapissample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by gokhanbarisaker on 12/16/14.
 */
public class Photo implements Parcelable {
    private static final String JSON_IDENTIFIER_KEY = "id";
    private static final String JSON_OWNER_KEY = "owner";
    private static final String JSON_SECRET_KEY = "secret";
    private static final String JSON_SERVER_KEY = "server";
    private static final String JSON_FARM_KEY = "farm";
    private static final String JSON_TITLE_KEY = "title";


    private String identifier;
    private String owner;
    private String secret;
    private String server;
    private String farm;
    private String title;


    public static Photo fromJson(JsonNode node)
    {
        return (node == null)?(null):(new Photo(node));
    }

    public Photo(JsonNode node)
    {
        identifier = node.get(JSON_IDENTIFIER_KEY).asText("");
        owner = node.get(JSON_OWNER_KEY).asText("");
        secret = node.get(JSON_SECRET_KEY).asText("");
        server = node.get(JSON_SERVER_KEY).asText("");
        farm = node.get(JSON_FARM_KEY).asText("");
        title = node.get(JSON_TITLE_KEY).asText("");
    }


    // =========================================================

    public String getIdentifier() {
        return identifier;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public String getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl()
    {
        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{o-secret}_o.(jpg|gif|png)

        return new StringBuffer()
                .append("https://farm")
                .append(farm)
                .append(".staticflickr.com/")
                .append(server)
                .append("/")
                .append(identifier)
                .append('_')
                .append(secret)
                .append(".jpg")
                .toString();
    }


    // =========================================================


    protected Photo(Parcel in) {
        identifier = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readString();
        farm = in.readString();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(identifier);
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeString(farm);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
