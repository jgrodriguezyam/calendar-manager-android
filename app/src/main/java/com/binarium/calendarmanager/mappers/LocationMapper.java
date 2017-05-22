package com.binarium.calendarmanager.mappers;

import com.binarium.calendarmanager.dto.location.LocationRequest;
import com.binarium.calendarmanager.dto.location.LocationResponse;
import com.binarium.calendarmanager.viewmodels.location.Location;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public class LocationMapper {
    public static Location toLocation(LocationResponse locationResponse) {
        Location location = new Location();
        location.setId(locationResponse.getId());
        location.setName(locationResponse.getName());
        location.setLatitude(locationResponse.getLatitude());
        location.setLongitude(locationResponse.getLongitude());
        location.setRadius(locationResponse.getRadius());
        location.setType(locationResponse.getType());
        location.setStartDate(locationResponse.getStartDate());
        location.setEndDate(locationResponse.getEndDate());
        location.setComment(locationResponse.getComment());
        return location;
    }

    public static LocationRequest ToLocationRequest(Location location) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setId(location.getId());
        locationRequest.setName(location.getName());
        locationRequest.setLatitude(location.getLatitude());
        locationRequest.setLongitude(location.getLongitude());
        locationRequest.setRadius(location.getRadius());
        locationRequest.setType(location.getType());
        locationRequest.setStartDate(location.getStartDate());
        locationRequest.setEndDate(location.getEndDate());
        locationRequest.setComment(location.getComment());
        return locationRequest;
    }
}
