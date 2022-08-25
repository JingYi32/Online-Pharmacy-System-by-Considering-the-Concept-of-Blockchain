package blockchain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import BCD.Order;

public class Block implements Serializable {
	
	private int index;
	private String currentHash, previousHash;
	private long timestamp;
    private String merkleRoot;
    private List<Order> orderList;
    private List<List<String>> data;
    
	public Block(List<Order> orderList, String previousHash) {
		this.previousHash = previousHash;
		this.data = getOrders(orderList);
		this.timestamp = Calendar.getInstance().getTimeInMillis();
		this.currentHash = this.blockHashCode(genByteArr(data), previousHash, timestamp);
		this.merkleRoot = buildMerkleRoot(orderList);
	}
	
		public List<List<String>> getData() {
			return data;
		}
		public void setData(List<List<String>> data) {
			this.data = data;
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
		
		//block to bytes
		public String blockHashCode(byte[] data, String previousHash, long timestamp) {
	        return Hasher.newhash(data + previousHash + timestamp, "SHA-256");
	        
	    }
		
		//get all orders
		public List<List<String>> getOrders(List<Order> orderList){
	        return data = Order.readOrders();
		}
		
		//order list to bytes
		private static byte[] genByteArr(List<List<String>> odList) {
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
		
		public String buildMerkleRoot(List<Order> orderList) {	
			ArrayList<byte[]> orderLst = new ArrayList<>();
			
			for (Order order : this.orderList) {
				orderLst.add(Hasher.getBytes(order));
			}
			
			ArrayList<byte[]> hashes = tranxHashLst( orderLst );
			while(  hashes.size() != 1 ) {
				hashes = tranxHashLst( hashes );
			}	
			return hashes.get(0).toString();
		}
		
		private ArrayList<byte[]> tranxHashLst(ArrayList<byte[]> orders) {
			ArrayList<byte[]> hashLst = new ArrayList<byte[]>();
			int i = 0;
			while( i < orders.size() ) {
				
				byte[] left = orders.get(i);
				i++;
				
				byte[] right = null;
				if( i != orders.size() ) right = orders.get(i);
				
				byte[] hash = new byte[left.length + right.length];
				System.arraycopy(left, 0, hash, 0, left.length);
				System.arraycopy(right, 0, hash, left.length, right.length);
				
				byte[] hashed = Hasher.newhash(hash, "SHA-256");
				hashLst.add(hashed);
				i++;
			}
			return hashLst;
		}
}
