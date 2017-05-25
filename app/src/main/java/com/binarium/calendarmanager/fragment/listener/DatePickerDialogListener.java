package com.binarium.calendarmanager.fragment.listener;

import android.widget.DatePicker;

/**
 * Created by jrodriguez on 24/05/2017.
 */

public interface DatePickerDialogListener {
    void setDate(DatePicker view, int year, int month, int day, String tag);
}
