package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KhoSach {
    private final String FILE_PATH = "backend/data/books.json";

    public List<model.Sach> danhSachSach;

    public KhoSach() {
        danhSachSach = new ArrayList<>();
        docTuFile();
    }

    public List<model.Sach> getDanhSachSach() {
        return danhSachSach;
    }

    public void luuSach(model.Sach sach) {
        danhSachSach.add(sach);
        ghiVaoFile();
    }

    public void ghiVaoFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (model.Sach sach : danhSachSach) {
                writer.write(sach.getMaSach() + "," + sach.getTenSach() + "," + sach.getTacGia() + "," + sach.getTheLoai() + "," + sach.getSoLuongHienCo() + "," + sach.getGiaSach());
                writer.newLine();
            }
        }catch (IOException e) {
            System.out.println("Không thể ghi dữ liệu vào File!");
        }
    }

    public void docTuFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            danhSachSach.add(new model.Sach("S001","Lập trình OOP","Trần Đình Nam Sơn","Giáo trình",16,250000));
            danhSachSach.add(new model.Sach("S002","Mắt biếc","Nguyễn Nhật Ánh","Tiểu thuyết",7,180000));
            danhSachSach.add(new model.Sach("S003","Doraemon","Fujiko F.Fujio","Truyện tranh",28,90000));
            ghiVaoFile();
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            danhSachSach.clear();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String maSach = data[0];
                    String tenSach = data[1];
                    String tacGia = data[2];
                    String theLoai = data[3];
                    int soLuongHienCo = Integer.parseInt(data[4]);
                    Double giaSach = Double.parseDouble(data[5]);

                    danhSachSach.add(new model.Sach(maSach,tenSach,tacGia,theLoai,soLuongHienCo, giaSach));
                }
            }
        }catch (IOException e) {
            System.out.println("Lỗi cấu trúc định dạng khi đọc file sách!");
        }
    }
}
