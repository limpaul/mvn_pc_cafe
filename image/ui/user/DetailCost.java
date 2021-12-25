package com.program.pc_cafe.image.ui.user;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
public class DetailCost extends JFrame{
	private JLabel namec,userc,st,usingTimec,serviceTimec;
	public DetailCost() {
		super("PC방 상세 요금");
		Container contain=getContentPane();
		JPanel p=new JPanel(new BorderLayout());
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();
		JPanel p4=new JPanel();
		
		userInfo(p1);
	

		contain.add(p1, "North");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500, 500);
		setSize(500, 500);
		setResizable(true);
		setVisible(true);
	}
	public void userInfo(JPanel panel) {
		panel.setLayout(new GridLayout(2, 2,10,5));
		panel.setBorder(new TitledBorder("요금"));
		JLabel name=new JLabel(" * 이름");namec=new JLabel("없음 ");
		 JLabel userLevel=new JLabel(" * 회원등급");userc=new JLabel("없음 ");
		 labelCenter(name, namec, userLevel, userc);
		 panel.add(name);panel.add(namec);
		 panel.add(userLevel);panel.add(userc);
		 
	}
	public void userTimeInfo(JPanel panel) {
		panel.setLayout(new GridLayout(4, 4));
		JLabel enterTime=new JLabel(" * 입실시간");st=new JLabel("없음 ");
		 JLabel startTime=new JLabel(" * 시작시간");
		 JLabel usingTime=new JLabel(" * 사용시간");usingTimec=new JLabel("없음 ");
		 JLabel serviceTime=new JLabel(" * 서비스시간");serviceTimec=new JLabel("없음 ");
		 panel.add(enterTime);panel.add(st);
		 panel.add(startTime);panel.add(st);
		 panel.add(usingTime);panel.add(usingTimec);
		 panel.add(serviceTime);panel.add(serviceTimec);
		 
	}
	public void userMoneyInfo(JPanel panel) {
		
	}
	public void accountMoneyInfo(JPanel panel) {
		
	}
	public void labelCenter(JLabel label1,JLabel label2) {
		label1.setVerticalAlignment(SwingConstants.CENTER);
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		
	}
	public void labelCenter(JLabel label1,JLabel label2,JLabel label3,JLabel label4) {
		label1.setVerticalAlignment(SwingConstants.CENTER);
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setVerticalAlignment(SwingConstants.CENTER);
		label3.setHorizontalAlignment(SwingConstants.LEFT);
		label4.setVerticalAlignment(SwingConstants.CENTER);
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	public static void main(String[] args) {
		new DetailCost();
	}
}