package model;

public class NguoiDung {

    private String maNguoiDung;
    private String hoTen;
    private String soDienThoai;

    public NguoiDung() {
    }

    public NguoiDung(String maNguoiDung, String hoTen, String soDienThoai) {
        setMaNguoiDung(maNguoiDung);
        setHoTen(hoTen);
        setSoDienThoai(soDienThoai);
    }

    public String getMaNguoiDung() { return maNguoiDung; }

    public void setMaNguoiDung(String maNguoiDung) {
        if (maNguoiDung == null || maNguoiDung.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã người dùng không được để trống");
        }
        this.maNguoiDung = maNguoiDung.trim();
    }

    public String getHoTen() { return hoTen; }

    public void setHoTen(String hoTen) {
        if (hoTen == null || hoTen.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        this.hoTen = hoTen.trim();
    }

    public String getSoDienThoai() { return soDienThoai; }

    public void setSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || !soDienThoai.matches("\\d{9,11}")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (phải gồm 9-11 chữ số)");
        }
        this.soDienThoai = soDienThoai;
    }

    @Override
    public String toString() {
        return "NguoiDung{maNguoiDung='" + maNguoiDung + "', hoTen='" + hoTen +
                "', soDienThoai='" + soDienThoai + "'}";
    }
}