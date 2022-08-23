package blockchain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Block implements Serializable {
	
	private int index;
	private String currentHash, previousHash;
	private long timestamp;
    private List<Order> orderList;
    private Order order;
    private String merkleRoot;
    
	public Block(String previousHash, List<Order> orderList) {
		this.previousHash = previousHash;
		this.orderList = orderList;
		this.timestamp = Calendar.getInstance().getTimeInMillis();
		this.currentHash = this.blockHashCode(genByteArr(orderList), previousHash, timestamp);
		this.merkleRoot = merkleRoot;
	}
	
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getCurrentHash() {
			return currentHash;
		}
		public void setCurrentHash(String currentHash) {
			this.currentHash = currentHash;
		}
		public String getPreviousHash() {
			return previousHash;
		}
		public void setPreviousHash(String previousHash) {
			this.previousHash = previousHash;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		
		//not sure
		public String blockHashCode(byte[] data, String previousHash, long timestamp) {
	        return Hasher.newhash(data + previousHash + timestamp, "SHA-256");
	        
	    }
		
		public Order getOrders(List<Order> orderList) {
			return null;
			
		}
		
		//not sure
		private static byte[] genByteArr(List<Order> odList) {
	        ByteArrayOutputStream boas = new ByteArrayOutputStream();
	        ObjectOutputStream out;
	        if (odList != null) {
	            try {
	                out = new ObjectOutputStream(boas);
	                out.writeObject(odList);
	                out.flush();
	            } catch (IOException ex) {
	                System.out.println(ex.getMessage());
	                return null;
	            }
	            return boas.toByteArray();
	        } else {
	            return null;
	        }
	    }
}
