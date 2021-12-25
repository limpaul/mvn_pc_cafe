package com.program.pc_cafe.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.program.pc_cafe.dao.UserDAO;
import com.program.pc_cafe.dto.UserDTO;
import com.program.pc_cafe.image.ui.user.AdminChat;
import com.program.pc_cafe.image.ui.user.ServerControl;
import com.program.pc_cafe.image.ui.user.Stocks;
import com.program.pc_cafe.image.ui.user.TimeEdit;
import com.program.pc_cafe.image.ui.user.UserManage;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

public class UsingPC extends JFrame {
	private String num;
	private String id;
	//관리자 옵션 버튼 
	private JButton[] pc = new JButton[16];
	private JButton stop = new JButton("사용중지");
	private JButton add = new JButton("시간 추가");
	private JButton minus = new JButton("시간 감소");
	private JButton moneyManage = new JButton("요금관리");
	private JButton info = new JButton("정보");
	private JButton thing = new JButton("물품");
	private JButton user = new JButton("회원");
	private JButton card = new JButton("카드");
	private JButton msg = new JButton("메세지");
	
	//관리자가 사용자 UI를 클릭시 나오는 정보 
	private DefaultTableModel model;
	private String[] menuArr = { "아이디", "비밀번호", "이름", "이메일", "휴대전화", "주소", "남은시간", "총금액" }; 
	private Vector<String> userColumn = new Vector<>();
	private JScrollPane scroll;
	private String buttonNumber;// pc자리 번호
	// 서버
	ServerSocket sSocket;
	Socket mySocket;
	BufferedReader br;
	PrintWriter pw;
	String data;
	String stopNum;
	Vector<UserDTO> v;
	Vector<UsingPC> userInfoList=new Vector<>();
	public UsingPC(String num,String id) {
		this.num=num;
		this.id=id;
	}
	public UsingPC() {
		UsingPCEvent event = new UsingPCEvent();
		Container contain = getContentPane();
		contain.setLayout(new BorderLayout());
		JPanel p1 = new JPanel(); 
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 전체 태두리 10만큼 감소 
		p1.setLayout(new GridLayout(4, 4, 10, 30)); // 좌석 UI layout 설정 
		for (int i = 0; i < 16; i++) { // 총 16자리 등록 
			String pcNum = (i + 1) + "번PC";
			pc[i] = new JButton(pcNum); // PC자리별 버튼 객체 생성. 
			p1.add(pc[i]); // 페널에 좌석 UI 적용 
			pc[i].addActionListener(event); // 사용자 자리 이벤트 등록. 
		}
		JPanel p2 = new JPanel();  // 사용자 정보 목록 UI위치 페널 
		p2.setLayout(new BorderLayout());  // layout설정  
		p2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 전체 태두리 20만큼 감소 
		JPanel p2_1 = new JPanel(); // 관리자 옵션 버튼 UI부분 
		// p2_1.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		p2_1.setLayout(new GridLayout(4, 3, 10, 10));
		p2_1.add(new JLabel(""));
		p2_1.add(new JLabel("캡스톤PC방 02-8888-8888"));
		p2_1.add(new JLabel(""));
		p2_1.add(stop); //사용자 종료 버튼 등록 
		p2_1.add(add); // 시간 추가 버튼 등록 
		p2_1.add(minus);//시간 감소 버튼 등록 
		add.addActionListener(event); //  시간 추가 버튼 이벤트 등록 
		minus.addActionListener(event); // 시간 감소 버튼 이벤트 등록 
		p2_1.add(moneyManage); // 
		p2_1.add(info); // 관리자 횡
		p2_1.add(msg); // 관리자 메세지 UI등록 
		msg.addActionListener(event); // 메세지 이벤트 등록 
		p2_1.add(thing); // 물품 버튼추가 
		thing.addActionListener(event); // 물품 버튼 이벤트 추가 
		p2_1.add(user); 
		user.addActionListener(event);
		p2_1.add(card); // 카드 이벤트 추가 
		p2.add(p2_1, "South"); //layout기준 남쪽으로 추가 
		
		for (int i = 0; i < menuArr.length; i++) { // 사용자 정보 목록 
			userColumn.add(menuArr[i]);
		}
		scroll = new JScrollPane(new JTable(new DefaultTableModel(userColumn, 0))); // 스크롤 기능 추가 

		p2.add(scroll, "Center");

		stop.addActionListener(event); //종료 버튼 추가 
		contain.add(p1, "Center");
		contain.add(p2, "East");
		setLocation(500, 50);
		setSize(1200, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// 서버부분
		// --------------------------------------------------------------------------------------------
		try {// 로그인할 보내는 데이터
			sSocket = new ServerSocket(9999);
			while (true) {
				System.out.println("서버실행중 ");
				mySocket = sSocket.accept();
				System.out.println("클라이언트 접속완료" + "ip:" + mySocket.getInetAddress() + "port:" + mySocket.getPort());
				br = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));// 수신스트림 생성
				pw = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()));// 출력스트림 생성
				while ((data = br.readLine()) != null) {
					System.out.println("받은데이타:" + data);// db아이디 비번 검증 메소드 넣은후 return 해준다
					String[] checkData = data.split("/");// login/아이디/비밀번호 이런식으로 받는다
					System.out.println(checkData.toString());
					if (checkData[0].equals("login")) {// 또는 userAdd/아이디/비밀번호/이름 이런식으로 데이터를입력받는다
						// if 문으로 checkData[0] 으로 로그인관련 데이터인 지 아니면 회원가입 데이터인지 //또는 계정찾는 데이터인지 구분한다
						int chkLogin = UserDAO.loginCheck(checkData[1], checkData[2]);
						if (chkLogin == 1) {// db에서 1을 리턴하면 로그인성공이다
							pw.println("로그인성공"); // 클라이언트 서버에게 문자를보내준다
							pw.flush();
							int pcNum = Integer.parseInt(checkData[3]);
							System.out.println("PC번호" + pcNum);
							pc[pcNum - 1].setIcon(new ImageIcon("image/system_image/user.png"));// pc번호 로그인한곳 이미지바꾼다
							//pc[pcNum - 1].setText(checkData[1]);//PC번호를 계정 아이디로 변경해준다
							userInfoList.add(new UsingPC(checkData[3],checkData[1]));//아이디 PC번호 넣어줌
							// 로그인정보를 관리자 서버에 등록시켜줘야 한다
							
							v = UserDAO.clientIdInfo(checkData[1]);// db로 부터 ArrayList 값을 반환받는다
							// ** 중요한 포인트다
						} else if (chkLogin == 2) {
							pw.println("카운터 에서 충전 하시길바랍니다");
							pw.flush();
						} else {
							pw.println("로그인실패");
							pw.flush();
						}
					}
					
					
					if (checkData[0].equals("userAdd")) {
						// if문에 checkData[0] 이 userAdd 이면 회원등록으로 넘긴다
						String id = checkData[1];
						String pwd = checkData[2];
						String name = checkData[3];
						String email = checkData[4];
						String phoneNumber = checkData[5];
						String address = checkData[6];
						if (UserDAO.insert(new UserDTO(id, pwd, name, email, phoneNumber, address)) == true) {
							pw.println("가입성공");
							pw.flush();
						} else {
							pw.println("가입실패");
							pw.flush();
						}
					}

					if (checkData[0].equals("userVerify")) {
						// if문에 checkData[0] 이 userAdd 이면 회원등록으로 넘긴다
						String id = checkData[1];
						if (UserDAO.verfyID(id) == false) {
							pw.println("중복아님");
							pw.flush();
						} else {
							pw.println("중복");
							pw.flush();
						}
					}
					if (checkData[0].equals("order")) {
						pw.println("주문성공");
						pw.flush();
						// if문에 checkData[0] 이 userAdd 이면 회원등록으로 넘긴다
						String type = checkData[1];// 결제종류
						String order1 = checkData[2];// 주문상품1
						String order2 = checkData[3];// 주문상품2
						String order3 = checkData[4];// 주문상품3
						String order4 = checkData[5];// 주문상품4
						String order5 = checkData[6];// 주문상품5
						int mountOfMoney = Integer.parseInt(checkData[7]);
						int clientOfMoeny = Integer.parseInt(checkData[8]);
						String getText = checkData[9];
						String num = checkData[10];// 자리번호
						JOptionPane.showMessageDialog(null,
								"주문 목록 \n" + "결제종류" + type + "\n" + "주문상품1: " + order1 + "\n" + "주문상품2: " + order2
										+ "\n" + "주문상품3: " + order3 + "\n" + "주문상품4: " + order4 + "\n" + "주문상품5 :"
										+ order5 + "\n" + "총 금액" + mountOfMoney + "\n" + "손님 :" + clientOfMoeny + "\n"
										+ "내용 : " + getText + "\n" + "자리번호" + num);

					}
					if (checkData[0].equals("userSearch")) {
						// if문에 checkData[0] 이 userAdd 이면 회원등록으로 넘긴다
						String name = checkData[1];
						String email = checkData[2];
						System.out.println("들어온 데이타"+name+"/"+email);
						String data=null;
						if ((data=UserDAO.idPw(name, email)) != null) {
							pw.println("조회성공/"+data);
							pw.flush();
						} else {
							pw.println("조회실패/");
							pw.flush();
						}
					}
					if (checkData[0].equals("ping")) {
						pw.println("ping");
						pw.flush();
					}
				}
				//mySocket.close();
				//System.out.println("서버 소켓 사라짐");
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception

		}
		
	}
	class Receiver{
		
	}
	// ------------------------------------------------------------------------------------------------------------------------
	class UsingPCEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String cmd = e.getActionCommand();
			System.out.println(cmd);
			try {
				cmd=cmd.replaceAll("번PC", "");
		
				for (int i = 0; i <userInfoList.size(); i++) {
					String num = userInfoList.get(i).num; //번호설정한거
					if (num.equals(cmd)) {// 만약 ArrayList 에 있는 id가 현재 버튼 id와 같은 아이디라면 아이디 정보를 출력해준다
						stopNum=cmd;
						String id=userInfoList.get(i).id;
						ArrayList<UserDTO> userInfo= UserDAO.clientAll(id);
						String gid=null;String pwd=null;String name=null;String email=null;
						String phonNumber=null;String address=null;String remainTime=null;String mountToMoney=null;
					
						for(int j=0;j<userInfo.size();j++) {
					
							gid=userInfo.get(j).getUserId();
							pwd=userInfo.get(j).getUserPw();
							name=userInfo.get(j).getUserName();
							email=userInfo.get(j).getUserEmail();
							phonNumber=userInfo.get(j).getUserPhoneNumber();
							address=userInfo.get(j).getUserAddress();
							remainTime=Integer.toString(userInfo.get(j).getUserTimeDto().getRemainTime());
							mountToMoney=Integer.toString(userInfo.get(j).getUserTimeDto().getMountOfMoney());
							buttonNumber=cmd;
						}
						model.setRowCount(0);//열을 초기화시켜준다
						for(int j=0;j<userInfo.size();j++) {
							String[] userRow= {gid,pwd,name,email,phonNumber,address,remainTime,mountToMoney};
							model.addRow(userRow);
						}
					}
				}
			} catch (NullPointerException x) {
				// TODO: handle exception
				x.printStackTrace();
				System.out.println("일치하는게 없음");
			}
			if (cmd.equals("사용중지")) { // 멀티쓰레드를 사용
				
				if (buttonNumber == null) {//아무것도 클릭 안하면 셧다운안됨
					JOptionPane.showMessageDialog(null, "pc자리를 클릭해주세요", "경고", JOptionPane.WARNING_MESSAGE);
				} else {
					pc[Integer.parseInt(stopNum)-1].setIcon(new ImageIcon(""));
					model.removeRow(0);
					stopNum=null;
					buttonNumber=null;
					Runnable r = new ServerControl(pc,buttonNumber);
					Thread t = new Thread(r);
					t.start();
				}
			}
			if(cmd.equals("물품")) {
				new Stocks(UsingPC.this);
				
			}
			if(cmd.equals("회원")) {
				new UserManage(UsingPC.this);
			}
			if(cmd.equals("시간 추가")) {
				new TimeEdit(UsingPC.this);
			}
			if(cmd.equals("시간 감소")) {
				new TimeEdit(UsingPC.this);
			}
			if(cmd.equals("메세지")) {
				new AdminChat(UsingPC.this);
			}
		}

	}

	public static void main(String[] args) {
		new UsingPC();	
	}
}