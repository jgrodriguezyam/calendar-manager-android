package com.binarium.calendarmanager.fragment.dialog;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.binarium.calendarmanager.fragment.listener.DatePickerDialogListener;

/**
 * Created by jrodriguez on 24/05/2017.
 */

public class DatePickerDialogFragment extends DialogFragment implements OnDateSetListener {
    private DatePickerDialogListener datePickerDialogListener;

    public static DatePickerDialogFragment newInstance(Long dateMinimum, Long dateMaximum, int year, int month, int day) {
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        if(dateMinimum!=null)
            args.putLong("dateMinimum", dateMinimum);
        if(dateMaximum!=null)
            args.putLong("dateMaximum", dateMaximum);
        datePickerDialogFragment.setArguments(args);
        return datePickerDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = getArguments().getInt("year");
        int month = getArguments().getInt("month");
        int day = getArguments().getInt("day");
        Long dateMinimum = getArguments().getLong("dateMinimum");
        Long dateMaximum = getArguments().getLong("dateMaximum");
        DatePickerDialog datePickerDialog =  new DatePickerDialog(getActivity(), this, year, month, day);
        if(dateMinimum!=0)
            datePickerDialog.getDatePicker().setMinDate(dateMinimum);
        if(dateMaximum!=0)
            datePickerDialog.getDatePicker().setMaxDate(dateMaximum);
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        datePickerDialogListener.setDate(view, year, month, day, getTag());
    }

    public void setDatePickerDialogListener(DatePickerDialogListener datePickerDialogListener) {
        this.datePickerDialogListener = datePickerDialogListener;
    }
}
