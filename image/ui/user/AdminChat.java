package com.program.pc_cafe.image.ui.user;
import javax.swing.*;

import com.program.pc_cafe.thread.MyReceiver;
import com.program.pc_cafe.thread.MySender;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


public class AdminChat extends JDialog implements ActionListener {
	JTextArea area;
	JScrollPane scroll;
	JTextField input;
	JButton btnOk;
	Socket mySocket;
	String myNick;
	BufferedReader br;
	PrintWriter pw;
	String data;
	MySender t; //메세지를 보내는 쓰레드 
	MyReceiver r;//메세지를 받는 쓰레드 
	public AdminChat(JFrame frame) {
		super(frame);
		setTitle("관리자채팅");
		Container c = getContentPane();
		JPanel p1 = new JPanel();
		area = new JTextArea(10, 25);

		area.setEditable(false);
		input = new JTextField(20);
		input.addActionListener(this);
		btnOk = new JButton("전송");
		btnOk.addActionListener(this);
		scroll = new JScrollPane(area);
		p1.add(scroll);
		p1.add(input);
		p1.add(btnOk);
		c.add(p1, "Center");

		setSize(320, 270);
		setLocation(500, 300);
		setVisible(true);

		try {
			mySocket = new Socket("127.0.0.1", 1995);
			t=new MySender("관리자", mySocket);
			t.start();
			
			r=new MyReceiver(mySocket,area) ;
			r.start();
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		t.sendMsg(input.getText());
		input.setText("");
	}

	

}