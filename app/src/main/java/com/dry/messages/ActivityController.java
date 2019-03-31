package com.dry.messages;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * Controller of Activity.
 *
 * @author DuRuyao
 * Create 19/03/31
 */
public class ActivityController {
    /**
     * Get current activity.
     *
     * @param context Current context.
     * @return An activity.
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
