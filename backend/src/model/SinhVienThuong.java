package model;

public class SinhVienThuong extends BanDoc {
    private static final int SO_LUONG_MUON_TOI_DA = 3;

    public SinhVienThuong() {
        super();
    }

    public SinhVienThuong(String maSinhVien, String hoTen, String soDienThoai) {
        super(maSinhVien, hoTen, soDienThoai);
    }

    @Override
    public int getSoLuongMuonToiDa() {
        return SO_LUONG_MUON_TOI_DA;
    }

    @Override
    public String getLoaiBanDoc() {
        return "Sinh viên thường";
    }
}
