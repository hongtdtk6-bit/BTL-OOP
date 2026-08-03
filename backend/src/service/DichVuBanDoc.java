package service;

import exception.LoiNghiepVu;
import model.BanDoc;
import model.GiangVien;
import model.SinhVienThuong;
import model.SinhVienUuTien;
import repository.KhoBanDoc;

import java.util.List;


public class DichVuBanDoc {

    private final KhoBanDoc khoBanDoc = new KhoBanDoc();

   
    public List<BanDoc> layTatCa() {
        return khoBanDoc.findAll();
    }

    /** Lấy một bạn đọc theo mã, ném lỗi nếu không tồn tại. */
    public BanDoc layTheoMa(String maBanDoc) {
        BanDoc banDoc = khoBanDoc.findById(maBanDoc);
        if (banDoc == null) {
            throw new LoiNghiepVu("Không tìm thấy bạn đọc có mã: " + maBanDoc);
        }
        return banDoc;
    }

    
    public BanDoc themBanDoc(String loaiBanDoc, String maBanDoc, String hoTen, String soDienThoai) {
        if (khoBanDoc.findById(maBanDoc) != null) {
            throw new LoiNghiepVu("Mã bạn đọc đã tồn tại: " + maBanDoc);
        }

        BanDoc banDocMoi = taoBanDoc(loaiBanDoc, maBanDoc, hoTen, soDienThoai);

        try {
            return khoBanDoc.save(banDocMoi);
        } catch (IllegalArgumentException e) {
            throw new LoiNghiepVu(e.getMessage());
        }
    }

    
    public BanDoc suaBanDoc(String maBanDoc, String hoTenMoi, String soDienThoaiMoi) {
        BanDoc banDocCu = layTheoMa(maBanDoc);

        BanDoc banDocMoi = taoBanDoc(banDocCu.getClass().getSimpleName(), maBanDoc, hoTenMoi, soDienThoaiMoi);

        try {
            return khoBanDoc.update(banDocMoi);
        } catch (IllegalArgumentException e) {
            throw new LoiNghiepVu(e.getMessage());
        }
    }

    /** Xoá một bạn đọc theo mã. */
    public void xoaBanDoc(String maBanDoc) {
        try {
            khoBanDoc.deleteById(maBanDoc);
        } catch (IllegalArgumentException e) {
            throw new LoiNghiepVu(e.getMessage());
        }
    }

    /** Tìm kiếm bạn đọc theo mã hoặc theo tên (không phân biệt hoa thường). */
    public List<BanDoc> timKiem(String tuKhoa) {
        String tuKhoaChuanHoa = tuKhoa == null ? "" : tuKhoa.trim().toLowerCase();

        return layTatCa().stream()
                .filter(bd -> bd.getMaBanDoc().toLowerCase().contains(tuKhoaChuanHoa)
                        || bd.getHoTen().toLowerCase().contains(tuKhoaChuanHoa))
                .toList();
    }

    
    private BanDoc taoBanDoc(String loaiBanDoc, String maBanDoc, String hoTen, String soDienThoai) {
        try {
            return switch (loaiBanDoc) {
                case "SinhVienThuong" -> new SinhVienThuong(maBanDoc, hoTen, soDienThoai);
                case "SinhVienUuTien" -> new SinhVienUuTien(maBanDoc, hoTen, soDienThoai);
                case "GiangVien" -> new GiangVien(maBanDoc, hoTen, soDienThoai);
                default -> throw new LoiNghiepVu("Loại bạn đọc không hợp lệ: " + loaiBanDoc);
            };
        } catch (IllegalArgumentException e) {
            
            throw new LoiNghiepVu(e.getMessage());
        }
    }
}
