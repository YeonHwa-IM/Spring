package com.kh.ajax.model.vo;

public class User {
	private String userId;
	private String userPwd;
	private int age;
	private String eamil;
	private String phone;
	public User() {}
	
	public User(String userId, String userPwd, int age, String eamil, String phone) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.age = age;
		this.eamil = eamil;
		this.phone = phone;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEamil() {
		return eamil;
	}
	public void setEamil(String eamil) {
		this.eamil = eamil;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPwd=" + userPwd + ", age=" + age + ", eamil=" + eamil + ", phone="
				+ phone + "]";
	}
	
	

}
