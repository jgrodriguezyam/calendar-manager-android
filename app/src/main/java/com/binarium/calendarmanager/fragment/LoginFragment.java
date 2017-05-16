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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.activity.AccountActivity;
import com.binarium.calendarmanager.activity.MapActivity;
import com.binarium.calendarmanager.infrastructure.EditTextExtensions;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.SnackBarExtensions;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.interfaces.login.LoginView;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.presenters.LoginPresenterImpl;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public class LoginFragment extends Fragment implements LoginView, OnClickListener, TextWatcher {
    @Bind(R.id.tl_user_name)
    public TextInputLayout tlUserName;
    @Bind(R.id.et_user_name)
    public EditText etUserName;

    @Bind(R.id.tl_user_password)
    public TextInputLayout tlUserPassword;
    @Bind(R.id.et_user_password)
    public EditText etUserPassword;

    @Bind(R.id.btn_user_login)
    Button btnUserLogin;

    @Bind(R.id.link_create_account)
    TextView linkCreateAccount;

    private ProgressDialog progressDialog;

    @Inject
    LoginPresenterImpl loginPresenter;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        Bundle args = new Bundle();
        loginFragment.setArguments(args);
        return loginFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectorUtils.getInjector(this).inject(this);
        loginPresenter.setLoginView(this);
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
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, root);
        btnUserLogin.setOnClickListener(this);
        etUserName.addTextChangedListener(this);
        etUserPassword.addTextChangedListener(this);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //region LoginView

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
    public void userLogin() {
        if(validFields()) {
            String userName = etUserName.getText().toString();
            String password = etUserPassword.getText().toString();
            loginPresenter.userLogin(userName, password);
        } else {
            SnackBarExtensions.showErrorMessage(getView(), getResources().getString(R.string.required_fields));
        }
    }

    @Override
    public void navigateToMap() {
        Util.sendAndFinish(getActivity(), MapActivity.class);
    }

    @Override
    public void navigateToAccount() {
        Util.sendAndFinish(getActivity(), AccountActivity.class);
    }

    //endregion

    //region OnClickListener

    @Override
    public void onClick(View view) {
        userLogin();
    }

    //endregion

    //region TextWatcher

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(EditTextExtensions.isFieldNotEmpty(etUserName.getText())) {
            tlUserName.setError(null);
        }

        if(EditTextExtensions.isFieldNotEmpty(etUserPassword.getText())) {
            tlUserPassword.setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //endregion

    //region Custom Methods

    private boolean validFields() {
        boolean valid = true;

        if(EditTextExtensions.isFieldEmpty(etUserName.getText())) {
            tlUserName.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        if(EditTextExtensions.isFieldEmpty(etUserPassword.getText())) {
            tlUserPassword.setError(getResources().getString(R.string.required_field));
            valid = false;
        }

        return valid;
    }

    //endregion
}
