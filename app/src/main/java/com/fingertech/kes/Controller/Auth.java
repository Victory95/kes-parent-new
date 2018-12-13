package com.fingertech.kes.Controller;

import com.fingertech.kes.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Auth {

    /////////////////////////////////////////////////////////////////////////////////////////////
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


    /////////////////////////////////////////////////////////////////////////////////////////////
    //////// Register Parent
    @FormUrlEncoded
    @POST("auth/register_orangtua")
    Call<JSONResponse>register_orangtua_post(@Field("fullname") String fullname,
                                             @Field("parent_nik") String parent_nik,
                                             @Field("email") String email,
                                             @Field("mobile_phone") String mobile_phone,
                                             @Field("password") String password,
                                             @Field("relation") String relation,
                                             @Field("gender") String gender,
                                             @Field("device_id") String device_id);

    /////////////////////////////////////////////////////////////////////////////////////////////
    //////// Update Parent
    @FormUrlEncoded
    @POST("auth/update_orangtua")
    Call<JSONResponse>update_orangtua_get(@Field("fullname") String fullname,
                                             @Field("parent_name") String parent_name,
                                             @Field("parent_nik") String parent_nik,
                                             @Field("parent_birth_place") String parent_birth_place,
                                             @Field("parent_birth_date") String parent_birth_date,
                                             @Field("parent_type") String parent_type,
                                             @Field("device_id") String device_id);

    //////// Login Parent
    @FormUrlEncoded
    @POST("auth/login")
    Call<JSONResponse>login_orangtua_post(@Field("email") String email,
                                          @Field("password") String password,
                                          @Field("device_id") String device_id);


    /////////////////////////////////////////////////////////////////////////////////////////////
    //////// Register Sosmed
    @FormUrlEncoded
    @POST("auth/register_sosmed")
    Call<JSONResponse>register_sosmed_post(@Field("email") String email,
                                           @Field("fullname") String fullname,
                                           @Field("fg_code") String fg_code,
                                           @Field("device_id") String device_id);

    //////// Login Sosmed
    @FormUrlEncoded
    @POST("auth/login_sosmed")
    Call<JSONResponse>login_sosmed_post(@Field("fg_code") String fg_code,
                                        @Field("device_id") String device_id);

    /////////////////////////////////////////////////////////////////////////////////////////////
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