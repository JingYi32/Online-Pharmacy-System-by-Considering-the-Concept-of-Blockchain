package BCD;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import blockchain.Hasher;

public class Order {

	private static final String file = "src/trnxpool.txt";
	private String orderID;
	private String userContact;
	private String userAddress;
	private List<Medicine> orderItem;
	private double paymentAmount;
	private LocalDateTime orderTime;
	
	public Order(String orderID, String userContact, String userAddress, List<Medicine> orderItem, double paymentAmount,
			LocalDateTime orderTime) {
		this.orderID = orderID;
		this.userContact = userContact;
		this.userAddress = userAddress;
		this.orderItem = orderItem;
		this.paymentAmount = paymentAmount;
		this.orderTime = orderTime;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setorderID(String orderID) {
		this.orderID = orderID;
	}

	public String getUserContact() {
		return userContact;
	}

	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public List<Medicine> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<Medicine> orderItem) {
		this.orderItem = orderItem;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}
	
	//insert new order into order list
	public static void insert(Order newOrder) {
        try (FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(newOrder);
        } catch (Exception e) {
        }
    }
	
	//retrieve orderList
	public static List<Order> getOrderList() {
		try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fis)) {
            return (List<Order>) in.readObject();
        } catch (Exception e) {
            return null;
        }
	}
	
	public static List<List<String>> readOrders(){
        List<Order> allOrders = getOrderList();
        List<List<String>> readOrders = new ArrayList();
        for (Order od : allOrders) {
            List<String> hashLst = new ArrayList();
            hashLst.add( od.getOrderID() );
            hashLst.add( od.getUserContact() );
            hashLst.add( od.getUserAddress() );
            hashLst.add( OrderItemString(od.getOrderItem()) );
            hashLst.add( Double.toString(od.getPaymentAmount()));
            hashLst.add( od.getOrderTime().toString() );
            readOrders.add(hashLst);
        }
        return readOrders;
	}
	
	//hash orders
	public static List<List<String>> hashOrders(){
        List<Order> allOrders = getOrderList();
        List<List<String>> hashedOrders = new ArrayList();
        for (Order od : allOrders) {
            List<String> hashLst = new ArrayList();
            hashLst.add( Hasher.newhash(od.getOrderID(), "SHA-256") );
            hashLst.add( Hasher.newhash(od.getUserContact().toString(), "SHA-256") );
            hashLst.add( Hasher.newhash(od.getUserAddress(), "SHA-256") );
            hashLst.add( Hasher.newhash(OrderItemString(od.getOrderItem()), "SHA-256") );
            hashLst.add( Hasher.newhash(Double.toString(od.getPaymentAmount()), "SHA-256") );
            hashLst.add( Hasher.newhash(od.getOrderTime().toString(), "SHA-256") );
            hashedOrders.add(hashLst);
        }
        return hashedOrders;
	}
	
	//empty file after putting into block
	public static void clearTrnxFile(){
        try {
            FileChannel.open(Paths.get(file), StandardOpenOption.WRITE).truncate(0).close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	private static String OrderItemString(List<Medicine> order) {
		String orderstring = null;
		for (Medicine o : order) {
			orderstring += String.join("|", Integer.toString(o.getID()), o.getName(), Double.toString(o.getPrice()))+"$";
		}
		
		return orderstring;
	}
	
	@Override
    public String toString() {
        return "Transaction{" + "orderItem=" + orderID + ", orderDt=" + userContact + ", payment=" + userAddress + ", email=" + orderItem + ", deliveryAddr=" + paymentAmount + ", status=" + orderTime + '}';
    }
	
}
