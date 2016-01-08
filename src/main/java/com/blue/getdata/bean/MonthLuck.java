package com.blue.getdata.bean;

import java.io.Serializable;

public class MonthLuck implements Serializable {
	private String zonghe;//综合运势
	private String love;//爱情运势
	private String work;//工作运势
	private String health;//健康运势
	private String caiyun;//财运运势
	public String getZonghe() {
		return zonghe;
	}
	public void setZonghe(String zonghe) {
		this.zonghe = zonghe;
	}
	public String getLove() {
		return love;
	}
	public void setLove(String love) {
		this.love = love;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getCaiyun() {
		return caiyun;
	}
	public void setCaiyun(String caiyun) {
		this.caiyun = caiyun;
	}
	@Override
	public String toString() {
		return "MonthLuck [zonghe=" + zonghe + ", love=" + love + ", work="
				+ work + ", health=" + health + ", caiyun=" + caiyun + "]";
	}
	

}
