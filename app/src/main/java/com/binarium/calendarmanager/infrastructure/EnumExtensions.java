package com.binarium.calendarmanager.infrastructure;

import com.binarium.calendarmanager.infrastructure.enums.LocationType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jrodriguez on 19/05/2017.
 */

public class EnumExtensions {
    public static int getImageOfLocationType(int key) {
        List index = Arrays.asList(LocationType.values());
        LocationType locationType = (LocationType) index.get(key - 1);
        return locationType.getImage();
    }
}
