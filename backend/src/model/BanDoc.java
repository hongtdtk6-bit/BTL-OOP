package model;

public abstract class BanDoc extends NguoiDung {

    public BanDoc() {
        super();
    }

    public BanDoc(String maBanDoc, String hoTen, String soDienThoai) {
        super(maBanDoc, hoTen, soDienThoai);
    }

    // Alias: mã bạn đọc dùng chung mã người dùng kế thừa từ NguoiDung
    public String getMaBanDoc() { return getMaNguoiDung(); }

    public void setMaBanDoc(String maBanDoc) { setMaNguoiDung(maBanDoc); }

    /** Số lượng sách tối đa được mượn — mỗi loại bạn đọc override khác nhau. */
    public abstract int getSoLuongMuonToiDa();

    /** Tên loại bạn đọc để hiển thị. */
    public abstract String getLoaiBanDoc();

    @Override
    public String toString() {
        return "BanDoc{maBanDoc='" + getMaBanDoc() + "', hoTen='" + getHoTen() +
                "', soDienThoai='" + getSoDienThoai() + "', loaiBanDoc='" + getLoaiBanDoc() +
                "', soLuongMuonToiDa=" + getSoLuongMuonToiDa() + "}";
    }
}