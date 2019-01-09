package com.fingertech.kes.Rest;

import com.fingertech.kes.Activity.DetailSekolah;
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
        public String office_latitude;
        @SerializedName("office_longitude")
        public String office_longitude;
        @SerializedName("parent_income")
        public String parent_income;
        @SerializedName("parent_address")
        public String parent_address;
        @SerializedName("parent_latitude")
        public String parent_latitude;
        @SerializedName("parent_longitude")
        public String parent_longitude;
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
        @SerializedName("statusKes")
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

        @SerializedName("edulevelId")
        private String edulevelId;

        @SerializedName("laboratorium")
        private String laboratorium;

        @SerializedName("school_Id")
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

        @SerializedName("memberId")
        private String memberId;

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
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
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
}