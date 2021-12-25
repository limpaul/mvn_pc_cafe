package com.program.pc_cafe.dto;

import java.sql.Timestamp;

public class UserDTO {
	private String userId;//아이디
	private String userPw;//비밀번호
	private String userName;//이름
	private String userEmail;//이메일
	private String userPhoneNumber;//폰번호
	private String userAddress;//주소
	private UserTimeDto userTimeDto;
	
	public UserDTO(String id, String pwd, String name, String email, String phoneNumber, String address) {
		this.userId = id;
		this.userPw = pwd;
		this.userName = name;
		this.userEmail = email;
		this.userPhoneNumber = phoneNumber;
		this.userAddress = address;
	}
	public UserDTO(String id, String pwd, String name, String email, String phoneNumber, String address, UserTimeDto userTimeDto) {
		this.userId = id;
		this.userPw = pwd;
		this.userName = name;
		this.userEmail = email;
		this.userPhoneNumber = phoneNumber;
		this.userAddress = address;
		this.userTimeDto = userTimeDto;
	}

	
	public UserDTO(String userId, UserTimeDto userTimeDto) {
		this.userId = userId;
		this.userTimeDto = userTimeDto;
	}
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserPw() {
		return userPw;
	}


	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}


	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}


	public String getUserAddress() {
		return userAddress;
	}


	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}


	public UserTimeDto getUserTimeDto() {
		return userTimeDto;
	}


	public void setUserTimeDto(UserTimeDto userTimeDto) {
		this.userTimeDto = userTimeDto;
	}
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userPw=" + userPw + ", userName=" + userName + ", userEmail="
				+ userEmail + ", userPhoneNumber=" + userPhoneNumber + ", userAddress=" + userAddress + ", userTimeDto="
				+ userTimeDto + "]";
	}

	
	
}
