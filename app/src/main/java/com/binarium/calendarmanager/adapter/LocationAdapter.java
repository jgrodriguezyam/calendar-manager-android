package com.binarium.calendarmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.holder.LocationHolder;
import com.binarium.calendarmanager.infrastructure.CollectionValidations;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.List;

/**
 * Created by jrodriguez on 27/07/2017.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationHolder>  {
    private List<Location> locations;

    public LocationAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public LocationHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_location, viewGroup, false);
        return new LocationHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationHolder locationHolder, int position) {
        Location location = locations.get(position);
        if (ObjectValidations.IsNotNull(location))
            locationHolder.configure(location);
    }

    @Override
    public int getItemCount() {
        if (CollectionValidations.IsEmpty(locations))
            return 0;
        return locations.size();
    }
}
