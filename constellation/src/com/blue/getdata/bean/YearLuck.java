package com.blue.getdata.bean;

import java.io.Serializable;

public class YearLuck implements Serializable {
	
	private String zongHeMsg ;
	private String loveMsg;
	private String workMsg;
	private String caiyunMsg;
	public String getZongHeMsg() {
		return zongHeMsg;
	}
	public void setZongHeMsg(String zongHeMsg) {
		this.zongHeMsg = zongHeMsg;
	}
	public String getLoveMsg() {
		return loveMsg;
	}
	public void setLoveMsg(String loveMsg) {
		this.loveMsg = loveMsg;
	}
	public String getWorkMsg() {
		return workMsg;
	}
	public void setWorkMsg(String workMsg) {
		this.workMsg = workMsg;
	}
	public String getCaiyunMsg() {
		return caiyunMsg;
	}
	public void setCaiyunMsg(String caiyunMsg) {
		this.caiyunMsg = caiyunMsg;
	}
	@Override
	public String toString() {
		return "YearLuck [zongHeMsg=" + zongHeMsg + "\n loveMsg=" + loveMsg
				+ "\n workMsg=" + workMsg + "\n caiyunMsg=" + caiyunMsg + "]";
	}


}
