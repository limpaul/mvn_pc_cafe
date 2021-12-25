package com.program.pc_cafe.dto;

import java.sql.Timestamp;

public class UserTimeDto {
	private String userId;
	private int remainTime;//잔여시간
	private int mountOfMoney;//총금액 
	public UserTimeDto() {
	}
	
	public UserTimeDto(int mountOfMoeny, int remainTime) {
		this.mountOfMoney = mountOfMoeny;
		this.remainTime = remainTime;
	}
	public int getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
	public int getMountOfMoney() {
		return mountOfMoney;
	}
	public void setMountOfMoney(int mountOfMoney) {
		this.mountOfMoney = mountOfMoney;
	}
	
	/*create table usertime (user_id varchar(20), remain_time timestamp, mount_of_money int)*/
}
