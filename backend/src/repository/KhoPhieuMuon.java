package repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.PhieuMuon;
import model.TrangThaiPhieu;

public class KhoPhieuMuon {
    private static final String DUONG_DAN_FILE = "data/borrowTickets.json";

    private List<PhieuMuon> danhSachPhieu;
    public KhoPhieuMuon(){
        this.danhSachPhieu = taiDuLieu();
    }

    public List<PhieuMuon> layTatCa(){
        return danhSachPhieu;
    }

    public PhieuMuon timTheoMa(String maPhieu){
        for(PhieuMuon p : danhSachPhieu){
            if(p.getMaPhieu().equalsIgnoreCase(maPhieu)){
                return p;
            }
        }
        return null;
    }

    public List<PhieuMuon> timTheoBanDoc(String maBanDoc){
        List<PhieuMuon> result = new ArrayList<>();
        for(PhieuMuon p: danhSachPhieu){
            if(p.getMaBanDoc().equalsIgnoreCase(maBanDoc)){
                result.add(p);
            }
        }
        return result;
    }

    public List<PhieuMuon> danhSachDangMuon(){
        List<PhieuMuon> result = new ArrayList<>();
        for(PhieuMuon p: danhSachPhieu){
            if (p.getTrangThai() == TrangThaiPhieu.DANG_MUON || p.getTrangThai() == TrangThaiPhieu.QUA_HAN){
                result.add(p);
            }
        }
        return result;
    }

    public int tongSoSachDangMuonCuaBanDoc(String maBanDoc) {
        int tong = 0;
        for (PhieuMuon p: danhSachPhieu){
            if (p.getMaBanDoc().equalsIgnoreCase(maBanDoc) && (p.getTrangThai() == TrangThaiPhieu.DANG_MUON || p.getTrangThai() == TrangThaiPhieu.QUA_HAN)){
                tong += p.getSoLuongMuon();
            }
        }
        return tong;
    }

    public void them(PhieuMuon phieu){
        danhSachPhieu.add(phieu);
        luuDuLieu();
    }

    public void capNhat(PhieuMuon phieu){
        for(int i=0; i<danhSachPhieu.size(); i++){
            if (danhSachPhieu.get(i).getMaPhieu().equalsIgnoreCase(phieu.getMaPhieu())){
                danhSachPhieu.set(i, phieu);
                break;
            }
        }
        luuDuLieu();
    }

    public String sinhMaPhieuMoi() {
        int max = 0;
        for (PhieuMuon p : danhSachPhieu) {
            String ma = p.getMaPhieu().replaceAll("\\D", "");
            if (!ma.isEmpty()) {
                try {
                    max = Math.max(max, Integer.parseInt(ma));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("PM%03d", max + 1);
    }

    private List<PhieuMuon> taiDuLieu() {
        List<PhieuMuon> ds = new ArrayList<>();
        File file = new File(DUONG_DAN_FILE);
        if (!file.exists()) {
            return ds;
        }
        try {
            String noiDung = new String(Files.readAllBytes(Paths.get(DUONG_DAN_FILE)), StandardCharsets.UTF_8);
            ds = phanTichJson(noiDung);
        } catch (IOException e) {
            System.out.println("Không thể đọc file dữ liệu phiếu mượn: " + e.getMessage());
        }
        return ds;
    }

    public void luuDuLieu() {
        try {
            File thuMuc = new File("data");
            if (!thuMuc.exists()) {
                thuMuc.mkdirs();
            }
            try (Writer writer = new OutputStreamWriter(
                    new FileOutputStream(DUONG_DAN_FILE), StandardCharsets.UTF_8)) {
                writer.write(chuyenSangJson(danhSachPhieu));
            }
        } catch (IOException e) {
            System.out.println("Không thể ghi file dữ liệu phiếu mượn: " + e.getMessage());
        }
    }

    private String chuyenSangJson(List<PhieuMuon> ds) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < ds.size(); i++) {
            PhieuMuon p = ds.get(i);
            sb.append("  {\n");
            sb.append("    \"maPhieu\": \"").append(p.getMaPhieu()).append("\",\n");
            sb.append("    \"maBanDoc\": \"").append(p.getMaBanDoc()).append("\",\n");
            sb.append("    \"maSach\": \"").append(p.getMaSach()).append("\",\n");
            sb.append("    \"soLuongMuon\": ").append(p.getSoLuongMuon()).append(",\n");
            sb.append("    \"ngayMuon\": \"").append(p.getNgayMuon()).append("\",\n");
            sb.append("    \"ngayHenTra\": \"").append(p.getNgayHenTra()).append("\",\n");
            sb.append("    \"ngayTra\": ").append(p.getNgayTra() == null ? "null" : "\"" + p.getNgayTra() + "\"").append(",\n");
            sb.append("    \"trangThai\": \"").append(p.getTrangThai().name()).append("\",\n");
            sb.append("    \"phiPhat\": ").append(p.getPhiPhat()).append("\n");
            sb.append("  }");
            if (i < ds.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

    private List<PhieuMuon> phanTichJson(String noiDung) {
        List<PhieuMuon> ds = new ArrayList<>();
        Pattern objectPattern = Pattern.compile("\\{[^{}]*\\}");
        Matcher objectMatcher = objectPattern.matcher(noiDung);

        while (objectMatcher.find()) {
            String obj = objectMatcher.group();
            PhieuMuon p = new PhieuMuon();
            p.setMaPhieu(layChuoi(obj, "maPhieu"));
            p.setMaBanDoc(layChuoi(obj, "maBanDoc"));
            p.setMaSach(layChuoi(obj, "maSach"));
            p.setSoLuongMuon(laySo(obj, "soLuongMuon", 1));

            String ngayMuonStr = layChuoi(obj, "ngayMuon");
            String ngayHenTraStr = layChuoi(obj, "ngayHenTra");
            String ngayTraStr = layChuoi(obj, "ngayTra");
            String trangThaiStr = layChuoi(obj, "trangThai");

            p.setNgayMuon(ngayMuonStr == null || ngayMuonStr.isEmpty() ? null : LocalDate.parse(ngayMuonStr));
            p.setNgayHenTra(ngayHenTraStr == null || ngayHenTraStr.isEmpty() ? null : LocalDate.parse(ngayHenTraStr));
            p.setNgayTra(ngayTraStr == null || ngayTraStr.isEmpty() ? null : LocalDate.parse(ngayTraStr));
            p.setTrangThai(trangThaiStr == null ? TrangThaiPhieu.DANG_MUON : TrangThaiPhieu.valueOf(trangThaiStr));
            p.setPhiPhat(laySoThuc(obj, "phiPhat", 0));

            ds.add(p);
        }
        return ds;
    }

    private String layChuoi(String jsonObj, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*(\"([^\"]*)\"|null)");
        Matcher m = p.matcher(jsonObj);
        if (m.find()) {
            return m.group(2); // null nếu giá trị là null (không có nhóm 2)
        }
        return null;
    }

    private int laySo(String jsonObj, String key, int macDinh) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*(-?\\d+)");
        Matcher m = p.matcher(jsonObj);
        return m.find() ? Integer.parseInt(m.group(1)) : macDinh;
    }

    private double laySoThuc(String jsonObj, String key, double macDinh) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*(-?[\\d.]+)");
        Matcher m = p.matcher(jsonObj);
        return m.find() ? Double.parseDouble(m.group(1)) : macDinh;
    }
}
