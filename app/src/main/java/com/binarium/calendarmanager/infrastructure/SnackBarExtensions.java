package com.binarium.calendarmanager.infrastructure;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.binarium.calendarmanager.R;

/**
 * Created by link_jorge on 15/12/2016.
 */

public class SnackBarExtensions {

    public static void showSuccessMessage(View view, String message) {
        showSnackBar(view, message, R.color.green_custom);
    }

    public static void showErrorMessage(View view, String message) {
        showSnackBar(view, message, R.color.red_custom);
    }

    private static void showSnackBar(View view, String message, int id){
        Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackBar.getView();
        TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), id));
        snackBar.show();
    }
}
