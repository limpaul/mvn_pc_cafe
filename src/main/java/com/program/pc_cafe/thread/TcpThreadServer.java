package com.program.pc_cafe.thread;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TcpThreadServer implements Runnable {
	ServerSocket serverSocket;
	Thread[] threadArr;


	public TcpThreadServer(int num) {
		try {
			serverSocket = new ServerSocket(7778);
			System.out.println(getTime() + "서버가 준비 되었습니다. ");
			threadArr = new Thread[num];
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e);
			e.printStackTrace();
		}

	}
	public void start() {
		System.out.println("length is:" +threadArr.length);
		for (int i = 0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}
	}
	public void run() {
		while (true) {
			try {
				System.out.println(getTime() + "가 연결을 기다립니다");
				Socket socket = serverSocket.accept();
				System.out.println(getTime() + socket.getInetAddress() + "로부터 연결됨.");

				//
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				pw.write("hi client");
				pw.flush();
				pw.close();
				socket.close();
				System.out.println(socket.getPort()+"is close");
			} catch (IOException e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}
	}

	static String getTime() {
		String name = Thread.currentThread().getName();
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date()) + name;
	}
	
	public static void main(String[] args) {
		System.out.println("test");
		TcpThreadServer server = new TcpThreadServer(5);
		server.start();
	}
}