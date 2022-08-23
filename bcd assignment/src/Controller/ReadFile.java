package Controller;

import java.io.File;
import java.nio.file.*;
import java.util.List;


public class ReadFile {
    public static void write(String filename, String text) {
        text += System.lineSeparator();
        try {
            Files.write(Paths.get(filename), text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> read(String filename) {

        if (new File(filename).exists()) {
            try {
                return Files.readAllLines(Paths.get(filename));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }

    }
}