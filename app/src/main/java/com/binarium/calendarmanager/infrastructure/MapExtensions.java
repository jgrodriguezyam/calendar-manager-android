package com.binarium.calendarmanager.infrastructure;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.viewmodels.location.Location;

/**
 * Created by jrodriguez on 26/05/2017.
 */

public class MapExtensions {
    public static String getSnippetOfLocation(Location location) {
        String date = ResourcesExtensions.toString(R.string.snippet_date_title);
        date = date + " " + (location.getStartDate().equals(location.getEndDate()) ? location.getStartDate() : location.getStartDate() + " - " + location.getEndDate());
        String type = ResourcesExtensions.toString(R.string.snippet_type_title);
        type = type + " " + EnumExtensions.getNameOfLocationType(location.getType());
        String radius = ResourcesExtensions.toString(R.string.snippet_radius_title);
        radius = radius + " " + (int) location.getRadius() + " " + ResourcesExtensions.toString(R.string.snippet_radius_measure);
        String latitude = ResourcesExtensions.toString(R.string.snippet_latitude_title);
        latitude = latitude + " " + location.getLatitude();
        String longitude = ResourcesExtensions.toString(R.string.snippet_longitude_title);
        longitude = longitude + " " + location.getLongitude();
        String snippet = date + "\n" + type + "\n" + radius + "\n" + latitude + "\n" + longitude;
        return snippet;
    }
}
