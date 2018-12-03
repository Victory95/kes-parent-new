package com.fingertech.kes.Rest;

import com.fingertech.kes.Model.JSONResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    //////// Register Public
    @FormUrlEncoded
    @POST("auth/register")
    Call<JSONResponse>register_post(@Field("fullname") String fullname,
                                    @Field("email") String email,
                                    @Field("mobile_phone") String mobile_phone,
                                    @Field("password") String password,
                                    @Field("device_id") String device_id);

    //////// Login Public
    @FormUrlEncoded
    @POST("auth/login")
    Call<JSONResponse>login_post(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("device_id") String device_id);

    //////// Register Parent
    @FormUrlEncoded
    @POST("auth/register")
    Call<JSONResponse>register_orangtua_post(@Field("fullname") String fullname,
                                   @Field("email") String email,
                                   @Field("mobile_phone") String mobile_phone,
                                   @Field("password") String password,
                                   @Field("device_id") String device_id);

    //////// Login Parent
    @FormUrlEncoded
    @POST("auth/login")
    Call<JSONResponse>login_orangtua_post(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("device_id") String device_id);




    @FormUrlEncoded
    @PUT("update")
    Call<JSONResponse> putPublic(@Field("memberid") String memberid,
                                     @Field("fullname") String fullname,
                                     @Field("email") String email,
                                     @Field("mobile_phone") String mobile_phone);
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "public", hasBody = true)
    Call<JSONResponse> deletePublic(@Field("memberid") String memberid);
}