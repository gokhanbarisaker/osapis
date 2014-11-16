package com.gokhanbarisaker.osapis.utility;

import android.text.TextUtils;

import java.util.Locale;


public class StringHelper
{
    public boolean isBlank(String s)
    {
        // Check for null or empty ("") value

        int length;

        if (s == null || (length = s.length()) == 0)
        {
            return true;
        }


        // Check for whitespace characters

        for (int i = 0; i < length; i++)
        {
            if ((Character.isWhitespace(s.charAt(i)) == false))
            {
                return false;
            }
        }

        return true;
    }

    public void replaceAll(StringBuffer buffer, String from, String to)
    {
        int length = from.length();
        int index = buffer.indexOf(from);
        while (index != -1)
        {
            buffer.replace(index, index + length, to);
            index += to.length(); // Move to the end of the replacement
            index = buffer.indexOf(from, index);
        }
    }

    public String capitalize(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }

        char first = s.charAt(0);

        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}