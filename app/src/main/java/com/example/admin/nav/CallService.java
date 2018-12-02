package com.example.admin.nav;

import android.arch.lifecycle.ReportFragment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CallService {
    @POST(value = "/hello")
    @Headers("content-type: application/json")
    Call<ResponseBody> getTextFromImage(@Body ImageData image);

    @POST(value = "/image-data/get-all")
    @Headers("content-type: application/json")
    Call<ResponseBody> getAllImageData();

    @POST(value = "/image-data/get/{id}")
    @Headers("content-type: application/json")
    Call<ResponseBody> getOneImageData(@Path("id") Long id);
}
