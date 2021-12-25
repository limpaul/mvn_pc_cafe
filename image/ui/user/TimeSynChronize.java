package com.program.pc_cafe.image.ui.user;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class TimeSynChronize  extends Thread{
	private String user;
	private int time;
	private Socket cSocket;
	private BufferedReader br;
	private PrintWriter pw;
	private String update;
	public TimeSynChronize(String user,int time,String update) {
		this.user=user;
		this.time=time;
		this.update=update;
	}
	
	public void run() {
		try {
			cSocket = new Socket("127.0.0.1", 3333);
			br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// 수신스트림 생성
			pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// 출력스트림 생성
			System.out.println("동기화"+"/"+update+"/"+user+"/"+time);
			pw.println("동기화"+"/"+update+"/"+user+"/"+time);//4번째 문자열에 pc번호를 입력받음
			
			pw.flush();
			pw.close();
			br.close();
			cSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "서버 연결실패", "경우", JOptionPane.WARNING_MESSAGE);
			System.out.println("에러내용"+e);
		} finally {
			try {
				pw.close();
				br.close();
				cSocket.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("세션이 종료되지 못하였습니다");
			}
		}
	}
}