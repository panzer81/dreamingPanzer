package com.panzer.memo.service;

import java.io.Serializable;

public class MemoVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	private String crtDate;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	}
	@Override
	public String toString() {
		return "MemoVO [message=" + message + ", crtDate=" + crtDate + "]";
	}
	
	

}
