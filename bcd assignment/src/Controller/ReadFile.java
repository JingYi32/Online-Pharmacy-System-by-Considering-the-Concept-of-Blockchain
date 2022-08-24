package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import BCD.Medicine;


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
    
    public static List<Medicine> readProduct(String filename){
        if (new File(filename).exists()) {
            try {
            	 BufferedReader bufReader = new BufferedReader(new FileReader(filename));
            	 String line;
            	 List<Medicine> productLst = new ArrayList<>();
            	 while((line = bufReader.readLine()) != null) {
            		 String[] contents = line.split("\\|");
            		 Medicine m = new Medicine();
        			 m.setID(Integer.parseInt(contents[0]));
        			 m.setName(contents[1]);
        			 m.setPrice(Double.parseDouble(contents[2]));
        			 productLst.add(m);
            	 }
                return productLst;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
                System.out.println("first");
                return null;
            }
        } else{
        	System.out.println();
            System.out.println("second");
            return null;
        }
    }
}
