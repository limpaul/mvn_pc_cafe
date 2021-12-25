package com.program.pc_cafe.image.ui.user;

import java.util.*;

import com.program.pc_cafe.dao.UserDAO;
import com.program.pc_cafe.dto.FoodDTO;



public class FoodValue {
	private String name;//음식이름 
	private int value; //가격 
	private int many;  //수량
	static Vector<FoodDTO> v=UserDAO.foodAll();

	public FoodValue() {
		// TODO Auto-generated constructor stub
	}
	public FoodValue(String name) {
		this.name=name;
	}
	public FoodValue(String name,int value) {
		this.name=name;
		this.value=value;
	}
	public FoodValue(String name,String text) {
	
	}
	
	public FoodValue returnValue(String name) {
		FoodValue returnValue;
	
		for(int i=0;i<v.size();i++) {
			if(name.equals(v.get(i).getName())) {
				String name1=v.get(i).getName();
				int value=v.get(i).getPrice();
				returnValue= new FoodValue(name1,value);
			
				return returnValue;
			}
		}
		System.out.println("리턴이 null되었네용");
		return new FoodValue("없음",0);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMany() {
		return many;
	}

	public void setMany(int many) {
		this.many = many;
	}

}