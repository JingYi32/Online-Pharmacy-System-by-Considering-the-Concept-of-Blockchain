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
    private List<List<String>> data;
    
	public Block(List<List<String>> data, String previousHash) throws IOException {
		this.previousHash = previousHash;
		this.data = Order.hashOrders();
		this.timestamp = Calendar.getInstance().getTimeInMillis();
		this.currentHash = this.blockHashCode(genByteArr(data), previousHash, timestamp);
		this.merkleRoot = buildMerkleRoot(Order.getOrderList());
	}

		public List<List<String>> getData() {
			return data;
		}
		public String getMerkleRoot() {
			return merkleRoot;
		}
		public void setMerkleRoot(String merkleRoot) {
			this.merkleRoot = merkleRoot;
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
		
		//current block hash
		public String blockHashCode(byte[] data, String previousHash, long timestamp) {
	        return Hasher.newHash(data + previousHash + timestamp, "SHA-256");    
	    }
		
		//hashed order list to bytes
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
		
		//generate merkle root
		public String buildMerkleRoot(List<Order> orderList) throws IOException {	
			ArrayList<byte[]> orderLst = new ArrayList<>();

			for (Order order : orderList) {
				orderLst.add(Hasher.convertToBytes(order));
			}
			
			ArrayList<byte[]> hashes = tranxHashLst( orderLst );
			while(  hashes.size() != 1 ) {
				hashes = tranxHashLst( hashes );
			}	
			return hashes.get(0).toString();
		}
		
		//generate merkle root
		private ArrayList<byte[]> tranxHashLst(ArrayList<byte[]> orders) {
			ArrayList<byte[]> hashLst = new ArrayList<byte[]>();
			int i = 0;
			while( i < orders.size() ) {
				
				byte[] left = orders.get(i);
				i++;
				
				byte[] right = {};
				if( i != orders.size() ) {
					right = orders.get(i);
				}
					
				byte[] hash = new byte[left.length + right.length];
				System.arraycopy(left, 0, hash, 0, left.length);
				System.arraycopy(right, 0, hash, left.length, right.length);
				
				byte[] hashed = Hasher.newHashByte(hash, "SHA-256");
				hashLst.add(hashed);
				i++;
			}
			return hashLst;
		}
}
