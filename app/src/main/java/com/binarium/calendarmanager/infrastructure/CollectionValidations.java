package com.binarium.calendarmanager.infrastructure;

import java.util.List;

/**
 * Created by link_jorge on 20/11/2016.
 */

public class CollectionValidations {

    public static <T> boolean IsNotEmpty(List<T> values) {
        return values != null && values.size() > 0;
    }

    public static <T> boolean IsEmpty(List<T> values) {
        return !IsNotEmpty(values);
    }
}
