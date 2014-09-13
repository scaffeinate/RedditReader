package com.app.m.reddit.reader.common;

import java.util.LinkedList;

public class Data {
	private String modhash;
	private String after;
	private String before;
	private LinkedList<Children> children;
	
	public String getModhash() {
		return modhash;
	}
	public void setModhash(String modhash) {
		this.modhash = modhash;
	}
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
	}
	public String getBefore() {
		return before;
	}
	public void setBefore(String before) {
		this.before = before;
	}
	public LinkedList<Children> getChildren() {
		return children;
	}
	public void setChildren(LinkedList<Children> children) {
		this.children = children;
	}
}
