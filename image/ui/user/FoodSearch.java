package com.program.pc_cafe.image.ui.user;


import javax.swing.*;

import com.program.pc_cafe.dao.UserDAO;
import com.program.pc_cafe.dto.FoodDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FoodSearch extends JDialog implements ActionListener {
	JTextField searchField;
	JLabel showImage;
	JLabel list;
	JButton searchOk, add, cancel;

	public FoodSearch(String searchName, JFrame frame) {
		super(frame,true);
		Container c = getContentPane();
		JPanel p = new JPanel();
		searchField = new JTextField(searchName, 23);
		searchOk = new JButton("검색");
		showImage = new JLabel(new ImageIcon("search.png"));
		searchOk.addActionListener(this);
		p.add(searchField);
		p.add(searchOk);
		p.add(showImage);
		c.add(p, "North");

		JPanel p2 = new JPanel();
		list = new JLabel("상품이름: 검색해주세요!      가격: 검색해주세요!");
		p2.add(list);
		c.add(p2, "Center");

		JPanel p3 = new JPanel();
		add = new JButton("추가");
		cancel = new JButton("취소");
		p3.add(add);
		p3.add(cancel);
		add.addActionListener(this);
		cancel.addActionListener(this);
		c.add(p3, "South");
		setSize(500, 500);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if (cmd.equals("검색")) {
			String name = searchField.getText();
			ArrayList<FoodDTO> searchV = UserDAO.foodSearch(name);
			searchV.add(new FoodDTO("", 0));
			for (int i = 0; i < searchV.size(); i++) {

				if (searchV.get(i).getName().equals(name)) {// 상품명이랑 일치하는게 있다면
					list.setText("상품이름: " + searchV.get(i).getName() + "   /   " + " 가격: " + searchV.get(i).getPrice());
					String imagePath = "여기다/" + searchV.get(i).getName() + ".jpg";
					showImage.setIcon(new ImageIcon(imagePath));
					break;
				}
				i++;
				if (i >= searchV.size()) {
					showImage.setIcon(new ImageIcon("sorry.gif"));
					list.setText("없음");
				}
			}

		}
		if (cmd.equals("추가")) {
			
		}
		if (cmd.equals("취소")) {
			setVisible(false);
		}

	}

}