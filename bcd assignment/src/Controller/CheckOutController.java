package Controller;

import java.time.LocalDateTime;
import java.util.*;

import BCD.*;

public class CheckOutController {
    private String contact;
    private String address;
    private static String orderID;
    static{
    	orderID = UUID.randomUUID().toString();
        System.out.println( orderID );
        
    }
	
	public void CreateOrder(List<Medicine> orderItem) {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("==================================================");
		System.out.println("Kindly provide the delivery details: ");
		System.out.println("Contact\t: ");
		contact = sc.next();
		System.out.println("Address\t: ");
		address = sc.next();
		Order o = new Order(orderID, contact, address, orderItem, orderItem.stream().mapToDouble(i -> i.getPrice()).sum() ,LocalDateTime.now());
		Order.insert(o);
	}
	
}
