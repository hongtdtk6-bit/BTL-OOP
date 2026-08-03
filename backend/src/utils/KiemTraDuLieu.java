package utils;

import java.util.regex.Pattern;

public class KiemTraDuLieu {

    // Không cho phép chuỗi rỗng
    public static boolean khongRong(String chuoi) {
        return chuoi != null && !chuoi.trim().isEmpty();
    }

    // Kiểm tra email
    public static boolean emailHopLe(String email) {
        if (!khongRong(email)) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }
    // Kiểm tra số điện thoại Việt Nam
    public static boolean soDienThoaiHopLe(String soDienThoai) {
        if (!khongRong(soDienThoai)) {
            return false;
        }
        String regex = "^0\\d{9}$";
        return Pattern.matches(regex, soDienThoai);
    }
    // Kiểm tra năm xuất bản
    public static boolean namXuatBanHopLe(int nam) {
        int namHienTai = java.time.Year.now().getValue();
        return nam >= 1900 && nam <= namHienTai;
    }
    // Kiểm tra số lượng sách
    public static boolean soLuongHopLe(int soLuong) {
        return soLuong >= 0;
    }
    // Kiểm tra chuỗi chỉ chứa số
    public static boolean laSo(String chuoi) {
        if (!khongRong(chuoi)) {
            return false;
        }
        return chuoi.matches("\\d+");
    }
    // Kiểm tra mã không được rỗng
    public static boolean maHopLe(String ma) {
        return khongRong(ma);
    }
}

