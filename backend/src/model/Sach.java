package model;

public class Sach {
    private String maSach;
    private String tenSach;
    private String tacGia;
    private String theLoai;
    private int soLuongHienCo;
    private double giaSach;

    public Sach() {}

    public Sach(String maSach, String tenSach, String tacGia, String theLoai, int soLuongHienCo, double giaSach) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.soLuongHienCo = soLuongHienCo;
        this.giaSach = giaSach;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public int getSoLuongHienCo() {
        return soLuongHienCo;
    }

    public void setSoLuongHienCo(int soLuongHienCo) {
        this.soLuongHienCo = soLuongHienCo;
    }

    public double getGiaSach() {
        return giaSach;
    }

    public void setGiaSach(double giaSach) {
        this.giaSach = giaSach;
    }

    @Override
    public String toString() {
        return "Sach{" +
                "maSach='" + maSach + '\'' +
                ", tenSach='" + tenSach + '\'' +
                ", tacGia='" + tacGia + '\'' +
                ", theLoai='" + theLoai + '\'' +
                ", soLuongHienCo=" + soLuongHienCo +
                ", giaSach=" + giaSach +
                '}';
    }
}
