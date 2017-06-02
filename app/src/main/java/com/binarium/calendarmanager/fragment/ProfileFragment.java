package com.binarium.calendarmanager.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.infrastructure.SnackBarExtensions;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.infrastructure.enums.Gender;
import com.binarium.calendarmanager.interfaces.profile.ProfileView;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.presenters.ProfilePresenterImpl;
import com.binarium.calendarmanager.viewmodels.user.User;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 01/06/2017.
 */

public class ProfileFragment extends Fragment implements ProfileView {
    @Bind(R.id.iv_user_gender)
    ImageView ivUserGender;

    @Bind(R.id.tv_user_gender)
    TextView tvUserGender;

    @Bind(R.id.tv_user_email)
    TextView tvUserEmail;

    @Bind(R.id.tv_user_cell_number)
    TextView tvUserCellNumber;

    @Bind(R.id.tv_user_username)
    TextView tvUserUsername;

    private ProgressDialog progressDialog;

    @Inject
    ProfilePresenterImpl profilePresenter;

    private static final String USER = "User";
    User user;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        profileFragment.setArguments(args);
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectorUtils.getInjector(this).inject(this);
        profilePresenter.setProfileView(this);
        progressDialog = Util.createModalProgressDialog(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(USER, user);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);
//        btnUserLogin.setOnClickListener(this);
//        etUserName.addTextChangedListener(this);
//        etUserPassword.addTextChangedListener(this);
//        linkCreateAccount.setOnClickListener(this);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ObjectValidations.IsNotNull(savedInstanceState)) {
            user = savedInstanceState.getParcelable(USER);
            showFormLocation();
        } else {
            profilePresenter.getUser(Preferences.getUserId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //region ProfileView

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
    public void getUserSuccess(User user) {
        this.user = user;
        showFormLocation();
    }

    //endregion

    //region Custom Methods

    private void showFormLocation() {
        int genderImage = R.drawable.ic_human_male_light;
        String gender = Gender.MALE.getValue();
        if (user.getGenderType() == Gender.FEMALE.getIdentifier()) {
            genderImage = R.drawable.ic_human_female_light;
            gender = Gender.FEMALE.getValue();
        }
        ivUserGender.setBackgroundResource(genderImage);
        tvUserGender.setText(gender);
        tvUserEmail.setText(user.getEmail());
        tvUserCellNumber.setText(String.valueOf(user.getCellNumber()));
        tvUserUsername.setText(user.getUserName());
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(user.getFirstName() + " " + user.getLastName());
    }

    //endregion
}