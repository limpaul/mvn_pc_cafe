package com.program.pc_cafe.image.ui.user;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.program.pc_cafe.dao.UserDAO;
import com.program.pc_cafe.dto.UserDTO;
import com.program.pc_cafe.dto.UserTimeDto;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

public class UserManage extends JDialog implements ActionListener {
	JTextField idField;
	JTextField id, pwd, name, email, phone, address;
	String[] moneyArr= {"1시간","2시간","3시간","5시간","10시간","24시간"}; 
	JComboBox<String> time=new JComboBox<>(moneyArr);
	JButton btnSearch, btnSearchAll, btnDelete, btnAdd;
	JTable userTable;
	DefaultTableModel model;

	String[] menuArr = { "아이디", "비밀번호", "이름", "이메일", "휴대전화", "주소", "남은시간", "총금액" };
	Vector<UserDTO> userFromDB = UserDAO.userAll();
	Vector<String> userColumn = new Vector<>();
	JScrollPane scroll;

	public UserManage(JFrame frame) {
		super(frame,true);
		setTitle("관리자 회원조회");
		Container c = getContentPane();
		JPanel p1 = new JPanel();
		idField = new JTextField(20);
		btnSearch = new JButton("아이디 검색");
		btnSearchAll = new JButton("전체 검색");
		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(this);
		btnSearch.addActionListener(this);
		btnSearchAll.addActionListener(this);
		btnDelete.addActionListener(this);
		p1.add(idField);
		p1.add(btnSearch);
		p1.add(btnSearchAll);
		p1.add(btnDelete);

		c.add(p1, "North");

		for (int i = 0; i < menuArr.length; i++) {
			userColumn.add(menuArr[i]);
		}
		model = new DefaultTableModel(userColumn, 0);

		for (int i = 0; i < userFromDB.size(); i++) {
			String[] userRow = { userFromDB.get(i).getUserId(), userFromDB.get(i).getUserPw(), userFromDB.get(i).getUserName(),
					userFromDB.get(i).getUserEmail(), userFromDB.get(i).getUserPhoneNumber(), userFromDB.get(i).getUserAddress(),
					Integer.toString(userFromDB.get(i).getUserTimeDto().getRemainTime()),
					Integer.toString(userFromDB.get(i).getUserTimeDto().getMountOfMoney()) };
			model.addRow(userRow);
		}

		userTable = new JTable(model);

		scroll = new JScrollPane(userTable);

		c.add(scroll, "Center");

		JPanel p2 = new JPanel();

		p2.setLayout(new GridLayout(2, 8));
		p2.add(new JLabel("       아이디"));
		p2.add(new JLabel("    비밀번호"));
		p2.add(new JLabel("      이름"));
		p2.add(new JLabel("     이메일"));
		p2.add(new JLabel("핸드폰(-제외)"));
		p2.add(new JLabel("       주소"));
		p2.add(new JLabel("   시간"));
		p2.add(new JLabel(""));
		id = new JTextField();
		pwd = new JTextField();
		name = new JTextField();
		email = new JTextField();
		phone = new JTextField();
		address = new JTextField();
		//시간 추가됨
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		p2.add(id);
		p2.add(pwd);
		p2.add(name);
		p2.add(email);
		p2.add(phone);
		p2.add(address);
		p2.add(time);
		p2.add(btnAdd);

		c.add(p2, "South");
		//////
		setSize(700, 500);
		setResizable(false);
		setVisible(true);
	}

	public void listAll() {
		model.setRowCount(0);// 열을 초기화시켜준다
		Vector<UserDTO> userFromDB = UserDAO.userAll();
		model.setRowCount(0);// 열을 초기화시켜준다
		for (int i = 0; i < userFromDB.size(); i++) {
			String[] userRow = { userFromDB.get(i).getUserId(), userFromDB.get(i).getUserPw(), userFromDB.get(i).getUserName(),
					userFromDB.get(i).getUserEmail(), userFromDB.get(i).getUserPhoneNumber(), userFromDB.get(i).getUserAddress(),
					Integer.toString(userFromDB.get(i).getUserTimeDto().getRemainTime()),
					Integer.toString(userFromDB.get(i).getUserTimeDto().getMountOfMoney()) };
			model.addRow(userRow);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd=e.getActionCommand();
		
		
		if(cmd.equals("아이디 검색")) {
			String getId=idField.getText();
			ArrayList<UserDTO> userInfo= UserDAO.clientAll(getId);
			String id=null;String pwd=null;String name=null;String email=null;
			String phonNumber=null;String address=null;String remainTime=null;String mountToMoney=null;
		
			for(int i=0;i<userInfo.size();i++) {
				id=userInfo.get(i).getUserId();
				pwd=userInfo.get(i).getUserPw();
				name=userInfo.get(i).getUserName();
				email=userInfo.get(i).getUserEmail();
				phonNumber=userInfo.get(i).getUserPhoneNumber();
				address=userInfo.get(i).getUserAddress();
				remainTime=Integer.toString(userInfo.get(i).getUserTimeDto().getRemainTime());
				mountToMoney=Integer.toString(userInfo.get(i).getUserTimeDto().getMountOfMoney());
			}
			model.setRowCount(0);//열을 초기화시켜준다
			for(int i=0;i<userInfo.size();i++) {
				String[] userRow= {id,pwd,name,email,phonNumber,address,remainTime,mountToMoney};
				model.addRow(userRow);
			}
			idField.setText("");
		}
		if(cmd.equals("전체 검색")) {
			listAll();
		}
		
		if(cmd.equals("추가")) {
			//id,pwd,name,email,phone,address;
			int money=0;
			int index=time.getSelectedIndex();
			switch(moneyArr[index]) {
				case "1시간": money=1000;break;
				case "2시간": money=2000;break;
				case "3시간": money=3000;break;
				case "5시간": money=5000;break;
				case "10시간": money=10000;break;
				case "24시간": money=20000;break;
				default: money=0;
			}
			int time=Integer.parseInt(moneyArr[index].replaceAll("시간", ""));
			UserTimeDto timeDto = new UserTimeDto();
			timeDto.setRemainTime(time);
			timeDto.setMountOfMoney(money);
			UserDTO userDTO= new UserDTO(id.getText(), pwd.getText(), name.getText(), email.getText(), phone.getText(),address.getText(),timeDto);
			if(UserDAO.addUserUpdate(userDTO)==false) {
				JOptionPane.showMessageDialog(null, "회원 추가 양식 올바르지 않음", "추가 안됨", JOptionPane.WARNING_MESSAGE);
			}
			id.setText("");pwd.setText("");name.setText("");email.setText("");phone.setText("");address.setText("");
			listAll();

		}
		if(cmd.equals("삭제")) {
			if(userTable.getSelectedRow()==-1) {
				return;
			}else {
				int row=userTable.getSelectedRow();
				model.removeRow(row);//화면상삭제
				UserDAO.deleteUser(userFromDB.get(row).getUserId());
				idField.setText("");
			}
		}

		
	}

}