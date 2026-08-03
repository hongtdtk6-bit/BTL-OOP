package model;

public class SinhVienUuTien extends BanDoc {
    private static final int SO_LUONG_MUON_TOI_DA = 5;

    public SinhVienUuTien() {
        super();
    }

    public SinhVienUuTien(String maSinhVien, String hoTen, String soDienThoai) {
        super(maSinhVien, hoTen, soDienThoai);
    }

    @Override
    public int getSoLuongMuonToiDa() {
        return SO_LUONG_MUON_TOI_DA;
    }

    @Override
    public String getLoaiBanDoc() {
        return "Sinh viên ưu tiên";
    }
}
