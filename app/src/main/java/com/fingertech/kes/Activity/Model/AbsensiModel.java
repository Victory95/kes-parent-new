package com.fingertech.kes.Activity.Model;

import java.util.List;

public class AbsensiModel {

    public String tanggal;
    public String timez_star,timez_finish;
    public List<dataAbsensi> dataAbsensis;
    public String day_type;
    public String absens_status;
    public String total_masuk;
    public String leave_sick;
    public String guru,mapel;

    public String getGuru() {
        return guru;
    }

    public void setGuru(String guru) {
        this.guru = guru;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDay_id() {
        return day_id;
    }

    public void setDay_id(String day_id) {
        this.day_id = day_id;
    }

    public String day_id;

    public String getDay_type() {
        return day_type;
    }

    public void setDay_type(String day_type) {
        this.day_type = day_type;
    }

    public String getAbsens_status() {
        return absens_status;
    }

    public void setAbsens_status(String absens_status) {
        this.absens_status = absens_status;
    }

    public String getTotal_masuk() {
        return total_masuk;
    }

    public void setTotal_masuk(String total_masuk) {
        this.total_masuk = total_masuk;
    }

    public String getLeave_sick() {
        return leave_sick;
    }

    public void setLeave_sick(String leave_sick) {
        this.leave_sick = leave_sick;
    }

    public List<dataAbsensi> getDataAbsensis() {
        return dataAbsensis;
    }

    public void setDataAbsensis(List<dataAbsensi> dataAbsensis) {
        this.dataAbsensis = dataAbsensis;
    }

    public String getTimez_star() {
        return timez_star;
    }

    public void setTimez_star(String timez_star) {
        this.timez_star = timez_star;
    }

    public String getTimez_finish() {
        return timez_finish;
    }

    public void setTimez_finish(String timez_finish) {
        this.timez_finish = timez_finish;
    }


    public static class dataAbsensi{
        public String day_type;
        public String absens_status;
        public String total_masuk;
        public String leave_sick;

        public String getDay_id() {
            return day_id;
        }

        public void setDay_id(String day_id) {
            this.day_id = day_id;
        }

        public String day_id;
        public String getDay_type() {
            return day_type;
        }

        public void setDay_type(String day_type) {
            this.day_type = day_type;
        }

        public String getAbsens_status() {
            return absens_status;
        }

        public void setAbsens_status(String absens_status) {
            this.absens_status = absens_status;
        }

        public String getTotal_masuk() {
            return total_masuk;
        }

        public void setTotal_masuk(String total_masuk) {
            this.total_masuk = total_masuk;
        }

        public String getLeave_sick() {
            return leave_sick;
        }

        public void setLeave_sick(String leave_sick) {
            this.leave_sick = leave_sick;
        }
    }
}
