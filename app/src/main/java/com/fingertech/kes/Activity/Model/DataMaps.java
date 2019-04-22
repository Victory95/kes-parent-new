package com.fingertech.kes.Activity.Model;

public class DataMaps {
    public static final String TABLE            = "maps";
    public static final String KEY_CourseId     = "schoolId";
    public static final String KEY_Name         = "nama_sekolah";
    public static final String KEY_ALAMAT       = "alamat_sekolah";
    public static final String KEY_LATITUDE     = "latitude";
    public static final String KEY_LONGITUDE    = "longitude";
    public static final String KEY_SCHOOLDETAIL = "schooldetailid";
    public static final String KEY_JENJANG      = "jenjang";

    private String id, name, address,schooldetailid,jenjang;
    private Double lng,lat;

    public DataMaps() {
    }


    public DataMaps(String id, String name, String address, Double lng, Double lat, String schooldetailid, String jenjang) {
        this.id             = id;
        this.name           = name;
        this.address        = address;
        this.lat            = lat;
        this.lng            = lng;
        this.schooldetailid = schooldetailid;
        this.jenjang        = jenjang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getSchooldetailid() {
        return schooldetailid;
    }

    public void setSchooldetailid(String schooldetailid) {
        this.schooldetailid = schooldetailid;
    }

    public String getJenjang() {
        return jenjang;
    }

    public void setJenjang(String jenjang) {
        this.jenjang = jenjang;
    }

}
