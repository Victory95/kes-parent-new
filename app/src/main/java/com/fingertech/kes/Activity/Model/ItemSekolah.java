package com.fingertech.kes.Activity.Model;

public class ItemSekolah {

    private String nama_sekolah;
    private String akreditas;
    private Double jarak;
    private Double lat;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    private Double lng;

    public String getName() {
        return nama_sekolah;
    }

    public void setName(String nama_sekolah) {
        this.nama_sekolah = nama_sekolah;
    }

    public String getAkreditas() {
        return akreditas;
    }

    public void setAkreditas(String akreditas) {
        this.akreditas = akreditas;
    }

    public Double getJarak() {
        return jarak;
    }

    public void setJarak(Double jarak) {
        this.jarak = jarak;
    }
}