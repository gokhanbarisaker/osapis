package com.gokhanbarisaker.osapis.model;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.ref.WeakReference;

/**
 * Created by gokhanbarisaker on 11/19/14.
 */
public class Keyboard
{
    public void hide(final View view)
    {
        Context context = view.getContext();

        InputMethodManager imm = (InputMethodManager)context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void show(final View view)
    {
        Context context = view.getContext();

        InputMethodManager imm = (InputMethodManager)context.getSystemService(
                Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
