package model;

public enum TrangThaiPhieu {
    DANG_MUON ("Đang mượn"),
    DA_TRA ("Đã trả"),
    QUA_HAN("Quá hạn");

    private final String moTa;

    TrangThaiPhieu(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTa() {
        return moTa;
    }
    
    @Override
    public String toString() {
        return moTa;
    }
}
