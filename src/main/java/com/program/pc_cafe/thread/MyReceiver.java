package com.program.pc_cafe.thread;
import java.io.*;//�ܼ��� ���� ������̶� �̷��Ը� ��
import java.net.*;//������ ���� ���� ���.

import javax.swing.JTextArea;

public class MyReceiver extends Thread {

	Socket socket;
	String data;
	JTextArea area;
	public MyReceiver(Socket socket,JTextArea area) {
		this.socket = socket;
		this.area=area;
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((data=br.readLine())!=null) {
				area.append(data+"\n");
			}
		} catch (Exception e) {

		}
	}

}
