package blockchain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MerkleTree {

	private List<List<String>> orders;
	private String root = "0";

	public String getRoot() {
		return root;
	}

	private MerkleTree(List<List<String>> orders) {
		super();
		this.orders = orders;
	}

	private static MerkleTree instance;
	public static MerkleTree getInstance( List<List<String>> orders ) {
		if( instance == null ) {
			return new MerkleTree(orders);
		}
		return instance;
	}

	public void build(List<List<String>> orders) {
		
		
		List<List<String>> orderLst = new ArrayList<>();
		
		for (List<String> order : this.orders) {
			orderLst.add(order);
		}
		
		List<List<String>> hashes = tranxHashLst( orderLst );
		while(  hashes.size() != 1 ) {
			hashes = tranxHashLst( hashes );
		}
		//this.root = hashes.get(0);
		//idk howwwwwww	
	}
	
	private List<List<String>> tranxHashLst(List<List<String>> orders) {
		List<List<String>> hashLst = new ArrayList<>();
		int i = 0;
		while( i < orders.size() ) {
			
			List<String> left = orders.get(i);
			i++;
			
			List<String> right = null;
			if( i != orders.size() ) right = orders.get(i);
			
			String hash = Hasher.newhash(left.toString().concat(right.toString()), "SHA-256");
			//hashLst.add(hash);
			i++;
		}
		return hashLst;
	}
}
