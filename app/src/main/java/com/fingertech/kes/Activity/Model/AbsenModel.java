package com.fingertech.kes.Activity.Model;

import com.fingertech.kes.Rest.JSONResponse;

import java.util.List;

public class AbsenModel {
    public String schedule_time,timez_start,times_finish,lesson_duration,total_attendedance,total_leave_sick;
    public List<JSONResponse.DataHari>days;

    public String getSchedule_time() {
        return schedule_time;
    }

    public void setSchedule_time(String schedule_time) {
        this.schedule_time = schedule_time;
    }

    public String getTimez_start() {
        return timez_start;
    }

    public void setTimez_start(String timez_start) {
        this.timez_start = timez_start;
    }

    public String getTimes_finish() {
        return times_finish;
    }

    public void setTimes_finish(String times_finish) {
        this.times_finish = times_finish;
    }

    public String getLesson_duration() {
        return lesson_duration;
    }

    public void setLesson_duration(String lesson_duration) {
        this.lesson_duration = lesson_duration;
    }

    public String getTotal_attendedance() {
        return total_attendedance;
    }

    public void setTotal_attendedance(String total_attendedance) {
        this.total_attendedance = total_attendedance;
    }

    public String getTotal_leave_sick() {
        return total_leave_sick;
    }

    public void setTotal_leave_sick(String total_leave_sick) {
        this.total_leave_sick = total_leave_sick;
    }

    public List<JSONResponse.DataHari> getDays() {
        return days;
    }

    public void setDays(List<JSONResponse.DataHari> days) {
        this.days = days;
    }

    public static class DataAbsensi{
        public String day_id,day_type,absent_status;

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

        public String getAbsent_status() {
            return absent_status;
        }

        public void setAbsent_status(String absent_status) {
            this.absent_status = absent_status;
        }
    }
    public static class data{
        public String authorization,school_code,student_id,classroom_id;

        public String getAuthorization() {
            return authorization;
        }

        public void setAuthorization(String authorization) {
            this.authorization = authorization;
        }

        public String getSchool_code() {
            return school_code;
        }

        public void setSchool_code(String school_code) {
            this.school_code = school_code;
        }

        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }
    }
}
