package com.mmrx.health.bean;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name="mood")
public class Mood {

	@Id
	private int id;
	private String content;
	private String time;
	private int state;
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Mood(int id, String content, String time,int state) {
		super();
		this.id = id;
		this.content = content;
		this.time = time;
		this.state=state;
	}
	public Mood() {
		super();
	}
	public Mood(String content, String time,int state) {
		super();
		this.content = content;
		this.time = time;
		this.state=state;
	}
	
	
}
