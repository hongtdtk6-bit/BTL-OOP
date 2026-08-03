package model;

import exception.LoiNghiepVu;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ChinhSachPhat {

    // Tiền phạt mỗi ngày trễ
    public static final double PHAT_MOI_NGAY = 5000;

    // Làm mất sách = 200% giá sách
    public static final double TY_LE_MAT_SACH = 2.0;

    // Hư hỏng sách = 30% giá sách
    public static final double TY_LE_HU_HONG = 0.3;

    /**
     * Tính tiền phạt trả trễ
     */
    public static double tinhTienPhatTre(LocalDate hanTra,
                                         LocalDate ngayTra)
            throws LoiNghiepVu {

        if (hanTra == null || ngayTra == null)
            throw new LoiNghiepVu("Ngày trả hoặc hạn trả không hợp lệ.");

        if (!ngayTra.isAfter(hanTra))
            return 0;

        long soNgayTre = ChronoUnit.DAYS.between(hanTra, ngayTra);

        return soNgayTre * PHAT_MOI_NGAY;
    }

    /**
     * Tính tiền phạt mất sách
     */
    public static double tinhTienMatSach(double giaSach)
            throws LoiNghiepVu {

        if (giaSach <= 0)
            throw new LoiNghiepVu("Giá sách phải lớn hơn 0.");

        return giaSach * TY_LE_MAT_SACH;
    }

    /**
     * Tính tiền phạt hư hỏng sách
     */
    public static double tinhTienHuHong(double giaSach)
            throws LoiNghiepVu {

        if (giaSach <= 0)
            throw new LoiNghiepVu("Giá sách phải lớn hơn 0.");

        return giaSach * TY_LE_HU_HONG;
    }

    /**
     * Tổng tiền phạt
     */
    public static double tinhTongTienPhat(
            LocalDate hanTra,
            LocalDate ngayTra,
            double giaSach,
            boolean matSach,
            boolean huHong)
            throws LoiNghiepVu {

        double tong = tinhTienPhatTre(hanTra, ngayTra);

        if (matSach) {
            tong += tinhTienMatSach(giaSach);
        }

        if (huHong) {
            tong += tinhTienHuHong(giaSach);
        }

        return tong;
    }
}
