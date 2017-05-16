package com.binarium.calendarmanager.infrastructure;

import android.text.TextUtils;

/**
 * Created by jrodriguez on 10/04/2017.
 */

public class EditTextExtensions {

    public static boolean isFieldEmpty(CharSequence text) {
        return TextUtils.isEmpty(text);
    }

    public static boolean isFieldNotEmpty(CharSequence text) {
        return !TextUtils.isEmpty(text);
    }
}
