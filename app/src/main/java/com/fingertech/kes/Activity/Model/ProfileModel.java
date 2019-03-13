package com.fingertech.kes.Activity.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ProfileModel  {
    public String student_id;
    public String school_code;
    public String nama;
    public String classroom_id;
    public String picture;
    public String school_name;
    public int height,width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getStudent_nik() {
        return student_nik;
    }

    public void setStudent_nik(String student_nik) {
        this.student_nik = student_nik;
    }

    public String student_nik;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSchool_code() {
        return school_code;
    }

    public void setSchool_code(String school_code) {
        this.school_code = school_code;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(String classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


}
