package com.program.pc_cafe.dao;

import java.sql.*;
import java.util.*;

import com.program.pc_cafe.dto.FoodDTO;
import com.program.pc_cafe.dto.UserDTO;
import com.program.pc_cafe.dto.UserTimeDto;

import java.sql.*;
import java.util.*;

public class UserDAO {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로딩 성공!");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		}
	}

	public static void close(Connection con, PreparedStatement st) {
		try {
			con.close();
			st.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("종료 되지 않음");
		}
	}

	public static void close(Connection con, PreparedStatement st, ResultSet rs) {
		try {
			con.close();
			st.close();
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("종료 되지 않음");
		}
	}

	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/pc";
		String user = "root";
		String password = "1234";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}

	public static int loginCheck(String id, String pwd) {
		String sql = 
				"select usr.user_id, "
				+ "usr.user_pw, "
				+ "usrt.remain_time "
				+ "from user as usr "
				+ "inner "
				+ "join usertime as usrt "
				+ "on usr.user_id = usrt.user_id "
				+ "where usr.user_id = ? and usr.user_pw = ?";
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int returnValue = 3;
		try {
			System.out.println("로그인점검");
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			st.setString(2, pwd);
			rs = st.executeQuery();

			while (rs.next()) {// 로그인성공시 1
				if (rs.getString("user_id").equals(id) && rs.getString("user_pw").equals(pwd) && rs.getInt("remain_time") != 0) {
					returnValue = 1;
				} else if (rs.getInt("remain_time") == 0) {
					returnValue = 2;
				} else {
					returnValue = 3;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("로그인이상없이 종료");
			close(con, st, rs);
		}
		return returnValue;
	}

	public static boolean insert(UserDTO userDTO) {
		String sql = "insert into user values(?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement st = null;
		boolean returnValue = false;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, userDTO.getUserId());
			st.setString(2, userDTO.getUserPw());
			st.setString(3, userDTO.getUserName());
			st.setString(4, userDTO.getUserEmail());
			st.setString(5, userDTO.getUserPhoneNumber());
			st.setString(6, userDTO.getUserAddress());
			if (st.executeUpdate() == 1) { // 정상적으로 리턴되면 true 값 반환
				System.out.println("가입성공!");
				addUserTimeUpdate(userDTO.getUserId());
				returnValue = true;
			} else {
				System.out.println("가입실패!");
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("이상없이종료");
			close(con, st);
		}
		return returnValue;
	}

	/* 가입 성공시 시간 테이블 업데이트*/
	public static boolean addUserTimeUpdate(String user_id) {// 아이디중복체크
		boolean returnValue = false;// 리턴값
		String sql = "insert into usertime (user_id, remain_time, mount_of_money) values(? , 1, 1000)";// sql문
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, user_id);

			if (st.executeUpdate() == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st);
		}
		return returnValue;
	}
	
	public static boolean verfyID(String id) {// 아이디중복체크
		boolean returnValue = false;// 리턴값
		String sql = "select user_id from user where user_id=?";// sql문
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			System.out.println("조회시작");
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// 만약 id 값에 대한 조회가 없으면 return 값이 없다
				if (id.equals(rs.getString("user_id"))) {
					returnValue = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return returnValue;
	}

	public static String idPw(String name, String email) {
		String sql = "select user_id, user_pw from user where user_name=? and user_email=?";
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String returnValue = "해당하는 이름 또는 비밀번호가 존재하지 않습니다";
		try {
			System.out.println("로그인점검");
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, name);
			st.setString(2, email);
			rs = st.executeQuery();

			while (rs.next()) {
				returnValue = "아이디:" + rs.getString("user_id") + "/" + "비밀번호:" + rs.getString("user_pw");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("name 과 email 조회이상없이 종료");
			close(con, st, rs);
		}
		return returnValue;
	}

	public static UserDTO clientInfo(String id) {// 아이디에대한 정보값리턴
		UserDTO returnValue = null;// 리턴값
		String sql = "select usr.user_id, usrt.mount_of_money, usrt.remain_time "
				+ "from user as usr "
				+ "inner "
				+ "join usertime as usrt "
				+ "on usr.user_id = usrt.user_id "
				+ "where usr.user_id = ?";
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			System.out.println("조회시작");
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// 만약 id 값에 대한 조회가 없으면 return 값이 없다
				UserTimeDto utdo = new UserTimeDto(rs.getInt("mount_of_money"), rs.getInt("remain_time"));
				returnValue = new UserDTO(rs.getString("user_id"), utdo);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return returnValue;
	}

	////////////////////////// 이름을 통한 전체조회
	public static ArrayList<UserDTO> clientAll(String id) {// 아이디에대한 정보값리턴
		ArrayList<UserDTO> al = new ArrayList<UserDTO>();

		String sql = "select * from user as usr "
				+ "inner join usertime as usrt "
				+ "on usr.user_id = usrt.user_id "
				+ "where usr.user_id = ?";// sql문
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			// st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// 만약 id 값에 대한 조회가 없으면 return 값이 없다
				UserTimeDto utdo = new UserTimeDto(rs.getInt("mount_of_money"), rs.getInt("remain_time"));
				al.add(new UserDTO(rs.getString("user_id"), rs.getString("user_pw"), rs.getString("user_name"), rs.getString("user_email"),
						rs.getString("user_phonenumber"), rs.getString("user_address"), utdo));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return al;
	}

	////////////////////////// 이름을 통한 전체조회
	public static Vector<UserDTO> clientIdInfo(String id) {// 아이디에대한 정보값리턴
		Vector<UserDTO> v = new Vector<UserDTO>();
		// mount_of_money, remain_time
		String sql = "select * from user as usr "
				+ "inner join usertime as usrt "
				+ "on usr.user_id = usrt.user_id "
				+ "where usr.user_id = ?";// sql문
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			// st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// 만약 id 값에 대한 조회가 없으면 return 값이 없다
				UserTimeDto utdo = new UserTimeDto(rs.getInt("mount_of_money"), rs.getInt("remain_time"));
				v.add(new UserDTO(rs.getString("user_id"), rs.getString("user_pw"), rs.getString("user_name"), rs.getString("user_email"),
						rs.getString("user_phonenumber"), rs.getString("user_address"), utdo));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return v;
	}

	////////////////////////////// 걍 전체 조회
	public static Vector<UserDTO> userAll() {// 아이디에대한 정보값리턴
		Vector<UserDTO> v = new Vector<UserDTO>();

		String sql = "select * from user";// sql문
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {// 만약 id 값에 대한 조회가 없으면 return 값이 없다
				UserTimeDto utdo = new UserTimeDto(rs.getInt("mount_of_money"), rs.getInt("remain_time"));
				v.add(new UserDTO(rs.getString("user_id"), rs.getString("user_pw"), rs.getString("user_name"), rs.getString("user_email"),
						rs.getString("user_phonenumber"), rs.getString("user_address"), utdo));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return v;
	}

	//////////////////////////////////////////////////////////////
	/////////////////////////////// 음식정보 전체 조회//////////////////

	public static Vector<FoodDTO> foodAll() {// 아이디에대한 정보값리턴
		Vector<FoodDTO> v = new Vector<FoodDTO>();

		String sql = "select * from food";// sql문
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			// st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// 만약 id 값에 대한 조회가 없으면 return 값이 없다
				v.add(new FoodDTO(rs.getString("name"), rs.getInt("price")));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return v;
	}

	public static ArrayList<FoodDTO> foodSearch(String name) {// 아이디에대한 정보값리턴
		ArrayList<FoodDTO> v = new ArrayList<FoodDTO>();

		String sql = "select name,price from food where name=?";// sql문
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, name);
			rs = st.executeQuery();

			while (rs.next()) {// 만약 id 값에 대한 조회가 없으면 return 값이 없다
				v.add(new FoodDTO(rs.getString("name"), rs.getInt("price")));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return v;
	}

	////////////////////////////// 업데이트
	public static boolean addTime(String oid, int time, int oMoney) {// 아이디중복체크
		boolean returnValue = false;// 리턴값
		String sql = "update user set remaintime=remaintime+?,mountofmoney=mountofmoney+? where id=? ";// sql문
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setInt(1, time);
			st.setInt(2, oMoney);
			st.setString(3, oid);
			if (st.executeUpdate() == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st);
		}
		return returnValue;
	}

	///// 시간감소
	////////////////////////////// 업데이트
	public static boolean deleteTime(String oid, int time, int oMoney) {// 아이디중복체크
		boolean returnValue = false;// 리턴값
		String sql = "update user set remaintime=remaintime-?,mountofmoney=mountofmoney-? where id=? ";// sql문
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setInt(1, time);
			st.setInt(2, oMoney);
			st.setString(3, oid);
			if (st.executeUpdate() == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st);
		}
		return returnValue;
	}

	////
	/////////////////////////// 삭제
	public static boolean deleteUser(String id) {// 아이디중복체크
		boolean returnValue = false;// 리턴값
		String sql = "delete from user where user_id=? ";// sql문
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			if (st.executeUpdate() == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st);
		}
		return returnValue;
	}

	////
	////////////////////////////////////////// 모든값을 적용한 업데이트
	public static boolean addUserUpdate(UserDTO userDTO) {// 아이디중복체크
		boolean returnValue = false;// 리턴값
		String sql = "insert into user values(? , ? , ? , ? , ? , ? )";// sql문
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, userDTO.getUserId());
			st.setString(2, userDTO.getUserPw());
			st.setString(3, userDTO.getUserName());
			st.setString(4, userDTO.getUserEmail());
			st.setString(5, userDTO.getUserPhoneNumber());
			st.setString(6, userDTO.getUserAddress());

			if (st.executeUpdate() == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st);
		}
		return returnValue;
	}
	///////////////////////////////

}