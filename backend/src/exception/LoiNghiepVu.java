package exception;

public class LoiNghiepVu extends Exception {

    public LoiNghiepVu() {
        super();
    }

    public LoiNghiepVu(String message) {
        super(message);
    }

    public LoiNghiepVu(String message, Throwable cause) {
        super(message, cause);
    }
}
