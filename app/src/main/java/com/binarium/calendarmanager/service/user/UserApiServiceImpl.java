package com.binarium.calendarmanager.service.user;

import android.graphics.Bitmap;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.user.AddImageUserRequest;
import com.binarium.calendarmanager.dto.user.AddImageUserResponse;
import com.binarium.calendarmanager.dto.user.ChangeUserPasswordRequest;
import com.binarium.calendarmanager.dto.user.DeleteUserRequest;
import com.binarium.calendarmanager.dto.user.GetUserRequest;
import com.binarium.calendarmanager.dto.user.LoginUserRequest;
import com.binarium.calendarmanager.dto.user.LoginUserResponse;
import com.binarium.calendarmanager.dto.user.LogoutUserRequest;
import com.binarium.calendarmanager.dto.user.UserRequest;
import com.binarium.calendarmanager.dto.user.UserResponse;
import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.service.retrofitconfig.RetrofitBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class UserApiServiceImpl implements UserApiService {
    @Override
    public CreateResponse create(UserRequest request, BaseListener baseListener) {
        try {
            UserApiServiceRetrofit userApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(UserApiServiceRetrofit.class);
            Call<CreateResponse> call = userApiServiceRetrofit.create(request);
            Response<CreateResponse> response = call.execute();

            if (response.isSuccessful()) {
                CreateResponse createResponse = response.body();
                return createResponse;
            } else {
                String errorMessage = RetrofitBuilder.getErrorMessage(response.errorBody());
                baseListener.onError(errorMessage);
                return null;
            }
        } catch (Exception e) {
            baseListener.onError(e.getMessage());
            return null;
        }
    }

    @Override
    public SuccessResponse update(UserRequest request, BaseListener baseListener) {
        try {
            UserApiServiceRetrofit userApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(UserApiServiceRetrofit.class);
            Call<SuccessResponse> call = userApiServiceRetrofit.update(request);
            Response<SuccessResponse> response = call.execute();

            if (response.isSuccessful()) {
                SuccessResponse successResponse = response.body();
                return successResponse;
            } else {
                String errorMessage = RetrofitBuilder.getErrorMessage(response.errorBody());
                baseListener.onError(errorMessage);
                return null;
            }
        } catch (Exception e) {
            baseListener.onError(e.getMessage());
            return null;
        }
    }

    @Override
    public UserResponse get(GetUserRequest request, BaseListener baseListener) {
        try {
            UserApiServiceRetrofit userApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(UserApiServiceRetrofit.class);
            Call<UserResponse> call = userApiServiceRetrofit.get(request.getId());
            Response<UserResponse> response = call.execute();

            if (response.isSuccessful()) {
                UserResponse userResponse = response.body();
                return userResponse;
            } else {
                String errorMessage = RetrofitBuilder.getErrorMessage(response.errorBody());
                baseListener.onError(errorMessage);
                return null;
            }
        } catch (Exception e) {
            baseListener.onError(e.getMessage());
            return null;
        }
    }

    @Override
    public SuccessResponse delete(DeleteUserRequest request, BaseListener baseListener) {
        try {
            UserApiServiceRetrofit userApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(UserApiServiceRetrofit.class);
            Call<SuccessResponse> call = userApiServiceRetrofit.delete(request.getId());
            Response<SuccessResponse> response = call.execute();

            if (response.isSuccessful()) {
                SuccessResponse successResponse = response.body();
                return successResponse;
            } else {
                String errorMessage = RetrofitBuilder.getErrorMessage(response.errorBody());
                baseListener.onError(errorMessage);
                return null;
            }
        } catch (Exception e) {
            baseListener.onError(e.getMessage());
            return null;
        }
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request, BaseListener baseListener) {
        try {
            UserApiServiceRetrofit userApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(UserApiServiceRetrofit.class);
            Call<LoginUserResponse> call = userApiServiceRetrofit.login(request);
            Response<LoginUserResponse> response = call.execute();

            if (response.isSuccessful()) {
                LoginUserResponse loginUserResponse = response.body();
                return loginUserResponse;
            } else {
                String errorMessage = RetrofitBuilder.getErrorMessage(response.errorBody());
                baseListener.onError(errorMessage);
                return null;
            }
        } catch (Exception e) {
            baseListener.onError(e.getMessage());
            return null;
        }
    }

    @Override
    public SuccessResponse logout(LogoutUserRequest request, BaseListener baseListener) {
        try {
            UserApiServiceRetrofit userApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(UserApiServiceRetrofit.class);
            Call<SuccessResponse> call = userApiServiceRetrofit.logout(request.getId());
            Response<SuccessResponse> response = call.execute();

            if (response.isSuccessful()) {
                SuccessResponse successResponse = response.body();
                return successResponse;
            } else {
                String errorMessage = RetrofitBuilder.getErrorMessage(response.errorBody());
                baseListener.onError(errorMessage);
                return null;
            }
        } catch (Exception e) {
            baseListener.onError(e.getMessage());
            return null;
        }
    }

    @Override
    public SuccessResponse changePassword(ChangeUserPasswordRequest request, BaseListener baseListener) {
        try {
            UserApiServiceRetrofit userApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(UserApiServiceRetrofit.class);
            Call<SuccessResponse> call = userApiServiceRetrofit.changePassword(request);
            Response<SuccessResponse> response = call.execute();

            if (response.isSuccessful()) {
                SuccessResponse successResponse = response.body();
                return successResponse;
            } else {
                String errorMessage = RetrofitBuilder.getErrorMessage(response.errorBody());
                baseListener.onError(errorMessage);
                return null;
            }
        } catch (Exception e) {
            baseListener.onError(e.getMessage());
            return null;
        }
    }

    @Override
    public AddImageUserResponse addImage(AddImageUserRequest request, Bitmap bitmapFile, BaseListener baseListener) {
        try {
            File file = new File("");

                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

            UserApiServiceRetrofit userApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(UserApiServiceRetrofit.class);
//            Call<SuccessResponse> call = userApiServiceRetrofit.changePassword(request);
//            Response<SuccessResponse> response = call.execute();
//
//            if (response.isSuccessful()) {
//                SuccessResponse successResponse = response.body();
//                return successResponse;
//            } else {
//                String errorMessage = RetrofitBuilder.getErrorMessage(response.errorBody());
//                baseListener.onError(errorMessage);
//                return null;
//            }
            return null;
        } catch (Exception e) {
            baseListener.onError(e.getMessage());
            return null;
        }
    }

    private File convertBitmapToFile(Bitmap bitmap) {
//        File f = new File(context.getCacheDir(), "prieba");
//        f.createNewFile();
//
//        Bitmap bitmap = your bitmap;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//        byte[] bitmapdata = bos.toByteArray();
//
////write the bytes in file
//        FileOutputStream fos = new FileOutputStream(f);
//        fos.write(bitmapdata);
//        fos.flush();
//        fos.close();
        return null;
    }
}
