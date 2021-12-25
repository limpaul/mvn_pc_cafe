package com.program.pc_cafe.image.ui.user;

import java.io.*;
import java.net.*;

import javax.swing.*;
 
public class ServerControl implements Runnable{ //내용 서버가 Client 가 되어 Client 에게 명령을 할수 있도록 해준다 3333 번 제어포트 
	// 클라이언트 필드
		Socket cSocket;
		BufferedReader br;
		PrintWriter pw;
		String data;
	public ServerControl(JButton[] btn,String text) {
		for(int i=0;i<btn.length;i++) {
			if(btn[i].getText().equals(text)) {
				btn[i].setIcon(null);
				btn[i].setText("사용가능");
			}
		}
	}
	
	
	// =========================클라이언트 구문================================
	public void run() {
		try {
			cSocket = new Socket("127.0.0.1", 3333);
			br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// 수신스트림 생성
			pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// 출력스트림 생성
			pw.println("shutdown");//4번째 문자열에 pc번호를 입력받음
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