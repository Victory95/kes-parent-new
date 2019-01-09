package com.fingertech.kes.Activity.Model;

public class InfoWindowData {
    private String image;
    private Double jarak;
    private String schooldetailid;

    public String getSchooldetailid() {
        return schooldetailid;
    }

    public void setSchooldetailid(String schooldetailid) {
        this.schooldetailid = schooldetailid;
    }

    private String alamat,nama,akreditasi;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getJarak() {
        return jarak;
    }

    public void setJarak(Double jarak) {
        this.jarak = jarak;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAkreditasi() {
        return akreditasi;
    }

    public void setAkreditasi(String akreditasi) {
        this.akreditasi = akreditasi;
    }

}
