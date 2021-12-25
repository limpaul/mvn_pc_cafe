package com.program.pc_cafe.thread;
import java.net.*;
import java.io.*;

public class TcpThreadClient implements Runnable{
	Thread threadArr[];
	Socket socket;
	public static void main(String[] args) {
		TcpThreadClient th = new TcpThreadClient(10);
		th.start();
	}
	public TcpThreadClient(int num) {
		try {
			threadArr = new Thread[num];
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void run() {
		try {
			socket = new Socket("127.0.0.1", 7778);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(br.readLine());
			String data;
			while((data =br.readLine())!=null) {
				System.out.println(data);
			}
			br.close();
			socket.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void start() {
		for(int i =0 ; i< threadArr.length; i++) {
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}
	}
	
}