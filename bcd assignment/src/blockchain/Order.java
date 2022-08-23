package blockchain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Order {

	private static final String file = "src/trnxpool.txt";
	private String orderID;
	private String userContact;
	private String userAddress;
	//private List<Product> orderItem;
	private double paymentAmount;
	private LocalDateTime orderTime;
	private List<Order> orderList;
	
	public Order(String orderID, String userContact, String userAddress, double paymentAmount,
			LocalDateTime orderTime) {
		this.orderID = orderID;
		this.userContact = userContact;
		this.userAddress = userAddress;
		//this.orderItem = orderItem;
		this.paymentAmount = paymentAmount;
		this.orderTime = orderTime;
	}

	public String getTransID() {
		return orderID;
	}

	public void setTransID(String transID) {
		this.orderID = transID;
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

//	public List<Product> getOrderItem() {
//		return orderItem;
//	}
//
//	public void setOrderItem(List<Product> orderItem) {
//		this.orderItem = orderItem;
//	}

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
	
	public List<Order> getOrderList() {
		try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fis)) {
            return (List<Order>) in.readObject();
        } catch (Exception e) {
            return null;
        }
	}
	
}
