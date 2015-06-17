package com.gokhanbarisaker.osapis.model;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by gokhanbarisaker on 11/19/14.
 */
public class Keyboard
{
    /**
     * Shows keyboard for specific view
     *
     * @param view to show keyboard
     */
    public void show(final View view)
    {
        Context context = view.getContext();

        InputMethodManager imm = (InputMethodManager)context.getSystemService(
                Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Hides keyboard for specific view.
     *
     * @param view to hide keyboard
     */
    public void hide(final View view)
    {
        Context context = view.getContext();

        InputMethodManager imm = (InputMethodManager)context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Hides keyboard from window
     *
     * @param window host for keyboard
     */
    public void hide(final Window window)
    {
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
