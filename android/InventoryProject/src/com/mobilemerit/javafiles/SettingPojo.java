package com.mobilemerit.javafiles;

public class SettingPojo {

	String name;
	int resource;	
	
	public SettingPojo(String name, int resource) {
		super();
		this.name = name;
		this.resource = resource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getResource() {
		return resource;
	}
	public void setResource(int resource) {
		this.resource = resource;
	}
	
}
