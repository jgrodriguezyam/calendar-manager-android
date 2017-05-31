package com.binarium.calendarmanager.infrastructure;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by jrodriguez on 31/05/2017.
 */

public class SpinnerExtensions {
    public static <T> void setSelection(MaterialSpinner materialSpinner, T value) {
        for(int i = 0; i < materialSpinner.getCount(); i++){
            if(materialSpinner.getItemAtPosition(i).equals(value)){
                materialSpinner.setSelection(i + 1);
                break;
            }
        }
    }
}
