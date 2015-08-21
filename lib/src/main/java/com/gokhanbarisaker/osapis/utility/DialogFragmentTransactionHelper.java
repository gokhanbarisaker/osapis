package com.gokhanbarisaker.osapis.utility;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by gokhanbarisaker on 8/21/15.
 */
public class DialogFragmentTransactionHelper
{
    // == Variables ================================================================================

    private static DialogFragmentTransactionHelper instance = new DialogFragmentTransactionHelper();


    // == Constructors & factory methods ===========================================================

    public static DialogFragmentTransactionHelper getInstance() {
        return instance;
    }


    // == Tools ====================================================================================

    /**
     * Shows the given fragment, if any fragment instance with same tag does not attached
     * to the given fragment manager input argument. Also see, @{show} for more details.
     *
     * @param fragment
     * @param manager
     * @param transaction
     * @param tag
     * @return null, there is no fragment instance with the same tag. Otherwise, instance of previous fragment
     */
    public DialogFragment showSingleton(DialogFragment fragment, FragmentManager manager, FragmentTransaction transaction, String tag)
    {
        if (manager == null)  {
            Log.v(getClass().getSimpleName(), "Given fragment manager input argument is null. This method can not detect previous instances with given tag. Therefore the singleton state is not guaranteed!");
        }
        else {
            DialogFragment ancestor = (DialogFragment) manager.findFragmentByTag(tag);

            if (ancestor != null) {
                return ancestor;
            }
        }

        if (fragment == null) {
            Log.v(getClass().getSimpleName(), "Unable to show dialog fragment \"" + fragment + "\".");
            return null;
        }

        if (tag == null) {
            Log.v(getClass().getSimpleName(), "Empty tag argument given to show singleton method. Please provide a tag or this method will not be able to detect this instance on consecutive calls!");
        }

        show(fragment, transaction, tag);

        return null;
    }

    /**
     * Shows the given fragment. It does nothing, if one of the fragment or transaction arguments are null.
     *
     * @param fragment Fragment instance
     * @param transaction Transaction instance
     * @param tag Tag to make recognizable on fragment manager
     */
    public void show(DialogFragment fragment, FragmentTransaction transaction, String tag)
    {
        if (fragment == null || transaction == null) {
            Log.v(getClass().getSimpleName(), "Unable to show dialog fragment \"" + fragment + "\", with transaction \"" + transaction + "\".");
            return;
        }

        fragment.show(transaction, tag);
    }

    /**
     * Attempts to dismiss given fragment.
     *
     * @param fragment Fragment to be dismissed
     * @return If the given fragment dismissed successfully, returns true. Otherwise, false.
     */
    public boolean dismiss(DialogFragment fragment)
    {
        if (fragment == null) {
            Log.v(getClass().getSimpleName(), "Unable to dismiss dialog fragment \"" + fragment + "\".");
            return false;
        }

        FragmentManager manager = fragment.getFragmentManager();

        if (manager == null) {
            Log.v(getClass().getSimpleName(), "Unable to dismiss dialog fragment \"" + fragment + "\". It has been detached from its fragment manager!");
            return false;
        }

        fragment.dismiss();

        return true;
    }
}
