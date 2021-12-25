package com.program.pc_cafe.image.ui.user;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

import com.program.pc_cafe.dao.UserDAO;
import com.program.pc_cafe.dto.UserDTO;


public class UserAdd extends JDialog {
	JTextField addressText, textId, nameText, emailText, phoneText1;
	JPasswordField textPw, textRpw;
	private JScrollPane scrollPane;
	private Font f1;
	private JButton btnOk, btnCancel, btnIdChk;

	// 서버 전역 필드
	Socket cSocket;
	BufferedReader br;
	PrintWriter pw;
	String data;

	public UserAdd(JFrame frame) {
		super(frame, true);
		
		// 배경 Panel 생성후 contentPane 으로 지정
		Container container = getContentPane();
		JPanel background = new JPanel();
		container.add(background);

		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("회원가입");
		setSize(480, 450);
		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
		////////////////////////// 백그라운드 이미지 삽입하였다
		userAddModel(background);
		setResizable(false);
		setLocation(500, 300);
		setVisible(true);
	}

	public void userAddModel(JPanel panel) {

		panel.setLayout(new BorderLayout());

		f1 = new Font("돋움", Font.BOLD, 30);
		JLabel logo = new JLabel("회 원 가 입");
		logo.setFont(f1);
		labelCenter(logo);
		panel.add(logo, "North");

		JPanel sub_panel = new JPanel();
		sub_panel.setLayout(new GridLayout(8, 3));
		JLabel id = new JLabel("아  이  디");
		labelCenter(id);
		sub_panel.add(id);

		textId = new JTextField();
		sub_panel.add(textId);

		btnIdChk = new JButton("아이디중복확인");
		sub_panel.add(btnIdChk);

		JLabel pw = new JLabel("비밀번호");
		labelCenter(pw);
		sub_panel.add(pw);
		textPw = new JPasswordField();
		sub_panel.add(textPw);
		JLabel blank = new JLabel("");
		sub_panel.add(blank);

		JLabel rpw = new JLabel("비밀번호확인");
		labelCenter(rpw);
		sub_panel.add(rpw);
		textRpw = new JPasswordField();
		sub_panel.add(textRpw);
		JLabel blank1 = new JLabel("");
		sub_panel.add(blank1);

		JLabel name = new JLabel("이 름");
		labelCenter(name);
		sub_panel.add(name);
		nameText = new JTextField();
		sub_panel.add(nameText);
		JLabel blank3 = new JLabel("");
		sub_panel.add(blank3);
		nameText.addKeyListener(new UserKeyEvent());

		JLabel eMail = new JLabel("이 메 일");
		labelCenter(eMail);
		sub_panel.add(eMail);
		emailText = new JTextField();
		sub_panel.add(emailText);
		JLabel blank4 = new JLabel("");
		sub_panel.add(blank4);

		JLabel phone = new JLabel("핸 드 폰");
		labelCenter(phone);
		sub_panel.add(phone);
		phoneText1 = new JTextField();
		phoneText1.addKeyListener(new UserNumber());
		JLabel alert = new JLabel("-을 제외하고 입력");
		labelCenter(alert);
		sub_panel.add(phoneText1);
		sub_panel.add(alert);

		JLabel address = new JLabel("주 소");
		labelCenter(address);
		sub_panel.add(address);
		addressText = new JTextField();
		sub_panel.add(addressText);
		JLabel blank5 = new JLabel("");
		sub_panel.add(blank5);

		btnOk = new JButton("가입하기");
		sub_panel.add(btnOk);
		btnCancel = new JButton("가입취소");
		sub_panel.add(btnCancel);
		JLabel blank6 = new JLabel("");
		sub_panel.add(blank6);
		panel.add(sub_panel, "Center");

		JPanel sub_panel3 = new JPanel();
		sub_panel3.setLayout(new FlowLayout());
		sub_panel3.add(btnOk);
		sub_panel3.add(btnCancel);
		panel.add(sub_panel3, "South");

		UserEvent userEvent = new UserEvent();
		btnOk.addActionListener(userEvent);
		btnCancel.addActionListener(userEvent);
		btnIdChk.addActionListener(userEvent);
	}

	public void labelCenter(JLabel label) {
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
	}

	//////////////////////////////////// 이벤트 정의 부분

	private class UserEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd = e.getActionCommand();
			String id = textId.getText();
			char[] sPw = textPw.getPassword();
			String pwd = new String(sPw);
			char[] rPw = textRpw.getPassword();
			String rPwd = new String(rPw);
			String name = nameText.getText();
			String email = emailText.getText();
			String phoneNumber = phoneText1.getText();
			String address = addressText.getText();
			if (cmd.equals("가입하기")) {

				if (id.equals("")) {
					JOptionPane.showMessageDialog(null, "아이디가 빈칸입니다");
				} else if (pwd.equals("")) {
					JOptionPane.showMessageDialog(null, "페스워드란이 빈칸입니다");
				} else if (!pwd.equals(rPwd)) {
					JOptionPane.showMessageDialog(null, "페스워드가 같지 않습니다");
				} else if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "이름란이 빈칸입니다");
				} else if (email.equals("")) {
					JOptionPane.showMessageDialog(null, "이메일란이 빈칸입니다");
				} else if (phoneNumber.equals("")) {
					JOptionPane.showMessageDialog(null, "휴대폰란이 빈칸입니다");
				} else if (address.equals("")) {
					JOptionPane.showMessageDialog(null, "주소란이 빈칸입니다");
				} else {
					if (sendVerify(id) == false
							&& sendData(new UserDTO(id, pwd, name, email, phoneNumber, address)) == true) {
						JOptionPane.showMessageDialog(null, "가입성공!");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "아이디가 중복되었습니다");
					}
				}
			}
			if (cmd.equals("아이디중복확인")) {
				if (UserDAO.verfyID(id) == false) {
					JOptionPane.showMessageDialog(null, "사용할수 있는 아이디 입니다");

				} else {
					JOptionPane.showMessageDialog(null, "사용불가능한 아이디입니다");
				}
			}
			if (cmd.equals("가입취소")) {
				dispose();
			}
		}
//---------------------------------------클라이언트 구문 ------------------------------------------
		public boolean sendData(UserDTO userDTO) { // 지역 UserDTO 로 수정

			try {
				cSocket = new Socket("127.0.0.1", 9999);
				br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// 수신스트림 생성
				pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// 출력스트림 생성
				pw.println("userAdd/" + userDTO.getUserId() + "/" + userDTO.getUserPw() + "/" + userDTO.getUserName() + "/"
						+ userDTO.getUserEmail() + "/" + userDTO.getUserPhoneNumber() + "/" + userDTO.getUserAddress());
				pw.flush();
				while ((data = br.readLine()) != null) {
					if (data.equals("가입성공")) {
						return true;
					} else if (data.equals("가입실패")) {
						return false;
					} else {
						return false;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "서버 연결실패", "경우", JOptionPane.WARNING_MESSAGE);
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

			return false;
		}

		public boolean sendVerify(String id) { // 지역 UserDTO 로 수정

			try {
				cSocket = new Socket("127.0.0.1", 9999);
				br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// 수신스트림 생성
				pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// 출력스트림 생성
				pw.println("userVerify/" + id);			
				pw.flush();
				while ((data = br.readLine()) != null) {
					if (data.equals("중복아님")) {
						return false;
					} else if (data.equals("중복")) {
						return true;
					} else {
						return true;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "서버 연결실패", "경우", JOptionPane.WARNING_MESSAGE);
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

			return true;
		}
	}
//----------------------------------------------------------Key 이벤트 ------------------------------
	class UserKeyEvent extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			char value = e.getKeyChar();
			System.out.println(value);
			if (!(value < '0' || value > '9')) {
				JOptionPane.showMessageDialog(null, "한글 또는 이름 입력", "경고", JOptionPane.WARNING_MESSAGE);
				e.consume();
			}

		}

	}

	class UserNumber extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			char value = e.getKeyChar();
			int expression = e.getKeyCode();
			System.out.println(expression);

			if ((value < '0' || value > '9')) {
				JOptionPane.showMessageDialog(null, "숫자만 입력", "경고", JOptionPane.WARNING_MESSAGE);
				e.consume();
			}

		}

	}

}