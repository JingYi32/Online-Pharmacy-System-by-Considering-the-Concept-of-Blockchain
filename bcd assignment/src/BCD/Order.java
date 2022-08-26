package BCD;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import blockchain.Hasher;

public class Order implements Serializable {
	
	private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

	private static final String file = "trnxpool.txt";
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
	
	//get order list from file
	public static List<String> getAll(){
        try {
            return Files.readAllLines(Paths.get(file)).stream().collect(Collectors.toList());
        } catch (IOException ex) {
            return null;
        }
    }
	
	//retrieve order object List
	public static List<Order> getOrderList(){
        List<String> trnxLst = getAll();
        return trnxLst.stream()
                .map( record -> record.split("\\|") )
                .map(arr -> new Order( arr[0], arr[1], arr[2], getMedList(arr[3]), Double.parseDouble(arr[4]), LocalDateTime.parse(arr[5], FORMATTER)))
                .collect(Collectors.toList());
    }
		
	//hash orders
	public static List<List<String>> hashOrders(){
        List<Order> allOrders = getOrderList();
        List<List<String>> hashedOrders = new ArrayList();
        for (Order od : allOrders) {
            List<String> hashLst = new ArrayList();
            hashLst.add( Hasher.newHash(od.getOrderID(), "SHA-256") );
            hashLst.add( Hasher.newHash(od.getUserContact().toString(), "SHA-256") );
            hashLst.add( Hasher.newHash(od.getUserAddress(), "SHA-256") );
            hashLst.add( Hasher.newHash(OrderItemString(od.getOrderItem()), "SHA-256") );
            hashLst.add( Hasher.newHash(Double.toString(od.getPaymentAmount()), "SHA-256") );
            hashLst.add( Hasher.newHash(od.getOrderTime().toString(), "SHA-256") );
            hashedOrders.add(hashLst);
        }
        return hashedOrders;
	}
	
	//empty file after transferring into block
	public static void clearTrnxFile(){
        try {
            FileChannel.open(Paths.get(file), StandardOpenOption.WRITE).truncate(0).close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	//convert medicine list to string
	public static String OrderItemString(List<Medicine> order) {
		String orderstring = "[";
		for (Medicine o : order) {
			orderstring += String.join("$", Integer.toString(o.getID()), o.getName(), Double.toString(o.getPrice()))+",";
		}
		orderstring += "]";
		return orderstring;
	}
	
	//convert string to medicine list
	public static List<Medicine> getMedList(String str) {
		List<Medicine> mL = new ArrayList<>();
		String var = str.substring(1, str.length() - 2);
		String[] array = var.split("\\,",-1); 
		
		for (String s : array) {
			String[] arrays = s.split("\\$",-1); 
			Medicine med = new Medicine(Integer.parseInt(arrays[0]), arrays[1], Double.parseDouble(arrays[2]));
			mL.add(med);
			}	
		return mL;
	}
	
}
