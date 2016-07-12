package com.mobilemerit.javafiles;

public class HomeItem {

	String title;
	int image;
	public HomeItem(String title, int image) {
		super();
		this.title = title;
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
}
