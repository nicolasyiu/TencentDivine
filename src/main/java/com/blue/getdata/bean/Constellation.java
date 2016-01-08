package com.blue.getdata.bean;

import java.io.Serializable;
import java.util.HashMap;

public class Constellation implements Serializable {
	
	private String name;
	private String url;
	
	private static HashMap<String, Integer> astros = new HashMap<String, Integer>();
	static{
		astros.put("白羊座", 1);
		astros.put("金牛座", 2);
		astros.put("双子座", 3);
		astros.put("巨蟹座", 4);
		astros.put("狮子座", 5);
		astros.put("处女座", 6);
		astros.put("天秤座", 7);
		astros.put("天蝎座", 8);
		astros.put("射手座", 9);
		astros.put("摩羯座", 10);
		astros.put("水瓶座", 11);
		astros.put("双鱼座", 12);
	}
	
	public static HashMap<String, Integer> getAstros() {
		return astros;
	}
	public static void setAstros(HashMap<String, Integer> astros) {
		Constellation.astros = astros;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Constellation [name=" + name + ", url=" + url + "]";
	}
}
