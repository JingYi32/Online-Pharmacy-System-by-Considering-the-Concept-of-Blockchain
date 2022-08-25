package blockchain;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.GsonBuilder;

import BCD.Order;

public class Blockchain {

    static LinkedList<Block> bc = new LinkedList<>();
    
    public static void Blockchain() throws IOException {
    	bc = ReadWriteBlockchain.get();
    	if (bc.equals(null)) {
    		LinkedList<Block> bch = new LinkedList<>();
    		firstBlock(bch);
    	}
    	else {
    		nextBlock();
    	}
    }
    
    public static void main(String[] args) throws IOException {
    	LinkedList<Block> bc = ReadWriteBlockchain.get();
    	if (bc == null) {
    		LinkedList<Block> bch = new LinkedList<>();
    		firstBlock(bch);
    	}
    	else {
    		nextBlock();
    	}

    }

    public static void firstBlock(LinkedList<Block> bc) throws IOException {
    	
        List<List<String>> trnxPool_hashes = Order.hashOrders();
        Block b1 = new Block(trnxPool_hashes, "0"); //genesis block
        bc.add(b1);
        //clear the trnxpool.txt
        Order.clearTrnxFile();
        ReadWriteBlockchain.insert(bc);
        //distribute/display the linkedlist elements/blocks
        //out(bc);
        
    }
    
    public static void nextBlock() throws IOException{
        bc = ReadWriteBlockchain.get();
        List<List<String>> trnxPool_hashes = Order.hashOrders();
        Block block = new Block(trnxPool_hashes, bc.getLast().getCurrentHash() );
        bc.add(block);
        Order.clearTrnxFile();
        ReadWriteBlockchain.insert(bc);
        //out(bc);

    }
    
    public static void out(LinkedList<Block> bchain){
        String temp = new GsonBuilder().setPrettyPrinting().create().toJson(bchain);
        System.out.println( temp );
        ReadWriteBlockchain.distribute(temp);
    }
}
