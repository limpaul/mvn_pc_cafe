package com.program.pc_cafe.image.ui.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

public class SearchInfo extends JDialog implements ActionListener { // 아이디랑 비밀번호를 찾는소재로 시스템을 구성함
	private ImageIcon icon;
	private JScrollPane scrollPane;
	private JButton okButton, cancelButton;
	private JTextField nameField, emailField;
	Socket cSocket =null;
	BufferedReader br = null;
	PrintWriter pw=null;
	String data=null;
	public SearchInfo(JFrame frame) {
		super(frame, "회원검색", true);
		icon = new ImageIcon("3.png");

		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
		searchInfoModel(background);
		setSize(400, 300);
		setVisible(true);
	}

	public void searchInfoModel(JPanel panel) {
		panel.setLayout(null);
		setTitle("회원 조회");
		Font font = new Font("돋움", Font.BOLD, 20);
		JLabel logo = new JLabel("이메일로 인증하기");
		logo.setFont(font);
		logo.setBounds(90, 5, 300, 80);
		panel.add(logo);

		JLabel name = new JLabel("이름");
		name.setBounds(90, 80, 50, 80);
		panel.add(name);

		JLabel email = new JLabel("이메일");
		email.setBounds(90, 110, 50, 80);
		panel.add(email);

		nameField = new JTextField(20);
		nameField.setBounds(140, 110, 100, 20);
		panel.add(nameField);

		emailField = new JTextField(20);
		emailField.setBounds(140, 140, 100, 20);
		panel.add(emailField);

		okButton = new JButton("확인");
		okButton.setBounds(100, 180, 100, 20);
		panel.add(okButton);

		cancelButton = new JButton("취소");
		cancelButton.setBounds(200, 180, 100, 20);
		panel.add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		String name = nameField.getText();
		String email = emailField.getText();
		if (cmd.equals("확인")) {
			// JOptionPane.showMessageDialog(null, UserDAO.idPw(name, email));
			try {
				cSocket = new Socket("127.0.0.1", 9999);
				br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// 수신스트림 생성
				pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// 출력스트림 생성
				pw.println("userSearch/" + name + "/" + email );// 4번째 문자열에 pc번호를 입력받음
				pw.flush();
				
				while ((data = br.readLine()) != null) {
					String arr[]=data.split("/");
					if(arr[0].equals("조회성공")) {
						nameField.setText("");
						emailField.setText("");
						JOptionPane.showMessageDialog(null, arr[1]+"\n"+arr[2]);
						
					}else if(arr[0].equals("조회실패")){
						nameField.setText("");
						emailField.setText("");	
						JOptionPane.showMessageDialog(null, "존재 하지 않습니다", "그런거 없음", JOptionPane.WARNING_MESSAGE);
											
					}
					
					break;
				}
				
				
			} catch (Exception ex) {
				// TODO: handle exception
				System.out.println(ex);
				JOptionPane.showMessageDialog(null, "서버 연결실패", "경우", JOptionPane.WARNING_MESSAGE);
			}finally {
				try {
					br.close();
					pw.close();
					cSocket.close();
				}catch (Exception e23) {
					// TODO: handle exception
				}
			}

		}
		if (cmd.equals("취소"))

		{
			dispose();
		}

	}

}