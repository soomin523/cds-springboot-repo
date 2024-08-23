package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDTO;

import lombok.RequiredArgsConstructor;

//@Repository : DB와 관련된 작업을 하는 클래스로 스프링 컨테이너에 빈으로 등록
@Repository
@RequiredArgsConstructor //자동의존주입
public class UserDAO {

	public static final String Mapper = "com.example.demo.mapper.UserMapper";
	//MyBatis를 사용할 때 각각의 Mapper 내에 있는 SQL 구문을 Mapper의 네임스페이스와 SQL 구문의 id값으로 구분하므로 
	//각 Mapper 파일의 네임스페이스를 상수로 정의해서 SqlSession 메소드 호출 시 사용함
	
	private final SqlSession sqlSession;
	//MyBatis를 이용한 DB작업은 SqlSession 객체가 담당함
	
	//회원 등록하기
	public int insertUser(UserDTO dto) {
		return sqlSession.insert(Mapper+".insertUser", dto);
	}

	//회원 목록 가져오기
	public List<UserDTO> getUserList() {
		return sqlSession.selectList(Mapper+".getUserList");
	}

	//회원 목록 수정하기
	public int updateUser(UserDTO dto) {
		return sqlSession.update(Mapper+".updateUser", dto);
	}

	public UserDTO getUser(String id) {
		return sqlSession.selectOne(Mapper+".getUser", id);
	}

	public UserDTO login(UserDTO dto) {
		return sqlSession.selectOne(Mapper+".login", dto);
	}

	public UserDTO getUserIdx(int uIdx) {
		return sqlSession.selectOne(Mapper+".getUserIdx", uIdx);
	}

	//회원정보 중 user_status 값을 Y로 변경함
	public int deleteUser(int uIdx) {
		return sqlSession.update(Mapper+".deleteUser", uIdx);
	}

	
	
}
