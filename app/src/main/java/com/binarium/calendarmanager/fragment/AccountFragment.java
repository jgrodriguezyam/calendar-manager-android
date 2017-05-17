package com.binarium.calendarmanager.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.activity.LoginActivity;
import com.binarium.calendarmanager.dto.user.GenderResponse;
import com.binarium.calendarmanager.infrastructure.CollectionValidations;
import com.binarium.calendarmanager.infrastructure.Constants;
import com.binarium.calendarmanager.infrastructure.EditTextExtensions;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.SnackBarExtensions;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.infrastructure.enums.Gender;
import com.binarium.calendarmanager.interfaces.account.AccountView;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.presenters.AccountPresenterImpl;
import com.binarium.calendarmanager.viewmodels.user.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

public class AccountFragment extends Fragment implements AccountView, OnClickListener, TextWatcher {
    @Bind(R.id.tl_user_first_name)
    public TextInputLayout tlUserFirstName;
    @Bind(R.id.et_user_first_name)
    public EditText etUserFirstName;

    @Bind(R.id.tl_user_last_name)
    public TextInputLayout tlUserLastName;
    @Bind(R.id.et_user_last_name)
    public EditText etUserLastName;

    @Bind(R.id.tv_error_user_gender)
    public TextView gendersErrorTextView;
    private List<GenderResponse> genders;
    @Bind(R.id.sp_user_gender)
    public MaterialSpinner gendersSpinner;

    @Bind(R.id.tl_user_email)
    public TextInputLayout tlUserEmail;
    @Bind(R.id.et_user_email)
    public EditText etUserEmail;

    @Bind(R.id.et_user_cell_number)
    public EditText etUserCellNumber;
    @Bind(R.id.tl_user_cell_number)
    public TextInputLayout tlUserCellNumber;

    @Bind(R.id.et_user_username)
    public EditText etUserUsername;
    @Bind(R.id.tl_user_username)
    public TextInputLayout tlUserUsername;

    @Bind(R.id.et_user_first_password)
    public EditText etUserFirstPassword;
    @Bind(R.id.tl_user_first_password)
    public TextInputLayout tlUserFirstPassword;

    @Bind(R.id.et_user_second_password)
    public EditText etUserSecondPassword;
    @Bind(R.id.tl_user_second_password)
    public TextInputLayout tlUserSecondPassword;

    private ProgressDialog progressDialog;

    @Bind(R.id.btn_user_save)
    Button btnUserSave;

    @Inject
    AccountPresenterImpl accountPresenter;

    public AccountFragment() {

    }

    public static AccountFragment newInstance() {
        AccountFragment accountFragment = new AccountFragment();
        Bundle args = new Bundle();
        accountFragment.setArguments(args);
        return accountFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectorUtils.getInjector(this).inject(this);
        accountPresenter.setAccountView(this);
        progressDialog = Util.createModalProgressDialog(getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ObjectValidations.IsNotNull(savedInstanceState)) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account  , container, false);
        ButterKnife.bind(this, root);
        btnUserSave.setOnClickListener(this);
        etUserFirstName.addTextChangedListener(this);
        etUserLastName.addTextChangedListener(this);
        etUserEmail.addTextChangedListener(this);
        etUserCellNumber.addTextChangedListener(this);
        etUserUsername.addTextChangedListener(this);
        etUserFirstPassword.addTextChangedListener(this);
        etUserSecondPassword.addTextChangedListener(this);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gendersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(gendersSpinner.getSelectedItemPosition()!=0){
                    ((TextView)gendersSpinner.getSelectedView()).setError(null);
                    gendersErrorTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        findGenders();
        setSpinnersData();
    }

    //region AccountView

    @Override
    public void showProgress(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showErrorMessage(String message) {
        SnackBarExtensions.showErrorMessage(getView(), message);
    }

    @Override
    public void showSuccessMessage(String message) {
        SnackBarExtensions.showSuccessMessage(getView(), message);
    }

    @Override
    public void createUser() {
        if(validFields()) {
            GenderResponse genderResponse = (GenderResponse) gendersSpinner.getSelectedItem();
            User user = new User();
            user.setFirstName(etUserFirstName.getText().toString());
            user.setLastName(etUserLastName.getText().toString());
            user.setGenderType(genderResponse.getId());
            user.setEmail(etUserEmail.getText().toString());
            user.setCellNumber(Long.parseLong(etUserCellNumber.getText().toString()));
            user.setUserName(etUserUsername.getText().toString());
            user.setPassword(etUserFirstPassword.getText().toString());
            accountPresenter.createUser(user);
        } else {
            SnackBarExtensions.showErrorMessage(getView(), getResources().getString(R.string.required_fields));
        }
    }

    @Override
    public void navigateToLogin() {
        Util.sendAndFinish(getActivity(), LoginActivity.class);
    }

    //endregion

    //region OnClickListener

    @Override
    public void onClick(View v) {
        createUser();
    }

    //endregion

    //region TextWatcher

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(EditTextExtensions.isFieldNotEmpty(etUserFirstName.getText())) {
            tlUserFirstName.setError(null);
        }

        if(EditTextExtensions.isFieldNotEmpty(etUserLastName.getText())) {
            tlUserLastName.setError(null);
        }

        if(EditTextExtensions.isFieldNotEmpty(etUserEmail.getText())) {
            tlUserEmail.setError(null);
        }

        if(EditTextExtensions.isFieldNotEmpty(etUserCellNumber.getText())) {
            tlUserCellNumber.setError(null);
        }

        if(EditTextExtensions.isFieldNotEmpty(etUserUsername.getText())) {
            tlUserUsername.setError(null);
        }

        if(EditTextExtensions.isFieldNotEmpty(etUserFirstPassword.getText())) {
            tlUserFirstPassword.setError(null);
        }

        if(EditTextExtensions.isFieldNotEmpty(etUserSecondPassword.getText())) {
            tlUserSecondPassword.setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //endregion

    //region Custom Methods

    private void setSpinnersData() {
        if(CollectionValidations.IsNotEmpty(genders)) {
            ArrayAdapter<GenderResponse> gendersAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, genders);
            gendersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gendersSpinner.setAdapter(gendersAdapter);
        }
    }

    private boolean validFields() {
        boolean valid = true;

        if (EditTextExtensions.isFieldEmpty(etUserFirstName.getText())) {
            tlUserFirstName.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if (EditTextExtensions.isFieldEmpty(etUserLastName.getText())) {
            tlUserLastName.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if (gendersSpinner.getAdapter() == null || gendersSpinner.getSelectedItemPosition() == Constants.SPINNER_NOT_SET) {
            gendersSpinner.setError("Error");
            gendersErrorTextView.setVisibility(View.VISIBLE);
            valid = false;
        }

        if (EditTextExtensions.isFieldEmpty(etUserEmail.getText())) {
            tlUserEmail.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if (EditTextExtensions.isFieldEmpty(etUserCellNumber.getText())) {
            tlUserCellNumber.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if (EditTextExtensions.isFieldEmpty(etUserUsername.getText())) {
            tlUserUsername.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if (EditTextExtensions.isFieldEmpty(etUserFirstPassword.getText())) {
            tlUserFirstPassword.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if(EditTextExtensions.isFieldEmpty(etUserSecondPassword.getText())) {
            tlUserSecondPassword.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if (EditTextExtensions.isFieldNotEmpty(etUserFirstPassword.getText()) &&
            EditTextExtensions.isFieldNotEmpty(etUserSecondPassword.getText()) &&
            EditTextExtensions.IsFieldsNotEqual(etUserFirstPassword.getText(), etUserSecondPassword.getText())) {
            tlUserFirstPassword.setError(getResources().getString(R.string.invalid_compare_password_fields));
            tlUserSecondPassword.setError(getResources().getString(R.string.invalid_compare_password_fields));
            valid = false;
        }

        return valid;
    }

    private void findGenders() {
        this.genders = new ArrayList<>();
        genders.add(new GenderResponse(Gender.MALE.getIdentifier(), Gender.MALE.getValue()));
        genders.add(new GenderResponse(Gender.FEMALE.getIdentifier(), Gender.FEMALE.getValue()));
    }

    private boolean invalidComparePassword(String firstPassword, String secondPassword) {
        return firstPassword == secondPassword;
    }

    //endregion
}
