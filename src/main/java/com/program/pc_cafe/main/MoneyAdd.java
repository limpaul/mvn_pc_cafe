package com.program.pc_cafe.main;


import javax.swing.*;

import com.program.pc_cafe.dao.UserDAO;
import com.program.pc_cafe.image.ui.user.UserAdd;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoneyAdd extends JFrame {
	JTabbedPane tPane = new JTabbedPane();
	JButton btn1 = new JButton();
	JLabel logo;
	int money=0;
	JButton leastMoney;
	public MoneyAdd() {
		Container contain = getContentPane();// 북쪽,중간
		JPanel nPanel = new JPanel();
		nPanel.setLayout(new GridLayout(2, 1));

		JLabel lastSeatInfo = new JLabel("저희 캡스톤 PC방에 오신걸 환영합니다  사용좌석은 12석 빈좌석은 12 석");
		JLabel logo = new JLabel("안녕하세요. 사용할 요금제를 선택하세요");
		labelCenter(lastSeatInfo);
		labelCenter(logo);
		nPanel.add(lastSeatInfo);
		nPanel.add(logo);

		JPanel p = new JPanel();// 1행 3열
		p.setLayout(new GridLayout(1, 3));
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();

		leftDesign(p1);
		centerDesign(p2);
		rightDesign(p3);
		p.add(p1);
		p.add(p2);
		p.add(p3);

		contain.add(nPanel, "North");
		contain.add(p, "Center");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1400, 600);
		setResizable(true);
		setVisible(true);
	}

	public void leftDesign(JPanel left) {
		MoneyAddEvent mEvent=new MoneyAddEvent();
		tPane.setTabPlacement(JTabbedPane.TOP);
		left.add(tPane);
		
		JPanel sub_panel_user = new JPanel();
		for (int i = 1; i <= 10; i++) {
			JButton btn = new JButton(userMoneyChange(i));
			sub_panel_user.add(btn);
			btn.addActionListener(mEvent);
		}
		JPanel sub_panel_sUser = new JPanel();
		for (int i = 1; i <= 10; i++) {
			JButton btn1 = new JButton(studentMoneyChange(i));
			sub_panel_sUser.add(btn1);
			btn1.addActionListener(mEvent);
		}
		JPanel sub_panel_bUser = new JPanel();
		for (int i = 1; i <= 10; i++) {
			JButton btn2 = new JButton(bUserMoneyChange(i));
			sub_panel_bUser.add(btn2);
			btn2.addActionListener(mEvent);
		}
		
		sub_panel_user.setLayout(new GridLayout(5, 2,10,10));
		sub_panel_sUser.setLayout(new GridLayout(5, 2,10,10));
		sub_panel_bUser.setLayout(new GridLayout(5, 2,10,10));
		tPane.add(" 회원 ", sub_panel_user);
		tPane.add(" 학생 ", sub_panel_sUser);
		tPane.add(" 비회원 ", sub_panel_bUser);
	}

	public void centerDesign(JPanel center) {
		MoneyAddEvent mEvent=new MoneyAddEvent();
		center.setLayout(new BorderLayout());
		JPanel sub_panel=new JPanel();
		sub_panel.setLayout(new GridLayout(5, 2,10,10));
		sub_panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		String[] arr= {"현금","문화상품권","도서문화상품권","틴캐시","온캐시","구글기프트","해피머니","에그머니","퍼니카드","아프리카TV별풍선"};
		for(int i=0;i<arr.length;i++) {
			JButton btn=new JButton(arr[i]);
			btn.addActionListener(mEvent);
			sub_panel.add(btn);
			center.add(sub_panel, "Center");
		}
		 logo=new JLabel("결제 수단 . 현재 요금 : 0원");
		labelCenter(logo);
		center.add(logo, "North");
	
	}

	public void rightDesign(JPanel right) {
		right.setLayout(new GridLayout(3,1));
		JPanel sub_panel=new JPanel();
		sub_panel.setLayout(new GridLayout(2, 1));
		sub_panel.add(new JLabel(new ImageIcon("howto.PNG")));
		
		
		JPanel sub_panel_Center=new JPanel();
		sub_panel_Center.setLayout(new GridLayout(2, 3,5,5));
		String[] arr= {"회원가입","비회원충전","좌석보기","신용카드","T머니카드","캐시비카드"};
		for(int i=0;i<arr.length;i++) {
			JButton btn=new JButton(arr[i]);
			sub_panel_Center.add(btn);
			sub_panel.add(sub_panel_Center);
			btn.addActionListener(new MoneyAddEvent());
		}
		
		JPanel sub_panel_South=new JPanel();
		leastMoney=new JButton("잔돈반환");
		leastMoney.addActionListener(new MoneyAddEvent());
		sub_panel_South.add(leastMoney);
		right.add(sub_panel_South);
		right.add(sub_panel);
		right.add(sub_panel_Center);
		right.add(sub_panel_South);
		
		
	}

	public String userMoneyChange(int time) {
		String returnValue = "";
		if (time == 1) {
			return "쿠폰 1,000원 01:00";
		}
		if (time == 2) {
			return "쿠폰 30,000원 30:00";
		}
		if (time == 3) {
			return "쿠폰 2,000원 02:00";
		}
		if (time == 4) {
			return "쿠폰 50,000원 50:00";
		}
		if (time == 5) {
			return "쿠폰 3,000원 03:00";
		}
		if (time == 7) {
			return "쿠폰 5,000 05:00";
		}
		if(time==9) {
			return "쿠폰 10,000 10:00";
		}
		if(time==6||time==8||time==10) {
			returnValue="";
		}
		return returnValue;
	}
	public String studentMoneyChange(int time) {
		String returnValue = "";
		if (time == 1) {
			return "쿠폰 1,000원 02:00";
		}
		if (time == 2) {
			return "쿠폰 30,000원 60:00";
		}
		if (time == 3) {
			return "쿠폰 2,000원 04:00원";
		}
		if (time == 4) {
			return "쿠폰 50,000원 100:00";
		}
		if (time == 5) {
			return "쿠폰 3,000원 06:00";
		}
		if (time == 7) {
			return "쿠폰 5,000 10:00";
		}
		if(time==9) {
			return "쿠폰 10,000 20:00";
		}
		if(time==6||time==8||time==10) {
			returnValue="";
		}
		return returnValue;
	}
	public String bUserMoneyChange(int time) {
		String returnValue = "";
		if (time == 1) {
			return "쿠폰 1,000원 00:30";
		}
		if (time == 2) {
			return "쿠폰 30,000원 15:00";
		}
		if (time == 3) {
			return "쿠폰 2,000원 01:00원";
		}
		if (time == 4) {
			return "쿠폰 50,000원 25:00";
		}
		if (time == 5) {
			return "쿠폰 3,000원 01:30";
		}
		if (time == 7) {
			return "쿠폰 5,000 02:30";
		}
		if(time==9) {
			return "쿠폰 10,000 05:00";
		}
		if(time==6||time==8||time==10) {
			returnValue="";
		}
		return returnValue;
	}
	public void labelCenter(JLabel label) {
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Serif", Font.BOLD, 30));
		label.setForeground(Color.BLACK);

	}
	
	
	class MoneyAddEvent implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			String cmd=e.getActionCommand();
			if(cmd.equals("현금")) {
				String money=JOptionPane.showInputDialog(null, "현금 입력");
				changeMoney(money);
			}
			if(cmd.equals("쿠폰 1,000원 01:00")) {
				if(money!=0) {
					String dbId=JOptionPane.showInputDialog(null, "아이디를 입력해주세요");
					if(UserDAO.addTime(dbId, 60, 1000)==true) {
						JOptionPane.showMessageDialog(null, "충전이 완료되었습니다 즐거운 시간 되세요");
						money-=1000;
						logo.setText("결제 수단 . 현재 요금 : "+money+"원");
					}else {
						JOptionPane.showMessageDialog(null, "아이디가 올바르지 않습니다");
					}
					//db로 부터 시간을 추가해준다
				}else {
					JOptionPane.showMessageDialog(null, "돈을 넣어주세요");
				}
				
			}
			if(cmd.equals("쿠폰 2,000원 02:00")) {
				System.out.println("2");
			}
			if(cmd.equals("잔돈반환")) {
				money=0;
				logo.setText("결제 수단 . 현재 요금 : "+money+"원");
			}
			if(cmd.equals("회원가입")) {
				new UserAdd(MoneyAdd.this);
			}
		
		}
		public void changeMoney(String omoney) {
			money+=Integer.parseInt(omoney);
			logo.setText("결제 수단 . 현재 요금 : "+money+"원");
			
			
		}
		
	}
	
	public static void main(String[] args) {
		new MoneyAdd();
	}
}