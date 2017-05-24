package com.binarium.calendarmanager.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.dto.location.RadiusResponse;
import com.binarium.calendarmanager.fragment.listener.CreateLocationDialogListener;
import com.binarium.calendarmanager.infrastructure.CollectionValidations;
import com.binarium.calendarmanager.infrastructure.Constants;
import com.binarium.calendarmanager.infrastructure.EditTextExtensions;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

import android.view.View.OnClickListener;

/**
 * Created by jrodriguez on 23/05/2017.
 */

public class CreateLocationDialogFragment extends DialogFragment implements OnClickListener, TextWatcher, OnItemSelectedListener {
    @Bind(R.id.tl_location_name)
    public TextInputLayout tlLocationName;
    @Bind(R.id.et_location_name)
    public EditText etLocationName;

    @Bind(R.id.tv_error_location_radius)
    public TextView radiusErrorTextView;
    private List<RadiusResponse> radius;
    @Bind(R.id.sp_location_radius)
    public MaterialSpinner radiusSpinner;

    @Bind(R.id.tl_location_start_date)
    public TextInputLayout tlLocationStartDate;
    @Bind(R.id.et_location_start_date)
    public EditText etLocationStartDate;

    @Bind(R.id.tl_location_end_date)
    public TextInputLayout tlLocationEndDate;
    @Bind(R.id.et_location_end_date)
    public EditText etLocationEndDate;

    @Bind(R.id.tl_location_comment)
    public TextInputLayout tlLocationComment;
    @Bind(R.id.et_location_comment)
    public EditText etLocationComment;

    @Bind(R.id.btn_location_save)
    Button btnLocationSave;

    private CreateLocationDialogListener createLocationDialogListener;
    private static final String LOCATION = "Location";
    Location location;

    public static CreateLocationDialogFragment newInstance(Location location) {
        CreateLocationDialogFragment createlocationFragment = new CreateLocationDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(LOCATION, location);
        createlocationFragment.setArguments(args);
        return createlocationFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        location = getArguments().getParcelable(LOCATION);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_create_location, null);
        ButterKnife.bind(this, view);
        btnLocationSave.setOnClickListener(this);
        etLocationName.addTextChangedListener(this);
        radiusSpinner.setOnItemSelectedListener(this);
        findRadius();
        setSpinnersData();
        builder.setView(view);
        return builder.create();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            createLocationDialogListener = (CreateLocationDialogListener) getTargetFragment();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + ResourcesExtensions.toString(R.string.dialog_listener_not_implemented));
        }
    }

    //region OnClickListener

    @Override
    public void onClick(View v) {
        createLocation();
    }

    //endregion

    //region TextWatcher

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(EditTextExtensions.isFieldNotEmpty(etLocationName.getText())) {
            tlLocationName.setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    //endregion

    //region OnItemSelectedListener

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(radiusSpinner.getSelectedItemPosition()!=0){
            ((TextView)radiusSpinner.getSelectedView()).setError(null);
            radiusErrorTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //endregion

    //region Custom Methods

    private void findRadius() {
        radius = new ArrayList<>();
        for(int x = ResourcesExtensions.toInt(R.integer.geofence_minimum_radius); x <= ResourcesExtensions.toInt(R.integer.geofence_maximum_radius); x++) {
            radius.add(new RadiusResponse(x, String.valueOf(x)));
        }
    }

    private void setSpinnersData() {
        if(CollectionValidations.IsNotEmpty(radius)) {
            ArrayAdapter<RadiusResponse> radiusAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, radius);
            radiusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            radiusSpinner.setAdapter(radiusAdapter);
        }
    }

    public void createLocation() {
        if(validFields()) {

            createLocationDialogListener.createLocation(location);
        }
    }

    private boolean validFields() {
        boolean valid = true;

        if (EditTextExtensions.isFieldEmpty(etLocationName.getText())) {
            tlLocationName.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if (radiusSpinner.getAdapter() == null || radiusSpinner.getSelectedItemPosition() == Constants.SPINNER_NOT_SET) {
            radiusSpinner.setError("Error");
            radiusErrorTextView.setVisibility(View.VISIBLE);
            valid = false;
        }

        return valid;
    }

    //endregion
}
