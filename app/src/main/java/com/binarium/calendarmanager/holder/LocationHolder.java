package com.binarium.calendarmanager.holder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.EnumExtensions;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.infrastructure.ViewUtils;
import com.binarium.calendarmanager.viewmodels.location.Location;

/**
 * Created by jrodriguez on 27/07/2017.
 */

public class LocationHolder extends ViewHolder {
    private Location location;
    private ImageView locationImage;
    private TextView locationName;
    private TextView locationDate;
    private TextView locationType;
    private TextView locationRadius;
    private TextView locationCheckInDate;
    private ImageView locationIsChecked;

    public LocationHolder(View view) {
        super(view);
        locationImage = ViewUtils.findViewById(view, R.id.img_location);
        locationName = ViewUtils.findViewById(view, R.id.tv_location_name);
        locationDate = ViewUtils.findViewById(view, R.id.tv_location_date);
        locationType = ViewUtils.findViewById(view, R.id.tv_location_type);
        locationRadius = ViewUtils.findViewById(view, R.id.tv_location_radius);
        locationCheckInDate = ViewUtils.findViewById(view, R.id.tv_location_check_in_date);
        locationIsChecked = ViewUtils.findViewById(view, R.id.img_location_is_checked);
    }

    public void configure(Location location) {
        this.location = location;
        int image = EnumExtensions.getImageOfLocationType(location.getType());
        locationImage.setImageResource(image);
        locationName.setText(location.getName());
        String date = ResourcesExtensions.toString(R.string.recycler_location_date);
        date = date + " " + (location.getStartDate().equals(location.getEndDate()) ? location.getStartDate() : location.getStartDate() + " - " + location.getEndDate());
        locationDate.setText(date);
        String type = ResourcesExtensions.toString(R.string.recycler_location_type);
        type = type + " " + EnumExtensions.getNameOfLocationType(location.getType());
        locationType.setText(type);
        String radius = ResourcesExtensions.toString(R.string.recycler_location_radius);
        radius = radius + " " + (int) location.getRadius() + " " + ResourcesExtensions.toString(R.string.recycler_location_radius_measure);
        locationRadius.setText(radius);
        if (location.isChecked()) {
            String[] checkInDate = location.getCheckInDate().split(" ");
            locationCheckInDate.setText(checkInDate[1] + " " + checkInDate[2] + checkInDate[3]);
            locationIsChecked.setImageResource(R.drawable.ic_check);
            locationIsChecked.setBackgroundColor(ContextCompat.getColor(locationIsChecked.getContext(), R.color.green_custom));
        }
    }
}
