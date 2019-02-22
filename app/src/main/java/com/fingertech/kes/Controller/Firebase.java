package com.fingertech.kes.Controller;

import com.fingertech.kes.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Firebase {

    @FormUrlEncoded
    @POST("firebase.php")
    Call<JSONResponse> send_notification(@Field("token") String token);
}
