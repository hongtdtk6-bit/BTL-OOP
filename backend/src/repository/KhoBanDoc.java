package repository;
import model.BanDoc;
import model.GiangVien;
import model.SinhVienThuong;
import model.SinhVienUuTien;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class KhoBanDoc {
    private static final String DUONG_DAN_FILE ="data/readers.json";

    public List<BanDoc> findAll() {
        List<BanDoc> danhSach = new ArrayList<>();
        Path path = Paths.get(DUONG_DAN_FILE);

        if (!Files.exists(path)) {
            return danhSach;
        }

        try {
            String noiDung = Files.readString(path, StandardCharsets.UTF_8).trim();
            if (noiDung.isEmpty() || noiDung.equals("[]")) {
                return danhSach;
            }

            noiDung = noiDung.substring(noiDung.indexOf('[') + 1, noiDung.lastIndexOf(']')).trim();

            for (String doanObject : tachCacObject(noiDung)) {
                BanDoc banDoc = parseBanDoc(doanObject);
                if (banDoc != null) {
                    danhSach.add(banDoc);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Không đọc được file " + DUONG_DAN_FILE, e);
        }

        return danhSach;
    }

    public BanDoc findById(String maBanDoc) {
        for (BanDoc banDoc : findAll()) {
            if (banDoc.getMaBanDoc().equals(maBanDoc)) {
                return banDoc;
            }
        }
        return null;
    }

    public BanDoc save(BanDoc banDoc) {
        List<BanDoc> danhSach = findAll();
        for (BanDoc bd : danhSach) {
            if (bd.getMaBanDoc().equals(banDoc.getMaBanDoc())) {
                throw new IllegalArgumentException("Mã bạn đọc đã tồn tại: " + banDoc.getMaBanDoc());
            }
        }
        danhSach.add(banDoc);
        ghiFile(danhSach);
        return banDoc;
    }

    public BanDoc update(BanDoc banDoc) {
        List<BanDoc> danhSach = findAll();
        boolean tonTai = false;

        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaBanDoc().equals(banDoc.getMaBanDoc())) {
                danhSach.set(i, banDoc);
                tonTai = true;
                break;
            }
        }

        if (!tonTai) {
            throw new IllegalArgumentException("Không tìm thấy bạn đọc có mã: " + banDoc.getMaBanDoc());
        }

        ghiFile(danhSach);
        return banDoc;
    }

    public void deleteById(String maBanDoc) {
        List<BanDoc> danhSach = findAll();
        boolean daXoa = danhSach.removeIf(bd -> bd.getMaBanDoc().equals(maBanDoc));

        if (!daXoa) {
            throw new IllegalArgumentException("Không tìm thấy bạn đọc có mã: " + maBanDoc);
        }

        ghiFile(danhSach);
    }

    // ===================== GHI FILE =====================

    private void ghiFile(List<BanDoc> danhSach) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < danhSach.size(); i++) {
            sb.append("  ").append(toJson(danhSach.get(i)));
            if (i < danhSach.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");

        try {
            Path path = Paths.get(DUONG_DAN_FILE);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Không ghi được file " + DUONG_DAN_FILE, e);
        }
    }

    private String toJson(BanDoc banDoc) {
        return "{"
                + "\"loai\":\"" + banDoc.getClass().getSimpleName() + "\","
                + "\"maBanDoc\":\"" + escape(banDoc.getMaBanDoc()) + "\","
                + "\"hoTen\":\"" + escape(banDoc.getHoTen()) + "\","
                + "\"soDienThoai\":\"" + escape(banDoc.getSoDienThoai()) + "\""
                + "}";
    }

    // ===================== ĐỌC / PARSE FILE =====================

    private List<String> tachCacObject(String noiDung) {
        List<String> ketQua = new ArrayList<>();
        int doSau = 0;
        StringBuilder hienTai = new StringBuilder();

        for (char c : noiDung.toCharArray()) {
            if (c == '{') doSau++;
            if (c == '}') doSau--;

            if (doSau > 0 || c == '}') {
                hienTai.append(c);
            }

            if (doSau == 0 && c == '}') {
                ketQua.add(hienTai.toString().trim());
                hienTai = new StringBuilder();
            }
        }
        return ketQua;
    }

    private BanDoc parseBanDoc(String jsonObject) {
        String noiDung = jsonObject.trim();

        String loai = layGiaTri(noiDung, "loai");
        String maBanDoc = layGiaTri(noiDung, "maBanDoc");
        String hoTen = layGiaTri(noiDung, "hoTen");
        String soDienThoai = layGiaTri(noiDung, "soDienThoai");

        if (loai == null || maBanDoc == null) {
            return null;
        }

        return switch (loai) {
            case "SinhVienThuong" -> new SinhVienThuong(maBanDoc, hoTen, soDienThoai);
            case "SinhVienUuTien" -> new SinhVienUuTien(maBanDoc, hoTen, soDienThoai);
            case "GiangVien" -> new GiangVien(maBanDoc, hoTen, soDienThoai);
            default -> null;
        };
    }

    private String layGiaTri(String noiDung, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]*)\"";
        Matcher m = Pattern.compile(pattern).matcher(noiDung);
        return m.find() ? m.group(1) : null;
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }


}
