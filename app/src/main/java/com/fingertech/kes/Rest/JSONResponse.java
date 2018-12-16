package com.fingertech.kes.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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
    //////// Data Array- Search School
    public class SData{
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
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
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

}