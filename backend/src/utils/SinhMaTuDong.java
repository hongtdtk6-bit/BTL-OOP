package utils;

public class SinhMaTuDong {
    public static String taoMaBanDoc(int soThuTu) {
        return String.format("BD%03d", soThuTu);
    }
    public static String taoMaSach(int soThuTu) {
        return String.format("S%03d", soThuTu);
    }
    public static String taoMaPhieuMuon(int soThuTu) {
        return String.format("PM%03d", soThuTu);
    }

}

