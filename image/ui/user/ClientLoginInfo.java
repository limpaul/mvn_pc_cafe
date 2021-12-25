package com.program.pc_cafe.image.ui.user;

import javax.swing.*;
import javax.swing.plaf.synth.SynthSplitPaneUI;

import com.program.pc_cafe.dao.UserDAO;
import com.program.pc_cafe.dto.UserDTO;
import com.program.pc_cafe.main.UserLogin;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientLoginInfo extends JFrame {
	private JButton move, menuImage, shutdown, order, delayTime, call, message, moneyInfo, imger;
	private JLabel number, id, money, startTime, usingTime, hotMenu, remainTime;
	private JLabel dbId, dbamountOfMoney, dbStartOfTime, dbUsingTime, dbRemainTime;
	private JButton internet, lol, overWatch, bettleGround;
	private JLabel board, board2, board3;
	UserDTO getDTO = null;
	Date today = new Date();
	SimpleDateFormat time = new SimpleDateFormat("hh시mm분");
	int num;
	// 자리번호,아이디,사용요금,시작시간,사용시간

	// 제어 서버구문
	ServerSocket controlServerSocket;
	Socket controlSocket;
	BufferedReader br;
	PrintWriter pw;
	String data;

	public ClientLoginInfo(String id,int num) {
		this.num=num;
		getDTO = UserDAO.clientInfo(id);
		JPanel panel = new JPanel();
		setTitle("회원정보");
		setResizable(false);
		setLocation(800, 200);
		setSize(420, 700);
		ClientLoginInfoModel(panel,num);
		add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		Runnable r = new ClientServerThread(getDTO.getUserId());
		Thread t = new Thread(r);
		t.start();
	}

	public void ClientLoginInfoModel(JPanel panel,int num) {

		panel.setLayout(null);

		number = new JLabel("자리번호"+num);
		number.setBounds(20, 20, 100, 20);
		panel.add(number);

		move = new JButton("자리이동");
		move.setBounds(100, 20, 90, 20);
		panel.add(move);

		board = new JLabel(
				"-------------------------------------------------------------------------------------------------------");
		board.setBounds(0, 40, 1000, 10);
		panel.add(board);

		id = new JLabel("아 이 디");
		id.setBounds(20, 60, 90, 20);
		panel.add(id);

		dbId = new JLabel(getDTO.getUserId());// db로부터아이디
		dbId.setBounds(120, 60, 90, 20);
		panel.add(dbId);

		money = new JLabel("사용요금");
		money.setBounds(20, 90, 90, 20);
		panel.add(money);

		dbamountOfMoney = new JLabel(String.valueOf(getDTO.getUserTimeDto().getMountOfMoney() + "원"));
		dbamountOfMoney.setBounds(120, 90, 90, 20);
		panel.add(dbamountOfMoney);

		startTime = new JLabel("시작시간");
		startTime.setBounds(20, 120, 90, 20);
		panel.add(startTime);

		dbStartOfTime = new JLabel(time.format(today));
		dbStartOfTime.setBounds(120, 120, 90, 20);
		panel.add(dbStartOfTime);

		usingTime = new JLabel("사용시간");
		usingTime.setBounds(20, 150, 100, 20);
		panel.add(usingTime);

		dbUsingTime = new JLabel("1시간");
		dbUsingTime.setBounds(120, 150, 100, 20);
		panel.add(dbUsingTime);

		remainTime = new JLabel("남은시간");
		remainTime.setBounds(20, 180, 100, 20);
		panel.add(remainTime);

		dbRemainTime = new JLabel(String.valueOf(getDTO.getUserTimeDto().getRemainTime() + "분"));
		dbRemainTime.setBounds(120, 180, 100, 20);
		panel.add(dbRemainTime);

		imger = new JButton(new ImageIcon("pic.png"));
		imger.setBounds(300, 50, 110, 70);
		panel.add(imger);

		shutdown = new JButton("사용종료");
		shutdown.setBounds(300, 125, 110, 70);
		panel.add(shutdown);

		order = new JButton("상품주문");
		order.setBounds(0, 200, 90, 55);
		panel.add(order);

		delayTime = new JButton("선불연장");
		delayTime.setBounds(90, 200, 90, 55);
		panel.add(delayTime);

		call = new JButton("호출");
		call.setBounds(180, 200, 60, 55);
		panel.add(call);

		message = new JButton("메세지");
		message.setBounds(240, 200, 80, 55);
		panel.add(message);
		message.addActionListener(new ClientLoginInfoEvent());

		moneyInfo = new JButton("요금정보");
		moneyInfo.setBounds(320, 200, 90, 55);
		panel.add(moneyInfo);

		board2 = new JLabel(
				"-------------------------------------------------------------------------------------------------------");
		board2.setBounds(0, 220, 1000, 100);
		panel.add(board2);

		Font f = new Font("굴림", Font.BOLD, 20);
		hotMenu = new JLabel("인 기 종 목");
		hotMenu.setFont(f);
		hotMenu.setBounds(150, 250, 1000, 100);
		panel.add(hotMenu);

		board3 = new JLabel(
				"-------------------------------------------------------------------------------------------------------");
		board3.setBounds(0, 280, 1000, 100);
		panel.add(board3);

		internet = new JButton("인터넷",new ImageIcon("chrome.jpg"));
		internet.setBounds(0, 335, 100, 100);
		panel.add(internet);
		internet.addActionListener(new ClientLoginInfoEvent());
		lol = new JButton("LOL",new ImageIcon("lol.jpg"));
		lol.setBounds(100, 335, 100, 100);
		panel.add(lol);
		lol.addActionListener(new ClientLoginInfoEvent());

		overWatch = new JButton("오버워치",new ImageIcon("overwatch.jpg"));
		overWatch.setBounds(200, 335, 100, 100);
		panel.add(overWatch);
		overWatch.addActionListener(new ClientLoginInfoEvent());

		bettleGround = new JButton("배틀그라운드",new ImageIcon("bettleground.png"));
		bettleGround.setBounds(300, 335, 120, 100);
		panel.add(bettleGround);
		bettleGround.addActionListener(new ClientLoginInfoEvent());
		ClientLoginInfoEvent infoEven = new ClientLoginInfoEvent();

		shutdown.addActionListener(infoEven);
		order.addActionListener(infoEven);

	}

	class ClientServerThread implements Runnable {
		private String id;

		public ClientServerThread(String id) {
			this.id=id;
	
		}
		public void run() { // 다른 쓰레드로 돌린다 왜냐하면 GUI 가 나오질안아서 ..
			// --------------------------------------------------------------------------------
			// 서버구문
			// 제어 서버 구문
			
			try {
				controlServerSocket = new ServerSocket(3333);
				while (true) {
					
					System.out.println("클라이언트 제어 실행중 ");
					controlSocket = controlServerSocket.accept();
					System.out.println(
							"클라이언트 접속완료" + "ip:" + controlSocket.getInetAddress() + "port:" + controlSocket.getPort());
					br = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));// 수신스트림 생성
					pw = new PrintWriter(new OutputStreamWriter(controlSocket.getOutputStream()));// 출력스트림 생성
					while ((data = br.readLine()) != null) {
						int sum=0;
						String[] rData=data.split("/");
						if(rData[0].equals("동기화") && rData[1].equals("시간추가") && rData[2].equals(id)) {
							int addTime=Integer.parseInt(rData[3]);
							String dbStringTime=dbRemainTime.getText().replaceAll("분", "");
							int dbNumTime=Integer.parseInt(dbStringTime);
							sum+=dbNumTime+addTime;
							dbRemainTime.setText(sum+"분");
							getDTO = UserDAO.clientInfo(id);
						}
						if(rData[0].equals("동기화") && rData[1].equals("시간감소") && rData[2].equals(id)) {
							int minusTime=Integer.parseInt(rData[3]);
							String dbStringTime=dbRemainTime.getText().replaceAll("분", "");
							int dbNumTime=Integer.parseInt(dbStringTime);
							if(dbNumTime<=0) {
								System.out.println("종료됨");
								dispose();
								new UserLogin();
								controlSocket.close();
								controlServerSocket.close();
								br.close();
								pw.close();
							}
							sum+=dbNumTime-minusTime;
							dbRemainTime.setText(sum+"분");
							getDTO = UserDAO.clientInfo(id);
						
						}
								
						
						if (data.equals("shutdown")) {// 또는 userAdd/아이디/비밀번호/이름 이런식으로 데이터를입력받는다
							dispose();
							new UserLogin();
							controlSocket.close();
							controlServerSocket.close();
							br.close();
							pw.close();
						}

					}

				}

			} catch (Exception shutdown) {
				// TODO: handle exception
			}
		}
	}
	class ClientLoginInfoEvent implements ActionListener {

		
		
		@Override
		public  void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd = e.getActionCommand();
			if (cmd.equals("사용종료")) {
				new UserLogin();
			}
			if (cmd.equals("상품주문")) {
				new OrderGood(ClientLoginInfo.this,num+"번");
			}
			if (cmd.equals("LOL")) {
				new GameView("LOL");
			}
			if (cmd.equals("오버워치")) {
				new GameView("오버워치");
			}
			if (cmd.equals("인터넷")) {
				new GameView("인터넷");
			}
			if (cmd.equals("배틀그라운드")) {
				new GameView("배틀그라운드");
			}
			if(cmd.equals("메세지")) {
				new ClientChat(ClientLoginInfo.this);
			}

		}
	}

}