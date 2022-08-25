package Controller;

import java.time.LocalDateTime;
import java.util.*;

import BCD.*;

public class CheckOutController {
    private String contact;
    private String address;
    private double price;
    private static String orderID;
    static{
    	orderID = UUID.randomUUID().toString();        
    }
	
	public void CreateOrder(List<Medicine> orderItem) {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("==================================================");
		System.out.println("Kindly provide the delivery details: ");
		System.out.println("Contact\t: ");
		contact = sc.next();
		System.out.println("Address\t: ");
		address = sc.nextLine();
		price = orderItem.stream().mapToDouble(i -> i.getPrice()).sum();
		Order o = new Order(orderID, contact, address, orderItem, price ,LocalDateTime.now());
		Order.insert(o);
		System.out.println(Order.OrderItemString(orderItem));
		String text = orderID + "|" + Order.OrderItemString(orderItem) + "|" + price + "|" + contact + "|" + address + "|" + LocalDateTime.now();
		ReadFile.write("trnxpool.txt", text);
	}
}
