package com.tjcj.carrental.model;

import java.io.Serializable;

public class Sms implements Serializable{
	private int state;
	private String content;
	private String date;

	public Sms(int state, String content, String date) {
		super();
		this.state = state;
		this.content = content;
		this.date = date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
