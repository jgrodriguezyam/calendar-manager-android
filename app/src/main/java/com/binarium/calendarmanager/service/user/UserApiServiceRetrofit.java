package com.binarium.calendarmanager.service.user;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.user.AddImageUserResponse;
import com.binarium.calendarmanager.dto.user.ChangeUserPasswordRequest;
import com.binarium.calendarmanager.dto.user.LoginUserRequest;
import com.binarium.calendarmanager.dto.user.LoginUserResponse;
import com.binarium.calendarmanager.dto.user.UserRequest;
import com.binarium.calendarmanager.dto.user.UserResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public interface UserApiServiceRetrofit {
    @POST("/users")
    Call<CreateResponse> create(@Body UserRequest request);
    @PUT("/users")
    Call<SuccessResponse> update(@Body UserRequest request);
    @GET("/users/{Id}")
    Call<UserResponse> get(@Path("Id") int id);
    @DELETE("/users/{Id}")
    Call<SuccessResponse> delete(@Path("Id") int id);
    @POST("/users/login")
    Call<LoginUserResponse> login(@Body LoginUserRequest request);
    @POST("/users/logout/{Id}")
    Call<SuccessResponse> logout(@Path("Id") int id);
    @POST("/users/change-password")
    Call<SuccessResponse> changePassword(@Body ChangeUserPasswordRequest request);
    @Multipart
    @POST("/users/{Id}/file")
    Call<AddImageUserResponse> addImage(@Path("Id") int id, @Part MultipartBody.Part filePart);
}