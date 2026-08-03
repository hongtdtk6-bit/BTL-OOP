package model;

public class GiangVien extends BanDoc {
    private static final int SO_LUONG_MUON_TOI_DA = 10;

    public GiangVien() {
        super();
    }

    public GiangVien(String maGiangVien, String hoTen, String soDienThoai) {
        super(maGiangVien, hoTen, soDienThoai);
    }

    @Override
    public int getSoLuongMuonToiDa() {
        return SO_LUONG_MUON_TOI_DA;
    }

    @Override
    public String getLoaiBanDoc() {
        return "Giảng viên";
    }
}
