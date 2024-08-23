package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDTO;

//@Repository : DB와 관련된 작업을 하는 클래스로 스프링 컨테이너에 빈으로 등록
@Repository
public class UserDAO_conn {

	private Connection conn;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	
	{
		try {
			//오라클DB와의 연결을 위해 build.gradle 파일에 다음의 내용을 dependencies 항목에 추가해줌
			// https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc6
		    //implementation 'com.oracle.database.jdbc:ojdbc6:11.2.0.4'
			
			//1. JDBC 드라이버를 JVM의 메모리에 로딩하기
			//Class.forName("각 DB의 JDBC 드라이버명");
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 드라이버가 정상적으로 로딩이 되면 DriverManager 클래스를 이용해서 DB와의 연결객체(Connection)를 얻을 수 있음
			//DriverManager.getConnection("DB의 서버URL","사용자계정","비밀번호");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "web_dev", "1234");
			
			System.out.println("DB 연결 성공!");
			
		} catch (Exception e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
	}
	
	public int insertUser(UserDTO dto) {
		int result = 0;
		
		String sql = " insert into tb_user (u_idx, user_name, user_id, user_pw, phone) "
						+ " values (tb_user_seq.nextval, ?, ?, ?, ?) ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserName());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getUserPw());
			pstmt.setString(4, dto.getPhone());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("회원정보 추가 중 예외 발생");
		}
		
		return result;
	}

	public List<UserDTO> getUserList() {
		List<UserDTO> userList = null;
		
		String sql = " select * from tb_user ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				userList = new ArrayList<UserDTO>();
				
				do {
					UserDTO dto = new UserDTO();
					dto.setUIdx(rs.getInt("u_idx"));
					dto.setUserName(rs.getString("user_name"));
					dto.setUserId(rs.getString("user_id"));
					dto.setUserPw(rs.getString("user_pw"));
					dto.setPhone(rs.getString("phone"));
					dto.setBirthday(rs.getString("birthday"));
					dto.setRegDate(rs.getDate("reg_date"));
					dto.setUpdateDate(rs.getDate("update_date"));
					dto.setUserStatus(rs.getString("user_status"));
					dto.setUserGrade(rs.getInt("user_grade"));
					
					userList.add(dto);
				} while(rs.next());
				
			}
			
		} catch (SQLException e) {
			System.out.println("회원목록 조회 중 예외 발생");
		}
		
		return userList;
	}

	
	
}
