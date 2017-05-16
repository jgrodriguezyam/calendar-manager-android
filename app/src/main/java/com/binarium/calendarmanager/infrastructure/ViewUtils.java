package com.binarium.calendarmanager.infrastructure;

import android.app.Activity;
import android.view.View;

/**
 * Created by jrodriguez on 08/04/2017.
 */

public class ViewUtils {

    public ViewUtils() {
    }

    public static <T extends View> T findViewById(View view, int resourceId) {
        return (T) view.findViewById(resourceId);
    }

    public static <T extends View> T findViewById(Activity activity, int resourceId) {
        return (T) activity.findViewById(resourceId);
    }
}
