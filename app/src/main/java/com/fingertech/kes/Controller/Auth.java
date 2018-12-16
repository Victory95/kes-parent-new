package com.fingertech.kes.Controller;

import com.fingertech.kes.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Auth {

    //////// Register Public
    @FormUrlEncoded
    @POST("auth/register")
    Call<JSONResponse>register_post(@Field("fullname") String fullname, @Field("email") String email, @Field("mobile_phone") String mobile_phone,
                                    @Field("password") String password, @Field("device_id") String device_id);

    //////// Login Public
    @FormUrlEncoded
    @POST("auth/login")
    Call<JSONResponse>login_post(@Field("email") String email, @Field("password") String password, @Field("device_id") String device_id);

    //////// Register Parent
    @FormUrlEncoded
    @POST("auth/register_orangtua")
    Call<JSONResponse>register_orangtua_post(@Field("fullname") String fullname, @Field("parent_nik") String parent_nik, @Field("email") String email,
                                             @Field("mobile_phone") String mobile_phone, @Field("password") String password, @Field("relation") String relation,
                                             @Field("gender") String gender, @Field("device_id") String device_id);

    //////// Login Parent
    @FormUrlEncoded
    @POST("auth/login")
    Call<JSONResponse>login_orangtua_post(@Field("email") String email, @Field("password") String password, @Field("device_id") String device_id);

    //////// Register Sosmed
    @FormUrlEncoded
    @POST("auth/register_sosmed")
    Call<JSONResponse>register_sosmed_post(@Field("email") String email, @Field("fullname") String fullname, @Field("fg_code") String fg_code,
                                           @Field("device_id") String device_id);

    //////// Login Sosmed
    @FormUrlEncoded
    @POST("auth/login_sosmed")
    Call<JSONResponse>login_sosmed_post(@Field("fg_code") String fg_code, @Field("device_id") String device_id);

    //////// Forgot password
    @FormUrlEncoded
    @POST("auth/forgot_password")
    Call<JSONResponse>forgot_password_post(@Field("email") String email);

    //////// Request code acsess
    @FormUrlEncoded
    @POST("auth/request_code_acsess")
    Call<JSONResponse>request_code_acsess_post(@Header("Authorization") String authorization, @Field("email") String email, @Field("fullname") String fullname, @Field("parent_id") String parent_id,
                                               @Field("student_id") String student_id, @Field("school_id") String school_id);

    //////// search_school
    @FormUrlEncoded
    @POST("auth/search_school")
    Call<JSONResponse.School>search_school_post(@Field("key") String key);

    /////////////////////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @PUT("update")
    Call<JSONResponse> putPublic(@Field("memberid") String memberid, @Field("fullname") String fullname, @Field("email") String email,
                                 @Field("mobile_phone") String mobile_phone);
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "public", hasBody = true)
    Call<JSONResponse> deletePublic(@Field("memberid") String memberid);
}