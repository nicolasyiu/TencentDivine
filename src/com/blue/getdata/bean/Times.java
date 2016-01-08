package com.blue.getdata.bean;

import java.io.Serializable;
import java.util.ArrayList;


public class Times implements Serializable {
	
	private String name;
	private ArrayList<Constellation> list ;
	private int model;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Constellation> getList() {
		return list;
	}
	public void setList(ArrayList<Constellation> list) {
		this.list = list;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return "Times [name=" + name + ", list=" + list + ", model=" + model
				+ "]";
	}


}
