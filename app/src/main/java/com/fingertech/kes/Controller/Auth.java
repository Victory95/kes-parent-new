package com.fingertech.kes.Controller;

import android.graphics.Bitmap;

import com.fingertech.kes.Rest.JSONResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
                                 @Field("device_id") String device_id,
                                 @Field("firebase_token") String firebase_token);

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
                                           @Field("device_id") String device_id,
                                           @Field("firebase_token") String firebase_token);

    //////// Login Sosmed
    @FormUrlEncoded
    @POST("auth/kes_login_sosmed")
    Call<JSONResponse>login_sosmed_post(@Field("fg_code") String fg_code,
                                        @Field("device_id") String device_id,
                                        @Field("firebase_token") String firebase_token);

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

    ///// Update Member
    @FormUrlEncoded
    @PUT("auth/kes_update/{memberid}")
    Call<JSONResponse> kes_update_put(@Header("Authorization") String authorization,
                                     @Path("memberid") String memberid,
                                     @Field("email") String email,
                                     @Field("fullname") String fullname,
                                     @Field("mobile_phone") String mobile_phone,
                                     @Field("lastupdate") String lastupdate,
                                     @Field("gender") String gender,
                                     @Field("religion") String religion,
                                     @Field("birth_date") String birth_date);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "public", hasBody = true)
    Call<JSONResponse> deletePublic(@Field("memberid") String memberid);

    //// Cari sekolah per Radius
    @FormUrlEncoded
    @POST("auth/kes_nearby_radius")
    Call<JSONResponse.Nearby_School>nearby_radius_post(@Field("latitude") Double latitude,
                                                       @Field("longitude") Double longitude,
                                                       @Field("radius") Double radius);

    ///// Sekolah per Provinsi
    @GET("school/kes_school_onprov")
    Call<JSONResponse.School_Provinsi>school_onprov_get(@Query("id_prov") String id_prov,
                                                        @Query("jp") String jp);


    ///// Provinsi
    @GET("school/kes_provinsi")
    Call<JSONResponse.Provinsi>provinsi_get();

    //// Detail Sekolah
    @GET("auth/kes_detail_school")
    Call<JSONResponse.DetailSchool>detail_school_get(@Query("sch") String sch);

    //// Full picture Sekolah
    @GET("school/kes_full_schoolpic")
    Call<JSONResponse.Foto_sekolah>kes_full_schoolpic_get(@Query("school_id") String school_id);

    //////// Delete Cerification
    @FormUrlEncoded
    @POST("auth/kes_delete_verification")
    Call<JSONResponse.DeleteCode>delete_verification_post(@Field("verification_code") String verification_code);

    ////// Update Orang Tua
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

    //////Detail Student
    @GET("students/kes_detail_student")
    Call<JSONResponse.DetailStudent>kes_detail_student_get(@Header("Authorization") String authorization,
                                                           @Query("school_code") String school_code,
                                                           @Query("student_id") String student_id,
                                                           @Query("parent_nik") String parent_nik);

    //////Get Profile
    @GET("auth/kes_profile")
    Call<JSONResponse.GetProfile>kes_profile_get(@Header("Authorization") String authorization,
                                                  @Query("mem") String mem);

    ///// Upload Foto
    @Multipart
    @POST("auth/kes_update_picture")
    Call<JSONResponse.UpdatePicture> kes_update_picture_post(@Header("Authorization") String authorization,
                                                            @Part("memberid") RequestBody memberid,
                                                            @Part MultipartBody.Part picture_old,
                                                            @Part("pic_type") RequestBody pic_type);
    ///// Ganti Password
    @FormUrlEncoded
    @PUT("auth/kes_change_password/{memberid}")
    Call<JSONResponse>kes_change_password_post(@Header("Authorization") String authorization,
                                               @Path("memberid") String memberid,
                                               @Field("password") String password,
                                               @Field("old_pass") String old_pass);
    ///// Jadi Orang Tua
    @FormUrlEncoded
    @PUT("auth/kes_switch_to_parent/{member_id}")
    Call<JSONResponse>kes_switch_to_parent_put(@Header("Authorization") String authorization,
                                               @Path("member_id") String member_id,
                                               @Field("nik") String nik,
                                               @Field("relation") String relation,
                                               @Field("gender") String gender,
                                               @Field("birth_date") String birth_date);

    ///// Update Student Detail
    @FormUrlEncoded
    @PUT("students/kes_update_student_detail/{studentdetailid}")
    Call<JSONResponse>update_student_detail_put(@Header("Authorization") String authorization,
                                                @Path("studentdetailid")String studentdetailid,
                                                @Field("school_code") String school_code,
                                                @Field("student_id") String student_id,
                                                @Field("rombel") String rombel,
                                                @Field("special_needs") String special_needs,
                                                @Field("rt") String rt,
                                                @Field("rw") String rw,
                                                @Field("dusun") String dusun,
                                                @Field("kelurahan") String kelurahan,
                                                @Field("kecamatan") String kecamatan,
                                                @Field("post_code") String post_code,
                                                @Field("jenis_tinggal") String jenis_tinggal,
                                                @Field("transportasi") String transportasi,
                                                @Field("latitude") String latitude,
                                                @Field("longitude") String longitude,
                                                @Field("home_phone") String home_phone,
                                                @Field("skhun") String skhun,
                                                @Field("penerima_kps") String penerima_kps,
                                                @Field("no_kps") String no_kps);

    ///// Update Student Member
    @FormUrlEncoded
    @PUT("students/kes_update_student_member/{student_id}")
    Call<JSONResponse>update_student_member_put(@Header("Authorization") String authorization,
                                                @Path("student_id")String student_id,
                                                @Field("school_code") String school_code,
                                                @Field("fullname") String fullname,
                                                @Field("gender") String gender,
                                                @Field("birth_place") String birth_place,
                                                @Field("birth_date") String birth_date,
                                                @Field("citizen_status") String dusun,
                                                @Field("religion") String kelurahan,
                                                @Field("address") String parent_home_phone,
                                                @Field("mobile_phone") String parent_phone);

    ///// Jadwal Ujian
    @GET("students/kes_exam_schedule")
    Call<JSONResponse.JadwalUjian>kes_exam_schedule_get(@Header("Authorization") String authorization,
                                                        @Query("school_code") String school_code,
                                                        @Query("student_id") String member_id,
                                                        @Query("classroom_id") String classroom_id,
                                                        @Query("semester_id") String semester_id);


    ///// Jadwal Pelajaran
    @GET("students/kes_class_schedule")
    Call<JSONResponse.JadwalPelajaran>kes_class_schedule_get(@Header("Authorization") String authorization,
                                                        @Query("school_code") String school_code,
                                                        @Query("student_id") String student_id,
                                                        @Query("classroom_id") String classroom_id);

    ///// Tugas Anak
    @GET("students/kes_cources_score")
    Call<JSONResponse.TugasAnak>kes_cources_score_get(@Header("Authorization") String authorization,
                                                      @Query("school_code") String school_code,
                                                      @Query("student_id") String student_id,
                                                      @Query("classroom_id") String classroom_id,
                                                      @Query("semester_id") String semester_id);

    ///// Raport Anak
    @GET("students/kes_rapor_score")
    Call<JSONResponse.Raport>kes_rapor_score_get(@Header("Authorization") String authorization,
                                                 @Query("school_code") String school_code,
                                                 @Query("student_id") String student_id,
                                                 @Query("classroom_id") String classroom_id,
                                                 @Query("semester_id") String semester_id);

    ////  Check Semester
    @GET("students/kes_check_semester")
    Call<JSONResponse.CheckSemester>kes_check_semester_get(@Header("Authorization") String authorization,
                                                           @Query("school_code") String school_code,
                                                           @Query("classroom_id") String classroom_id,
                                                           @Query("date_now") String date_now);

    ///// List Semester
    @GET("students/kes_list_semester")
    Call<JSONResponse.ListSemester>kes_list_semester_get(@Header("Authorization") String authorization,
                                                     @Query("school_code") String school_code,
                                                     @Query("classroom_id") String classroom_id);

    //// List Anak
    @GET("parents/kes_list_children")
    Call<JSONResponse.ListChildren>kes_list_children_get(@Header("Authorization") String authorization,
                                                     @Query("parent_id") String parent_id);


    //// Absen Anak
    @GET("students/kes_class_attendance")
    Call<JSONResponse.AbsenSiswa>kes_class_attendance_get(@Header("Authorization") String authorization,
                                                           @Query("school_code") String school_code,
                                                           @Query("student_id") String student_id,
                                                           @Query("classroom_id") String classroom_id,
                                                           @Query("attendance_month") String attendance_month,
                                                           @Query("attendance_year") String attendance_year);

    //// Kalendar Kelas
    @GET("students/kes_class_calendar")
    Call<JSONResponse.ClassCalendar>kes_class_calendar_get(@Header("Authorization") String authorization,
                                                          @Query("school_code") String school_code,
                                                          @Query("student_id") String student_id,
                                                          @Query("classroom_id") String classroom_id,
                                                          @Query("calendar_month") String calendar_month,
                                                          @Query("calendar_year") String calendar_year);

    ///// Detail Kelas
    @GET("students/kes_classroom_detail")
    Call<JSONResponse.ClassroomDetail>kes_classroom_detail_get(@Header("Authorization") String authorization,
                                                               @Query("school_code") String school_code,
                                                               @Query("classroom_id") String classroom_id);

    ///// Detail Kalendar
    @GET("students/kes_calendar_detail")
    Call<JSONResponse.CalendarDetail>kes_calendar_detail_get(@Header("Authorization") String authorization,
                                                             @Query("school_code") String school_code,
                                                             @Query("calendar_id") String calendar_id);

    ///// Pesan orang tua
    @GET("parents/kes_message_inbox")
    Call<JSONResponse.PesanAnak>kes_message_inbox_get(@Header("Authorization") String authorization,
                                                      @Query("parent_id") String parent_id,
                                                      @Query("date_from") String date_from,
                                                      @Query("date_to") String date_to);


    @GET("parents/kes_message_sent")
    Call<JSONResponse.PesanAnak>kes_message_send_get(@Header("Authorization") String authorization,
                                                      @Query("school_code") String school_code,
                                                      @Query("parent_id") String parent_id,
                                                      @Query("date_from") String date_from,
                                                      @Query("date_to") String date_to);

    ///// Pesan orang tua
    @GET("parents/kes_message_inbox")
    Observable<JSONResponse.PesanAnak>kes_message_inbox_get(@Header("Authorization") String authorization,
                                                     @Query("school_code") String school_code,
                                                     @Query("parent_id") String parent_id,
                                                     @Query("date_from") String date_from,
                                                     @Query("date_to") String date_to);


    //// pesan anak
    @GET("students/kes_message_inbox")
    Call<JSONResponse.Pesan_Anak>kes_message_anak_get(@Header("Authorization") String authorization,
                                                      @Query("school_code") String school_code,
                                                      @Query("student_id") String student_id);

    //// Detail Pesan
    @GET("parents/kes_message_inbox_detail")
    Call<JSONResponse.PesanDetail>kes_message_inbox_detail_get(@Header("Authorization") String authorization,
                                                               @Query("school_code") String school_code,
                                                               @Query("parent_id") String parent_id,
                                                               @Query("message_id") String message_id,
                                                               @Query("parent_message_id") String parent_message_id);

    //// Detail Pesan
    @GET("students/kes_message_detail")
    Call<JSONResponse.PesanDetail>kes_message_anak_detail_get(@Header("Authorization") String authorization,
                                                               @Query("school_code") String school_code,
                                                               @Query("student_id") String student_id,
                                                               @Query("classroom_id") String classroom_id,
                                                               @Query("message_id") String message_id);

    ///// List Notifikasi
    @GET("parents/kes_notification_list")
    Call<JSONResponse.ListNotification>kes_notification_list_get(@Header("Authorization") String authorization,
                                                                 @Query("school_code") String school_code,
                                                                 @Query("student_id") String parent_id);

    ///// Balas Pesan
    @FormUrlEncoded
    @POST("parents/kes_reply_message")
    Call<JSONResponse.BalasPesan>kes_reply_message_post(@Header("Authorization") String authorization,
                                                        @Field("school_code") String school_code,
                                                        @Field("parent_id") String parent_id,
                                                        @Field("message_id") String message_id,
                                                        @Field("message_cont") String message_cont);

    ///// List Mata Pelajaran
    @GET("students/kes_list_cources")
    Call<JSONResponse.ListMapel>kes_list_cources_get(@Header("Authorization") String authorization,
                                                     @Query("school_code") String school_code,
                                                     @Query("classroom_id") String classroom_id);

    ///// List Guru
    @GET("students/kes_list_teacher")
    Call<JSONResponse.ListTeacher>kes_list_teacher_get(@Header("Authorization") String authorization,
                                                       @Query("school_code") String school_code,
                                                       @Query("classroom_id") String classroom_id);

    //// Kirim Pesan
    @FormUrlEncoded
    @POST("parents/kes_send_message")
    Call<JSONResponse.KirimPesan>kes_send_message_post(@Header("Authorization") String authorization,
                                                       @Field("school_code") String school_code,
                                                       @Field("parent_id") String parent_id,
                                                       @Field("student_id") String student_id,
                                                       @Field("classroom_id") String classroom_id,
                                                       @Field("teacher_id") String teacher_id,
                                                       @Field("cources_id") String cources_id,
                                                       @Field("message") String message);

    //// download rapor
    @GET("students/kes_rapor_pdf")
    Call<ResponseBody>kes_rapor_pdf(@Header("Authorization") String authorization,
                                    @Query("school_code") String school_code,
                                    @Query("student_id") String student_id,
                                    @Query("classroom_id") String classroom_id,
                                    @Query("semester_id") String semester_id);

    @GET("students/kes_message_inbox")
    Observable<JSONResponse.Pesan_Anak>kes_message_get(@Header("Authorization") String authorization,
                                                        @Query("school_code") String school_code,
                                                        @Query("student_id") String student_id);

    //// Recommend sekolah ke kes
    @FormUrlEncoded
    @POST("auth/kes_recommended_school")
    Call<JSONResponse.BalasPesan>kes_recommend_school_post(@Header("Authorization") String authorization,
                                                        @Field("school_id") String school_id,
                                                        @Field("school_code") String school_code,
                                                        @Field("school_name") String school_name,
                                                        @Field("member_id") String member_id);

    @GET("news/latest_news")
    Observable<JSONResponse.last_news> latest_news_get();

    @GET("news/full_news")
    Observable<JSONResponse.last_news> full_news_get();

    @GET("news/detail_news")
    Observable<JSONResponse.DetailBerita> detail_news_get(@Query("news") String news);


}