package com.blue.getdata.bean;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;

public class TodayLuck implements Serializable {
	private String overviews;// 今日概述内容
	private String titleZonghe;// 综合运势
	private String titleLove;// 爱情运势
	private String titleWoke;// 工作运势
	private String titleCaiyun;// 财运运势
	private String guiren;// 贵人星座
	private String color;// 幸运颜色
	private String number;// 幸运数字
	private String health;// 健康运势

	public String getOverviews() {
		return overviews;
	}

	public void setOverviews(String overviews) {
		this.overviews = overviews;
	}

	public String getTitleZonghe() {
		return titleZonghe;
	}

	public void setTitleZonghe(String titleZonghe) {
		this.titleZonghe = titleZonghe;
	}

	public String getTitleLove() {
		return titleLove;
	}

	public void setTitleLove(String titleLove) {
		this.titleLove = titleLove;
	}

	public String getTitleWoke() {
		return titleWoke;
	}

	public void setTitleWoke(String titleWoke) {
		this.titleWoke = titleWoke;
	}

	public String getTitleCaiyun() {
		return titleCaiyun;
	}

	public void setTitleCaiyun(String titleCaiyun) {
		this.titleCaiyun = titleCaiyun;
	}

	public String getGuiren() {
		return guiren;
	}

	public void setGuiren(String guiren) {
		this.guiren = guiren;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	@Override
	public String toString() {
		return "TodayLuck [overviews=" + overviews + ", titleZonghe="
				+ titleZonghe + ", titleLove=" + titleLove + ", titleWoke="
				+ titleWoke + ", titleCaiyun=" + titleCaiyun + ", guiren="
				+ guiren + ", color=" + color + ", number=" + number
				+ ", health=" + health + "]";
	}

}