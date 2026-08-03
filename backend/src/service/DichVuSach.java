package service;

import java.util.ArrayList;
import java.util.List;

public class DichVuSach {
    private repository.KhoSach khoSach;

    public DichVuSach(repository.KhoSach khoSach) {
        this.khoSach = khoSach;
    }

    public void hienThiDanhSach() {
        List<model.Sach> ds = khoSach.getDanhSachSach();

        System.out.println("\n======= Danh Sách Sách Trong Kho =======");
        for (model.Sach sach : ds) {
            System.out.println(sach.toString());
        }
    }

    public void themSachMoi(model.Sach sachMoi) {
        List<model.Sach> dsm = new ArrayList<>();

        for (model.Sach sach : dsm) {
            if (sach.getMaSach().equalsIgnoreCase(sachMoi.getMaSach())) {
                System.out.println("Mã sách này đã tồn tạo trong hệ thống");
                return;
            }
        }
        khoSach.luuSach(sachMoi);
        System.out.println("Thêm sách mới thành công!");
    }

    public model.Sach timKiemSach(String tuKhoa) {
        List<model.Sach> dst = new ArrayList<>();
        for (model.Sach sach : dst) {
            if (sach.getMaSach().equalsIgnoreCase(tuKhoa) || sach.getTenSach().toLowerCase().contains(tuKhoa)) {
                return sach;
            }
        }
        return null;
    }

    public boolean kiemTraConSach(String maSach) {
        model.Sach sach = timKiemSach(maSach);
        if (sach != null && sach.getSoLuongHienCo() > 0) {
            return true;
        }
        return false;
    }
}
