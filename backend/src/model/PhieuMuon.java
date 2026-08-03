package model;

import java.time.LocalDate;

public class PhieuMuon {
    private String maPhieu;
    private String maBanDoc;
    private String maSach;
    private int soLuongMuon;
    private LocalDate ngayMuon;
    private LocalDate ngayHenTra;
    private LocalDate ngayTra;
    private TrangThaiPhieu trangThai;
    private double phiPhat;

    public PhieuMuon() {
    }

    public PhieuMuon(String maPhieu, String maBanDoc, String maSach, int soLuongMuon, LocalDate ngayMuon, LocalDate ngayHenTra, LocalDate ngayTra, TrangThaiPhieu trangThai, double phiPhat) {
        this.maPhieu = maPhieu;
        this.maBanDoc = maBanDoc;
        this.maSach = maSach;
        this.soLuongMuon = soLuongMuon;
        this.ngayMuon = ngayMuon;
        this.ngayHenTra = ngayHenTra;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
        this.phiPhat = phiPhat;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaBanDoc() {
        return maBanDoc;
    }

    public void setMaBanDoc(String maBanDoc) {
        this.maBanDoc = maBanDoc;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public int getSoLuongMuon() {
        return soLuongMuon;
    }

    public void setSoLuongMuon(int soLuongMuon) {
        this.soLuongMuon = soLuongMuon;
    }

    public LocalDate getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(LocalDate ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public LocalDate getNgayHenTra() {
        return ngayHenTra;
    }

    public void setNgayHenTra(LocalDate ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }

    public TrangThaiPhieu getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiPhieu trangThai) {
        this.trangThai = trangThai;
    }

    public double getPhiPhat() {
        return phiPhat;
    }

    public void setPhiPhat(double phiPhat) {
        this.phiPhat = phiPhat;
    }

    @Override
    public String toString() {
        return String.format(
                "Phiếu[%s] Bạn đọc:%s Sách:%s SL:%d - Mượn:%s Hẹn Trả:%s Trả:%s - %s%s",
                maPhieu, maBanDoc, maSach, soLuongMuon, ngayMuon, ngayHenTra,
                (ngayTra == null ? "Chưa trả": ngayTra), trangThai,
                (phiPhat > 0 ? String.format(" - Phí phạt: %.0fđ", phiPhat) : "")
        );
    }
}
