package menu;

import exception.LoiNghiepVu;
import model.PhieuMuon;
import service.DichVuMuonTraSach;

import java.util.List;
import java.util.Scanner;

public class TraSachMenu {
    private final DichVuMuonTraSach dichVu;
    private final Scanner scanner;

    public TraSachMenu(DichVuMuonTraSach dichVu, Scanner scanner) {
        this.dichVu = dichVu;
        this.scanner = scanner;
    }

    public void hienThi() {
        System.out.println("\n===== TRẢ SÁCH =====");

        List<PhieuMuon> dangMuon = dichVu.danhSachDangMuon();
        if (dangMuon.isEmpty()) {
            System.out.println("Hiện không có phiếu nào đang mượn.");
            return;
        }

        System.out.println("Danh sách phiếu đang mượn:");
        for (PhieuMuon p : dangMuon) {
            System.out.println("  " + p);
        }

        System.out.println("\nNhập mã phiếu cần trả (Enter để hủy): ");
        String maPhieu = scanner.nextLine().trim();
        if (maPhieu.isEmpty()) {
            System.out.println("Đã hủy thao tác trả sách.");
            return;
        }

        try {
            PhieuMuon phieu = dichVu.traSach(maPhieu);
            System.out.println("Trả sách thành công cho phiếu " + phieu.getMaPhieu() + ".");
            if (phieu.getPhiPhat() > 0) {
                System.out.printf("Trả trễ hạn (hẹn trả %s). Phí phạt: %,.0fđ%n",
                        phieu.getNgayHenTra(), phieu.getPhiPhat());
            }
        } catch (LoiNghiepVu e) {
            System.out.println("Không thể trả sách: " + e.getMessage());
        }
    }
}
