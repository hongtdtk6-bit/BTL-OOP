package menu;

import service.DichVuSach;

import java.util.Scanner;

public class SachMenu {
    private service.DichVuSach dichVuSach;
    private Scanner scanner;

    public SachMenu(DichVuSach dichVuSach) {
        this.dichVuSach = dichVuSach;
        this.scanner = new Scanner(System.in);
    }

    public void hienThiMenu(){
        int chon = -1;
        do{
            System.out.println("\n----- Phân hệ quản lý sách -----");
            System.out.println("1. Hiển thị danh sách sách");
            System.out.println("2. Nhập thêm sách mới");
            System.out.println("3. Tìm kiếm sách theo mã hoặc tên");
            System.out.println("0. Quay lại Menu chính");
            System.out.println("Mời chọn số: ");

            chon = scanner.nextInt();
            scanner.nextLine();

            switch(chon){
                case 1:
                    dichVuSach.hienThiDanhSach();
                    break;
                case 2:
                    xuLyThem();
                    break;
                case 3:
                    xuLyTim();
                    break;
                case 0:
                    System.out.println("Thoát quản lý sách.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }while(chon != 0);
    }

    public void xuLyThem(){
        System.out.println("\n----- Nhập thông tin sách -----");
        System.out.println("Mã sách: "); String maSach = scanner.nextLine();
        System.out.println("tên sách: "); String tenSach = scanner.nextLine();
        System.out.println("Tác giả: "); String tacGia = scanner.nextLine();
        System.out.println("Thể loại: "); String theLoai = scanner.nextLine();
        System.out.println("Số lượng hiện có: "); int soLuong = scanner.nextInt();
        System.out.println("Gía trị sách: "); double gia = scanner.nextDouble();
        scanner.nextLine();

        model.Sach sach = new model.Sach(maSach,tenSach,tacGia,theLoai,soLuong,gia);
        dichVuSach.themSachMoi(sach);
    }

    private void xuLyTim(){
        System.out.println("Nhập mã hoặc tên sách muốn tìm: ");
        String tuKhoa = scanner.nextLine();
        model.Sach kq = dichVuSach.timKiemSach(tuKhoa);
        if (kq != null){
            System.out.println("Tìm thấy kết quả: " + kq.toString());
        }else {
            System.out.println("Không tìm thấy sách khớp với từ khoá!");
        }
    }
}
