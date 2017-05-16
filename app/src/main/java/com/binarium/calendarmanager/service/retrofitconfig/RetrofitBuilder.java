package com.binarium.calendarmanager.service.retrofitconfig;

import com.binarium.calendarmanager.dto.base.ErrorMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by link_jorge on 12/12/2016.
 */

public class RetrofitBuilder {

    private static final Integer READ_TIMEOUT = 60;
    private static final Integer CONNECT_TIMEOUT = 60;
    private static Retrofit retrofit;

    public static void Build(String url){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new NullOrEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static String getErrorMessage(ResponseBody errorBody){
        try {
            JSONObject jsonObject = new JSONObject(errorBody.string());
            ErrorMessage errorMessage = new Gson().fromJson(jsonObject.toString(), ErrorMessage.class);
            return errorMessage.getMessage();
        } catch (Exception e) {
            return "Error al convertir respuesta de error";
        }
    }
}
