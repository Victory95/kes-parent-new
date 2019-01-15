package com.fingertech.kes.Activity.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;


public class ClusterItemSekolah implements ClusterItem {

    private final LatLng mPosition;
    private String name;
    private String twitterHandle;
    private String alamat;
    private String schooldetailid;

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    private double latitude,longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSchooldetailid() {
        return schooldetailid;
    }

    public void setSchooldetailid(String schooldetailid) {
        this.schooldetailid = schooldetailid;
    }

    public ClusterItemSekolah(double lat, double lng, String name, String twitterHandle, String alamat, String schooldetailid) {
        this.name = name;
        this.twitterHandle = twitterHandle;
        mPosition = new LatLng(lat, lng);
        this.alamat = alamat;
        this.schooldetailid = schooldetailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return twitterHandle;
    }
}
