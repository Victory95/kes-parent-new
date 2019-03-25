package com.fingertech.kes.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponse{
    @SerializedName("status")
    public Integer status;

    @SerializedName("code")
    public String code;

    @SerializedName("token")
    public String token;

    @SerializedName("data")
    public Login_Data login_data;
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

    //////// Response Search School
    public class School{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<SData> data;

        public List<SData> getData() {
            return data;
        }
    }
    public static class SData {
        @SerializedName("schoolid")
        @Expose
        private String schoolid;
        @SerializedName("school_code")
        @Expose
        private String school_code;
        @SerializedName("school_name")
        @Expose
        private String school_name;
        @SerializedName("school_quez")
        @Expose
        private String school_quez;
        @SerializedName("school_email")
        @Expose
        private String school_email;
        @SerializedName("school_phone")
        @Expose
        private String school_phone;
        @SerializedName("school_address")
        @Expose
        private String school_address;
        @SerializedName("school_contact")
        @Expose
        private String school_contact;
        @SerializedName("school_publish")
        @Expose
        private String school_publish;
        @SerializedName("picture")
        @Expose
        private String picture;
        @SerializedName("datez")
        @Expose
        private String datez;
        @SerializedName("member_id")
        @Expose
        private String member_id;
        @SerializedName("lastupdate")
        @Expose
        private String lastupdate;
        @SerializedName("school_dbase")
        @Expose
        private String school_dbase;
        @SerializedName("lesson_hour")
        @Expose
        private String lesson_hour;
        @SerializedName("schooldetailid")
        @Expose
        private String schooldetailid;
        @SerializedName("school_id")
        @Expose
        private String school_id;
        @SerializedName("jenjang_pendidikan")
        @Expose
        private String jenjang_pendidikan;
        @SerializedName("rt")
        @Expose
        private String rt;
        @SerializedName("rw")
        @Expose
        private String rw;
        @SerializedName("kode_pos")
        @Expose
        private String kode_pos;
        @SerializedName("kelurahan")
        @Expose
        private String kelurahan;
        @SerializedName("provinsi_id")
        @Expose
        private String provinsi_id;
        @SerializedName("kabupaten_id")
        @Expose
        private String kabupaten_id;
        @SerializedName("kecamatan_id")
        @Expose
        private String kecamatan_id;
        @SerializedName("sk_pendirian")
        @Expose
        private String sk_pendirian;
        @SerializedName("tanggal_pendirian")
        @Expose
        private String tanggal_pendirian;
        @SerializedName("sk_izin")
        @Expose
        private String sk_izin;
        @SerializedName("tanggal_izin")
        @Expose
        private String tanggal_izin;
        @SerializedName("kebutuhan_khusus")
        @Expose
        private String kebutuhan_khusus;
        @SerializedName("no_rekening")
        @Expose
        private String no_rekening;
        @SerializedName("nama_bank")
        @Expose
        private String nama_bank;
        @SerializedName("cabang")
        @Expose
        private String cabang;
        @SerializedName("account_name")
        @Expose
        private String account_name;
        @SerializedName("mbs")
        @Expose
        private String mbs;
        @SerializedName("tanah_milik")
        @Expose
        private String tanah_milik;
        @SerializedName("tanah_bukan_milik")
        @Expose
        private String tanah_bukan_milik;
        @SerializedName("nwp")
        @Expose
        private String nwp;
        @SerializedName("npwp")
        @Expose
        private String npwp;
        @SerializedName("no_fax")
        @Expose
        private String no_fax;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("waktu_penyelenggaraan")
        @Expose
        private String waktu_penyelenggaraan;
        @SerializedName("bersedia_menerima_bos")
        @Expose
        private String bersedia_menerima_bos;
        @SerializedName("sertifikasi_iso")
        @Expose
        private String sertifikasi_iso;
        @SerializedName("sumber_listrik")
        @Expose
        private String sumber_listrik;
        @SerializedName("daya_listrik")
        @Expose
        private String daya_listrik;
        @SerializedName("akses_internet")
        @Expose
        private String akses_internet;
        @SerializedName("internet_alternatif")
        @Expose
        private String internet_alternatif;
        @SerializedName("kepsek")
        @Expose
        private String kepsek;
        @SerializedName("operator")
        @Expose
        private String operator;
        @SerializedName("akreditasi")
        @Expose
        private String akreditasi;
        @SerializedName("kurikulum")
        @Expose
        private String kurikulum;
        @SerializedName("status_sekolah")
        @Expose
        private String status_sekolah;
        @SerializedName("status_kepemilikan")
        @Expose
        private String status_kepemilikan;
        @SerializedName("tguru")
        @Expose
        private String tguru;
        @SerializedName("tsiswa_pria")
        @Expose
        private String tsiswa_pria;
        @SerializedName("tsiswa_wanita")
        @Expose
        private String tsiswa_wanita;
        @SerializedName("rombel")
        @Expose
        private String rombel;
        @SerializedName("ruang_kelas")
        @Expose
        private String ruang_kelas;
        @SerializedName("laboratorium")
        @Expose
        private String laboratorium;
        @SerializedName("perpustakaan")
        @Expose
        private String perpustakaan;
        @SerializedName("sanitasi")
        @Expose
        private String sanitasi;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("detail_picture")
        @Expose
        private String detail_picture;
        @SerializedName("kurikulum_id")
        @Expose
        private String kurikulum_id;
        @SerializedName("edulevel_id")
        @Expose
        private String edulevel_id;

        public String getSchoolid() {
            return schoolid;
        }

        public void setSchoolid(String schoolid) {
            this.schoolid = schoolid;
        }

        public String getSchool_code() {
            return school_code;
        }

        public void setSchool_code(String school_code) {
            this.school_code = school_code;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSchool_quez() {
            return school_quez;
        }

        public void setSchool_quez(String school_quez) {
            this.school_quez = school_quez;
        }

        public String getSchool_email() {
            return school_email;
        }

        public void setSchool_email(String school_email) {
            this.school_email = school_email;
        }

        public String getSchool_phone() {
            return school_phone;
        }

        public void setSchool_phone(String school_phone) {
            this.school_phone = school_phone;
        }

        public String getSchool_address() {
            return school_address;
        }

        public void setSchool_address(String school_address) {
            this.school_address = school_address;
        }

        public String getSchool_contact() {
            return school_contact;
        }

        public void setSchool_contact(String school_contact) {
            this.school_contact = school_contact;
        }

        public String getSchool_publish() {
            return school_publish;
        }

        public void setSchool_publish(String school_publish) {
            this.school_publish = school_publish;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getLastupdate() {
            return lastupdate;
        }

        public void setLastupdate(String lastupdate) {
            this.lastupdate = lastupdate;
        }

        public String getSchool_dbase() {
            return school_dbase;
        }

        public void setSchool_dbase(String school_dbase) {
            this.school_dbase = school_dbase;
        }

        public String getLesson_hour() {
            return lesson_hour;
        }

        public void setLesson_hour(String lesson_hour) {
            this.lesson_hour = lesson_hour;
        }

        public String getSchooldetailid() {
            return schooldetailid;
        }

        public void setSchooldetailid(String schooldetailid) {
            this.schooldetailid = schooldetailid;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getJenjang_pendidikan() {
            return jenjang_pendidikan;
        }

        public void setJenjang_pendidikan(String jenjang_pendidikan) {
            this.jenjang_pendidikan = jenjang_pendidikan;
        }

        public String getRt() {
            return rt;
        }

        public void setRt(String rt) {
            this.rt = rt;
        }

        public String getRw() {
            return rw;
        }

        public void setRw(String rw) {
            this.rw = rw;
        }

        public String getKode_pos() {
            return kode_pos;
        }

        public void setKode_pos(String kode_pos) {
            this.kode_pos = kode_pos;
        }

        public String getKelurahan() {
            return kelurahan;
        }

        public void setKelurahan(String kelurahan) {
            this.kelurahan = kelurahan;
        }

        public String getProvinsi_id() {
            return provinsi_id;
        }

        public void setProvinsi_id(String provinsi_id) {
            this.provinsi_id = provinsi_id;
        }

        public String getKabupaten_id() {
            return kabupaten_id;
        }

        public void setKabupaten_id(String kabupaten_id) {
            this.kabupaten_id = kabupaten_id;
        }

        public String getKecamatan_id() {
            return kecamatan_id;
        }

        public void setKecamatan_id(String kecamatan_id) {
            this.kecamatan_id = kecamatan_id;
        }

        public String getSk_pendirian() {
            return sk_pendirian;
        }

        public void setSk_pendirian(String sk_pendirian) {
            this.sk_pendirian = sk_pendirian;
        }

        public String getTanggal_pendirian() {
            return tanggal_pendirian;
        }

        public void setTanggal_pendirian(String tanggal_pendirian) {
            this.tanggal_pendirian = tanggal_pendirian;
        }

        public String getSk_izin() {
            return sk_izin;
        }

        public void setSk_izin(String sk_izin) {
            this.sk_izin = sk_izin;
        }

        public String getTanggal_izin() {
            return tanggal_izin;
        }

        public void setTanggal_izin(String tanggal_izin) {
            this.tanggal_izin = tanggal_izin;
        }

        public String getKebutuhan_khusus() {
            return kebutuhan_khusus;
        }

        public void setKebutuhan_khusus(String kebutuhan_khusus) {
            this.kebutuhan_khusus = kebutuhan_khusus;
        }

        public String getNo_rekening() {
            return no_rekening;
        }

        public void setNo_rekening(String no_rekening) {
            this.no_rekening = no_rekening;
        }

        public String getNama_bank() {
            return nama_bank;
        }

        public void setNama_bank(String nama_bank) {
            this.nama_bank = nama_bank;
        }

        public String getCabang() {
            return cabang;
        }

        public void setCabang(String cabang) {
            this.cabang = cabang;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public String getMbs() {
            return mbs;
        }

        public void setMbs(String mbs) {
            this.mbs = mbs;
        }

        public String getTanah_milik() {
            return tanah_milik;
        }

        public void setTanah_milik(String tanah_milik) {
            this.tanah_milik = tanah_milik;
        }

        public String getTanah_bukan_milik() {
            return tanah_bukan_milik;
        }

        public void setTanah_bukan_milik(String tanah_bukan_milik) {
            this.tanah_bukan_milik = tanah_bukan_milik;
        }

        public String getNwp() {
            return nwp;
        }

        public void setNwp(String nwp) {
            this.nwp = nwp;
        }

        public String getNpwp() {
            return npwp;
        }

        public void setNpwp(String npwp) {
            this.npwp = npwp;
        }

        public String getNo_fax() {
            return no_fax;
        }

        public void setNo_fax(String no_fax) {
            this.no_fax = no_fax;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getWaktu_penyelenggaraan() {
            return waktu_penyelenggaraan;
        }

        public void setWaktu_penyelenggaraan(String waktu_penyelenggaraan) {
            this.waktu_penyelenggaraan = waktu_penyelenggaraan;
        }

        public String getBersedia_menerima_bos() {
            return bersedia_menerima_bos;
        }

        public void setBersedia_menerima_bos(String bersedia_menerima_bos) {
            this.bersedia_menerima_bos = bersedia_menerima_bos;
        }

        public String getSertifikasi_iso() {
            return sertifikasi_iso;
        }

        public void setSertifikasi_iso(String sertifikasi_iso) {
            this.sertifikasi_iso = sertifikasi_iso;
        }

        public String getSumber_listrik() {
            return sumber_listrik;
        }

        public void setSumber_listrik(String sumber_listrik) {
            this.sumber_listrik = sumber_listrik;
        }

        public String getDaya_listrik() {
            return daya_listrik;
        }

        public void setDaya_listrik(String daya_listrik) {
            this.daya_listrik = daya_listrik;
        }

        public String getAkses_internet() {
            return akses_internet;
        }

        public void setAkses_internet(String akses_internet) {
            this.akses_internet = akses_internet;
        }

        public String getInternet_alternatif() {
            return internet_alternatif;
        }

        public void setInternet_alternatif(String internet_alternatif) {
            this.internet_alternatif = internet_alternatif;
        }

        public String getKepsek() {
            return kepsek;
        }

        public void setKepsek(String kepsek) {
            this.kepsek = kepsek;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getAkreditasi() {
            return akreditasi;
        }

        public void setAkreditasi(String akreditasi) {
            this.akreditasi = akreditasi;
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

        public String getStatus_kepemilikan() {
            return status_kepemilikan;
        }

        public void setStatus_kepemilikan(String status_kepemilikan) {
            this.status_kepemilikan = status_kepemilikan;
        }

        public String getTguru() {
            return tguru;
        }

        public void setTguru(String tguru) {
            this.tguru = tguru;
        }

        public String getTsiswa_pria() {
            return tsiswa_pria;
        }

        public void setTsiswa_pria(String tsiswa_pria) {
            this.tsiswa_pria = tsiswa_pria;
        }

        public String getTsiswa_wanita() {
            return tsiswa_wanita;
        }

        public void setTsiswa_wanita(String tsiswa_wanita) {
            this.tsiswa_wanita = tsiswa_wanita;
        }

        public String getRombel() {
            return rombel;
        }

        public void setRombel(String rombel) {
            this.rombel = rombel;
        }

        public String getRuang_kelas() {
            return ruang_kelas;
        }

        public void setRuang_kelas(String ruang_kelas) {
            this.ruang_kelas = ruang_kelas;
        }

        public String getLaboratorium() {
            return laboratorium;
        }

        public void setLaboratorium(String laboratorium) {
            this.laboratorium = laboratorium;
        }

        public String getPerpustakaan() {
            return perpustakaan;
        }

        public void setPerpustakaan(String perpustakaan) {
            this.perpustakaan = perpustakaan;
        }

        public String getSanitasi() {
            return sanitasi;
        }

        public void setSanitasi(String sanitasi) {
            this.sanitasi = sanitasi;
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

        public String getDetail_picture() {
            return detail_picture;
        }

        public void setDetail_picture(String detail_picture) {
            this.detail_picture = detail_picture;
        }

        public String getKurikulum_id() {
            return kurikulum_id;
        }

        public void setKurikulum_id(String kurikulum_id) {
            this.kurikulum_id = kurikulum_id;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

    }

    //////// Response check student nik
    public class Check_Student_NIK{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<CSNIK_Data> data;
        public List<CSNIK_Data> getData() {
            return data;
        }
    }
    public class CSNIK_Data{
        @SerializedName("memberid")
        @Expose
        private String memberid;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("nik")
        @Expose
        private String nik;

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getNik() {
            return nik;
        }

        public void setNik(String nik) {
            this.nik = nik;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        @SerializedName("picture")
        @Expose
        private String picture;
    }

    //////// masuk_code_acsess
    public class Masuk_code_acsess{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public MCA_Data data;
    }
    public class MCA_Data{
        @SerializedName("parent_id")
        @Expose
        private String parent_id;
        @SerializedName("school_id")
        @Expose
        private String school_id;
        @SerializedName("student_id")
        @Expose
        private String student_id;
        @SerializedName("student_nik")
        @Expose
        private String student_nik;
        @SerializedName("parstd_status")
        @Expose
        private String parstd_status;
        @SerializedName("datez")
        @Expose
        private String datez;
        @SerializedName("member_id")
        @Expose
        private String member_id;
    }

    //////// data_parent_student
    public class Data_parent_student{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DPS_Data data;
    }

    public class DPS_Data{
        @SerializedName("studentparentid")
        public String studentparentid;
        @SerializedName("student_id")
        public String student_id;
        @SerializedName("parent_type")
        public String parent_type;
        @SerializedName("parent_name")
        public String parent_name;
        @SerializedName("type_warga")
        public String type_warga;
        @SerializedName("parent_nik")
        public String parent_nik;
        @SerializedName("parent_birth_place")
        public String parent_birth_place;
        @SerializedName("parent_birth_date")
        public String parent_birth_date;
        @SerializedName("parent_home_phone")
        public String parent_home_phone;
        @SerializedName("parent_phone")
        public String parent_phone;
        @SerializedName("parent_education")
        public String parent_education;
        @SerializedName("parent_email")
        public String parent_email;
        @SerializedName("employment")
        public String employment;
        @SerializedName("company_name")
        public String company_name;
        @SerializedName("workplace_address")
        public String workplace_address;
        @SerializedName("office_latitude")
        public String  office_latitude;
        @SerializedName("office_longitude")
        public String office_longitude;
        @SerializedName("parent_income")
        public String parent_income;
        @SerializedName("parent_address")
        public String parent_address;
        @SerializedName("parent_latitude")
        public String  parent_latitude;
        @SerializedName("parent_longitude")
        public String parent_longitude;


        public String getStudentparentid() {
            return studentparentid;
        }

        public void setStudentparentid(String studentparentid) {
            this.studentparentid = studentparentid;
        }

        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getParent_type() {
            return parent_type;
        }

        public void setParent_type(String parent_type) {
            this.parent_type = parent_type;
        }

        public String getParent_name() {
            return parent_name;
        }

        public void setParent_name(String parent_name) {
            this.parent_name = parent_name;
        }

        public String getType_warga() {
            return type_warga;
        }

        public void setType_warga(String type_warga) {
            this.type_warga = type_warga;
        }

        public String getParent_nik() {
            return parent_nik;
        }

        public void setParent_nik(String parent_nik) {
            this.parent_nik = parent_nik;
        }

        public String getParent_birth_place() {
            return parent_birth_place;
        }

        public void setParent_birth_place(String parent_birth_place) {
            this.parent_birth_place = parent_birth_place;
        }

        public String getParent_birth_date() {
            return parent_birth_date;
        }

        public void setParent_birth_date(String parent_birth_date) {
            this.parent_birth_date = parent_birth_date;
        }

        public String getParent_home_phone() {
            return parent_home_phone;
        }

        public void setParent_home_phone(String parent_home_phone) {
            this.parent_home_phone = parent_home_phone;
        }

        public String getParent_phone() {
            return parent_phone;
        }

        public void setParent_phone(String parent_phone) {
            this.parent_phone = parent_phone;
        }

        public String getParent_education() {
            return parent_education;
        }

        public void setParent_education(String parent_education) {
            this.parent_education = parent_education;
        }

        public String getParent_email() {
            return parent_email;
        }

        public void setParent_email(String parent_email) {
            this.parent_email = parent_email;
        }

        public String getEmployment() {
            return employment;
        }

        public void setEmployment(String employment) {
            this.employment = employment;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getWorkplace_address() {
            return workplace_address;
        }

        public void setWorkplace_address(String workplace_address) {
            this.workplace_address = workplace_address;
        }

        public String getOffice_latitude() {
            return office_latitude;
        }

        public void setOffice_latitude(String office_latitude) {
            this.office_latitude = office_latitude;
        }

        public String getOffice_longitude() {
            return office_longitude;
        }

        public void setOffice_longitude(String office_longitude) {
            this.office_longitude = office_longitude;
        }

        public String getParent_income() {
            return parent_income;
        }

        public void setParent_income(String parent_income) {
            this.parent_income = parent_income;
        }

        public String getParent_address() {
            return parent_address;
        }

        public void setParent_address(String parent_address) {
            this.parent_address = parent_address;
        }

        public String getParent_latitude() {
            return parent_latitude;
        }

        public void setParent_latitude(String parent_latitude) {
            this.parent_latitude = parent_latitude;
        }

        public String getParent_longitude() {
            return parent_longitude;
        }

        public void setParent_longitude(String parent_longitude) {
            this.parent_longitude = parent_longitude;
        }

    }

    //////Data Response - Nearby School
    public class Nearby_School{

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataItem> data;

        @SerializedName("status")
        public int status;


        public void setData(List<DataItem> data) {
            this.data = data;
        }

        public List<DataItem> getData() {
            return data;
        }
        }

    public class DataItem {

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
        private Double distance;

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

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }
    }

    //// Cari Sekolah per provinsi
    public class School_Provinsi{
        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataProv> data;

        @SerializedName("status")
        public int status;


        public void setData(List<DataProv> data) {
            this.data = data;
        }

        public List<DataProv> getData() {
            return data;
        }
    }
    public class DataProv{

        @SerializedName("tanggalIzin")
        @Expose
        private String tanggalIzin;

        @SerializedName("kurikulum")
        @Expose
        private String kurikulum;

        @SerializedName("akreditasi")
        @Expose
        private String akreditasi;

        @SerializedName("detailPicture")
        @Expose
        private String detailPicture;

        @SerializedName("operator")
        @Expose
        private String operator;

        @SerializedName("kurikulumId")
        @Expose
        private String kurikulumId;

        @SerializedName("ruangKelas")
        @Expose
        private String ruangKelas;

        @SerializedName("kabupatenId")
        @Expose
        private String kabupatenId;

        @SerializedName("schoolPhone")
        @Expose
        private String schoolPhone;

        @SerializedName("kepsek")
        @Expose
        private String kepsek;

        @SerializedName("sanitasi")
        @Expose
        private String sanitasi;

        @SerializedName("internetAlternatif")
        @Expose
        private String internetAlternatif;

        @SerializedName("longitude")
        @Expose
        private Double longitude;

        @SerializedName("sk_pendirian")
        @Expose
        private String sk_pendirian;

        @SerializedName("kebutuhanKhusus")
        @Expose
        private String kebutuhanKhusus;

        @SerializedName("datez")
        @Expose
        private String datez;

        @SerializedName("schoolPublish")
        @Expose
        private String schoolPublish;

        @SerializedName("dayaListrik")
        @Expose
        private String dayaListrik;

        @SerializedName("statusKepemilikan")
        @Expose
        private String statusKepemilikan;

        @SerializedName("kodePos")
        @Expose
        private String kodePos;

        @SerializedName("npwp")
        @Expose
        private String npwp;

        @SerializedName("kecamatanId")
        @Expose
        private String kecamatanId;

        @SerializedName("kelurahan")
        @Expose
        private String kelurahan;

        @SerializedName("tsiswaPria")
        @Expose
        private String tsiswaPria;

        @SerializedName("tguru")
        @Expose
        private String tguru;

        @SerializedName("lastupdate")
        @Expose
        private String lastupdate;

        @SerializedName("schooldetailid")
        @Expose
        private String schooldetailid;

        @SerializedName("aksesInternet")
        @Expose
        private String aksesInternet;

        @SerializedName("rt")
        @Expose
        private String rt;

        @SerializedName("jenjang_Pendidikan")
        @Expose
        private String jenjang_Pendidikan;

        @SerializedName("schoolCode")
        @Expose
        private String schoolCode;

        @SerializedName("rw")
        @Expose
        private String rw;

        @SerializedName("lessonHour")
        @Expose
        private String lessonHour;

        @SerializedName("nwp")
        @Expose
        private String nwp;

        @SerializedName("latitude")
        @Expose
        private Double latitude;

        @SerializedName("noRekening")
        @Expose
        private String noRekening;

        @SerializedName("schoolContact")
        @Expose
        private String schoolContact;

        @SerializedName("edulevelId")
        @Expose
        private String edulevelId;

        @SerializedName("laboratorium")
        @Expose
        private String laboratorium;

        @SerializedName("schoolId")
        @Expose
        private String schoolId;

        @SerializedName("sumberListrik")
        @Expose
        private String sumberListrik;

        @SerializedName("schoolAddress")
        @Expose
        private String schoolAddress;

        @SerializedName("account_name")
        @Expose
        private String account_name;

        @SerializedName("tsiswaWanita")
        @Expose
        private String tsiswaWanita;

        @SerializedName("namaBank")
        @Expose
        private String namaBank;

        @SerializedName("tanah_bukan_milik")
        @Expose
        private String tanah_bukan_milik;

        @SerializedName("waktuPenyelenggaraan")
        @Expose
        private String waktuPenyelenggaraan;

        @SerializedName("sertifikasiIso")
        @Expose
        private String sertifikasiIso;

        @SerializedName("memberId")
        @Expose
        private String memberId;

        @SerializedName("cabang")
        @Expose
        private String cabang;

        @SerializedName("noFax")
        @Expose
        private String noFax;

        @SerializedName("website")
        @Expose
        private String website;

        @SerializedName("schoolQuez")
        @Expose
        private String schoolQuez;

        @SerializedName("schoolDbase")
        @Expose
        private String schoolDbase;

        @SerializedName("perpustakaan")
        @Expose
        private String perpustakaan;

        @SerializedName("schoolName")
        @Expose
        private String schoolName;

        @SerializedName("rombel")
        @Expose
        private String rombel;

        @SerializedName("picture")
        @Expose
        private String picture;

        @SerializedName("sk_izin")
        @Expose
        private String sk_izin;

        @SerializedName("schoolEmail")
        @Expose
        private String schoolEmail;

        @SerializedName("provinsiId")
        @Expose
        private String provinsiId;

        @SerializedName("tanggalPendirian")
        @Expose
        private String tanggalPendirian;

        @SerializedName("schoolid")
        @Expose
        private String schoolid;

        @SerializedName("addressMap")
        @Expose
        private String addressMap;

        @SerializedName("statusSekolah")
        @Expose
        private String statusSekolah;

        @SerializedName("mbs")
        @Expose
        private String mbs;

        @SerializedName("TanahMilik")
        @Expose
        private String tanahMilik;

        @SerializedName("bersediaMenerimaBos")
        @Expose
        private String bersediaMenerimaBos;

        public void setTanggalIzin(String tanggalIzin){
            this.tanggalIzin = tanggalIzin;
        }

        public String getTanggalIzin(){
            return tanggalIzin;
        }

        public void setKurikulum(String kurikulum){
            this.kurikulum = kurikulum;
        }

        public String getKurikulum(){
            return kurikulum;
        }

        public void setAkreditasi(String akreditasi){
            this.akreditasi = akreditasi;
        }

        public String getAkreditasi(){
            return akreditasi;
        }

        public void setDetailPicture(String detailPicture){
            this.detailPicture = detailPicture;
        }

        public String getDetailPicture(){
            return detailPicture;
        }

        public void setOperator(String operator){
            this.operator = operator;
        }

        public String getOperator(){
            return operator;
        }

        public void setKurikulumId(String kurikulumId){
            this.kurikulumId = kurikulumId;
        }

        public String getKurikulumId(){
            return kurikulumId;
        }

        public void setRuangKelas(String ruangKelas){
            this.ruangKelas = ruangKelas;
        }

        public String getRuangKelas(){
            return ruangKelas;
        }

        public void setKabupatenId(String kabupatenId){
            this.kabupatenId = kabupatenId;
        }

        public String getKabupatenId(){
            return kabupatenId;
        }

        public void setSchoolPhone(String schoolPhone){
            this.schoolPhone = schoolPhone;
        }

        public String getSchoolPhone(){
            return schoolPhone;
        }

        public void setKepsek(String kepsek){
            this.kepsek = kepsek;
        }

        public String getKepsek(){
            return kepsek;
        }

        public void setSanitasi(String sanitasi){
            this.sanitasi = sanitasi;
        }

        public String getSanitasi(){
            return sanitasi;
        }

        public void setInternetAlternatif(String internetAlternatif){
            this.internetAlternatif = internetAlternatif;
        }

        public String getInternetAlternatif(){
            return internetAlternatif;
        }

        public void setLongitude(Double longitude){
            this.longitude = longitude;
        }

        public Double getLongitude(){
            return longitude;
        }

        public void setSkPendirian(String skPendirian){
            this.sk_pendirian = skPendirian;
        }

        public String getSkPendirian(){
            return sk_pendirian;
        }

        public void setKebutuhanKhusus(String kebutuhanKhusus){
            this.kebutuhanKhusus = kebutuhanKhusus;
        }

        public String getKebutuhanKhusus(){
            return kebutuhanKhusus;
        }

        public void setDatez(String datez){
            this.datez = datez;
        }

        public String getDatez(){
            return datez;
        }

        public void setSchoolPublish(String schoolPublish){
            this.schoolPublish = schoolPublish;
        }

        public String getSchoolPublish(){
            return schoolPublish;
        }

        public void setDayaListrik(String dayaListrik){
            this.dayaListrik = dayaListrik;
        }

        public String getDayaListrik(){
            return dayaListrik;
        }

        public void setStatusKepemilikan(String statusKepemilikan){
            this.statusKepemilikan = statusKepemilikan;
        }

        public String getStatusKepemilikan(){
            return statusKepemilikan;
        }

        public void setKodePos(String kodePos){
            this.kodePos = kodePos;
        }

        public String getKodePos(){
            return kodePos;
        }

        public void setNpwp(String npwp){
            this.npwp = npwp;
        }

        public String getNpwp(){
            return npwp;
        }

        public void setKecamatanId(String kecamatanId){
            this.kecamatanId = kecamatanId;
        }

        public String getKecamatanId(){
            return kecamatanId;
        }

        public void setKelurahan(String kelurahan){
            this.kelurahan = kelurahan;
        }

        public String getKelurahan(){
            return kelurahan;
        }

        public void setTsiswaPria(String tsiswaPria){
            this.tsiswaPria = tsiswaPria;
        }

        public String getTsiswaPria(){
            return tsiswaPria;
        }

        public void setTguru(String tguru){
            this.tguru = tguru;
        }

        public String getTguru(){
            return tguru;
        }

        public void setLastupdate(String lastupdate){
            this.lastupdate = lastupdate;
        }

        public String getLastupdate(){
            return lastupdate;
        }

        public void setSchooldetailid(String schooldetailid){
            this.schooldetailid = schooldetailid;
        }

        public String getSchooldetailid(){
            return schooldetailid;
        }

        public void setAksesInternet(String aksesInternet){
            this.aksesInternet = aksesInternet;
        }

        public String getAksesInternet(){
            return aksesInternet;
        }

        public void setRt(String rt){
            this.rt = rt;
        }

        public String getRt(){
            return rt;
        }

        public void setJenjangPendidikan(String jenjangPendidikan){
            this.jenjang_Pendidikan = jenjangPendidikan;
        }

        public String getJenjangPendidikan(){
            return jenjang_Pendidikan;
        }

        public void setSchoolCode(String schoolCode){
            this.schoolCode = schoolCode;
        }

        public String getSchoolCode(){
            return schoolCode;
        }

        public void setRw(String rw){
            this.rw = rw;
        }

        public String getRw(){
            return rw;
        }

        public void setLessonHour(String lessonHour){
            this.lessonHour = lessonHour;
        }

        public String getLessonHour(){
            return lessonHour;
        }

        public void setNwp(String nwp){
            this.nwp = nwp;
        }

        public String getNwp(){
            return nwp;
        }

        public void setLatitude(Double latitude){
            this.latitude = latitude;
        }

        public Double getLatitude(){
            return latitude;
        }

        public void setNoRekening(String noRekening){
            this.noRekening = noRekening;
        }

        public String getNoRekening(){
            return noRekening;
        }

        public void setSchoolContact(String schoolContact){
            this.schoolContact = schoolContact;
        }

        public String getSchoolContact(){
            return schoolContact;
        }

        public void setEdulevelId(String edulevelId){
            this.edulevelId = edulevelId;
        }

        public String getEdulevelId(){
            return edulevelId;
        }

        public void setLaboratorium(String laboratorium){
            this.laboratorium = laboratorium;
        }

        public String getLaboratorium(){
            return laboratorium;
        }

        public void setSchoolId(String schoolId){
            this.schoolId = schoolId;
        }

        public String getSchoolId(){
            return schoolId;
        }

        public void setSumberListrik(String sumberListrik){
            this.sumberListrik = sumberListrik;
        }

        public String getSumberListrik(){
            return sumberListrik;
        }

        public void setSchoolAddress(String schoolAddress){
            this.schoolAddress = schoolAddress;
        }

        public String getSchoolAddress(){
            return schoolAddress;
        }

        public void setAccountName(String accountName){
            this.account_name = accountName;
        }

        public String getAccountName(){
            return account_name;
        }

        public void setTsiswaWanita(String tsiswaWanita){
            this.tsiswaWanita = tsiswaWanita;
        }

        public String getTsiswaWanita(){
            return tsiswaWanita;
        }

        public void setNamaBank(String namaBank){
            this.namaBank = namaBank;
        }

        public String getNamaBank(){
            return namaBank;
        }

        public void setTanahBukanMilik(String tanahBukanMilik){
            this.tanah_bukan_milik = tanahBukanMilik;
        }

        public String getTanahBukanMilik(){
            return tanah_bukan_milik;
        }

        public void setWaktuPenyelenggaraan(String waktuPenyelenggaraan){
            this.waktuPenyelenggaraan = waktuPenyelenggaraan;
        }

        public String getWaktuPenyelenggaraan(){
            return waktuPenyelenggaraan;
        }

        public void setSertifikasiIso(String sertifikasiIso){
            this.sertifikasiIso = sertifikasiIso;
        }

        public String getSertifikasiIso(){
            return sertifikasiIso;
        }

        public void setMemberId(String memberId){
            this.memberId = memberId;
        }

        public String getMemberId(){
            return memberId;
        }

        public void setCabang(String cabang){
            this.cabang = cabang;
        }

        public String getCabang(){
            return cabang;
        }

        public void setNoFax(String noFax){
            this.noFax = noFax;
        }

        public String getNoFax(){
            return noFax;
        }

        public void setWebsite(String website){
            this.website = website;
        }

        public String getWebsite(){
            return website;
        }

        public void setSchoolQuez(String schoolQuez){
            this.schoolQuez = schoolQuez;
        }

        public String getSchoolQuez(){
            return schoolQuez;
        }

        public void setSchoolDbase(String schoolDbase){
            this.schoolDbase = schoolDbase;
        }

        public String getSchoolDbase(){
            return schoolDbase;
        }

        public void setPerpustakaan(String perpustakaan){
            this.perpustakaan = perpustakaan;
        }

        public String getPerpustakaan(){
            return perpustakaan;
        }

        public void setSchoolName(String schoolName){
            this.schoolName = schoolName;
        }

        public String getSchoolName(){
            return schoolName;
        }

        public void setRombel(String rombel){
            this.rombel = rombel;
        }

        public String getRombel(){
            return rombel;
        }

        public void setPicture(String picture){
            this.picture = picture;
        }

        public String getPicture(){
            return picture;
        }

        public void setSkIzin(String skIzin){
            this.sk_izin = skIzin;
        }

        public String getSkIzin(){
            return sk_izin;
        }

        public void setSchoolEmail(String schoolEmail){
            this.schoolEmail = schoolEmail;
        }

        public String getSchoolEmail(){
            return schoolEmail;
        }

        public void setProvinsiId(String provinsiId){
            this.provinsiId = provinsiId;
        }

        public String getProvinsiId(){
            return provinsiId;
        }

        public void setTanggalPendirian(String tanggalPendirian){
            this.tanggalPendirian = tanggalPendirian;
        }

        public String getTanggalPendirian(){
            return tanggalPendirian;
        }

        public void setSchoolid(String schoolid){
            this.schoolid = schoolid;
        }

        public String getSchoolid(){
            return schoolid;
        }

        public void setAddressMap(String addressMap){
            this.addressMap = addressMap;
        }

        public String getAddressMap(){
            return addressMap;
        }

        public void setStatusSekolah(String statusSekolah){
            this.statusSekolah = statusSekolah;
        }

        public String getStatusSekolah(){
            return statusSekolah;
        }

        public void setMbs(String mbs){
            this.mbs = mbs;
        }

        public String getMbs(){
            return mbs;
        }

        public void setTanahMilik(String tanahMilik){
            this.tanahMilik = tanahMilik;
        }

        public String getTanahMilik(){
            return tanahMilik;
        }

        public void setBersediaMenerimaBos(String bersediaMenerimaBos){
            this.bersediaMenerimaBos = bersediaMenerimaBos;
        }

        public String getBersediaMenerimaBos(){
            return bersediaMenerimaBos;
        }
    }

    //// Provinsi di indonesia
    public class Provinsi{
        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<Prov> data;

        @SerializedName("status")
        public int status;


        public void setData(List<Prov> data) {
            this.data = data;
        }

        public List<Prov> getData() {
            return data;
        }
    }
    public static class Prov{

        @SerializedName("provinsiid")
        @Expose
        private String provinsiid;

        @SerializedName("nama_provinsi")
        @Expose
        private String nama_provinsi;

        public void setProvinsiid(String provinsiid){
            this.provinsiid = provinsiid;
        }

        public String getProvinsiid(){
            return provinsiid;
        }

        public void setNamaProvinsi(String nama_provinsi){
            this.nama_provinsi = nama_provinsi;
        }

        public String getNamaProvinsi(){
            return nama_provinsi;
        }
    }

    ///// Detail Sekolah
    public class DetailSchool{

        @SerializedName("code")
        public String code;

        @SerializedName("school")
        public SchoolDetail school;

        @SerializedName("status")
        public int status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public SchoolDetail getSchool() {
            return school;
        }

        public void setSchool(SchoolDetail school) {
            this.school = school;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
    public class SchoolDetail{
        @SerializedName("status_kes")
        public int statusKes;

        @SerializedName("data")
        public dataDetail data;

        public void setStatusKes(int statusKes){
            this.statusKes = statusKes;
        }

        public int getStatusKes(){
            return statusKes;
        }

        public void setData(dataDetail data){
            this.data = data;
        }

        public dataDetail getData(){
            return data;
        }
    }
    public class dataDetail{

        @SerializedName("tanggal_izin")
        private String tanggal_izin;

        @SerializedName("nama_provinsi")
        private String nama_provinsi;

        @SerializedName("kabupatenid")
        private String kabupatenid;

        @SerializedName("kurikulum")
        private String kurikulum;

        @SerializedName("akreditasi")
        private String akreditasi;

        @SerializedName("detail_picture")
        private String detail_picture;

        @SerializedName("operator")
        private String operator;

        @SerializedName("kurikulumId")
        private String kurikulumId;

        @SerializedName("ruang_kelas")
        private String ruang_kelas;

        @SerializedName("kabupatenId")
        private String kabupatenId;

        @SerializedName("school_phone")
        private String school_phone;

        @SerializedName("kepsek")
        private String kepsek;

        @SerializedName("sanitasi")
        private String sanitasi;

        @SerializedName("internet_alternatif")
        private String internet_alternatif;

        @SerializedName("longitude")
        private Double longitude;

        @SerializedName("sk_pendirian")
        private String skPendirian;

        @SerializedName("kebutuhan_khusus")
        private String kebutuhan_khusus;

        @SerializedName("datez")
        private String datez;

        @SerializedName("school_publish")
        private String school_publish;

        @SerializedName("daya_listrik")
        private String daya_listrik;

        @SerializedName("status_kepemilikan")
        private String status_kepemilikan;

        @SerializedName("kode_pos")
        private String kode_pos;

        @SerializedName("npwp")
        private String npwp;

        @SerializedName("kecamatanId")
        private String kecamatanId;

        @SerializedName("kelurahan")
        private String kelurahan;

        @SerializedName("npsn")
        private String npsn;

        @SerializedName("nama_kecamatan")
        private String nama_kecamatan;

        @SerializedName("tsiswa_pria")
        private String tsiswa_pria;

        @SerializedName("tguru")
        private String tguru;

        @SerializedName("lastupdate")
        private String lastupdate;

        @SerializedName("schooldetailid")
        private String schooldetailid;

        @SerializedName("akses_internet")
        private String akses_internet;

        @SerializedName("rt")
        private String rt;

        @SerializedName("jenjang_pendidikan")
        private String jenjang_pendidikan;

        @SerializedName("school_code")
        private String school_code;

        @SerializedName("rw")
        private String rw;

        @SerializedName("lesson_hour")
        private String lessonHour;

        @SerializedName("nwp")
        private String nwp;

        @SerializedName("kecamatanid")
        private String kecamatanid;

        @SerializedName("latitude")
        private Double latitude;

        @SerializedName("nama_kabupaten")
        private String nama_kabupaten;

        @SerializedName("no_rekening")
        private String no_rekening;

        @SerializedName("school_contact")
        private String school_contact;

        @SerializedName("edulevel_id")
        private String edulevelId;

        @SerializedName("laboratorium")
        private String laboratorium;

        @SerializedName("school_id")
        private String school_Id;

        @SerializedName("sumber_listrik")
        private String sumber_listrik;

        @SerializedName("school_address")
        private String school_address;

        @SerializedName("accountName")
        private String accountName;

        @SerializedName("tsiswa_wanita")
        private String tsiswa_wanita;

        @SerializedName("nama_bank")
        private String nama_bank;

        @SerializedName("tanah_bukan_milik")
        private String tanah_bukan_milik;

        @SerializedName("waktu_penyelengaraan")
        private String waktu_penyelengaraan;

        @SerializedName("sertifikasi_iso")
        private String sertifikasi_iso;

        @SerializedName("member_id")
        private String member_id;

        @SerializedName("cabang")
        private String cabang;

        @SerializedName("no_fax")
        private String no_fax;

        @SerializedName("website")
        private String website;

        @SerializedName("schoolQuez")
        private String schoolQuez;

        @SerializedName("schoolDbase")
        private String schoolDbase;

        @SerializedName("perpustakaan")
        private String perpustakaan;

        @SerializedName("school_name")
        private String school_name;

        @SerializedName("rombel")
        private String rombel;

        @SerializedName("picture")
        private String picture;

        @SerializedName("sk_izin")
        private String skIzin;

        @SerializedName("provinsiid")
        private String provinsiid;

        @SerializedName("school_email")
        private String school_email;

        @SerializedName("provinsiId")
        private String provinsiId;

        @SerializedName("tanggal_pendirian")
        private String tanggal_pendirian;

        @SerializedName("schoolid")
        private String schoolid;

        @SerializedName("addressMap")
        private String addressMap;

        @SerializedName("status_sekolah")
        private String status_sekolah;

        @SerializedName("mbs")
        private String mbs;

        @SerializedName("tanah_milik")
        private String tanah_milik;

        @SerializedName("bersedia_menerima_bos")
        private String bersedia_menerima_bos;

        public String getTanggal_izin() {
            return tanggal_izin;
        }

        public void setTanggal_izin(String tanggal_izin) {
            this.tanggal_izin = tanggal_izin;
        }

        public String getNama_provinsi() {
            return nama_provinsi;
        }

        public void setNama_provinsi(String nama_provinsi) {
            this.nama_provinsi = nama_provinsi;
        }

        public String getKabupatenid() {
            return kabupatenid;
        }

        public void setKabupatenid(String kabupatenid) {
            this.kabupatenid = kabupatenid;
        }

        public String getKurikulum() {
            return kurikulum;
        }

        public void setKurikulum(String kurikulum) {
            this.kurikulum = kurikulum;
        }

        public String getAkreditasi() {
            return akreditasi;
        }

        public void setAkreditasi(String akreditasi) {
            this.akreditasi = akreditasi;
        }

        public String getDetail_picture() {
            return detail_picture;
        }

        public void setDetail_picture(String detail_picture) {
            this.detail_picture = detail_picture;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getKurikulumId() {
            return kurikulumId;
        }

        public void setKurikulumId(String kurikulumId) {
            this.kurikulumId = kurikulumId;
        }

        public String getRuang_kelas() {
            return ruang_kelas;
        }

        public void setRuang_kelas(String ruang_kelas) {
            this.ruang_kelas = ruang_kelas;
        }

        public String getKabupatenId() {
            return kabupatenId;
        }

        public void setKabupatenId(String kabupatenId) {
            this.kabupatenId = kabupatenId;
        }

        public String getSchool_phone() {
            return school_phone;
        }

        public void setSchool_phone(String school_phone) {
            this.school_phone = school_phone;
        }

        public String getKepsek() {
            return kepsek;
        }

        public void setKepsek(String kepsek) {
            this.kepsek = kepsek;
        }

        public String getSanitasi() {
            return sanitasi;
        }

        public void setSanitasi(String sanitasi) {
            this.sanitasi = sanitasi;
        }

        public String getInternet_alternatif() {
            return internet_alternatif;
        }

        public void setInternet_alternatif(String internet_alternatif) {
            this.internet_alternatif = internet_alternatif;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getSkPendirian() {
            return skPendirian;
        }

        public void setSkPendirian(String skPendirian) {
            this.skPendirian = skPendirian;
        }

        public String getKebutuhan_khusus() {
            return kebutuhan_khusus;
        }

        public void setKebutuhan_khusus(String kebutuhan_khusus) {
            this.kebutuhan_khusus = kebutuhan_khusus;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getSchool_publish() {
            return school_publish;
        }

        public void setSchool_publish(String school_publish) {
            this.school_publish = school_publish;
        }

        public String getDaya_listrik() {
            return daya_listrik;
        }

        public void setDaya_listrik(String daya_listrik) {
            this.daya_listrik = daya_listrik;
        }

        public String getStatus_kepemilikan() {
            return status_kepemilikan;
        }

        public void setStatus_kepemilikan(String status_kepemilikan) {
            this.status_kepemilikan = status_kepemilikan;
        }

        public String getKode_pos() {
            return kode_pos;
        }

        public void setKode_pos(String kode_pos) {
            this.kode_pos = kode_pos;
        }

        public String getNpwp() {
            return npwp;
        }

        public void setNpwp(String npwp) {
            this.npwp = npwp;
        }

        public String getKecamatanId() {
            return kecamatanId;
        }

        public void setKecamatanId(String kecamatanId) {
            this.kecamatanId = kecamatanId;
        }

        public String getKelurahan() {
            return kelurahan;
        }

        public void setKelurahan(String kelurahan) {
            this.kelurahan = kelurahan;
        }

        public String getNpsn() {
            return npsn;
        }

        public void setNpsn(String npsn) {
            this.npsn = npsn;
        }

        public String getNama_kecamatan() {
            return nama_kecamatan;
        }

        public void setNama_kecamatan(String nama_kecamatan) {
            this.nama_kecamatan = nama_kecamatan;
        }

        public String getTsiswa_pria() {
            return tsiswa_pria;
        }

        public void setTsiswa_pria(String tsiswa_pria) {
            this.tsiswa_pria = tsiswa_pria;
        }

        public String getTguru() {
            return tguru;
        }

        public void setTguru(String tguru) {
            this.tguru = tguru;
        }

        public String getLastupdate() {
            return lastupdate;
        }

        public void setLastupdate(String lastupdate) {
            this.lastupdate = lastupdate;
        }

        public String getSchooldetailid() {
            return schooldetailid;
        }

        public void setSchooldetailid(String schooldetailid) {
            this.schooldetailid = schooldetailid;
        }

        public String getAkses_internet() {
            return akses_internet;
        }

        public void setAkses_internet(String akses_internet) {
            this.akses_internet = akses_internet;
        }

        public String getRt() {
            return rt;
        }

        public void setRt(String rt) {
            this.rt = rt;
        }

        public String getJenjangPendidikan() {
            return jenjang_pendidikan;
        }

        public void setJenjangPendidikan(String jenjangPendidikan) {
            this.jenjang_pendidikan = jenjangPendidikan;
        }

        public String getSchool_code() {
            return school_code;
        }

        public void setSchool_code(String school_code) {
            this.school_code = school_code;
        }

        public String getRw() {
            return rw;
        }

        public void setRw(String rw) {
            this.rw = rw;
        }

        public String getLessonHour() {
            return lessonHour;
        }

        public void setLessonHour(String lessonHour) {
            this.lessonHour = lessonHour;
        }

        public String getNwp() {
            return nwp;
        }

        public void setNwp(String nwp) {
            this.nwp = nwp;
        }

        public String getKecamatanid() {
            return kecamatanid;
        }

        public void setKecamatanid(String kecamatanid) {
            this.kecamatanid = kecamatanid;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public String getNama_kabupaten() {
            return nama_kabupaten;
        }

        public void setNama_kabupaten(String nama_kabupaten) {
            this.nama_kabupaten = nama_kabupaten;
        }

        public String getNo_rekening() {
            return no_rekening;
        }

        public void setNo_rekening(String no_rekening) {
            this.no_rekening = no_rekening;
        }

        public String getSchool_contact() {
            return school_contact;
        }

        public void setSchool_contact(String school_contact) {
            this.school_contact = school_contact;
        }

        public String getEdulevelId() {
            return edulevelId;
        }

        public void setEdulevelId(String edulevelId) {
            this.edulevelId = edulevelId;
        }

        public String getLaboratorium() {
            return laboratorium;
        }

        public void setLaboratorium(String laboratorium) {
            this.laboratorium = laboratorium;
        }

        public String getSchool_Id() {
            return school_Id;
        }

        public void setSchool_Id(String school_Id) {
            this.school_Id = school_Id;
        }

        public String getSumber_listrik() {
            return sumber_listrik;
        }

        public void setSumber_listrik(String sumber_listrik) {
            this.sumber_listrik = sumber_listrik;
        }

        public String getSchool_address() {
            return school_address;
        }

        public void setSchool_address(String school_address) {
            this.school_address = school_address;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getTsiswa_wanita() {
            return tsiswa_wanita;
        }

        public void setTsiswa_wanita(String tsiswa_wanita) {
            this.tsiswa_wanita = tsiswa_wanita;
        }

        public String getNama_bank() {
            return nama_bank;
        }

        public void setNama_bank(String nama_bank) {
            this.nama_bank = nama_bank;
        }

        public String getTanah_bukan_milik() {
            return tanah_bukan_milik;
        }

        public void setTanah_bukan_milik(String tanah_bukan_milik) {
            this.tanah_bukan_milik = tanah_bukan_milik;
        }

        public String getWaktu_penyelengaraan() {
            return waktu_penyelengaraan;
        }

        public void setWaktu_penyelengaraan(String waktu_penyelengaraan) {
            this.waktu_penyelengaraan = waktu_penyelengaraan;
        }

        public String getSertifikasi_iso() {
            return sertifikasi_iso;
        }

        public void setSertifikasi_iso(String sertifikasi_iso) {
            this.sertifikasi_iso = sertifikasi_iso;
        }

        public String getMemberId() {
            return member_id;
        }

        public void setMemberId(String memberId) {
            this.member_id = memberId;
        }

        public String getCabang() {
            return cabang;
        }

        public void setCabang(String cabang) {
            this.cabang = cabang;
        }

        public String getNo_fax() {
            return no_fax;
        }

        public void setNo_fax(String no_fax) {
            this.no_fax = no_fax;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getSchoolQuez() {
            return schoolQuez;
        }

        public void setSchoolQuez(String schoolQuez) {
            this.schoolQuez = schoolQuez;
        }

        public String getSchoolDbase() {
            return schoolDbase;
        }

        public void setSchoolDbase(String schoolDbase) {
            this.schoolDbase = schoolDbase;
        }

        public String getPerpustakaan() {
            return perpustakaan;
        }

        public void setPerpustakaan(String perpustakaan) {
            this.perpustakaan = perpustakaan;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getRombel() {
            return rombel;
        }

        public void setRombel(String rombel) {
            this.rombel = rombel;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getSkIzin() {
            return skIzin;
        }

        public void setSkIzin(String skIzin) {
            this.skIzin = skIzin;
        }

        public String getProvinsiid() {
            return provinsiid;
        }

        public void setProvinsiid(String provinsiid) {
            this.provinsiid = provinsiid;
        }

        public String getSchool_email() {
            return school_email;
        }

        public void setSchool_email(String school_email) {
            this.school_email = school_email;
        }

        public String getProvinsiId() {
            return provinsiId;
        }

        public void setProvinsiId(String provinsiId) {
            this.provinsiId = provinsiId;
        }

        public String getTanggal_pendirian() {
            return tanggal_pendirian;
        }

        public void setTanggal_pendirian(String tanggal_pendirian) {
            this.tanggal_pendirian = tanggal_pendirian;
        }

        public String getSchoolid() {
            return schoolid;
        }

        public void setSchoolid(String schoolid) {
            this.schoolid = schoolid;
        }

        public String getAddressMap() {
            return addressMap;
        }

        public void setAddressMap(String addressMap) {
            this.addressMap = addressMap;
        }

        public String getStatus_sekolah() {
            return status_sekolah;
        }

        public void setStatus_sekolah(String status_sekolah) {
            this.status_sekolah = status_sekolah;
        }

        public String getMbs() {
            return mbs;
        }

        public void setMbs(String mbs) {
            this.mbs = mbs;
        }

        public String getTanah_milik() {
            return tanah_milik;
        }

        public void setTanah_milik(String tanah_milik) {
            this.tanah_milik = tanah_milik;
        }

        public String getBersedia_menerima_bos() {
            return bersedia_menerima_bos;
        }

        public void setBersedia_menerima_bos(String bersedia_menerima_bos) {
            this.bersedia_menerima_bos = bersedia_menerima_bos;
        }
    }

    //// Hapus kode
    public class DeleteCode{
        @SerializedName("message")
        public String message;

        @SerializedName("status")
        public int status;

    }

    ///// Detail Student
    public class DetailStudent{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;


        @SerializedName("data")
        public DataStudent data;

        public DataStudent getData() {
            return data;
        }

        public void setData(DataStudent data) {
            this.data = data;
        }

    }
    public class DataStudent{

        @SerializedName("memberid")
        public String memberid;

        @SerializedName("studentdetailid")
        public String studentdetailid;

        @SerializedName("student_id")
        public String student_id;

        @SerializedName("edulevel_id")
        public String edulevel_id;

        @SerializedName("fullname")
        public String fullname;

        @SerializedName("gender")
        public String gender;

        @SerializedName("member_code")
        public String member_code;

        @SerializedName("nisn")
        public String nisn;

        @SerializedName("rombel")
        public String rombel;

        @SerializedName("birth_place")
        public String birth_place;

        @SerializedName("birth_date")
        public String birth_date;

        @SerializedName("citizen_status")
        public String citizen_status;

        @SerializedName("nik")
        public String nik;

        @SerializedName("religion")
        public String religion;

        @SerializedName("special_needs")
        public String special_needs;

        @SerializedName("rt")
        public String rt;

        @SerializedName("rw")
        public String rw;

        @SerializedName("dusun")
        public String dusun;

        @SerializedName("kelurahan")
        public String kelurahan;

        @SerializedName("kecamatan")
        public String kecamatan;

        @SerializedName("post_code")
        public String post_code;

        @SerializedName("jenis_tinggal")
        public String jenis_tinggal;

        @SerializedName("transportasi")
        public String transportasi;


        @SerializedName("email")
        public String email;

        @SerializedName("address")
        public String address;

        @SerializedName("latitude")
        public String latitude;

        @SerializedName("longitude")
        public String longitude;

        @SerializedName("mobile_phone")
        public String mobile_phone;

        @SerializedName("home_phone")
        public String home_phone;

        @SerializedName("skhun")
        public String skhun;

        @SerializedName("penerima_kps")
        public String penerima_kps;

        @SerializedName("no_kps")
        public String no_kps;

        @SerializedName("picture")
        public String picture;

        @SerializedName("classroom_id")
        public String classroom_id;

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getStudentdetailid() {
            return studentdetailid;
        }

        public void setStudentdetailid(String studentdetailid) {
            this.studentdetailid = studentdetailid;
        }

        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMember_code() {
            return member_code;
        }

        public void setMember_code(String member_code) {
            this.member_code = member_code;
        }

        public String getNisn() {
            return nisn;
        }

        public void setNisn(String nisn) {
            this.nisn = nisn;
        }

        public String getRombel() {
            return rombel;
        }

        public void setRombel(String rombel) {
            this.rombel = rombel;
        }

        public String getBirth_place() {
            return birth_place;
        }

        public void setBirth_place(String birth_place) {
            this.birth_place = birth_place;
        }

        public String getBirth_date() {
            return birth_date;
        }

        public void setBirth_date(String birth_date) {
            this.birth_date = birth_date;
        }

        public String getCitizen_status() {
            return citizen_status;
        }

        public void setCitizen_status(String citizen_status) {
            this.citizen_status = citizen_status;
        }

        public String getNik() {
            return nik;
        }

        public void setNik(String nik) {
            this.nik = nik;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getSpecial_needs() {
            return special_needs;
        }

        public void setSpecial_needs(String special_needs) {
            this.special_needs = special_needs;
        }

        public String getRt() {
            return rt;
        }

        public void setRt(String rt) {
            this.rt = rt;
        }

        public String getRw() {
            return rw;
        }

        public void setRw(String rw) {
            this.rw = rw;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDusun() {
            return dusun;
        }

        public void setDusun(String dusun) {
            this.dusun = dusun;
        }

        public String getKelurahan() {
            return kelurahan;
        }

        public void setKelurahan(String kelurahan) {
            this.kelurahan = kelurahan;
        }

        public String getKecamatan() {
            return kecamatan;
        }

        public void setKecamatan(String kecamatan) {
            this.kecamatan = kecamatan;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public String getJenis_tinggal() {
            return jenis_tinggal;
        }

        public void setJenis_tinggal(String jenis_tinggal) {
            this.jenis_tinggal = jenis_tinggal;
        }

        public String getTransportasi() {
            return transportasi;
        }

        public void setTransportasi(String transportasi) {
            this.transportasi = transportasi;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getMobile_phone() {
            return mobile_phone;
        }

        public void setMobile_phone(String mobile_phone) {
            this.mobile_phone = mobile_phone;
        }

        public String getHome_phone() {
            return home_phone;
        }

        public void setHome_phone(String home_phone) {
            this.home_phone = home_phone;
        }

        public String getSkhun() {
            return skhun;
        }

        public void setSkhun(String skhun) {
            this.skhun = skhun;
        }

        public String getPenerima_kps() {
            return penerima_kps;
        }

        public void setPenerima_kps(String penerima_kps) {
            this.penerima_kps = penerima_kps;
        }

        public String getNo_kps() {
            return no_kps;
        }

        public void setNo_kps(String no_kps) {
            this.no_kps = no_kps;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }


    ///// Dapat Profile
    public class GetProfile{

        @SerializedName("status")
        public int status;
        @SerializedName("data")
        private DataProfile data;

        public void setData(DataProfile data){
            this.data = data;
        }

        public DataProfile getData(){
            return data;
        }

        public void setStatus(int status){
            this.status = status;
        }

        public int getStatus(){
            return status;
        }
    }
    public class DataProfile{

        @SerializedName("Member_Code")
        private String Member_Code;

        @SerializedName("Parent_Latitude")
        private String Parent_Latitude;

        @SerializedName("Birth_Date")
        private String Birth_Date;

        @SerializedName("Email")
        private String Email;

        @SerializedName("Address")
        private String Address;

        @SerializedName("Member_Type")
        private String Member_Type;

        @SerializedName("Fullname")
        private String Fullname;

        @SerializedName("Last_Update")
        private String Last_Update;

        @SerializedName("Religion")
        private String Religion;

        @SerializedName("Gender")
        private String Gender;

        @SerializedName("Date")
        private String Date;

        @SerializedName("Parent_Count")
        private String Parent_Count;

        @SerializedName("Birth_Place")
        private String Birth_Place;

        @SerializedName("Relation")
        private String Relation;

        @SerializedName("Username")
        private String Username;

        @SerializedName("Last_Login")
        private String Last_Login;

        @SerializedName("Picture")
        private String Picture;

        @SerializedName("Device_Id")
        private String Device_Id;

        @SerializedName("Number_Phone")
        private String Number_Phone;

        @SerializedName("Parent_NIK")
        private String Parent_NIK;

        @SerializedName("FG_Code")
        private String FG_Code;

        @SerializedName("Parent_Longitude")
        private String parentLongitude;

        @SerializedName("Publish")
        private String publish;

        @SerializedName("Total_Children")
        private String Total_Children;

        public String getTotal_Children() {
            return Total_Children;
        }

        public void setTotal_Children(String total_Children) {
            Total_Children = total_Children;
        }

        public String getMember_Code() {
            return Member_Code;
        }

        public void setMember_Code(String member_Code) {
            Member_Code = member_Code;
        }

        public String getParent_Latitude() {
            return Parent_Latitude;
        }

        public void setParent_Latitude(String parent_Latitude) {
            Parent_Latitude = parent_Latitude;
        }

        public String getBirth_Date() {
            return Birth_Date;
        }

        public void setBirth_Date(String birth_Date) {
            Birth_Date = birth_Date;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getMember_Type() {
            return Member_Type;
        }

        public void setMember_Type(String member_Type) {
            Member_Type = member_Type;
        }

        public String getFullname() {
            return Fullname;
        }

        public void setFullname(String fullname) {
            Fullname = fullname;
        }

        public String getLast_Update() {
            return Last_Update;
        }

        public void setLast_Update(String last_Update) {
            Last_Update = last_Update;
        }

        public String getReligion() {
            return Religion;
        }

        public void setReligion(String religion) {
            Religion = religion;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getParent_Count() {
            return Parent_Count;
        }

        public void setParent_Count(String parent_Count) {
            Parent_Count = parent_Count;
        }

        public String getBirth_Place() {
            return Birth_Place;
        }

        public void setBirth_Place(String birth_Place) {
            Birth_Place = birth_Place;
        }

        public String getRelation() {
            return Relation;
        }

        public void setRelation(String relation) {
            Relation = relation;
        }

        public String getUsername() {
            return Username;
        }

        public void setUsername(String username) {
            Username = username;
        }

        public String getLast_Login() {
            return Last_Login;
        }

        public void setLast_Login(String last_Login) {
            Last_Login = last_Login;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String picture) {
            Picture = picture;
        }

        public String getDevice_Id() {
            return Device_Id;
        }

        public void setDevice_Id(String device_Id) {
            Device_Id = device_Id;
        }

        public String getNumber_Phone() {
            return Number_Phone;
        }

        public void setNumber_Phone(String number_Phone) {
            Number_Phone = number_Phone;
        }

        public String getParent_NIK() {
            return Parent_NIK;
        }

        public void setParent_NIK(String parent_NIK) {
            Parent_NIK = parent_NIK;
        }

        public String getFG_Code() {
            return FG_Code;
        }

        public void setFG_Code(String FG_Code) {
            this.FG_Code = FG_Code;
        }

        public String getParentLongitude() {
            return parentLongitude;
        }

        public void setParentLongitude(String parentLongitude) {
            this.parentLongitude = parentLongitude;
        }

        public String getPublish() {
            return publish;
        }

        public void setPublish(String publish) {
            this.publish = publish;
        }

    }

    //// update foto profile
    public class UpdatePicture{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        private String message;
        private String path;

        public String getMessage() {
            return message;
        }

        public String getPath() {
            return path;
        }
    }

    //// dapat jadwal ujian
    public class  JadwalUjian{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataUjian> data;

        public void setData(List<DataUjian> data) {
            this.data = data;
        }

        public List<DataUjian> getData() {
            return data;
        }
    }
    public class DataUjian{

        @SerializedName("exam_type")
        private String exam_type;

        @SerializedName("member_id")
        private String member_id;

        @SerializedName("exam_status")
        private String exam_status;

        @SerializedName("classroom_id")
        private String classroom_id;

        @SerializedName("exam_desc")
        private String exam_desc;

        @SerializedName("type_name")
        private String type_name;

        @SerializedName("datez")
        private String datez;

        @SerializedName("scyear_id")
        private String scyear_id;

        @SerializedName("cources_id")
        private String cources_id;

        @SerializedName("type_id")
        private String type_id;

        @SerializedName("class_stat")
        private String class_stat;

        @SerializedName("edulevel_id")
        private String edulevel_id;

        @SerializedName("exam_time_ok")
        private String exam_time_ok;

        @SerializedName("total_exam_file")
        private int total_exam_file;

        @SerializedName("exam_date_ok")
        private String exam_date_ok;

        @SerializedName("exam_time")
        private String exam_time;

        @SerializedName("score_value")
        private String score_value;

        @SerializedName("examid")
        private String examid;

        @SerializedName("exam_date")
        private String exam_date;

        @SerializedName("courcesreligion_id")
        private String courcesreligion_id;

        @SerializedName("edulevel_name")
        private String edulevel_name;

        @SerializedName("cources_name")
        private String cources_name;

        public String getEdulevel_name() {
            return edulevel_name;
        }

        public void setEdulevel_name(String edulevel_name) {
            this.edulevel_name = edulevel_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getExam_type() {
            return exam_type;
        }

        public void setExam_type(String exam_type) {
            this.exam_type = exam_type;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getExam_status() {
            return exam_status;
        }

        public void setExam_status(String exam_status) {
            this.exam_status = exam_status;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getExam_desc() {
            return exam_desc;
        }

        public void setExam_desc(String exam_desc) {
            this.exam_desc = exam_desc;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getScyear_id() {
            return scyear_id;
        }

        public void setScyear_id(String scyear_id) {
            this.scyear_id = scyear_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getClass_stat() {
            return class_stat;
        }

        public void setClass_stat(String class_stat) {
            this.class_stat = class_stat;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

        public String getExam_time_ok() {
            return exam_time_ok;
        }

        public void setExam_time_ok(String exam_time_ok) {
            this.exam_time_ok = exam_time_ok;
        }

        public int getTotal_exam_file() {
            return total_exam_file;
        }

        public void setTotal_exam_file(int total_exam_file) {
            this.total_exam_file = total_exam_file;
        }

        public String getExam_date_ok() {
            return exam_date_ok;
        }

        public void setExam_date_ok(String exam_date_ok) {
            this.exam_date_ok = exam_date_ok;
        }

        public String getExam_time() {
            return exam_time;
        }

        public void setExam_time(String exam_time) {
            this.exam_time = exam_time;
        }

        public String getScore_value() {
            return score_value;
        }

        public void setScore_value(String score_value) {
            this.score_value = score_value;
        }

        public String getExamid() {
            return examid;
        }

        public void setExamid(String examid) {
            this.examid = examid;
        }

        public String getExam_date() {
            return exam_date;
        }

        public void setExam_date(String exam_date) {
            this.exam_date = exam_date;
        }

        public String getCourcesreligion_id() {
            return courcesreligion_id;
        }

        public void setCourcesreligion_id(String courcesreligion_id) {
            this.courcesreligion_id = courcesreligion_id;
        }
    }

    //// dapat jadwal pelajaran
    public class JadwalPelajaran{


        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataJadwal data;

        @SerializedName("status")
        public int status;

        public void setCode(String code){
            this.code = code;
        }

        public String getCode(){
            return code;
        }

        public DataJadwal getData() {
            return data;
        }

        public void setData(DataJadwal data) {
            this.data = data;
        }

        public void setStatus(int status){
            this.status = status;
        }

        public int getStatus(){
            return status;
        }
    }

    public class DataJadwal {
        @SerializedName("class_schedule")
        public List<JadwalData> class_schedule;

        public List<JadwalData> getClass_schedule() {
            return class_schedule;
        }

        public void setClass_schedule(List<JadwalData> class_schedule) {
            this.class_schedule = class_schedule;
        }

        public List<AgendaData> getClass_agenda() {
            return class_agenda;
        }

        public void setClass_agenda(List<AgendaData> class_agenda) {
            this.class_agenda = class_agenda;
        }

        @SerializedName("class_agenda")
        public List<AgendaData> class_agenda;
    }
    public class JadwalData{

        @SerializedName("day_type")
        public String dayType;

        @SerializedName("schedule_class")
        public List<ScheduleClassItem> scheduleClass;

        @SerializedName("day_name")
        public String dayName;

        @SerializedName("dayid")
        public String dayid;

        @SerializedName("day_status")
        public String dayStatus;

        public void setDayType(String dayType){
            this.dayType = dayType;
        }

        public String getDayType(){
            return dayType;
        }

        public void setScheduleClass(List<ScheduleClassItem> scheduleClass){
            this.scheduleClass = scheduleClass;
        }

        public List<ScheduleClassItem> getScheduleClass(){
            return scheduleClass;
        }

        public void setDayName(String dayName){
            this.dayName = dayName;
        }

        public String getDayName(){
            return dayName;
        }

        public void setDayid(String dayid){
            this.dayid = dayid;
        }

        public String getDayid(){
            return dayid;
        }

        public void setDayStatus(String dayStatus){
            this.dayStatus = dayStatus;
        }

        public String getDayStatus(){
            return dayStatus;
        }
    }
    public class AgendaData{
        @SerializedName("desc")
        private String desc;
        @SerializedName("type")
        private String type;
        @SerializedName("date")
        private String date;
        @SerializedName("content")
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }


    }
    public class ScheduleClassItem{


        @SerializedName("timez_ok")
        private String timezOk;

        @SerializedName("teacher_name")
        private String teacherName;

        @SerializedName("schedule_time")
        private String scheduleTime;

        @SerializedName("cources_id")
        private String courcesId;

        @SerializedName("teacher_id")
        private String teacherId;

        @SerializedName("lesson_duration")
        private int lessonDuration;

        @SerializedName("cources_name")
        private String courcesName;

        @SerializedName("timez_finish")
        private String timezFinish;

        public void setTimezOk(String timezOk){
            this.timezOk = timezOk;
        }

        public String getTimezOk(){
            return timezOk;
        }

        public void setTeacherName(String teacherName){
            this.teacherName = teacherName;
        }

        public String getTeacherName(){
            return teacherName;
        }

        public void setScheduleTime(String scheduleTime){
            this.scheduleTime = scheduleTime;
        }

        public String getScheduleTime(){
            return scheduleTime;
        }

        public void setCourcesId(String courcesId){
            this.courcesId = courcesId;
        }

        public String getCourcesId(){
            return courcesId;
        }

        public void setTeacherId(String teacherId){
            this.teacherId = teacherId;
        }

        public String getTeacherId(){
            return teacherId;
        }

        public void setLessonDuration(int lessonDuration){
            this.lessonDuration = lessonDuration;
        }

        public int getLessonDuration(){
            return lessonDuration;
        }

        public void setCourcesName(String courcesName){
            this.courcesName = courcesName;
        }

        public String getCourcesName(){
            return courcesName;
        }

        public void setTimezFinish(String timezFinish){
            this.timezFinish = timezFinish;
        }

        public String getTimezFinish(){
            return timezFinish;
        }
    }

    //// dapat tugas anak
    public class TugasAnak{
        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<TugasItem> data;

        @SerializedName("status")
        public int status;

        public void setCode(String code){
            this.code = code;
        }

        public String getCode(){
            return code;
        }

        public void setData(List<TugasItem> data){
            this.data = data;
        }

        public List<TugasItem> getData(){
            return data;
        }

        public void setStatus(int status){
            this.status = status;
        }

        public int getStatus(){
            return status;
        }
    }
    public class TugasItem{


        @SerializedName("cources_name")
        private  String cources_name;

        @SerializedName("teacher_name")
        private  String teacher_name;


        @SerializedName("exam_type")
        private String examType;

        @SerializedName("member_id")
        private String memberId;

        @SerializedName("exam_status")
        private String examStatus;

        @SerializedName("classroom_id")
        private String classroomId;

        @SerializedName("exam_desc")
        private String examDesc;

        @SerializedName("datez")
        private String datez;

        @SerializedName("scyear_id")
        private String scyearId;

        @SerializedName("cources_id")
        private String courcesId;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("class_stat")
        private String classStat;

        @SerializedName("edulevel_id")
        private String edulevelId;

        @SerializedName("total_exam_file")
        private int totalExamFile;

        @SerializedName("exam_date_ok")
        private String examDateOk;

        @SerializedName("exam_time")
        private String examTime;

        @SerializedName("score_value")
        private String scoreValue;

        @SerializedName("examid")
        private String examid;

        @SerializedName("exam_date")
        private String examDate;

        @SerializedName("courcesreligion_id")
        private String courcesreligionId;

        @SerializedName("exam_type_name")
        private String examTypeName;

        public void setExamType(String examType){
            this.examType = examType;
        }

        public String getExamType(){
            return examType;
        }

        public void setMemberId(String memberId){
            this.memberId = memberId;
        }

        public String getMemberId(){
            return memberId;
        }

        public void setExamStatus(String examStatus){
            this.examStatus = examStatus;
        }

        public String getExamStatus(){
            return examStatus;
        }

        public void setClassroomId(String classroomId){
            this.classroomId = classroomId;
        }

        public String getClassroomId(){
            return classroomId;
        }

        public void setExamDesc(String examDesc){
            this.examDesc = examDesc;
        }

        public String getExamDesc(){
            return examDesc;
        }

        public void setDatez(String datez){
            this.datez = datez;
        }

        public String getDatez(){
            return datez;
        }

        public void setScyearId(String scyearId){
            this.scyearId = scyearId;
        }

        public String getScyearId(){
            return scyearId;
        }

        public void setCourcesId(String courcesId){
            this.courcesId = courcesId;
        }

        public String getCourcesId(){
            return courcesId;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setClassStat(String classStat){
            this.classStat = classStat;
        }

        public String getClassStat(){
            return classStat;
        }

        public void setEdulevelId(String edulevelId){
            this.edulevelId = edulevelId;
        }

        public String getEdulevelId(){
            return edulevelId;
        }

        public void setTotalExamFile(int totalExamFile){
            this.totalExamFile = totalExamFile;
        }

        public int getTotalExamFile(){
            return totalExamFile;
        }

        public void setExamDateOk(String examDateOk){
            this.examDateOk = examDateOk;
        }

        public String getExamDateOk(){
            return examDateOk;
        }

        public void setExamTime(String examTime){
            this.examTime = examTime;
        }

        public String getExamTime(){
            return examTime;
        }

        public void setScoreValue(String scoreValue){
            this.scoreValue = scoreValue;
        }

        public String getScoreValue(){
            return scoreValue;
        }

        public void setExamid(String examid){
            this.examid = examid;
        }

        public String getExamid(){
            return examid;
        }

        public void setExamDate(String examDate){
            this.examDate = examDate;
        }

        public String getExamDate(){
            return examDate;
        }

        public void setCourcesreligionId(String courcesreligionId){
            this.courcesreligionId = courcesreligionId;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getCourcesreligionId(){
            return courcesreligionId;
        }

        public void setExamTypeName(String examTypeName){
            this.examTypeName = examTypeName;
        }

        public String getExamTypeName(){
            return examTypeName;
        }
    }

    //// dapat nilai raport anak
    public class Raport{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public RaportData data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public RaportData getData() {
            return data;
        }

        public void setData(RaportData data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public class RaportData{

        @SerializedName("detail_score")
        public List<DetailScoreItem> detailScore;

        @SerializedName("classroom")
        public Classroom classroom;

        public List<DetailScoreItem> getDetailScore() {
            return detailScore;
        }

        public void setDetailScore(List<DetailScoreItem> detailScore) {
            this.detailScore = detailScore;
        }

        public Classroom getClassroom() {
            return classroom;
        }

        public void setClassroom(Classroom classroom) {
            this.classroom = classroom;
        }
    }
    public class DetailScoreItem{

        @SerializedName("member_id")
        private String memberId;

        @SerializedName("religion_type")
        private String religionType;

        @SerializedName("datez")
        private String datez;

        @SerializedName("colour_id")
        private String colourId;

        @SerializedName("scoring_percent")
        private int scoringPercent;

        @SerializedName("type_exam")
        private TypeExam typeExam;

        @SerializedName("cources_publish")
        private String courcesPublish;

        @SerializedName("cources_spec")
        private String courcesSpec;

        @SerializedName("class_average_score")
        private double classAverageScore;

        @SerializedName("cources_type")
        private String courcesType;

        @SerializedName("final_score")
        private double finalScore;

        @SerializedName("cources_code")
        private String courcesCode;

        @SerializedName("cources_name")
        private String courcesName;

        @SerializedName("lastupdate")
        private String lastupdate;

        @SerializedName("courcesid")
        private String courcesid;

        public void setMemberId(String memberId){
            this.memberId = memberId;
        }

        public String getMemberId(){
            return memberId;
        }

        public void setReligionType(String religionType){
            this.religionType = religionType;
        }

        public String getReligionType(){
            return religionType;
        }

        public void setDatez(String datez){
            this.datez = datez;
        }

        public String getDatez(){
            return datez;
        }

        public void setColourId(String colourId){
            this.colourId = colourId;
        }

        public String getColourId(){
            return colourId;
        }

        public void setScoringPercent(int scoringPercent){
            this.scoringPercent = scoringPercent;
        }

        public int getScoringPercent(){
            return scoringPercent;
        }

        public void setTypeExam(TypeExam typeExam){
            this.typeExam = typeExam;
        }

        public TypeExam getTypeExam(){
            return typeExam;
        }

        public void setCourcesPublish(String courcesPublish){
            this.courcesPublish = courcesPublish;
        }

        public String getCourcesPublish(){
            return courcesPublish;
        }

        public void setCourcesSpec(String courcesSpec){
            this.courcesSpec = courcesSpec;
        }

        public String getCourcesSpec(){
            return courcesSpec;
        }

        public void setClassAverageScore(double classAverageScore){
            this.classAverageScore = classAverageScore;
        }

        public double getClassAverageScore(){
            return classAverageScore;
        }

        public void setCourcesType(String courcesType){
            this.courcesType = courcesType;
        }

        public String getCourcesType(){
            return courcesType;
        }

        public void setFinalScore(double finalScore){
            this.finalScore = finalScore;
        }

        public double getFinalScore(){
            return finalScore;
        }

        public void setCourcesCode(String courcesCode){
            this.courcesCode = courcesCode;
        }

        public String getCourcesCode(){
            return courcesCode;
        }

        public void setCourcesName(String courcesName){
            this.courcesName = courcesName;
        }

        public String getCourcesName(){
            return courcesName;
        }

        public void setLastupdate(String lastupdate){
            this.lastupdate = lastupdate;
        }

        public String getLastupdate(){
            return lastupdate;
        }

        public void setCourcesid(String courcesid){
            this.courcesid = courcesid;
        }

        public String getCourcesid(){
            return courcesid;
        }
    }
    public class Classroom{
        @SerializedName("classroom_desc")
        private String classroomDesc;

        @SerializedName("classroom_name")
        private String classroomName;

        @SerializedName("scyear_id")
        private String scyearId;

        @SerializedName("promote_ranking")
        private String promoteRanking;

        @SerializedName("classroom_code")
        private String classroomCode;

        @SerializedName("promote_text")
        private String promoteText;

        @SerializedName("promote_status")
        private String promoteStatus;

        @SerializedName("classroomid")
        private String classroomid;

        @SerializedName("description_text")
        private String descriptionText;

        @SerializedName("edulevel_id")
        private String edulevelId;

        public void setClassroomDesc(String classroomDesc){
            this.classroomDesc = classroomDesc;
        }

        public String getClassroomDesc(){
            return classroomDesc;
        }

        public void setClassroomName(String classroomName){
            this.classroomName = classroomName;
        }

        public String getClassroomName(){
            return classroomName;
        }

        public void setScyearId(String scyearId){
            this.scyearId = scyearId;
        }

        public String getScyearId(){
            return scyearId;
        }

        public void setPromoteRanking(String promoteRanking){
            this.promoteRanking = promoteRanking;
        }

        public String getPromoteRanking(){
            return promoteRanking;
        }

        public void setClassroomCode(String classroomCode){
            this.classroomCode = classroomCode;
        }

        public String getClassroomCode(){
            return classroomCode;
        }

        public void setPromoteText(String promoteText){
            this.promoteText = promoteText;
        }

        public String getPromoteText(){
            return promoteText;
        }

        public void setPromoteStatus(String promoteStatus){
            this.promoteStatus = promoteStatus;
        }

        public String getPromoteStatus(){
            return promoteStatus;
        }

        public void setClassroomid(String classroomid){
            this.classroomid = classroomid;
        }

        public String getClassroomid(){
            return classroomid;
        }

        public void setDescriptionText(String descriptionText){
            this.descriptionText = descriptionText;
        }

        public String getDescriptionText(){
            return descriptionText;
        }

        public void setEdulevelId(String edulevelId){
            this.edulevelId = edulevelId;
        }

        public String getEdulevelId(){
            return edulevelId;
        }
    }
    public class TypeExam{

        @SerializedName("1")
        private UjianSekolah ujianSekolah;

        @SerializedName("2")
        private UjianNegara ujianNegara;

        @SerializedName("3")
        private UlanganHarian ulanganHarian;

        @SerializedName("4")
        private LatihanTeori latihanTeori;

        @SerializedName("5")
        private Praktikum praktikum;

        @SerializedName("6")
        private Ekstrakulikuler ekstrakulikuler;

        public UjianSekolah getUjianSekolah() {
            return ujianSekolah;
        }

        public void setUjianSekolah(UjianSekolah ujianSekolah) {
            this.ujianSekolah = ujianSekolah;
        }

        public UjianNegara getUjianNegara() {
            return ujianNegara;
        }

        public void setUjianNegara(UjianNegara ujianNegara) {
            this.ujianNegara = ujianNegara;
        }

        public UlanganHarian getUlanganHarian() {
            return ulanganHarian;
        }

        public void setUlanganHarian(UlanganHarian ulanganHarian) {
            this.ulanganHarian = ulanganHarian;
        }

        public LatihanTeori getLatihanTeori() {
            return latihanTeori;
        }

        public void setLatihanTeori(LatihanTeori latihanTeori) {
            this.latihanTeori = latihanTeori;
        }

        public Praktikum getPraktikum() {
            return praktikum;
        }

        public void setPraktikum(Praktikum praktikum) {
            this.praktikum = praktikum;
        }

        public Ekstrakulikuler getEkstrakulikuler() {
            return ekstrakulikuler;
        }

        public void setEkstrakulikuler(Ekstrakulikuler ekstrakulikuler) {
            this.ekstrakulikuler = ekstrakulikuler;
        }
    }
    public class UjianSekolah{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class UjianNegara{

        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class UlanganHarian{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class LatihanTeori{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class Praktikum{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class Ekstrakulikuler{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }

    /// cek semester sekarang
    public class CheckSemester{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public String data;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    ///// Response List Semester
    public class ListSemester{
        @SerializedName("code")
        public String code;

        @SerializedName("data")
        @Expose
        public List<DataSemester> data;

        @SerializedName("status")
        public int status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataSemester> getData() {
            return data;
        }

        public void setData(List<DataSemester> data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public class DataSemester{

        @SerializedName("semesterid")
        public String semester_id;

        @SerializedName("zedulevel_id")
        public String zedulevel_id;

        @SerializedName("semester_publish")
        public String semester_publish;

        @SerializedName("semester_name")
        public String semester_name;

        @SerializedName("start_date")
        public String start_date;

        @SerializedName("end_date")
        public String end_date;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("lastupdate")
        public String lastupdate;

        @SerializedName("zedulevelid")
        public String zedulevelid;

        @SerializedName("edulevel_name")
        public String edulevel_name;

        @SerializedName("edulevel_id")
        public String edulevel_id;

        @SerializedName("scyear_id")
        public String scyear_id;

        public String getZedulevel_id() {
            return zedulevel_id;
        }

        public void setZedulevel_id(String zedulevel_id) {
            this.zedulevel_id = zedulevel_id;
        }

        public String getSemester_publish() {
            return semester_publish;
        }

        public void setSemester_publish(String semester_publish) {
            this.semester_publish = semester_publish;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getLastupdate() {
            return lastupdate;
        }

        public void setLastupdate(String lastupdate) {
            this.lastupdate = lastupdate;
        }

        public String getZedulevelid() {
            return zedulevelid;
        }

        public void setZedulevelid(String zedulevelid) {
            this.zedulevelid = zedulevelid;
        }

        public String getEdulevel_name() {
            return edulevel_name;
        }

        public void setEdulevel_name(String edulevel_name) {
            this.edulevel_name = edulevel_name;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

        public String getScyear_id() {
            return scyear_id;
        }

        public void setScyear_id(String scyear_id) {
            this.scyear_id = scyear_id;
        }

        public String getSemester_id() {
            return semester_id;
        }

        public void setSemester_id(String semester_id) {
            this.semester_id = semester_id;
        }

        public String getSemester_name() {
            return semester_name;
        }

        public void setSemester_name(String semester_name) {
            this.semester_name = semester_name;
        }
    }

    ////// List anak
    public class ListChildren{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataChildren> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataChildren> getData() {
            return data;
        }

        public void setData(List<DataChildren> data) {
            this.data = data;
        }
    }
    public class DataChildren{
        @SerializedName("student_id")
        public String student_id;

        @SerializedName("school_id")
        public String school_id;

        @SerializedName("school_code")
        public String school_code;

        @SerializedName("children_name")
        public String children_name;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("picture")
        public String picture;

        @SerializedName("school_name")
        public String school_name;

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchool_code() {
            return school_code;
        }

        public void setSchool_code(String school_code) {
            this.school_code = school_code;
        }

        public String getChildren_name() {
            return children_name;
        }

        public void setChildren_name(String children_name) {
            this.children_name = children_name;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

    //// Absensi anak
    public class AbsenSiswa{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataJam> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataJam> getData() {
            return data;
        }

        public void setData(List<DataJam> data) {
            this.data = data;
        }
    }
    public class DataJam{
        @SerializedName("schedule_time")
        public String schedule_time;


        @SerializedName("timez_ok")
        public String timez_ok;

        @SerializedName("timez_finish")
        public String timez_finish;

        @SerializedName("lesson_duration")
        public String lesson_duration;

        @SerializedName("total_attendance")
        public String total_attendance;

        @SerializedName("total_leave_sick")
        public String total_leave_sick;

        @SerializedName("days")
        public List<DataHari> days;

        public String getTimez_ok() {
            return timez_ok;
        }

        public void setTimez_ok(String timez_ok) {
            this.timez_ok = timez_ok;
        }
        public String getSchedule_time() {
            return schedule_time;
        }

        public void setSchedule_time(String schedule_time) {
            this.schedule_time = schedule_time;
        }

        public String getTimez_finish() {
            return timez_finish;
        }

        public void setTimez_finish(String timez_finish) {
            this.timez_finish = timez_finish;
        }

        public String getLesson_duration() {
            return lesson_duration;
        }

        public void setLesson_duration(String lesson_duration) {
            this.lesson_duration = lesson_duration;
        }

        public String getTotal_attendance() {
            return total_attendance;
        }

        public void setTotal_attendance(String total_attendance) {
            this.total_attendance = total_attendance;
        }

        public String getTotal_leave_sick() {
            return total_leave_sick;
        }

        public void setTotal_leave_sick(String total_leave_sick) {
            this.total_leave_sick = total_leave_sick;
        }

        public List<DataHari> getDays() {
            return days;
        }

        public void setDays(List<DataHari> days) {
            this.days = days;
        }
    }
    public class DataHari{
        @SerializedName("day_id")
        public String day_id;

        @SerializedName("day_type")
        public String day_type;

        @SerializedName("absent_status")
        public String absen_status;

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

    ///// Dapat kalendar kelas
    public class ClassCalendar{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataCalendar> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataCalendar> getData() {
            return data;
        }

        public void setData(List<DataCalendar> data) {
            this.data = data;
        }
    }
    public class DataCalendar{

        @SerializedName("calendar_id")
        public int calendar_id;

        @SerializedName("calendar_title")
        public String calendar_title;

        @SerializedName("calendar_desc")
        public String calendar_desc;

        @SerializedName("calendar_date")
        public String calendar_date;

        @SerializedName("calendar_time")
        public String calendar_time;

        @SerializedName("calendar_type")
        public String calendar_type;

        @SerializedName("calendar_colour")
        public String calendar_colour;

        public String getCalendar_colour() {
            return calendar_colour;
        }

        public void setCalendar_colour(String calendar_colour) {
            this.calendar_colour = calendar_colour;
        }

        public int getCalendar_id() {
            return calendar_id;
        }

        public void setCalendar_id(int calendar_id) {
            this.calendar_id = calendar_id;
        }

        public String getCalendar_title() {
            return calendar_title;
        }

        public void setCalendar_title(String calendar_title) {
            this.calendar_title = calendar_title;
        }

        public String getCalendar_desc() {
            return calendar_desc;
        }

        public void setCalendar_desc(String calendar_desc) {
            this.calendar_desc = calendar_desc;
        }

        public String getCalendar_date() {
            return calendar_date;
        }

        public void setCalendar_date(String calendar_date) {
            this.calendar_date = calendar_date;
        }

        public String getCalendar_time() {
            return calendar_time;
        }

        public void setCalendar_time(String calendar_time) {
            this.calendar_time = calendar_time;
        }

        public String getCalendar_type() {
            return calendar_type;
        }

        public void setCalendar_type(String calendar_type) {
            this.calendar_type = calendar_type;
        }
    }

    //// detail kelas
    public class ClassroomDetail{

        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataClassroom data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DataClassroom getData() {
            return data;
        }

        public void setData(DataClassroom data) {
            this.data = data;
        }
    }
    public class DataClassroom{
        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("edulevel_id")
        public String edulevel_id;

        @SerializedName("scyear_id")
        public String scyear_id;

        @SerializedName("scyear_name")
        public String scyear_name;

        @SerializedName("homeroom_teacher")
        public String homeroom_teacher;

        @SerializedName("edulevel_name")
        public String edulevel_name;

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

        public String getScyear_id() {
            return scyear_id;
        }

        public void setScyear_id(String scyear_id) {
            this.scyear_id = scyear_id;
        }

        public String getScyear_name() {
            return scyear_name;
        }

        public void setScyear_name(String scyear_name) {
            this.scyear_name = scyear_name;
        }

        public String getHomeroom_teacher() {
            return homeroom_teacher;
        }

        public void setHomeroom_teacher(String homeroom_teacher) {
            this.homeroom_teacher = homeroom_teacher;
        }

        public String getEdulevel_name() {
            return edulevel_name;
        }

        public void setEdulevel_name(String edulevel_name) {
            this.edulevel_name = edulevel_name;
        }
    }

    //// Detail kalendar
    public class CalendarDetail{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataCalendarDetail data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DataCalendarDetail getData() {
            return data;
        }

        public void setData(DataCalendarDetail data) {
            this.data = data;
        }
    }
    public class DataCalendarDetail{
        @SerializedName("calendarid")
        public String calendarid;

        @SerializedName("calendar_title")
        public String calendar_title;

        @SerializedName("calendar_desc")
        public String calendar_desc;

        @SerializedName("calendar_date")
        public String calendar_date;

        @SerializedName("calendar_time")
        public String calendar_time;

        @SerializedName("calendar_bg")
        public String calendar_bg;

        @SerializedName("calendar_status")
        public String calendar_status;

        @SerializedName("calendar_type")
        public String calendar_type;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("lastupdate")
        public String lastupdate;

        @SerializedName("event_type")
        public String event_type;

        @SerializedName("edulevel_id")
        public String edulevel_id;

        @SerializedName("scyear_id")
        public String scyear_id;

        @SerializedName("courcesreligion_id")
        public String courcesreligion_id;

        @SerializedName("reminder_status")
        public String reminder_status;

        @SerializedName("reminder_duration")
        public String reminder_duration;

        @SerializedName("calendar_date_ok")
        public String calendar_date_ok;

        @SerializedName("timez")
        public String timez;

        @SerializedName("created_by")
        public String created_by;

        @SerializedName("edulevel_name")
        public String edulevel_name;

        @SerializedName("classroom_name")
        public String classroom_name;

        public String getCalendarid() {
            return calendarid;
        }

        public void setCalendarid(String calendarid) {
            this.calendarid = calendarid;
        }

        public String getCalendar_title() {
            return calendar_title;
        }

        public void setCalendar_title(String calendar_title) {
            this.calendar_title = calendar_title;
        }

        public String getCalendar_desc() {
            return calendar_desc;
        }

        public void setCalendar_desc(String calendar_desc) {
            this.calendar_desc = calendar_desc;
        }

        public String getCalendar_date() {
            return calendar_date;
        }

        public void setCalendar_date(String calendar_date) {
            this.calendar_date = calendar_date;
        }

        public String getCalendar_time() {
            return calendar_time;
        }

        public void setCalendar_time(String calendar_time) {
            this.calendar_time = calendar_time;
        }

        public String getCalendar_bg() {
            return calendar_bg;
        }

        public void setCalendar_bg(String calendar_bg) {
            this.calendar_bg = calendar_bg;
        }

        public String getCalendar_status() {
            return calendar_status;
        }

        public void setCalendar_status(String calendar_status) {
            this.calendar_status = calendar_status;
        }

        public String getCalendar_type() {
            return calendar_type;
        }

        public void setCalendar_type(String calendar_type) {
            this.calendar_type = calendar_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getLastupdate() {
            return lastupdate;
        }

        public void setLastupdate(String lastupdate) {
            this.lastupdate = lastupdate;
        }

        public String getEvent_type() {
            return event_type;
        }

        public void setEvent_type(String event_type) {
            this.event_type = event_type;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

        public String getScyear_id() {
            return scyear_id;
        }

        public void setScyear_id(String scyear_id) {
            this.scyear_id = scyear_id;
        }

        public String getCourcesreligion_id() {
            return courcesreligion_id;
        }

        public void setCourcesreligion_id(String courcesreligion_id) {
            this.courcesreligion_id = courcesreligion_id;
        }

        public String getReminder_status() {
            return reminder_status;
        }

        public void setReminder_status(String reminder_status) {
            this.reminder_status = reminder_status;
        }

        public String getReminder_duration() {
            return reminder_duration;
        }

        public void setReminder_duration(String reminder_duration) {
            this.reminder_duration = reminder_duration;
        }

        public String getCalendar_date_ok() {
            return calendar_date_ok;
        }

        public void setCalendar_date_ok(String calendar_date_ok) {
            this.calendar_date_ok = calendar_date_ok;
        }

        public String getTimez() {
            return timez;
        }

        public void setTimez(String timez) {
            this.timez = timez;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getEdulevel_name() {
            return edulevel_name;
        }

        public void setEdulevel_name(String edulevel_name) {
            this.edulevel_name = edulevel_name;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }
    }

    //// dapat pesan orang tua
    public class PesanAnak{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataPesan> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataPesan> getData() {
            return data;
        }

        public void setData(List<DataPesan> data) {
            this.data = data;
        }
    }
    public class DataPesan{
        @SerializedName("messageid")
        public String messageid;

        @SerializedName("user_id_from")
        public String user_id_from;

        @SerializedName("user_id_to")
        public String user_id_to;

        @SerializedName("message_cont")
        public String message_cont;

        @SerializedName("message_date")
        public String message_date;

        @SerializedName("message_title")
        public String message_title;

        @SerializedName("message_status")
        public String message_status;

        @SerializedName("message_type")
        public String message_type;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("cources_id")
        public String cources_id;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("reply_message_id")
        public String reply_message_id;

        @SerializedName("read_status")
        public String read_status;

        @SerializedName("read_date")
        public String read_date;

        @SerializedName("read_message_id")
        public String read_message_id;

        @SerializedName("school_id")
        public String school_id;

        @SerializedName("parent_status")
        public String parent_status;

        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("datez_ok")
        public String datez_ok;

        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("cources_name")
        public String cources_name;

        @SerializedName("sender_name")
        public String sender_name;

        @SerializedName("member_type_text")
        public String member_type_text;

        @SerializedName("parent_message_id")
        public String parent_message_id;

        @SerializedName("reply_status")
        public int reply_status;

        public String getMessage_title() {
            return message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUser_id_from() {
            return user_id_from;
        }

        public void setUser_id_from(String user_id_from) {
            this.user_id_from = user_id_from;
        }

        public String getUser_id_to() {
            return user_id_to;
        }

        public void setUser_id_to(String user_id_to) {
            this.user_id_to = user_id_to;
        }

        public String getMessage_cont() {
            return message_cont;
        }

        public void setMessage_cont(String message_cont) {
            this.message_cont = message_cont;
        }

        public String getMessage_date() {
            return message_date;
        }

        public void setMessage_date(String message_date) {
            this.message_date = message_date;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getReply_message_id() {
            return reply_message_id;
        }

        public void setReply_message_id(String reply_message_id) {
            this.reply_message_id = reply_message_id;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getRead_date() {
            return read_date;
        }

        public void setRead_date(String read_date) {
            this.read_date = read_date;
        }

        public String getRead_message_id() {
            return read_message_id;
        }

        public void setRead_message_id(String read_message_id) {
            this.read_message_id = read_message_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getParent_status() {
            return parent_status;
        }

        public void setParent_status(String parent_status) {
            this.parent_status = parent_status;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getMember_type_text() {
            return member_type_text;
        }

        public void setMember_type_text(String member_type_text) {
            this.member_type_text = member_type_text;
        }

        public String getParent_message_id() {
            return parent_message_id;
        }

        public void setParent_message_id(String parent_message_id) {
            this.parent_message_id = parent_message_id;
        }

        public int getReply_status() {
            return reply_status;
        }

        public void setReply_status(int reply_status) {
            this.reply_status = reply_status;
        }
    }

    /// pesan detail
    public class PesanDetail{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataPesanDetail data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DataPesanDetail getData() {
            return data;
        }

        public void setData(DataPesanDetail data) {
            this.data = data;
        }
    }
    public class DataPesanDetail{
        @SerializedName("message")
        public DataMessage dataMessage;

        @SerializedName("reply_messages")
        public List<ReplyMessage> replyMessages;

        public DataMessage getDataMessage() {
            return dataMessage;
        }

        public void setDataMessage(DataMessage dataMessage) {
            this.dataMessage = dataMessage;
        }

        public List<ReplyMessage> getReplyMessages() {
            return replyMessages;
        }

        public void setReplyMessages(List<ReplyMessage> replyMessages) {
            this.replyMessages = replyMessages;
        }
    }
    public class DataMessage{
        @SerializedName("messageid")
        public String messageid;

        @SerializedName("user_id_from")
        public String user_id_from;

        @SerializedName("user_id_to")
        public String user_id_to;

        @SerializedName("message_cont")
        public String message_cont;

        @SerializedName("message_date")
        public String message_date;

        @SerializedName("message_status")
        public String message_status;

        @SerializedName("message_type")
        public String message_type;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("cources_id")
        public String cources_id;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("reply_message_id")
        public String reply_message_id;

        @SerializedName("read_status")
        public String read_status;

        @SerializedName("read_date")
        public String read_date;

        @SerializedName("read_message_id")
        public String read_message_id;

        @SerializedName("school_id")
        public String school_id;

        @SerializedName("parent_status")
        public String parent_status;

        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("datez_ok")
        public String datez_ok;

        @SerializedName("school_name")
        public String school_name;

        @SerializedName("created_by")
        public String created_by;

        @SerializedName("student_name")
        public String student_name;

        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("cources_name")
        public String cources_name;

        @SerializedName("sender_name")
        public String sender_name;

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getMessage_title() {
            return message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        @SerializedName("message_title")
        public String message_title;

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUser_id_from() {
            return user_id_from;
        }

        public void setUser_id_from(String user_id_from) {
            this.user_id_from = user_id_from;
        }

        public String getUser_id_to() {
            return user_id_to;
        }

        public void setUser_id_to(String user_id_to) {
            this.user_id_to = user_id_to;
        }

        public String getMessage_cont() {
            return message_cont;
        }

        public void setMessage_cont(String message_cont) {
            this.message_cont = message_cont;
        }

        public String getMessage_date() {
            return message_date;
        }

        public void setMessage_date(String message_date) {
            this.message_date = message_date;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getReply_message_id() {
            return reply_message_id;
        }

        public void setReply_message_id(String reply_message_id) {
            this.reply_message_id = reply_message_id;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getRead_date() {
            return read_date;
        }

        public void setRead_date(String read_date) {
            this.read_date = read_date;
        }

        public String getRead_message_id() {
            return read_message_id;
        }

        public void setRead_message_id(String read_message_id) {
            this.read_message_id = read_message_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getParent_status() {
            return parent_status;
        }

        public void setParent_status(String parent_status) {
            this.parent_status = parent_status;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }
    }
    public class ReplyMessage{
        @SerializedName("messageid")
        public String messageid;

        @SerializedName("user_id_from")
        public String user_id_from;

        @SerializedName("user_id_to")
        public String user_id_to;

        @SerializedName("message_cont")
        public String message_cont;

        @SerializedName("message_date")
        public String message_date;

        @SerializedName("message_status")
        public String message_status;

        @SerializedName("message_type")
        public String message_type;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("cources_id")
        public String cources_id;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("reply_message_id")
        public String reply_message_id;

        @SerializedName("read_status")
        public String read_status;

        @SerializedName("read_date")
        public String read_date;

        @SerializedName("read_message_id")
        public String read_message_id;

        @SerializedName("school_id")
        public String school_id;

        @SerializedName("parent_status")
        public String parent_status;

        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("datez_ok")
        public String datez_ok;

        @SerializedName("school_name")
        public String school_name;

        @SerializedName("created_by")
        public String created_by;

        @SerializedName("student_name")
        public String student_name;

        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("cources_name")
        public String cources_name;

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUser_id_from() {
            return user_id_from;
        }

        public void setUser_id_from(String user_id_from) {
            this.user_id_from = user_id_from;
        }

        public String getUser_id_to() {
            return user_id_to;
        }

        public void setUser_id_to(String user_id_to) {
            this.user_id_to = user_id_to;
        }

        public String getMessage_cont() {
            return message_cont;
        }

        public void setMessage_cont(String message_cont) {
            this.message_cont = message_cont;
        }

        public String getMessage_date() {
            return message_date;
        }

        public void setMessage_date(String message_date) {
            this.message_date = message_date;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getReply_message_id() {
            return reply_message_id;
        }

        public void setReply_message_id(String reply_message_id) {
            this.reply_message_id = reply_message_id;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getRead_date() {
            return read_date;
        }

        public void setRead_date(String read_date) {
            this.read_date = read_date;
        }

        public String getRead_message_id() {
            return read_message_id;
        }

        public void setRead_message_id(String read_message_id) {
            this.read_message_id = read_message_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getParent_status() {
            return parent_status;
        }

        public void setParent_status(String parent_status) {
            this.parent_status = parent_status;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }
    }

    ///// List nofitication
    public class ListNotification{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataList> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataList> getData() {
            return data;
        }

        public void setData(List<DataList> data) {
            this.data = data;
        }
    }
    public class DataList{
        @SerializedName("notifyid")
        public String notifyid;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("notify_type")
        public String notify_type;

        @SerializedName("az_id")
        public String az_id;

        @SerializedName("notify_date")
        public String notify_date;

        @SerializedName("notify_status")
        public String notify_status;

        @SerializedName("notify_read")
        public String notify_read;

        @SerializedName("notify_date_ok")
        public String notify_date_ok;

        @SerializedName("title")
        public String title;

        public String getNotifyid() {
            return notifyid;
        }

        public void setNotifyid(String notifyid) {
            this.notifyid = notifyid;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getNotify_type() {
            return notify_type;
        }

        public void setNotify_type(String notify_type) {
            this.notify_type = notify_type;
        }

        public String getAz_id() {
            return az_id;
        }

        public void setAz_id(String az_id) {
            this.az_id = az_id;
        }

        public String getNotify_date() {
            return notify_date;
        }

        public void setNotify_date(String notify_date) {
            this.notify_date = notify_date;
        }

        public String getNotify_status() {
            return notify_status;
        }

        public void setNotify_status(String notify_status) {
            this.notify_status = notify_status;
        }

        public String getNotify_read() {
            return notify_read;
        }

        public void setNotify_read(String notify_read) {
            this.notify_read = notify_read;
        }

        public String getNotify_date_ok() {
            return notify_date_ok;
        }

        public void setNotify_date_ok(String notify_date_ok) {
            this.notify_date_ok = notify_date_ok;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    ///// Balas Pesan
    public class BalasPesan{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public Integer data;

    }

    ///// Response List Mata Pelajaran
    public class ListMapel{
        @SerializedName("code")
        public String code;

        @SerializedName("data")
        @Expose
        public List<DataMapel> data;

        @SerializedName("status")
        public int status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataMapel> getData() {
            return data;
        }

        public void setData(List<DataMapel> data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public class DataMapel{

        @SerializedName("courcesid")
        public String courcesid;

        @SerializedName("cources_code")
        public String cources_code;

        @SerializedName("cources_name")
        public String cources_name;

        @SerializedName("cources_publish")
        public String cources_publish;

        @SerializedName("cources_type")
        public String cources_type;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("lastupdate")
        public String lastupdate;

        @SerializedName("colour_id")
        public String colour_id;

        @SerializedName("cources_spec")
        public String cources_spec;

        @SerializedName("religion_type")
        public String religion_type;

        public String getCourcesid() {
            return courcesid;
        }

        public void setCourcesid(String courcesid) {
            this.courcesid = courcesid;
        }

        public String getCources_code() {
            return cources_code;
        }

        public void setCources_code(String cources_code) {
            this.cources_code = cources_code;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getCources_publish() {
            return cources_publish;
        }

        public void setCources_publish(String cources_publish) {
            this.cources_publish = cources_publish;
        }

        public String getCources_type() {
            return cources_type;
        }

        public void setCources_type(String cources_type) {
            this.cources_type = cources_type;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getLastupdate() {
            return lastupdate;
        }

        public void setLastupdate(String lastupdate) {
            this.lastupdate = lastupdate;
        }

        public String getColour_id() {
            return colour_id;
        }

        public void setColour_id(String colour_id) {
            this.colour_id = colour_id;
        }

        public String getCources_spec() {
            return cources_spec;
        }

        public void setCources_spec(String cources_spec) {
            this.cources_spec = cources_spec;
        }

        public String getReligion_type() {
            return religion_type;
        }

        public void setReligion_type(String religion_type) {
            this.religion_type = religion_type;
        }
    }

    ///// Response List Guru
    public class ListTeacher{
        @SerializedName("code")
        public String code;

        @SerializedName("data")
        @Expose
        public List<DataGuru> data;

        @SerializedName("status")
        public int status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataGuru> getData() {
            return data;
        }

        public void setData(List<DataGuru> data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public class DataGuru {
        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("nig")
        public String nig;

        @SerializedName("fullname")
        public String fullname;

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getNig() {
            return nig;
        }

        public void setNig(String nig) {
            this.nig = nig;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }
    }

    ///// Response Kirim Pesan
    public class KirimPesan{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public String data;

    }

    //// Response Foto Sekolah
    public class Foto_sekolah{

        @SerializedName("data")
        @Expose
        public List<DataFoto> data;

        @SerializedName("status")
        public int status;

        public List<DataFoto> getData() {
            return data;
        }

        public void setData(List<DataFoto> data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public class DataFoto{
        @SerializedName("pictureid")
        public String pictureid;

        @SerializedName("npsn")
        public String npsn;

        @SerializedName("school_id")
        public String school_id;

        @SerializedName("filename")
        public String filename;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("pic_url")
        public String pic_url;

        @SerializedName("picture_status")
        public String picture_status;

        @SerializedName("device_id")
        public String device_id;

        public String getPictureid() {
            return pictureid;
        }

        public void setPictureid(String pictureid) {
            this.pictureid = pictureid;
        }

        public String getNpsn() {
            return npsn;
        }

        public void setNpsn(String npsn) {
            this.npsn = npsn;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getPicture_status() {
            return picture_status;
        }

        public void setPicture_status(String picture_status) {
            this.picture_status = picture_status;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }
    }


    public class Download{
        @SerializedName("status")
        public int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @SerializedName("code")
        public String code;
    }
    //// dapat pesan anak
    public class Pesan_Anak{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<Data_Pesan> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<Data_Pesan> getData() {
            return data;
        }

        public void setData(List<Data_Pesan> data) {
            this.data = data;
        }
    }
    public class Data_Pesan{
        @SerializedName("messageid")
        public String messageid;

        @SerializedName("user_id_from")
        public String user_id_from;

        @SerializedName("user_id_to")
        public String user_id_to;

        @SerializedName("message_cont")
        public String message_cont;

        @SerializedName("message_date")
        public String message_date;

        @SerializedName("message_status")
        public String message_status;

        @SerializedName("message_type")
        public String message_type;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("cources_id")
        public String cources_id;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("reply_message_id")
        public String reply_message_id;

        @SerializedName("read_status")
        public String read_status;

        @SerializedName("read_date")
        public String read_date;

        @SerializedName("read_message_id")
        public String read_message_id;

        @SerializedName("school_id")
        public String school_id;

        @SerializedName("parent_status")
        public String parent_status;

        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("datez_ok")
        public String datez_ok;

        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("cources_name")
        public String cources_name;

        @SerializedName("sender_name")
        public String sender_name;

        @SerializedName("member_type_text")
        public String member_type_text;

        @SerializedName("parent_message_id")
        public String parent_message_id;

        @SerializedName("reply_status")
        public int reply_status;

        @SerializedName("message_title")
        public String message_title;

        public String getMessage_title() {
            return message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUser_id_from() {
            return user_id_from;
        }

        public void setUser_id_from(String user_id_from) {
            this.user_id_from = user_id_from;
        }

        public String getUser_id_to() {
            return user_id_to;
        }

        public void setUser_id_to(String user_id_to) {
            this.user_id_to = user_id_to;
        }

        public String getMessage_cont() {
            return message_cont;
        }

        public void setMessage_cont(String message_cont) {
            this.message_cont = message_cont;
        }

        public String getMessage_date() {
            return message_date;
        }

        public void setMessage_date(String message_date) {
            this.message_date = message_date;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getReply_message_id() {
            return reply_message_id;
        }

        public void setReply_message_id(String reply_message_id) {
            this.reply_message_id = reply_message_id;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getRead_date() {
            return read_date;
        }

        public void setRead_date(String read_date) {
            this.read_date = read_date;
        }

        public String getRead_message_id() {
            return read_message_id;
        }

        public void setRead_message_id(String read_message_id) {
            this.read_message_id = read_message_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getParent_status() {
            return parent_status;
        }

        public void setParent_status(String parent_status) {
            this.parent_status = parent_status;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getMember_type_text() {
            return member_type_text;
        }

        public void setMember_type_text(String member_type_text) {
            this.member_type_text = member_type_text;
        }

        public String getParent_message_id() {
            return parent_message_id;
        }

        public void setParent_message_id(String parent_message_id) {
            this.parent_message_id = parent_message_id;
        }

        public int getReply_status() {
            return reply_status;
        }

        public void setReply_status(int reply_status) {
            this.reply_status = reply_status;
        }
    }

    /// berita terakhir
    public class last_news{
        @SerializedName("status")
        public int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<Data_Last_News> getData() {
            return data;
        }

        public void setData(List<Data_Last_News> data) {
            this.data = data;
        }

        @SerializedName("data")
        public List<Data_Last_News> data;
    }
    public class DetailBerita{
        @SerializedName("status")
        public int status;

        public Data_Last_News getData() {
            return data;
        }

        public void setData(Data_Last_News data) {
            this.data = data;
        }

        @SerializedName("data")

        public Data_Last_News data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public class Data_Last_News{
        @SerializedName("newsid")
        public String newsid;

        @SerializedName("newspicture")
        public String newspicture;

        @SerializedName("newstitle")
        public String newstitle;

        @SerializedName("newsbody")
        public String newsbody;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        public String getNewsid() {
            return newsid;
        }

        public void setNewsid(String newsid) {
            this.newsid = newsid;
        }

        public String getNewspicture() {
            return newspicture;
        }

        public void setNewspicture(String newspicture) {
            this.newspicture = newspicture;
        }

        public String getNewstitle() {
            return newstitle;
        }

        public void setNewstitle(String newstitle) {
            this.newstitle = newstitle;
        }

        public String getNewsbody() {
            return newsbody;
        }

        public void setNewsbody(String newsbody) {
            this.newsbody = newsbody;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }


    }
}