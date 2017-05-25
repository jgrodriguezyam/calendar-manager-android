package com.binarium.calendarmanager.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.dto.location.RadiusResponse;
import com.binarium.calendarmanager.dto.location.TypeResponse;
import com.binarium.calendarmanager.fragment.listener.FormLocationDialogListener;
import com.binarium.calendarmanager.fragment.listener.DatePickerDialogListener;
import com.binarium.calendarmanager.infrastructure.CollectionValidations;
import com.binarium.calendarmanager.infrastructure.Constants;
import com.binarium.calendarmanager.infrastructure.DateExtensions;
import com.binarium.calendarmanager.infrastructure.EditTextExtensions;
import com.binarium.calendarmanager.infrastructure.IntegerValidations;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.infrastructure.enums.LocationType;
import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

/**
 * Created by jrodriguez on 23/05/2017.
 */

public class FormLocationDialogFragment extends DialogFragment implements OnClickListener, TextWatcher, OnItemSelectedListener, DatePickerDialogListener, OnTouchListener {
    @Bind(R.id.tl_location_name)
    TextInputLayout tlLocationName;
    @Bind(R.id.et_location_name)
    EditText etLocationName;

    @Bind(R.id.tv_error_location_radius)
    TextView radiusErrorTextView;
    private List<RadiusResponse> radius;
    @Bind(R.id.sp_location_radius)
    MaterialSpinner radiusSpinner;

    @Bind(R.id.tv_error_location_type)
    TextView typesErrorTextView;
    private List<TypeResponse> types;
    @Bind(R.id.sp_location_type)
    MaterialSpinner typesSpinner;

    @Bind(R.id.tl_location_start_date)
    TextInputLayout tlLocationStartDate;
    @Bind(R.id.et_location_start_date)
    EditText etLocationStartDate;

    @Bind(R.id.tl_location_end_date)
    TextInputLayout tlLocationEndDate;
    @Bind(R.id.et_location_end_date)
    EditText etLocationEndDate;

    @Bind(R.id.tl_location_comment)
    TextInputLayout tlLocationComment;
    @Bind(R.id.et_location_comment)
    EditText etLocationComment;

    @Bind(R.id.btn_location_save)
    Button btnLocationSave;

    @Bind(R.id.btn_location_cancel)
    Button btnLocationCancel;

    private static final String TAG_START_DATE = "TAG_START_DATE";
    private static final String TAG_END_DATE = "TAG_END_DATE";

    private FormLocationDialogListener formLocationDialogListener;
    private static final String LOCATION = "Location";
    Location location;

    public static FormLocationDialogFragment newInstance(Location location) {
        FormLocationDialogFragment formLocationFragment = new FormLocationDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(LOCATION, location);
        formLocationFragment.setArguments(args);
        return formLocationFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        location = getArguments().getParcelable(LOCATION);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_form_location, null);
        ButterKnife.bind(this, view);
        btnLocationSave.setOnClickListener(this);
        btnLocationCancel.setOnClickListener(this);
        etLocationName.addTextChangedListener(this);
        radiusSpinner.setOnItemSelectedListener(this);
        typesSpinner.setOnItemSelectedListener(this);
        findRadius();
        findTypes();
        setSpinnersData();
        setDatesData();
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            formLocationDialogListener = (FormLocationDialogListener) getTargetFragment();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + ResourcesExtensions.toString(R.string.dialog_listener_not_implemented));
        }
    }

    //region OnClickListener

    @Override
    public void onClick(View view) {
        if (view.getId() == btnLocationSave.getId()) {
            saveLocation();
        } else {
            getDialog().dismiss();
        }
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
        if(typesSpinner.getSelectedItemPosition()!=0){
            ((TextView)typesSpinner.getSelectedView()).setError(null);
            typesErrorTextView.setVisibility(View.GONE);
        }

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

    private void findTypes() {
        types = new ArrayList<>();
        types.add(new TypeResponse(LocationType.EVENT.getIdentifier(), LocationType.EVENT.getValue()));
        types.add(new TypeResponse(LocationType.ACTIVITY.getIdentifier(), LocationType.ACTIVITY.getValue()));
        types.add(new TypeResponse(LocationType.TASK.getIdentifier(), LocationType.TASK.getValue()));
    }

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

        if(CollectionValidations.IsNotEmpty(types)) {
            ArrayAdapter<TypeResponse> typesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, types);
            typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typesSpinner.setAdapter(typesAdapter);
        }
    }

    private void setDatesData() {
        etLocationStartDate.setOnTouchListener(this);
        etLocationEndDate.setOnTouchListener(this);
    }

    public void saveLocation() {
        if(!validFields())
            return;

        RadiusResponse radiusResponse = (RadiusResponse) radiusSpinner.getSelectedItem();
        TypeResponse typeResponse = (TypeResponse) typesSpinner.getSelectedItem();
        location.setName(etLocationName.getText().toString());
        location.setRadius(radiusResponse.getId());
        location.setType(typeResponse.getId());
        location.setStartDate(etLocationStartDate.getText().toString());
        location.setEndDate(etLocationEndDate.getText().toString());
        location.setComment(etLocationComment.getText().toString());

        if (IntegerValidations.IsZero(location.getId()))
            formLocationDialogListener.createLocation(location);

        if (IntegerValidations.IsNotZero(location.getId()))
            formLocationDialogListener.updateLocation(location);

        getDialog().dismiss();
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

        if (typesSpinner.getAdapter() == null || typesSpinner.getSelectedItemPosition() == Constants.SPINNER_NOT_SET) {
            typesSpinner.setError("Error");
            typesErrorTextView.setVisibility(View.VISIBLE);
            valid = false;
        }

        if (EditTextExtensions.isFieldEmpty(etLocationStartDate.getText())) {
            tlLocationStartDate.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if (EditTextExtensions.isFieldEmpty(etLocationEndDate.getText())) {
            tlLocationEndDate.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        return valid;
    }

    //endregion

    //region DatePickerDialogListener

    @Override
    public void setDate(DatePicker view, int year, int month, int day, String tag) {
        String date = new DateExtensions().convertToStringDate(year, month, day);
        switch (tag){
            case TAG_START_DATE:
                etLocationStartDate.setText(date);
                tlLocationStartDate.setError(null);
                tlLocationEndDate.setError(null);
                etLocationEndDate.setText("");
                break;

            case TAG_END_DATE:
                etLocationEndDate.setText(date);
                tlLocationEndDate.setError(null);
                break;
        }
    }

    //endregion

    //region OnTouchListener

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP)
            return false;

        if (view.getId() == etLocationStartDate.getId())
            setStartDate();

        if (view.getId() == etLocationEndDate.getId())
            setEndDate();

        return false;
    }

    public void setStartDate() {
        Calendar minimumDate = Calendar.getInstance();
        Calendar currentDate;
        if (EditTextExtensions.isFieldEmpty(etLocationStartDate.getText())) {
            currentDate = minimumDate;
        } else {
            currentDate = new DateExtensions().convertToCalendar(etLocationStartDate.getText().toString());
        }

        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int month = currentDate.get(Calendar.MONTH);
        int year = currentDate.get(Calendar.YEAR);

        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(minimumDate.getTime().getTime(), null, year, month, day);
        datePickerDialogFragment.setDatePickerDialogListener(this);
        datePickerDialogFragment.show(getChildFragmentManager(), TAG_START_DATE);
    }

    public void setEndDate() {
        if (EditTextExtensions.isFieldEmpty(etLocationStartDate.getText())) {
            tlLocationEndDate.setError(getResources().getString(R.string.required_location_start_date_field));
        } else {
            Calendar minimumDate = new DateExtensions().convertToCalendar(etLocationStartDate.getText().toString());
            Calendar currentDate;
            if (EditTextExtensions.isFieldEmpty(etLocationEndDate.getText())) {
                currentDate = minimumDate;
            } else {
                currentDate = new DateExtensions().convertToCalendar(etLocationEndDate.getText().toString());
            }

            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            int month = currentDate.get(Calendar.MONTH);
            int year = currentDate.get(Calendar.YEAR);

            DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(minimumDate.getTime().getTime(), null, year, month, day);
            datePickerDialogFragment.setDatePickerDialogListener(this);
            datePickerDialogFragment.show(getChildFragmentManager(), TAG_END_DATE);
        }
    }

    //endregion
}