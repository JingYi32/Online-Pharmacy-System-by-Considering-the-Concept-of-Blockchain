package blockchain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Block implements Serializable {
	
	private int index;
	private String currentHash, previousHash;
	private long timestamp;
    private String merkleRoot;
    
	public Block(List<List<String>> odList, String previousHash) {
		this.previousHash = previousHash;
		this.timestamp = Calendar.getInstance().getTimeInMillis();
		this.currentHash = this.blockHashCode(genByteArr(odList), previousHash, timestamp);
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
		
		//block to bytes
		public String blockHashCode(byte[] data, String previousHash, long timestamp) {
	        return Hasher.newhash(data + previousHash + timestamp, "SHA-256");
	        
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
}
