package com.gokhanbarisaker.osapissample.utilities;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by gokhanbarisaker on 22/12/13.
 */
public class RandomColorGenerator
{
    private static final int maxColorValue = 180;

    public static int generate()
    {
        return generate(1.f);
    }

    public static int generate(float alphaLevel)
    {
        Random rand = new Random();
        int red = rand.nextInt(120);
        int green = rand.nextInt(120);
        int blue = rand.nextInt(120);

        int alpha = (int) (255 * alphaLevel);

        return Color.argb(alpha, red, green, blue);
    }
}
