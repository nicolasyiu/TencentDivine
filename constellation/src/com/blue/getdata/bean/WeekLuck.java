package com.blue.getdata.bean;

import java.io.Serializable;

public class WeekLuck implements Serializable {
	private String loveMsg;
	private String workMsg;
	private String applyMsg;
	private String caiYunMsg;
	private String healthMsg;
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
	public String getApplyMsg() {
		return applyMsg;
	}
	public void setApplyMsg(String applyMsg) {
		this.applyMsg = applyMsg;
	}
	public String getCaiYunMsg() {
		return caiYunMsg;
	}
	public void setCaiYunMsg(String caiYunMsg) {
		this.caiYunMsg = caiYunMsg;
	}
	public String getHealthMsg() {
		return healthMsg;
	}
	public void setHealthMsg(String healthMsg) {
		this.healthMsg = healthMsg;
	}
	@Override
	public String toString() {
		return "WeekLuck [loveMsg=" + loveMsg + ", workMsg=" + workMsg
				+ ", applyMsg=" + applyMsg + ", caiYunMsg=" + caiYunMsg
				+ ", healthMsg=" + healthMsg + "]";
	}

	

}
