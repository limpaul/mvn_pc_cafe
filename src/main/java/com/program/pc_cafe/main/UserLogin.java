package com.program.pc_cafe.main;

import javax.swing.*;

import com.program.pc_cafe.image.ui.user.ClientLoginInfo;
import com.program.pc_cafe.image.ui.user.SearchInfo;
import com.program.pc_cafe.image.ui.user.UserAdd;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class UserLogin extends JFrame {
	private JButton btnLogin, btnSearch, btnAdd, btnOff, btnRestart;
	private JPasswordField passText;
	private JTextField userText, numberText;
	private JTextArea noticeArea;
	private ButtonGroup bgp = new ButtonGroup();
	private JRadioButton r; // radio 버튼추가
	private JButton serverInfo;
	private int rValue; // radioButton 값 자리번호를 뜻한다
	// 서버 상태
	boolean serverStatus;// 서버 연결상태
	InetAddress serverIp;// 서버 아이피

	// 클라이언트 필드
	Socket cSocket;
	BufferedReader br;
	PrintWriter pw;
	String data;

	public UserLogin() {
		super("로그인창");

		Container contain = getContentPane();
		JPanel p = new JPanel();
		for (int i = 0; i < 16; i++) {
			r = new JRadioButton((i + 1) + "번");
			r.addActionListener(new JRadioEvent());
			bgp.add(r);
			p.add(r);
		}
		serverInfo = new JButton("서버상태확인");
		p.add(serverInfo);
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		centerPanel(p1);
		SouthPanel(p2);

		contain.add(p, "North");
		contain.add(p1, "Center");
		contain.add(p2, "South");

		LoginEvent loginEvent = new LoginEvent();
		serverInfo.addActionListener(loginEvent);
		btnLogin.addActionListener(loginEvent);
		btnAdd.addActionListener(loginEvent);
		btnOff.addActionListener(loginEvent);
		btnRestart.addActionListener(loginEvent);
		btnSearch.addActionListener(loginEvent);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 800);
		// setUndecorated(true);
		setResizable(true);
		setVisible(true);

	}

	public void centerPanel(JPanel panel) {
		JLabel label = new JLabel(new ImageIcon("image/system_image/loginBackGround.jpg"));
		panel.add(label);
	}

	public void SouthPanel(JPanel panel) {
		panel.setLayout(new BorderLayout());

		JPanel loginAllPanel = new JPanel(new GridLayout(1, 5, 5, 5));
		loginAllPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		///
		loginAllPanel.add(new JLabel(" "));
		loginAllPanel.add(new JLabel(" "));
		loginAllPanel.add(new JLabel(" "));
		///

		JPanel login_panel = new JPanel(new GridLayout(3, 2, 5, 2));

		JLabel userLabel = new JLabel("아이디");
		JLabel pwLabel = new JLabel("비밀번호");
		JLabel numberLabel = new JLabel("비회원(카드번호)");
		userText = new JTextField(40);
		passText = new JPasswordField(40);
		numberText = new JTextField(40);

		login_panel.add(userLabel);
		login_panel.add(userText);
		login_panel.add(pwLabel);
		login_panel.add(passText);
		login_panel.add(numberLabel);
		login_panel.add(numberText);

		labelCenter(userLabel, pwLabel, numberLabel);

		loginAllPanel.add(login_panel);

		btnLogin = new JButton("로그인");
		loginAllPanel.add(btnLogin);

		JPanel addOption = new JPanel(new GridLayout(4, 1, 5, 5));
		btnAdd = new JButton("회원가입");
		btnSearch = new JButton("ID/PW찾기");
		btnOff = new JButton("전원끄기");
		btnRestart = new JButton("다시시작");
		addOption.add(btnAdd);
		addOption.add(btnSearch);
		addOption.add(btnOff);
		addOption.add(btnRestart);
		loginAllPanel.add(addOption);

		btnOff.addActionListener(new LoginEvent());
		noticeArea = new JTextArea();
		noticeArea.setText("급식 여러분들 샷건금지,욕금지");
		loginAllPanel.add(noticeArea);

		// 전체 페널을 업로드 시켜주는데
		JLabel text = new JLabel(" ");
		panel.add(text, "West");
		panel.add(loginAllPanel, "Center");

	}

	public void labelCenter(JLabel label1, JLabel label2, JLabel label3) {
		label1.setVerticalAlignment(SwingConstants.CENTER);
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setVerticalAlignment(SwingConstants.CENTER);
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	public class LoginEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String id = userText.getText();
			char[] chpw = passText.getPassword(); // 문자열변호나
			String pwd = new String(chpw);
			// String number = numberText.getText();
			String cmd = e.getActionCommand();
			int receive = 3;
			if (cmd.equals("서버상태확인")) {
				pingTest();
				if (serverStatus == true) {
					JOptionPane.showMessageDialog(null, "서버상태: 양호\n");
				} else {
					JOptionPane.showMessageDialog(null, "서버 연결 되지 않음", "경고", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				receive = sendData(id, pwd);

				if (receive == 1) {// 서버로 데이터를 전송!! 하고 데이터를 받는다
					dispose();
					new ClientLoginInfo(id, rValue);
				} else if (receive == 2) {
					JOptionPane.showMessageDialog(null, "카운터 에서 충전 하시길 바랍니다");
				} else {
					userText.setText("");
					passText.setText("");
					numberText.setText("");
				}

				if (cmd.equals("회원가입")) {
					new UserAdd(UserLogin.this);

				}
				if (cmd.equals("ID/PW찾기")) {
					new SearchInfo(UserLogin.this);
				}
				if (cmd.equals("전원끄기")) {
					JOptionPane.showMessageDialog(null, "전원종료됨!");
					System.exit(0);
				}
				if (cmd.equals("다시시작")) {
					JOptionPane.showMessageDialog(null, "다시시작!");
					setVisible(false);
					setVisible(true);
				}
			}

		}

		// =========================클라이언트 구문================================
		public boolean pingTest() {
			Socket ping = null;
			PrintWriter write = null;
			BufferedReader receive = null;
			try {
				ping = new Socket("127.0.0.1", 9999);
				write = new PrintWriter(new OutputStreamWriter(ping.getOutputStream()));
				receive = new BufferedReader(new InputStreamReader(ping.getInputStream()));
				write.println("ping");
				write.flush();
				String data;
				while((data = receive.readLine())!=null) {
					//System.out.println("test");
					if(data.equals("ping")) 
						serverStatus = true;
						
					else
						serverStatus = false;
						
					break;
				}
			}catch (Exception e) {
				System.out.println("server ping test fail");
			}finally {
				try {
					write.close();
					receive.close();
					ping.close();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			return false;
		}
		public int sendData(String id, String pwd) {
			System.out.println("아이디 비번 전송");
			if (rValue == 0) {
				JOptionPane.showMessageDialog(null, "자리를 선택해주세요", "경고", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					cSocket = new Socket("127.0.0.1", 9999);
					br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// 수신스트림 생성
					pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// 출력스트림 생성

					pw.println("login/" + id + "/" + pwd + "/" + rValue);// 4번째 문자열에 pc번호를 입력받음
					pw.flush();
					while ((data = br.readLine()) != null) {
						if (data.equals("로그인성공")) {
							return 1;
						} else if (data.equals("카운터 에서 충전 하시길바랍니다")) {
							return 2;
						} else if (data.equals("로그인실패")) {
							return 3;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "서버 연결실패", "경우", JOptionPane.WARNING_MESSAGE);
				} finally {
					try {
						System.out.println("로그인 접속 정보 종료");
						pw.close();
						br.close();
						cSocket.close();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("세션이 종료되지 못하였습니다");
					}
				}

			}
			return 3;
		}

	}

	class JRadioEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JRadioButton btn = (JRadioButton) e.getSource();
			rValue = Integer.parseInt(btn.getText().replaceAll("번", ""));

		}
	}

	// ----------------------------------------------------------------------
	public static void main(String[] args) {
		new UserLogin();
	}
}