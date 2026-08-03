package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    // Đối tượng Gson dùng chung
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    public static <T> List<T> docDanhSach(String duongDan, Type type) {

        try (FileReader reader = new FileReader(duongDan)) {

            List<T> danhSach = gson.fromJson(reader, type);

            if (danhSach == null) {
                return new ArrayList<>();
            }

            return danhSach;

        } catch (IOException e) {

            System.out.println("Không thể đọc file: " + duongDan);
            return new ArrayList<>();

        }
    }

    public static <T> void ghiDanhSach(String duongDan, List<T> danhSach) {

        try (FileWriter writer = new FileWriter(duongDan)) {

            gson.toJson(danhSach, writer);

        } catch (IOException e) {

            System.out.println("Không thể ghi file: " + duongDan);

        }
    }

    public static <T> Type getListType(Class<T> clazz) {

        return TypeToken
                .getParameterized(List.class, clazz)
                .getType();

    }

}