package Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.*;

import BCD.*;
import blockchain.KeyAccess;
import blockchain.KeyGen;

public class CheckOutController {
    private String contact;
    private String address;
    private double price;
    private static String orderID;
    static{
    	orderID = UUID.randomUUID().toString();        
    }
	
	public void CreateOrder(List<Medicine> orderItem) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("==================================================");
		System.out.println("Kindly provide the delivery details: ");
		System.out.println("Contact\t: ");
		contact = sc.next();
		sc.nextLine();
		System.out.println("Address\t: ");
		address = sc.nextLine();
		price = orderItem.stream().mapToDouble(i -> i.getPrice()).sum();
		SignatureController sig = new SignatureController();
		KeyGen.create(App.user);
		PrivateKey p = KeyAccess.getPrivateKey("KeyPair/PrivateKey"+App.user);
		String signature = sig.sign(Double.toString(price), p);
		Order o = new Order(orderID, contact, address, orderItem, price ,LocalDateTime.now(), signature,App.user);
		Order.insert(o);
		String d = o.getOrderID() + "|" + o.getUserContact() + "|" + o.getUserAddress() + "|" + Order.OrderItemString(o.getOrderItem()) + "|" + Double.toString(o.getPaymentAmount()) + "|" + o.getOrderTime().toString() + "|" + signature + "|" + App.user;
		writepool("trnxpool.txt", d);
		System.out.println("Done");
	}
	
	private void writepool(String fileName, String data) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writer.write(data);
		writer.close();

		
	}
}
