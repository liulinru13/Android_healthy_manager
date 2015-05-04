package com.mmrx.health.bean;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
@Table(name="fragment_drug")
public class Drug {
	@Id
	private int id;
	private String name;
	private long shengchanriqi;
	private int baozhiqi;
	private boolean isEdible;
	
	
	public Drug(String name, long shengchanriqi, int baozhiqi, boolean isEdible) {
		super();
		this.name = name;
		this.shengchanriqi = shengchanriqi;
		this.baozhiqi = baozhiqi;
		this.isEdible = isEdible;
	}
	public boolean isEdible() {
		return isEdible;
	}
	public void setEdible(boolean isEdible) {
		this.isEdible = isEdible;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getShengchanriqi() {
		return shengchanriqi;
	}
	public void setShengchanriqi(long shengchanriqi) {
		this.shengchanriqi = shengchanriqi;
	}
	public int getBaozhiqi() {
		return baozhiqi;
	}
	public void setBaozhiqi(int baozhiqi) {
		this.baozhiqi = baozhiqi;
	}
	public Drug(int id, String name, long shengchanriqi, int baozhiqi) {
		super();
		this.id = id;
		this.name = name;
		this.shengchanriqi = shengchanriqi;
		this.baozhiqi = baozhiqi;
		this.isEdible=false;
	}
	public Drug(String name, long shengchanriqi, int baozhiqi) {
		super();
		this.name = name;
		this.shengchanriqi = shengchanriqi;
		this.baozhiqi = baozhiqi;
		this.isEdible=false;
	}
	public Drug() {
		super();
	}
	
	
	
	

	
}
