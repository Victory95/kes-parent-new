package com.fingertech.kes.Activity.Model;

import com.google.gson.annotations.SerializedName;

public class DataHari {

    public String day_id;

    public String day_type;

    public String absen_status;

    public String timez_ok;

    public String timez_finish;

    public String getTimez_ok() {
        return timez_ok;
    }

    public void setTimez_ok(String timez_ok) {
        this.timez_ok = timez_ok;
    }

    public String getTimez_finish() {
        return timez_finish;
    }

    public void setTimez_finish(String timez_finish) {
        this.timez_finish = timez_finish;
    }

    public String getDay_id() {
        return day_id;
    }

    public void setDay_id(String day_id) {
        this.day_id = day_id;
    }

    public String getDay_type() {
        return day_type;
    }

    public void setDay_type(String day_type) {
        this.day_type = day_type;
    }

    public String getAbsen_status() {
        return absen_status;
    }

    public void setAbsen_status(String absen_status) {
        this.absen_status = absen_status;
    }
}
