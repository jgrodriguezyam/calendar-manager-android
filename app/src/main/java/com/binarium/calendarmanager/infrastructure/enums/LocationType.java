package com.binarium.calendarmanager.infrastructure.enums;

import com.binarium.calendarmanager.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jrodriguez on 19/05/2017.
 */

public enum LocationType {
    EVENT(1, "Event", R.drawable.ic_event),
    ACTIVITY(2, "Activity", R.drawable.ic_activity),
    TASK(3, "Task", R.drawable.ic_task);

    private int key;
    private String value;
    private int image;

    private LocationType(int enumKey, String enumValue, int enumImage) {
        key = enumKey;
        value = enumValue;
        image = enumImage;
    }

    public int getIdentifier() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int getImage() {
        return image;
    }

    @Override
    public String toString() {
        return value;
    }
}
