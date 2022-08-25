package BCD;

import java.io.Serializable;

public class Medicine implements Serializable {
	private int id;
	private String name;
	private double price;
	
	public Medicine(int id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public int getID() {return id;}
	public String getName() {return name;}
	public double getPrice() {return price;}
	
	public void setID(int id) {this.id = id;}
	public void setName(String name) {this.name = name;}
	public void setPrice(double price) {this.price = price;}
}
