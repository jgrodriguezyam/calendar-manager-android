package com.binarium.calendarmanager.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.infrastructure.SnackBarExtensions;
import com.binarium.calendarmanager.infrastructure.Util;
import com.binarium.calendarmanager.infrastructure.enums.Gender;
import com.binarium.calendarmanager.interfaces.profile.ProfileView;
import com.binarium.calendarmanager.myapp.injector.InjectorUtils;
import com.binarium.calendarmanager.presenters.ProfilePresenterImpl;
import com.binarium.calendarmanager.viewmodels.user.User;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrodriguez on 01/06/2017.
 */

public class ProfileFragment extends Fragment implements ProfileView, OnClickListener {
    @BindView(R.id.iv_user_gender)
    ImageView ivUserGender;

    @BindView(R.id.tv_user_gender)
    TextView tvUserGender;

    @BindView(R.id.tv_user_email)
    TextView tvUserEmail;

    @BindView(R.id.tv_user_cell_number)
    TextView tvUserCellNumber;

    @BindView(R.id.tv_user_username)
    TextView tvUserUsername;

    private ProgressDialog progressDialog;

    @Inject
    ProfilePresenterImpl profilePresenter;

    private static final String USER = "User";
    User user;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    TextView tvTakePhoto;
    TextView tvPhotoFile;
    FloatingActionButton fabBtnEditProfile;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == -1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String hola = "jeje";
        }
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

    //region Menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.btn_add_image:
                showChangeImageDialog();
                return true;
        }
        return false;
    }

    //endregion

    //region OnClickListener

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take_photo:
                dispatchTakePictureIntent();
                break;
            case R.id.tv_photo_file:
                String mundo = "";
                break;
            case R.id.fab_btn_edit_profile:
                String jeje = "";
                break;
            default:
                break;
        }
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
        fabBtnEditProfile = (FloatingActionButton) getActivity().findViewById(R.id.fab_btn_edit_profile);
        fabBtnEditProfile.setOnClickListener(this);
    }

    private void showChangeImageDialog() {
        View changeProfileImage = getActivity().getLayoutInflater().inflate(R.layout.change_profile_image, null);
        this.tvTakePhoto = (TextView) changeProfileImage.findViewById(R.id.tv_take_photo);
        this.tvPhotoFile = (TextView) changeProfileImage.findViewById(R.id.tv_photo_file);
        tvTakePhoto.setOnClickListener(this);
        tvPhotoFile.setOnClickListener(this);

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        builder.setView(changeProfileImage);
        builder.setTitle(ResourcesExtensions.toString(R.string.title_change_image));
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (ObjectValidations.IsNotNull(takePictureIntent.resolveActivity(getActivity().getPackageManager())))
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    //endregion
}