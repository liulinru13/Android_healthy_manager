package com.mmrx.health.bean;

public class User {
	private String name;
	private int age;
	private String gender;
	private int tall;
	private int weight;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getTall() {
		return tall;
	}
	public void setTall(int tall) {
		this.tall = tall;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public User(String name, int age, String gender, int tall, int weight) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.tall = tall;
		this.weight = weight;
	}
	public User() {
		super();
	}

	
	
}
