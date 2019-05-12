package com.dry.messages;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.StaticLayout;
import androidx.appcompat.app.ActionBar;

/**
 * A class as controller of Activity who contains of some method about operating Activity.
 *
 * @author DuRuyao
 * Create 19/03/31
 */
public class ActivityHelper {
    /**
     * Get current activity.
     *
     * @param context Current context.
     * @return Current activity who provide the context.
     */
    public static Activity getActivity(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        if (context instanceof Activity) {
            return (Activity) context;
        } else {
            return null;
        }
    }
}
