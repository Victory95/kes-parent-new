package com.fingertech.kes.Rest;

import com.google.gson.annotations.SerializedName;

public class JSONResponse {
    @SerializedName("status")
    public Integer status;

    @SerializedName("code")
    public String code;

    @SerializedName("data")
    public Login_Data login_data;

    @SerializedName("token")
    public String token;

    //////// Data Response - Login Public
    public class Login_Data {
        @SerializedName("member_type")
        public String member_type;
        @SerializedName("count_children")
        public String count_children;
        @SerializedName("parent_nik")
        public String parent_nik;
        @SerializedName("relation")
        public String relation;
        @SerializedName("token")
        public String token;
    }

}