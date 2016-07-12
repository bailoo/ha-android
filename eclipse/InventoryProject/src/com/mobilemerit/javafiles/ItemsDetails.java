package com.mobilemerit.javafiles;


public class ItemsDetails {

	String name;
	String details;
	String company;
	String location;
	String date;
	String productID;
	String brand;
	int catID;
	int price;
	int quantity;
	int notifyLimit;
	int id;
	byte[] image;

	public ItemsDetails() {
		super();
	}

	public ItemsDetails(String name) {
		super();
		this.name = name;
	}

	public ItemsDetails(String name, String details, String company,
			String location, int catID, int price, int quantity, byte[] image,
			String date, String productID, String brand, int notifyLimit) {
		super();
		this.name = name;
		this.details = details;
		this.company = company;
		this.location = location;
		this.catID = catID;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
		this.date = date;
		this.productID = productID;
		this.brand = brand;
		this.notifyLimit = notifyLimit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCatID() {
		return catID;
	}

	public void setCatID(int catID) {
		this.catID = catID;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getNotifyLimit() {
		return notifyLimit;
	}

	public void setNotifyLimit(int notifyLimit) {
		this.notifyLimit = notifyLimit;
	}	

}
