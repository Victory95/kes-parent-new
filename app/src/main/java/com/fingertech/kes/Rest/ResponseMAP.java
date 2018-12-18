package com.fingertech.kes.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMAP {

        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataItem> data;

        public List<DataItem> getData(){
            return data;
        }


    public class DataItem{

        @SerializedName("school_id")
        @Expose
        private String school_id;

        @SerializedName("schooldetailid")
        @Expose
        private String schooldetailid;

        @SerializedName("school_name")
        @Expose
        private String school_name;

        @SerializedName("school_code")
        @Expose
        private String school_code;

        @SerializedName("picture")
        @Expose
        private String picture;

        @SerializedName("school_address")
        @Expose
        private String school_address;

        @SerializedName("akreditasi")
        @Expose
        private String akreditasi;

        @SerializedName("kurikulum_id")
        @Expose
        private String kurikulumId;

        @SerializedName("jenjang_pendidikan")
        @Expose
        private String jenjang_pendidikan;

        @SerializedName("kurikulum")
        @Expose
        private String kurikulum;

        @SerializedName("status_sekolah")
        @Expose
        private String status_sekolah;

        @SerializedName("latitude")
        @Expose
        private Double latitude;

        @SerializedName("longitude")
        @Expose
        private Double longitude;

        @SerializedName("distance")
        @Expose
        private String distance;

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchooldetailid() {
            return schooldetailid;
        }

        public void setSchooldetailid(String schooldetailid) {
            this.schooldetailid = schooldetailid;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSchool_code() {
            return school_code;
        }

        public void setSchool_code(String school_code) {
            this.school_code = school_code;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getSchool_address() {
            return school_address;
        }

        public void setSchool_address(String school_address) {
            this.school_address = school_address;
        }

        public String getAkreditasi() {
            return akreditasi;
        }

        public void setAkreditasi(String akreditasi) {
            this.akreditasi = akreditasi;
        }

        public String getKurikulumId() {
            return kurikulumId;
        }

        public void setKurikulumId(String kurikulumId) {
            this.kurikulumId = kurikulumId;
        }

        public String getJenjang_pendidikan() {
            return jenjang_pendidikan;
        }

        public void setJenjang_pendidikan(String jenjang_pendidikan) {
            this.jenjang_pendidikan = jenjang_pendidikan;
        }

        public String getKurikulum() {
            return kurikulum;
        }

        public void setKurikulum(String kurikulum) {
            this.kurikulum = kurikulum;
        }

        public String getStatus_sekolah() {
            return status_sekolah;
        }

        public void setStatus_sekolah(String status_sekolah) {
            this.status_sekolah = status_sekolah;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
