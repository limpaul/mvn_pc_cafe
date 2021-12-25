package com.program.pc_cafe.image.ui.user;


import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class OrderGood extends JDialog {

	private JButton top10Button1, top10Button2, top10Button3, top10Button4, top10Button5;
	private JButton all, eat, ramen, setMenu, gansick, cafe, gaga, pushSearch;
	private JTextField search;
	private JTable table;
	private JButton btnPlus, btnMinus;
	private JScrollPane scroll;
	private JLabel orderValue;
	private ButtonGroup bgp1 = new ButtonGroup();
	private ButtonGroup bgp2 = new ButtonGroup();
	private JRadioButton cash, card, trafficCard, daily;
	private JRadioButton frontCash, backCash;
	private String[] moneyValue = { "지불금액 선택", "1000원", "2000원", "3000원", "4000원", "5000원", "10000원", "50000원" };
	private JComboBox<String> cb = new JComboBox<String>(moneyValue);
	private JTextArea need;// 주문 요청사항 입력
	private JButton orderClick;// 주문 버튼 클릭
	private Vector<String> column = new Vector<String>();
	private Vector<OrderGood> moneyManage = new Vector<OrderGood>();
	private DefaultTableModel model; // JTable 추가 삭제

	private String name;// 상품이름
	private int money;// 상품가격
	private int count;// 상품갯수

	private String num;
	// 클라이언트 필드
	Socket cSocket;
	BufferedReader br;
	PrintWriter pw;
	String data;

	String[] allText = { "김치볶음", "참치볶음", "떡볶이", "햄버거", "인디아", "진라면", "주스", "오므라이스", "라면", "삼양라면", "채소", "육계장" };
	JPanel p1;
	JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
	JButton[] btnArr = { btn1, btn2, btn3, btn4, btn4, btn6, btn7, btn8, btn9, btn10, btn11, btn12 };

	String[] foodAll = { "전체/bokum.jpg", "전체/chamcibokum.jpg", "전체/ddukk.jpg", "전체/hamberger.jpg", "전체/indea.jpg",
			"전체/jinramen.jpg", "전체/juice.jpg", "전체/omelrais.jpg", "전체/ramen.jpg", "전체/samyangramen.jpg",
			"전체/vegetal.png", "전체/yukgaeramen.jpg" };

	OrderEvent orderEvent = new OrderEvent();
	JFrame frame;
	public OrderGood(String name, int money, int count) {
		this.name = name;
		this.money = money;
		this.count = count;
	}

	public OrderGood(JFrame frame,String num) {
		super(frame,num,true);
		this.num=num;
		Container contain = getContentPane();
		JPanel panelModel = new JPanel();
		JPanel panelList = new JPanel();

		setSize(1300, 1000);
		setLocation(500, 10);

		orderGoodModel(panelModel);
		OrderList(panelList);

		
		contain.add(panelModel, "North");
		contain.add(panelList, "Center");
		// setResizable(false);
		setResizable(false);
		setVisible(true);
	}

	public void orderGoodModel(JPanel panel) {
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(1, 5));
		JPanel pick = new JPanel();
		pick.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pick.setLayout(new GridLayout(1, 9, 10, 10));

		top10Button1 = new JButton(new ImageIcon("전체/bokum.jpg"));
		subPanel.add(top10Button1);

		top10Button2 = new JButton(new ImageIcon("전체/chamcibokum.jpg"));
		subPanel.add(top10Button2);

		top10Button3 = new JButton(new ImageIcon("전체/ddukk.jpg"));
		subPanel.add(top10Button3);

		top10Button4 = new JButton(new ImageIcon("전체/hamberger.jpg"));
		subPanel.add(top10Button4);

		top10Button5 = new JButton(new ImageIcon("전체/jajangmen.jpg"));
		subPanel.add(top10Button5);

		all = new JButton("전체");
		eat = new JButton("식사류");
		ramen = new JButton("라면류");
		setMenu = new JButton("세트메뉴");
		gansick = new JButton("간식");
		cafe = new JButton("CAFE");
		gaga = new JButton("과자류");
		pushSearch = new JButton("검색");
		search = new JTextField();

		pick.add(all);
		pick.add(eat);
		pick.add(ramen);
		pick.add(setMenu);
		pick.add(gansick);
		pick.add(cafe);
		pick.add(gaga);
		pick.add(search);
		pick.add(pushSearch);

		all.addActionListener(orderEvent);
		eat.addActionListener(orderEvent);
		ramen.addActionListener(orderEvent);
		setMenu.addActionListener(orderEvent);
		gansick.addActionListener(orderEvent);
		cafe.addActionListener(orderEvent);
		gaga.addActionListener(orderEvent);
		pushSearch.addActionListener(orderEvent);

		JLabel label = new JLabel("캡스톤 기초설계PC방입니다!");
		panel.add(label, "North");
		panel.add(subPanel, "Center");
		panel.add(pick, "South");

	}

	public void OrderList(JPanel list) {

		list.setLayout(new BorderLayout());
		list.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 10));
		p1 = new JPanel();
		p1.setLayout(new GridLayout(3, 6, 5, 5));
		p1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// ---------------------------------------------------------------------
		for (int i = 0; i < btnArr.length; i++) {
			btnArr[i] = new JButton(new ImageIcon(foodAll[i]));
			p1.add(btnArr[i]);
		}
		for (int i = 0; i < btnArr.length; i++) {
			btnArr[i].setIcon(new ImageIcon(foodAll[i]));
			btnArr[i].setText(allText[i]);
			btnArr[i].addActionListener(orderEvent);
		}
		/////// ---------------------------------------------------
		JPanel p2 = new JPanel();
		JPanel p2_1 = new JPanel();
		JPanel sub_panel = new JPanel();
		JPanel addMinus_panel = new JPanel();

		p2.setLayout(new BorderLayout());// 전체 layout p2 이다
		p2_1.setLayout(new BorderLayout());// 보조패널

		JLabel mokrok = new JLabel("주문목록");
		sub_panel.setLayout(new BorderLayout());
		addMinus_panel.setLayout(new GridLayout(1, 2));
		//

		column.addElement("상품목록");
		column.addElement("가격");
		column.addElement("수량");
		model = new DefaultTableModel(column, 0);

		table = new JTable(model);
		table.setPreferredSize(new Dimension(200, 380));
		scroll = new JScrollPane(table);

		btnPlus = new JButton("추가");
		btnMinus = new JButton("삭제");
		sub_panel.add(scroll, "Center");
		addMinus_panel.add(btnPlus);
		addMinus_panel.add(btnMinus);
		sub_panel.add(addMinus_panel, "South");

		btnPlus.addActionListener(new OrderEvent2());
		btnMinus.addActionListener(new OrderEvent2());

		orderValue = new JLabel("전체금액: 0원");
		orderValue.setFont(new Font("고딕체", Font.BOLD, 20));
		p2_1.add(mokrok, "North");
		p2_1.add(sub_panel, "Center");
		p2_1.add(orderValue, "South");

		// p2_2 는 센터
		JPanel p2_2 = new JPanel();
		p2_2.setLayout(new GridLayout(2, 1));

		JPanel p2_2_1 = new JPanel();
		p2_2_1.setLayout(new GridLayout(1, 5));

		// 라디오 버튼추가
		JLabel l1 = new JLabel("결제수단");
		cash = new JRadioButton("현금");
		card = new JRadioButton("신용카드");
		trafficCard = new JRadioButton("교통카드");
		daily = new JRadioButton("정액권");
		bgp1.add(cash);
		bgp1.add(card);
		bgp1.add(trafficCard);
		bgp1.add(daily);
		cash.addActionListener(new OrderEvent2());
		card.addActionListener(new OrderEvent2());
		trafficCard.addActionListener(new OrderEvent2());
		daily.addActionListener(new OrderEvent2());
		p2_2_1.add(l1);
		p2_2_1.add(cash);
		p2_2_1.add(card);
		p2_2_1.add(trafficCard);
		p2_2_1.add(daily);
		JPanel p2_2_2 = new JPanel();
		p2_2_2.setLayout(new GridLayout(1, 3));

		// 지불금액 JList 와 textArea 와 선불 후불 라디오추가
		frontCash = new JRadioButton("선불");
		backCash = new JRadioButton("후불");
		bgp2.add(frontCash);
		bgp2.add(backCash);
		p2_2_2.add(cb);
		p2_2_2.add(frontCash);
		p2_2_2.add(backCash);
		p2_2.add(p2_2_1);
		p2_2.add(p2_2_2);

		JPanel p2_3 = new JPanel();
		p2_3.setLayout(new BorderLayout());
		need = new JTextArea("주문시 요청사항 입력", 5, 20);
		orderClick = new JButton("상품주문");
		orderClick.addActionListener(new OrderEvent2());
		p2_3.add(need, "Center");
		p2_3.add(orderClick, "East");

		p2.add(p2_1, "North");
		p2.add(p2_2, "Center");
		p2.add(p2_3, "South");
		list.add(p1, "Center");
		list.add(p2, "East");
		all.addActionListener(orderEvent);

	}

	////////////////////////////////////////////// 이벤트
	////////////////////////////////////////////// 클래스/////////////////////////////////
	class OrderEvent implements ActionListener {
		JButton btnA, btnB, btnC, btnD, btnE, btnG, btnH, btnI, btnJ, btnK, btnL, btnM;
		JButton[] btnArr2 = { btnA, btnB, btnC, btnD, btnE, btnG, btnH, btnI, btnJ, btnK, btnL, btnM };
		FoodSearch sDialog = null;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd = e.getActionCommand();

			if (cmd.equals("전체")) {
				btnAll();

			}
			if (cmd.equals("식사류")) {
				sicksaryu();
			}
			if (cmd.equals("라면류")) {
				ramenryu();

			}
			if (cmd.equals("세트메뉴")) {
				set();
			}
			if (cmd.equals("간식")) {
				gansick();
			}
			if (cmd.equals("CAFE")) {
				cafe();
			}
			if (cmd.equals("과자류")) {
				gaga();
			}
			if (cmd.equals("검색")) {
				String searchData = search.getText();
				sDialog = new FoodSearch(searchData, frame);
				sDialog.setVisible(true);
			}
			if (!cmd.equals("전체") && !cmd.equals("식사류") && !cmd.equals("라면류") && !cmd.equals("세트메뉴")
					&& !cmd.equals("간식") && !cmd.equals("CAFE") && !cmd.equals("과자류") && !cmd.equals("검색")) {

				FoodValue returnValue = new FoodValue().returnValue(cmd);

				String name = returnValue.getName();
				int value = returnValue.getValue();

				String[] arr = { name, Integer.toString(value), Integer.toString(1) };
				model.addRow(arr);

			}

		}

		public void btnAll() {
			for (int i = 0; i < btnArr.length; i++) {
				btnArr[i].setIcon(new ImageIcon(foodAll[i]));
				btnArr[i].setText(allText[i]);

			}
		}

		public void sicksaryu() {

			String[] sicksa = { "전체/bokum.jpg", "전체/chamcibokum.jpg", "전체/ddukk.jpg", "전체/hamberger.jpg",
					"전체/indea.jpg", "전체/juice.jpg", "전체/omelrais.jpg", "전체/vegetal.png", "전체/꼬치피면.jpg", "전체/바다.jpg",
					"전체/바베큐.jpg", "전체/부대찌게.jpg" };
			String[] arr = { "김치볶음", "참치볶음", "떡볶이", "햄버거", "인디아", "음료수", "오므라이스", "채소", "꼬치", "바다", "바베큐", "부대찌게" };
			for (int i = 0; i < btnArr.length; i++) {
				btnArr[i].setIcon(new ImageIcon(sicksa[i]));// 버튼 사진변경
				btnArr[i].setText(arr[i]);// 글자 바꿈

			}

		}

		public void ramenryu() {
			String[] arr = { "진라면", "라면", "삼양라면", "육개장", "일본라면", "돼지라면", "분식라면", "소고기라면", "부추라면", "콩나물라면", "신라면",
					"일본라면2" };
			String ramenryu[] = { "라면류/jinramen.jpg", "라면류/ramen.jpg", "라면류/samyangramen.jpg", "라면류/yukgaeramen.jpg",
					"라면류/간장라면.jpeg", "라면류/돼지라면.jpg", "라면류/라면점빵.jpg", "라면류/사부야.jpeg", "라면류/선라면.jpg", "라면류/수라면.jpg",
					"라면류/신라면.jpg", "라면류/일본.jpg" };

			for (int i = 0; i < arr.length; i++) {
				btnArr[i].setIcon(new ImageIcon(ramenryu[i]));
				btnArr[i].setText(arr[i]);

			}

		}

		public void set() {
			for (int i = 0; i < btnArr.length; i++) {
				btnArr[i].setIcon(new ImageIcon(""));
				btnArr[i].setText("준비중");
			}
		}

		public void gansick() {
			String[] gansick = { "간식/두부햄.png", "간식/오잉.jpg", "간식/오징어.jpg", "간식/짱죽.jpg", "간식/포카칩.jpg", "간식/포테토칩.png",
					"간식/쫀드기.jpg", "간식/초코링.jpg", "간식/통크.jpg", "준비중", "준비중", "준비중" };
			String[] arr = { "두부햄", "오잉", "오징어", "헬로짱", "포카칩", "포테토칩", "쫀드기", "초코링", "", "", "", "" };
			for (int i = 0; i < gansick.length; i++) {
				btnArr[i].setIcon(new ImageIcon(gansick[i]));
				btnArr[i].setText(arr[i]);
			}
		}

		public void cafe() {
			String[] cafe = { "커피/고양이.jpg", "커피/맥심.jpg", "커피/바닐라.jpg", "커피/발라드.jpg", "커피/블랙커피.gif", "커피/아메리카노.jpg",
					"커피/일본.jpg", "커피/제너러스커피.jpg", "커피/코코아.jpg", "커피/프롬하츠.png", "커피/핸즈커피.jpg", "커피/호주커피.jpg" };
			String[] arr = { "고양이", "맥심", "바닐라", "발라드", "블랙", "아메리카노", "일본", "제너러스", "코코아", "프롬하츠", "핸즈", "호주" };
			for (int i = 0; i < cafe.length; i++) {
				btnArr[i].setIcon(new ImageIcon(cafe[i]));
				btnArr[i].setText(arr[i]);
			}
		}

		public void gaga() {
			String[] gaga = { "과자/mini.png", "과자/고래밥.jpg", "과자/꼬깔콘.gif", "과자/꼬북칩.png", "과자/나나콘.jpg", "과자/도리토스.jpg",
					"과자/두부과자.jpg", "과자/스윙칩.png", "과자/쌀과자.jpg", "과자/오감자.jpg", "과자/옥수수과자.jpg", "과자/짱구.jpg" };
			String[] arr = { "미니", "고래밥", "꼬깔콘", "꼬북칩", "나나콘", "도리토스", "두부과자", "스윙칩", "쌀과자", "오감자", "두부과자", "짱구" };
			for (int i = 0; i < gaga.length; i++) {
				btnArr[i].setIcon(new ImageIcon(gaga[i]));
				btnArr[i].setText(arr[i]);
			}
		}

	}

	// -------------------------------새로운 동작 클래스
	// 등록-----------------------------------------
	class OrderEvent2 implements ActionListener {
		int mount = 0;
		String[] arr = { "없음", "없음", "없음", "없음", "없음" };

		public void actionPerformed(ActionEvent x) {
			int m = 0;
			String cmd = x.getActionCommand();

			if (cmd.equals("전체")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}

			if (cmd.equals("식사류")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("라면류")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("세트메뉴")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("간식")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("CAFE")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("과자류")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			int mountOfMoney = 0;
			if (cmd.equals("추가")) {/////////////// 계산 상품 추가 했을때
				try {

					int row = table.getSelectedRow();

					String name = (String) table.getValueAt(row, 0); // 상품이름
					int money = Integer.parseInt((String) table.getValueAt(row, 1)); // 돈
					int count = Integer.parseInt((String) table.getValueAt(row, 2)); // 횟수

					moneyManage.add(new OrderGood(name, money, count)); // vector 에 값 추가
					orderValue.setFont(new Font("고딕", Font.BOLD, 20));// 글자 설정

					for (int i = 0; i < moneyManage.size(); i++) {
						mountOfMoney += moneyManage.get(i).getMoney();
					}
					orderValue.setText("전체금액: " + mountOfMoney + "원");
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "상품을 선택한다음 추가버튼을 눌러주세요");

				}
			}
			if (cmd.equals("삭제")) {

				if (table.getSelectedRow() == -1) {
					return;

				} else {
					int row = table.getSelectedRow();// JTable에서 선택한 줄
					String name = (String) table.getValueAt(row, 0); // 이름
					for (int i = 0; i < moneyManage.size(); i++) {
						if (moneyManage.get(i).getName().equals(name)) {
							moneyManage.remove(i); // 행당 Vector 값을 지워라
						}
					}

					for (int i = 0; i < moneyManage.size(); i++) {
						mountOfMoney += moneyManage.get(i).getMoney(); // 그리고 나머지값을 구하라
					}
					orderValue.setText("전체금액: " + mountOfMoney + "원");// 값변경
					model.removeRow(table.getSelectedRow());

				}
			}
			try {
				if (cmd.equals("상품주문")) {
					String typeOfBuy = null;

					for (int i = 0; i < moneyManage.size(); i++) {
						System.out.println("ㄱㅊ상품이름" + moneyManage.get(i).getName());
						m += moneyManage.get(i).getMoney();
						arr[i] = moneyManage.get(i).getName();
					}

					String clientMoney = cb.getSelectedItem().toString();
					if (!cash.isSelected() && !card.isSelected() && !trafficCard.isSelected() && !daily.isSelected()) {
						JOptionPane.showMessageDialog(null, "결제수단 선택해주세요", "경고", JOptionPane.WARNING_MESSAGE);
					} else if (clientMoney.equals("지불금액 선택")) {
						JOptionPane.showMessageDialog(null, "지불금액을 선택해주세요", "경고", JOptionPane.WARNING_MESSAGE);
					} else if (Integer.parseInt(clientMoney.replace("원", "")) < m) {
						System.out.println(Integer.parseInt(clientMoney.replace("원", "")));

						JOptionPane.showMessageDialog(null, "상품가격이 더 높습니다", "경고", JOptionPane.WARNING_MESSAGE);

					} else if (moneyManage.size() > 5) {
						JOptionPane.showMessageDialog(null, " 6개 이상 못시킴니다", "경고", JOptionPane.WARNING_MESSAGE);
					} else {

						if (cash.isSelected()) {
							System.out.println("결제수단:현금");
							typeOfBuy = cash.getText();
						} else if (card.isSelected()) {
							System.out.println("결제수단:카드");
							typeOfBuy = card.getText();
						} else if (trafficCard.isSelected()) {
							System.out.println("결제수단:교통카드");
							typeOfBuy = trafficCard.getText();
						} else if (daily.isSelected()) {
							System.out.println("결제수단:정액권");
							typeOfBuy = daily.getText();
						}
						System.out.println("결제종류:" + typeOfBuy);
						try {
							System.out.println("주문 1" + arr[0]);
							System.out.println("주문 2" + arr[1]);
							System.out.println("주문 3" + arr[2]);
							System.out.println("주문 4" + arr[3]);
							System.out.println("주문 5" + arr[4]);
							System.out.println("상품 가격" + m);
							System.out.println("사용자 지불금액" + clientMoney);
							System.out.println("자리번호:" + num);
						} catch (ArrayIndexOutOfBoundsException c) {
							// TODO: handle exception
							System.out.println("상품갸격 버서남");
						}

						sendOrderData(typeOfBuy, arr[0], arr[1], arr[2], arr[3], arr[4], m,
								Integer.parseInt(clientMoney.replace("원", "")), need.getText(), num);

					}
				}
			} catch (Exception e) {
				// TODO: handle exception

				JOptionPane.showMessageDialog(null, "상품을 추가해주세요", "경고", JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	// =========================클라이언트 구문================================
	public void sendOrderData(String type, String order1, String order2, String order3, String order4, String order5,
			int mountOfMoney, int clientOfMoney, String text, String num) {

		try {
			cSocket = new Socket("127.0.0.1", 9999);
			br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// 수신스트림 생성
			pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// 출력스트림 생성

			pw.println("order/" + type + "/" + order1 + "/" + order2 + "/" + order3 + "/" + order4 + "/" + order5 + "/"
					+ mountOfMoney + "/" + clientOfMoney + "/" + need.getText() + "/" + num);// 11개 데이터 보냄
			pw.flush();
			while ((data = br.readLine()) != null) {
				if (data.equals("주문성공")) {
					JOptionPane.showMessageDialog(null, "주문이 완료되었습니다");
					pw.close();
					br.close();
					cSocket.close();
				} else {
					JOptionPane.showMessageDialog(null, "카운터에서 주문해주세요");
				}

			}
			pw.close();
			br.close();
			cSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
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

	// ----------------------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	class SearchWord extends JFrame {
		public SearchWord() {
			String searchText = search.getText();
			if (new FoodValue().returnValue(searchText) != null) {

			}

			setSize(500, 500);
			setVisible(true);
		}
	}

	
}