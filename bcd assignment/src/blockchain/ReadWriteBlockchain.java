package blockchain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

public class ReadWriteBlockchain {
	
	private static final String CHAIN_OBJ = "src/chainobj.dat";

	//insert block into blockchain
	public static void insert(LinkedList<Block> bc) {
        try (FileOutputStream fos = new FileOutputStream(CHAIN_OBJ);
                ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(bc);
        } catch (Exception e) {
        }

    }

	//retrieve block from blockchain
    public static LinkedList<Block> get() {
        try (FileInputStream fis = new FileInputStream(CHAIN_OBJ);
                ObjectInputStream in = new ObjectInputStream(fis)) {
            return (LinkedList<Block>) in.readObject();
        } catch (Exception e) {
            return null;
        }

    }

    //display blockchain info (bytes)
    public static void distribute( String temp ){
        try {
            Files.write(Paths.get("src/bc.txt"), temp.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException ex) {
            
        }
    }
}
