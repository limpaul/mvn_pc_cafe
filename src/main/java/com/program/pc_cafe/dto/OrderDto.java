package com.program.pc_cafe.dto;

import java.sql.Timestamp;

public class OrderDto {
	private int pcNum;
	private String userId;
	private String foodName;
	private int foodCount;
	private Timestamp orderTime;
	public int getPcNum() {
		return pcNum;
	}
	public void setPcNum(int pcNum) {
		this.pcNum = pcNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public int getFoodCount() {
		return foodCount;
	}
	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	
	
}
