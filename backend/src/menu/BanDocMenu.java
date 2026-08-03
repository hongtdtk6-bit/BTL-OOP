package menu;

import exception.LoiNghiepVu;
import model.BanDoc;
import service.DichVuBanDoc;

import java.util.List;
import java.util.Scanner;
public class BanDocMenu {
   
    private final DichVuBanDoc dichVuBanDoc = new DichVuBanDoc();
    private final Scanner scanner = new Scanner(System.in);

    public void hienThi() {
        int luaChon;
        do {
            System.out.println("=== Quản lý bạn đọc ===");
            System.out.println("1. Thêm bạn đọc");
            System.out.println("2. Xóa bạn đọc");
            System.out.println("3. Cập nhật thông tin bạn đọc");
            System.out.println("4. Hiển thị danh sách bạn đọc");
            System.out.println("5. Tìm kiếm bạn đọc theo tên");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Lựa chọn của bạn: ");
            luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới

            switch (luaChon) {
                case 1 -> hienThiDanhSach();
                case 2 -> themBanDoc();
                case 3 -> suaBanDoc();
                case 4 -> xoaBanDoc();
                case 5 -> timKiem();
                case 0 -> System.out.println("Quay lại menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (luaChon != 0);
    }

    private void hienThiDanhSach() {
        List<BanDoc> danhSach = dichVuBanDoc.layTatCa();

        if (danhSach.isEmpty()){
            System.out.println("Chưa có bạn đọc nào.");
        
            return;
        }
        System.out.printf("%-10s %-20s %-15s %-20s %-5s%n",
                "Mã BĐ", "Họ tên", "SĐT", "Loại bạn đọc", "SL tối đa");
                for (BanDoc banDoc : danhSach) {
                    System.out.printf("%-10s %-20s %-15s %-20s %-5d%n",
                    banDoc.getMaBanDoc(), banDoc.getHoTen(), banDoc.getSoDienThoai(),
                    banDoc.getLoaiBanDoc(), banDoc.getSoLuongMuonToiDa());
                }



    }
    private void themBanDoc() {
        try {
            System.out.print("Nhập mã bạn đọc: ");
            String maBanDoc = scanner.nextLine();
            System.out.print("Nhập họ tên: ");
            String hoTen = scanner.nextLine();
            System.out.print("Nhập số điện thoại: ");
            String soDienThoai = scanner.nextLine();
          
            String loaiBanDoc = choLoaiBanDoc();

            BanDoc banDoc = dichVuBanDoc.themBanDoc(loaiBanDoc, maBanDoc, hoTen, soDienThoai);
            System.out.println("Thêm bạn đọc thành công.");
        } catch (LoiNghiepVu e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
    private void suaBanDoc() {
        try {
            System.out.print("Nhập mã bạn đọc cần sửa: ");
            String maBanDoc = scanner.nextLine();

            System.out.print("Họ tên mới: ");
            String hoTen = scanner.nextLine();

            System.out.print("Số điện thoại mới: ");
            String soDienThoai = scanner.nextLine();

            BanDoc banDoc = dichVuBanDoc.suaBanDoc(maBanDoc, hoTen, soDienThoai);
            System.out.println("Cập nhật thành công: " + banDoc);
        } catch (LoiNghiepVu e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void xoaBanDoc() {
        try {
            System.out.print("Nhập mã bạn đọc cần xóa: ");
            String maBanDoc = scanner.nextLine();

            dichVuBanDoc.xoaBanDoc(maBanDoc);
            System.out.println("Xóa bạn đọc thành công.");
        } catch (LoiNghiepVu e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
    private void timKiem() {
        System.out.print("Nhập mã hoặc tên bạn đọc cần tìm kiếm: ");
        String tuKhoa = scanner.nextLine();

        Líst<BanDoc> ketQua = dichVuBanDoc.timKiem(tuKhoa);
        if (ketQua.isEmpty()) {
            System.out.println("Không tìm thấy bạn đọc nào.");
        return;
        }
        for (BanDoc bd : ketQua) {
            System.out.println(bd);
        }
    }
    private String choLoaiBanDoc() {
        System.out.println("Chọn loại bạn đọc:");
        System.out.println("1. Sinh viên thường");
        System.out.println("2. Sinh viên ưu tiên");
        System.out.println("3. Giảng viên");
        System.out.print("Lựa chọn của bạn: ");

        int luaChon = doSoNguyen();
        return switch (luaChon) {
            case 1 -> "Sinh viên thường";
            case 2 -> "Sinh viên ưu tiên";
            case 3 -> "Giảng viên";
            default -> throw new LoiNghiepVu("Loại bạn đọc không hợp lệ.");
        };
    }
    private int doSoNguyen() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Trả về giá trị không hợp lệ nếu không phải số nguyên
        }
    }

}
