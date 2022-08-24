package blockchain;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.GsonBuilder;

import BCD.Order;

public class Blockchain {

    static LinkedList<Block> bc = new LinkedList<>();
    
    public static void main(String[] args) {
//        firstBlock(); //CAN BE EMPTY!
        nextBlock();

    }

    public static void firstBlock() {
       
        System.out.println("--- Transaction objects ---");
        //List<Transaction> trnxPool = TrnxPoolAdapter.getTransactions();
//        trnxPool.stream().forEach( System.out::println );
        
        System.out.println("--- Transactions with hashes ---");
        //List<List<String>> trnxPool_hashes = TrnxPoolAdapter.getTransactionsHashes();
//        System.out.println( trnxPool_hashes );
        
        
        //Block b1 = new Block(trnxPool_hashes, "0"); //genesis block
        //bc.add(b1);
        //clear the trnxpool.txt
        Order.clearTrnxFile();
        ReadWriteBlockchain.insert(bc);
        //distribute/display the linkedlist elements/blocks
         out(bc);
        
    }
    
    public static void nextBlock(){
        //List<List<String>> trnxPool_hashes = TrnxPoolAdapter.getTransactionsHashes();
        bc = ReadWriteBlockchain.get();
        //Block block = new Block(trnxPool_hashes, bchain.getLast().getCurrentHash() );
        //bc.add(block);
        Order.clearTrnxFile();
        ReadWriteBlockchain.insert(bc);
        out(bc);

    }
    
    public static void out(LinkedList<Block> bchain){
        String temp = new GsonBuilder().setPrettyPrinting().create().toJson(bchain);
        System.out.println( temp );
        ReadWriteBlockchain.distribute(temp);
    }
}
