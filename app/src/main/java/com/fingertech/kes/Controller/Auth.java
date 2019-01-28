package com.fingertech.kes.Controller;

import android.graphics.Bitmap;

import com.fingertech.kes.Rest.JSONResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Auth {

    //////// Register Public
    @FormUrlEncoded
    @POST("auth/kes_register")
    Call<JSONResponse>register_post(@Field("fullname") String fullname,
                                    @Field("email") String email,
                                    @Field("mobile_phone") String mobile_phone,
                                    @Field("password") String password,
                                    @Field("device_id") String device_id);

    //////// Login Public
    @FormUrlEncoded
    @POST("auth/kes_login")
    Call<JSONResponse>login_post(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("device_id") String device_id);

    //////// Register Parent
    @FormUrlEncoded
    @POST("auth/kes_register_orangtua")
    Call<JSONResponse>register_orangtua_post(@Field("fullname") String fullname,
                                             @Field("parent_nik") String parent_nik,
                                             @Field("email") String email,
                                             @Field("mobile_phone") String mobile_phone,
                                             @Field("password") String password,
                                             @Field("relation") String relation,
                                             @Field("gender") String gender,
                                             @Field("device_id") String device_id);

    //////// Update Parent
    @FormUrlEncoded
    @POST("auth/kes_update_orangtua")
    Call<JSONResponse>update_orangtua_get(@Field("fullname") String fullname, @Field("parent_nik") String parent_nik, @Field("parent_birth_place") String parent_birth_place,
                                          @Field("parent_birth_date") String parent_birth_date, @Field("parent_type") String parent_type);

    //////// Login Parent
    @FormUrlEncoded
    @POST("auth/kes_login")
    Call<JSONResponse>login_orangtua_post(@Field("email") String email,
                                          @Field("password") String password,
                                          @Field("device_id") String device_id);

    //////// Register Sosmed
    @FormUrlEncoded
    @POST("auth/kes_register_sosmed")
    Call<JSONResponse>register_sosmed_post(@Field("email") String email,
                                           @Field("fullname") String fullname,
                                           @Field("fg_code") String fg_code,
                                           @Field("device_id") String device_id);

    //////// Login Sosmed
    @FormUrlEncoded
    @POST("auth/kes_login_sosmed")
    Call<JSONResponse>login_sosmed_post(@Field("fg_code") String fg_code,
                                        @Field("device_id") String device_id);

    //////// Forgot password
    @FormUrlEncoded
    @POST("auth/kes_forgot_password")
    Call<JSONResponse>forgot_password_post(@Field("email") String email);

    //////// Request code acsess
    @FormUrlEncoded
    @POST("auth/kes_request_code_acsess")
    Call<JSONResponse>request_code_acsess_post(@Header("Authorization") String authorization,
                                               @Field("email") String email,
                                               @Field("fullname") String fullname,
                                               @Field("parent_id") String parent_id,
                                               @Field("student_id") String student_id,
                                               @Field("school_id") String school_id);

    //////// search_school
    @FormUrlEncoded
    @POST("auth/kes_search_school")
    Call<JSONResponse.School>search_school_post(@Field("key") String key);

    //////// check_student_nik
    @FormUrlEncoded
    @POST("auth/kes_check_student_nik")
    Call<JSONResponse.Check_Student_NIK>check_student_nik_post(@Header("Authorization") String authorization,
                                                               @Field("parent_id") String parent_id,
                                                               @Field("children_nik") String children_nik,
                                                               @Field("school_code") String school_code);

    //////// check_school_kes
    @FormUrlEncoded
    @POST("auth/kes_check_school_kes")
    Call<JSONResponse>check_school_kes_post(@Header("Authorization") String authorization,
                                            @Field("school_code") String school_code);

    //////// masuk_code_acsess
    @FormUrlEncoded
    @POST("auth/kes_masuk_code_acsess")
    Call<JSONResponse.Masuk_code_acsess>masuk_code_acsess_post(@Header("Authorization") String authorization,
                                                               @Field("verification_code") String verification_code,
                                                               @Field("parent_id") String parent_id,
                                                               @Field("student_id") String student_id,
                                                               @Field("student_nik") String student_nik,
                                                               @Field("school_id") String school_id);

    //////// data_parent_student
    @GET("parents/kes_data_parent_student")
    Call<JSONResponse.Data_parent_student>data_parent_student_get(@Header("Authorization") String authorization,
                                                                  @Query("school_code") String school_code,
                                                                  @Query("parent_nik") String parent_nik,
                                                                  @Query("student_id") String student_id);

    /////////////////////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @PUT("kes_update/{memberid}")
    Call<JSONResponse> kes_update_put(@Header("Authorization") String authorization,
                                     @Path("memberid") String memberid,
                                     @Field("fullname") String fullname,
                                     @Field("email") String email,
                                     @Field("mobile_phone") String mobile_phone,
                                     @Field("religion") String religion,
                                     @Field("gender") String gender,
                                     @Field("lastupdate") String lastupdate);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "public", hasBody = true)
    Call<JSONResponse> deletePublic(@Field("memberid") String memberid);

    @FormUrlEncoded
    @POST("auth/kes_nearby_radius")
    Call<JSONResponse.Nearby_School>nearby_radius_post(@Field("latitude") Double latitude,
                                                       @Field("longitude") Double longitude,
                                                       @Field("radius") Double radius);

    @GET("school/kes_school_onprov")
    Call<JSONResponse.School_Provinsi>school_onprov_get(@Query("id_prov") String id_prov,
                                                        @Query("jp") String jp);


    @GET("school/kes_provinsi")
    Call<JSONResponse.Provinsi>provinsi_get();

    @GET("auth/kes_detail_school")
    Call<JSONResponse.DetailSchool>detail_school_get(@Query("sch") String sch);

    //////// search_school
    @FormUrlEncoded
    @POST("auth/kes_delete_verification")
    Call<JSONResponse.DeleteCode>delete_verification_post(@Field("verification_code") String verification_code);

    @FormUrlEncoded
    @PUT("parents/kes_update_parent/{studentparentid}")
    Call<JSONResponse>update_parent_put(@Header("Authorization") String authorization,
                                        @Path("studentparentid")String studentparentid,
                                        @Field("school_code") String school_code,
                                        @Field("student_id") String student_id,
                                        @Field("parent_name") String parent_name,
                                        @Field("parent_nik") String parent_nik,
                                        @Field("parent_type") String parent_type,
                                        @Field("birth_date") String birth_date,
                                        @Field("parent_birth_place") String parent_birth_place,
                                        @Field("type_warga") String type_warga,
                                        @Field("parent_home_phone") String parent_home_phone,
                                        @Field("parent_phone") String parent_phone,
                                        @Field("parent_address") String parent_address,
                                        @Field("parent_latitude") String parent_latitude,
                                        @Field("parent_longitude") String parent_longitude,
                                        @Field("parent_email") String parent_email,
                                        @Field("parent_education") String parent_education,
                                        @Field("company_name") String company_name,
                                        @Field("employment") String employment,
                                        @Field("parent_income") String parent_income,
                                        @Field("workplace_address") String workplace_address,
                                        @Field("office_latitude") String office_latitude,
                                        @Field("office_longitude") String office_longitude);

    //////DetailStudent
    @GET("students/kes_detail_student")
    Call<JSONResponse.DetailStudent>kes_detail_student_get(@Header("Authorization") String authorization,
                                                           @Query("school_code") String school_code,
                                                           @Query("student_id") String student_id,
                                                           @Query("parent_nik") String parent_nik);

    //////GETPROFILE
    @GET("auth/kes_profile")
    Call<JSONResponse.GetProfile>kes_profile_get(@Header("Authorization") String authorization,
                                                  @Query("mem") String mem);

    @Multipart
    @POST("auth/kes_update_picture")
    Call<JSONResponse.UpdatePicture> kes_update_picture_post(@Header("Authorization") String authorization,
                                                            @Part("memberid") RequestBody memberid,
                                                            @Part MultipartBody.Part picture_old,
                                                            @Part("pic_type") RequestBody pic_type);

    @FormUrlEncoded
    @PUT("auth/kes_change_password/{memberid}")
    Call<JSONResponse>kes_change_password_post(@Header("Authorization") String authorization,
                                               @Path("memberid") String memberid,
                                               @Field("password") String password,
                                               @Field("old_pass") String old_pass);

    @FormUrlEncoded
    @PUT("auth/kes_switch_to_parent/{member_id}")
    Call<JSONResponse>kes_switch_to_parent_put(@Header("Authorization") String authorization,
                                               @Path("member_id") String member_id,
                                               @Field("nik") String nik,
                                               @Field("relation") String relation,
                                               @Field("gender") String gender,
                                               @Field("birth_date") String birth_date);

    @FormUrlEncoded
    @PUT("parents/kes_update_parent/{studentdetailid}")
    Call<JSONResponse>update_student_put(@Header("Authorization") String authorization,
                                        @Path("studentdetailid")String studentdetailid,
                                        @Field("school_code") String school_code,
                                        @Field("student_id") String student_id,
                                        @Field("rombel") String rombel,
                                        @Field("special_needs") String special_needs,
                                        @Field("rt") String rt,
                                        @Field("rw") String rw,
                                        @Field("dusun") String dusun,
                                        @Field("kelurahan") String kelurahan,
                                        @Field("parent_home_phone") String parent_home_phone,
                                        @Field("parent_phone") String parent_phone,
                                        @Field("parent_address") String parent_address,
                                        @Field("parent_latitude") String parent_latitude,
                                        @Field("parent_longitude") String parent_longitude,
                                        @Field("parent_email") String parent_email,
                                        @Field("parent_education") String parent_education,
                                        @Field("company_name") String company_name,
                                        @Field("employment") String employment,
                                        @Field("parent_income") String parent_income,
                                        @Field("workplace_address") String workplace_address,
                                        @Field("office_latitude") String office_latitude,
                                        @Field("office_longitude") String office_longitude);

    @GET("students/kes_exam_schedule")
    Call<JSONResponse.JadwalUjian>kes_exam_schedule_get(@Header("Authorization") String authorization,
                                                       @Query("school_code") String school_code,
                                                       @Query("student_id") String member_id,
                                                       @Query("classroom_id") String classroom_id
                                                        );


    @GET("students/kes_class_schedule")
    Call<JSONResponse.JadwalPelajaran>kes_class_schedule_get(@Header("Authorization") String authorization,
                                                        @Query("school_code") String school_code,
                                                        @Query("student_id") String student_id,
                                                        @Query("classroom_id") String classroom_id);



}