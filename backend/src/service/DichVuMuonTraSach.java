package service;

import exception.LoiNghiepVu;
import model.BanDoc;
import model.ChinhSachPhat;
import model.PhieuMuon;
import model.Sach;
import model.TrangThaiPhieu;
import repository.KhoBanDoc;
import repository.KhoPhieuMuon;
import repository.KhoSach;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DichVuMuonTraSach {
    private static final int SO_NGAY_MUON_MAC_DINH = 14;

    private final KhoPhieuMuon khoPhieuMuon;
    private final KhoSach khoSach;
    private final KhoBanDoc khoBanDoc;
    private final ChinhSachPhat chinhSachPhat;

    public DichVuMuonTraSach(KhoPhieuMuon khoPhieuMuon, KhoSach khoSach,
                             KhoBanDoc khoBanDoc, ChinhSachPhat chinhSachPhat) {
        this.khoPhieuMuon = khoPhieuMuon;
        this.khoSach = khoSach;
        this.khoBanDoc = khoBanDoc;
        this.chinhSachPhat = chinhSachPhat;
    }
    public PhieuMuon lapPhieuMuon(String maBanDoc, String maSach) throws LoiNghiepVu {
        return lapPhieuMuon(maBanDoc, maSach, 1, SO_NGAY_MUON_MAC_DINH);
    }
    
    public PhieuMuon lapPhieuMuon(String maBanDoc, String maSach, int soLuongMuon, int soNgayMuon)
            throws LoiNghiepVu {

        if (soLuongMuon <= 0) {
            throw new LoiNghiepVu("Số lượng sách muốn mượn phải lớn hơn 0.");
        }

        // TODO: xác nhận KhoBanDoc có hàm timTheoMa(String) trả về BanDoc (null nếu không có)
        BanDoc banDoc = khoBanDoc.timTheoMa(maBanDoc);
        if (banDoc == null) {
            throw new LoiNghiepVu("Không tìm thấy bạn đọc có mã: " + maBanDoc);
        }

        // TODO: xác nhận KhoSach có hàm timTheoMa(String) trả về Sach (null nếu không có)
        Sach sach = khoSach.timTheoMa(maSach);
        if (sach == null) {
            throw new LoiNghiepVu("Không tìm thấy sách có mã: " + maSach);
        }

        // TODO: xác nhận Sach có hàm getSoLuongHienCo()
        int soLuongConLai = sach.getSoLuongHienCo();
        if (soLuongConLai <= 0) {
            throw new LoiNghiepVu("Sách \"" + maSach + "\" đã hết, không thể cho mượn.");
        }
        if (soLuongConLai < soLuongMuon) {
            throw new LoiNghiepVu("Sách \"" + maSach + "\" chỉ còn " + soLuongConLai + " bản, không đủ để mượn " + soLuongMuon + " bản.");
        }

        // TODO: xác nhận BanDoc có hàm getSoLuongMuonToiDa()
        int daMuon = khoPhieuMuon.tongSoSachDangMuonCuaBanDoc(maBanDoc);
        int toiDa = banDoc.getSoLuongMuonToiDa();
        if (daMuon + soLuongMuon > toiDa) {
            throw new LoiNghiepVu("Bạn đọc \"" + maBanDoc + "\" đang mượn " + daMuon + "/" + toiDa + " cuốn, không thể mượn thêm " + soLuongMuon + " cuốn.");
        }

        // Hợp lệ -> tạo phiếu mượn
        String maPhieu = khoPhieuMuon.sinhMaPhieuMoi();
        LocalDate homNay = LocalDate.now();
        PhieuMuon phieu = new PhieuMuon(
                maPhieu, maBanDoc, maSach, soLuongMuon,
                homNay, homNay.plusDays(soNgayMuon),
                null, TrangThaiPhieu.DANG_MUON, 0
        );

        khoPhieuMuon.them(phieu);

        // TODO: xác nhận Sach có hàm setSoLuongHienCo(int) và KhoSach có hàm capNhat(Sach)
        sach.setSoLuongHienCo(soLuongConLai - soLuongMuon);
        khoSach.capNhat(sach);

        return phieu;
    }

    // Xử lý trả sách theo mã phiếu. Tự động tính phí phạt nếu trả trễ hạn.
    
    public PhieuMuon traSach(String maPhieu) throws LoiNghiepVu {
        PhieuMuon phieu = khoPhieuMuon.timTheoMa(maPhieu);

        if (phieu == null) {
            throw new LoiNghiepVu("Không tìm thấy phiếu mượn có mã: " + maPhieu);
        }
        if (phieu.getTrangThai() == TrangThaiPhieu.DA_TRA) {
            throw new LoiNghiepVu("Phiếu \"" + maPhieu + "\" đã được trả trước đó, không thể trả lại.");
        }

        LocalDate homNay = LocalDate.now();
        phieu.setNgayTraThucTe(homNay);

        long soNgayTre = ChronoUnit.DAYS.between(phieu.getNgayHenTra(), homNay);
        if (soNgayTre > 0) {
            // TODO: xác nhận ChinhSachPhat có hàm tinhPhiPhat(long soNgayTre) -> double
            double phiPhat = chinhSachPhat.tinhPhiPhat(soNgayTre);
            phieu.setPhiPhat(phiPhat);
        }

        phieu.setTrangThai(TrangThaiPhieu.DA_TRA);
        khoPhieuMuon.capNhat(phieu);

        // TODO: xác nhận KhoSach có hàm timTheoMa / capNhat như trên
        Sach sach = khoSach.timTheoMa(phieu.getMaSach());
        if (sach != null) {
            sach.setSoLuongHienCo(sach.getSoLuongHienCo() + phieu.getSoLuongMuon());
            khoSach.capNhat(sach);
        }

        return phieu;
    }

    /**
     * Duyệt qua toàn bộ phiếu đang mượn, cập nhật thành QUA_HAN nếu đã
     * quá ngày hẹn trả mà chưa trả sách.
     */
    public void capNhatTrangThaiQuaHan() {
        LocalDate homNay = LocalDate.now();
        for (PhieuMuon p : khoPhieuMuon.layTatCa()) {
            if (p.getTrangThai() == TrangThaiPhieu.DANG_MUON
                    && homNay.isAfter(p.getNgayHenTra())) {
                p.setTrangThai(TrangThaiPhieu.QUA_HAN);
                khoPhieuMuon.capNhat(p);
            }
        }
    }

    public List<PhieuMuon> danhSachDangMuon() {
        capNhatTrangThaiQuaHan();
        return khoPhieuMuon.danhSachDangMuon();
    }

    public List<PhieuMuon> danhSachTatCa() {
        return khoPhieuMuon.layTatCa();
    }
}