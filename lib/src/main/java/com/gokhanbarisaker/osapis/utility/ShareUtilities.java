package com.gokhanbarisaker.osapis.utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.gokhanbarisaker.osapis.model.Device;
import com.google.android.gms.plus.PlusShare;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by gokhanbarisaker on 02/01/14.
 */
public class ShareUtilities
{
    public static final String PACKAGENAME_TWITTER = "com.twitter.android";
    public static final String PACKAGENAME_FACEBOOK = "com.facebook.katana";
    public static final String PACKAGENAME_GOOGLEPLUS = "com.google.android.apps.plus";
    public static final String PACKAGENAME_WHATSAPP = "com.whatsapp";

    private static final int LIMIT_CHARACTER_TWITTER = 140;

    /**
     * Character length when urls altered to t.co counterparts. <a href="https://support.twitter.com/articles/78124-posting-links-in-a-tweet">Source</a>
     */
    private static final int LENGTH_CHARACTER_URL_SHORT_TWITTER = 22;



    public Intent createShareIntentForPackage(final Context context, final Device device, final String packageName, final String shareText, final String shareUrl)
    {
        Intent shareIntent = null;

        if (PACKAGENAME_TWITTER.equalsIgnoreCase(packageName))
        {
            shareIntent = createShareIntentForTwitter(context, device, shareText, shareUrl);
        }
        else if (PACKAGENAME_FACEBOOK.equalsIgnoreCase(packageName))
        {
            shareIntent = createShareIntentForFacebook(context, device, shareText, shareUrl);
        }
        else if (PACKAGENAME_GOOGLEPLUS.equalsIgnoreCase(packageName))
        {
            shareIntent = createShareIntentForGooglePlus(context, shareText, shareUrl);
        }
        else if (PACKAGENAME_WHATSAPP.equalsIgnoreCase(packageName))
        {
            shareIntent = createShareIntentForWhatsapp(context, device, shareText, shareUrl);
        }
        else
        {
            if (device.isAppInstalled(context, packageName))
            {
                Log.d(ShareUtilities.class.getSimpleName(), "Share intent generation for package name \"" + packageName + "\" is officially not supported! The generated intent may not perform as intended.");

                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, new StringBuffer(shareText).append('\n').append(shareUrl).toString());
            }
            else
            {
                Log.d(ShareUtilities.class.getSimpleName(), "Cancelling share intent generation for package name \"" + packageName + "\"! The application is not installed.");
            }
        }

        return shareIntent;
    }

    public Uri createShareUriForTwitter(final String text, final String url)
    {
//        http://www.twitter.com/intent/tweet?url=YOURURL&text=YOURTEXT

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")
                .authority("www.twitter.com")
                .appendPath("intent")
                .appendPath("tweet")
                .appendQueryParameter("text", text)
                .appendQueryParameter("url", url);

        return builder.build();
    }

    public Uri createShareUriForFacebook(final String url)
    {
//        https://www.facebook.com/sharer/sharer.php?u=shareUrlGoesHere

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("www.facebook.com")
                .appendPath("sharer")
                .appendPath("sharer.php")
                .appendQueryParameter("u", url);

        return builder.build();
    }

    public Intent createShareIntentForTwitter(final Context context, final Device device, final String shareText, final String shareUrl)
    {
        final Intent intent = new Intent();

        if (device.isAppInstalled(context, PACKAGENAME_TWITTER))
        {
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setPackage(PACKAGENAME_TWITTER);
            intent.putExtra(Intent.EXTRA_TEXT, createTwittableContent(shareText, shareUrl));
        }
        else
        {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(createShareUriForTwitter(shareText, shareUrl));
        }

        return intent;
    }

    public Intent createShareIntentForFacebook(final Context context, final Device device, final String shareText, final String shareUrl)
    {
        final Intent intent = new Intent();

        if (device.isAppInstalled(context, PACKAGENAME_FACEBOOK))
        {
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
            intent.setPackage(PACKAGENAME_FACEBOOK);
        }
        else
        {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(createShareUriForFacebook(shareUrl));
        }

        return intent;
    }

    public Intent createShareIntentForGooglePlus(final Context context, final String shareText, final String shareUrl)
    {
        // TODO: Unlock full power of Google Plus share!

        // Launch the Google+ share dialog with attribution to your app.
        return new PlusShare.Builder(context)
                .setType("text/plain")
                .setText(shareText)
                .setContentUrl(Uri.parse(shareUrl))
                .getIntent();
    }

    public Intent createShareIntentForWhatsapp(final Context context, final Device device, final String shareText, final String shareUrl)
    {
        Intent intent = null;

        if (device.isAppInstalled(context, PACKAGENAME_WHATSAPP))
        {
            intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setPackage(PACKAGENAME_WHATSAPP);
            intent.putExtra(Intent.EXTRA_TEXT, new StringBuffer(shareText).append('\n').append(shareUrl).toString());
        }
        else
        {
            Log.d(ShareUtilities.class.getSimpleName(), "Cancelling share intent generation for whatsapp! Application is not installed.");
        }

        return intent;
    }


    public String createTwittableContent(final String text, final String url)
    {
        final boolean textAvailable = !TextUtils.isEmpty(text);
        final boolean urlAvailable = !TextUtils.isEmpty(url);

        StringBuffer twitBuffer = new StringBuffer();

        if (textAvailable)
        {
            int characterLimit = LIMIT_CHARACTER_TWITTER;

            if (urlAvailable)
            {
                // -1 is for separator character for text and url
                characterLimit -= LENGTH_CHARACTER_URL_SHORT_TWITTER - 1;
            }

            // If possible abbreviation with ellipsize available
            if (text.length() > characterLimit)
            {
                // Decrease character limit by ellipsize amount "..."
                characterLimit -= 3;
            }

            // Abbreviate to fit twitter
            twitBuffer.append(StringUtils.abbreviate(text, characterLimit));

            if (urlAvailable)
            {
                twitBuffer.append('\n');
                twitBuffer.append(url);
            }
        }
        else
        {
            twitBuffer.append(url);
        }

        return twitBuffer.toString();
    }
}
