package com.binarium.calendarmanager.infrastructure;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.binarium.calendarmanager.R;
import com.google.android.material.snackbar.Snackbar;

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
        TextView tv = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), id));
        snackBar.show();
    }
}
