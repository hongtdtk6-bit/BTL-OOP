package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class XuLyNgay {
    // Định dạng ngày: dd/MM/yyyy
    private static final DateTimeFormatter DINH_DANG =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String layNgayHienTai() {
        return LocalDate.now().format(DINH_DANG);
    }

    public static LocalDate chuyenSangLocalDate(String ngay) {
        return LocalDate.parse(ngay, DINH_DANG);
    }

    public static String chuyenSangString(LocalDate ngay) {
        return ngay.format(DINH_DANG);
    }

    public static String tinhHanTra(String ngayMuon, int soNgayMuon) {

        LocalDate ngay = chuyenSangLocalDate(ngayMuon);

        return ngay.plusDays(soNgayMuon)
                .format(DINH_DANG);
    }

    public static int tinhSoNgayQuaHan(String hanTra) {
        LocalDate ngayHanTra = chuyenSangLocalDate(hanTra);
        LocalDate homNay = LocalDate.now();
        long soNgay = ChronoUnit.DAYS.between(ngayHanTra, homNay);
        return (int) Math.max(0, soNgay);
    }

    public static boolean laQuaHan(String hanTra) {
        LocalDate ngayHanTra = chuyenSangLocalDate(hanTra);
        return LocalDate.now().isAfter(ngayHanTra);
    }
}

