package menu;

import exception.LoiNghiepVu;
import model.PhieuMuon;
import service.DichVuMuonTraSach;

import java.util.Scanner;

public class MuonSachMenu {
    private final DichVuMuonTraSach dichVu;
    private final Scanner scanner;

    public MuonSachMenu(DichVuMuonTraSach dichVu, Scanner scanner) {
        this.dichVu = dichVu;
        this.scanner = scanner;
    }

    public void hienThi() {
        System.out.println("\n===== LẬP PHIẾU MƯỢN SÁCH =====");

        System.out.println("Nhập mã bạn đọc: ");
        String maBanDoc = scanner.nextLine().trim();

        System.out.println("Nhập mã sách: ");
        String maSach = scanner.nextLine().trim();

        int soLuongMuon = docSoNguyen("Số lượng muốn mượn (Enter = 1): ", 1);
        int soNgayMuon = docSoNguyen("Số ngày mượn (Enter = 14): ", 14);

        try {
            PhieuMuon phieu = dichVu.lapPhieuMuon(maBanDoc, maSach, soLuongMuon, soNgayMuon);
            System.out.println("Lập phiếu mượn thành công. Mã phiếu: " + phieu.getMaPhieu() + " - Hẹn trả: " + phieu.getNgayHenTra());
        } catch (LoiNghiepVu e) {
            System.out.println("Không thể lập phiếu mượn: " + e.getMessage());
        }
    }

    private int docSoNguyen(String nhacNho, int macDinh) {
        System.out.print(nhacNho);
        String nhap = scanner.nextLine().trim();
        if (nhap.isEmpty()) {
            return macDinh;
        }
        try {
            return Integer.parseInt(nhap);
        } catch (NumberFormatException e) {
            System.out.println("Giá trị không hợp lệ, dùng mặc định: " + macDinh);
            return macDinh;
        }
    }
}
