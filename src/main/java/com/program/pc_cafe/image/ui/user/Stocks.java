package com.program.pc_cafe.image.ui.user;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.program.pc_cafe.dao.UserDAO;
import com.program.pc_cafe.dto.FoodDTO;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Stocks extends JDialog{
	JTextField searchField,inputStock,inputPrice,deleteStock;
	JButton btn,btnAdd,btnDelete;
	JTable stockTable;
	DefaultTableModel model;
	Vector<FoodDTO> stockFromDB=UserDAO.foodAll();
	//String[] stockRow;
	Vector<String> stockColumn=new Vector<>();
	JScrollPane scroll;
	public Stocks(JFrame frame) {
		super(frame,true);
		Container c=getContentPane();
		
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(5, 3));
		searchField=new JTextField(20);
		inputStock=new JTextField();
		inputPrice=new JTextField();
		deleteStock=new JTextField();
		btn=new JButton("찾기");
		btnAdd=new JButton("추가");
		btnDelete=new JButton("삭제");
		p1.add(new JLabel("상품이름"));p1.add(new JLabel("상품가격"));p1.add(new JLabel(""));
		p1.add(inputStock);p1.add(inputPrice);p1.add(btnAdd);
		p1.add(new JLabel("상품이름"));p1.add(deleteStock);p1.add(btnDelete);
		p1.add(new JLabel("상품이름"));p1.add(searchField);p1.add(btn);
		
		c.add(p1, "North");
		
		
		stockColumn.add("상품");stockColumn.addElement("가격");
		model=new DefaultTableModel(stockColumn, 0);
		stockTable=new JTable(model);
		scroll=new JScrollPane(stockTable);
		
		for(int i=0;i<stockFromDB.size();i++) {
			String[] stockRow= {stockFromDB.get(i).getName(),Integer.toString(stockFromDB.get(i).getPrice())};
			model.addRow(stockRow);
		}
	
		
		
		c.add(scroll,"South");
		setResizable(false);
		setSize(500, 650);
		setVisible(true);
	
	}

}