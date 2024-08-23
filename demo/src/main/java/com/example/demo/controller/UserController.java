package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;


//REST API를 처리할 수 있는 컨트롤러로 명시함
@RestController
@RequiredArgsConstructor
public class UserController {
		
	//요청에 대해서 실제적으로 처리하는 클래스 : UserServiceImpl 클래스
	//상위의 인터페이스를 이용해서 필드로 정의함
	private final UserService userService;
	//스프링 컨테이너로부터 의존 자동주입을 받기 위해서 클래스 위에 @RequiredArgsConstructor 어노테이션을 추가함

	//회원정보 입력하기 : POST
	@PostMapping("/user/insertUser")
	public Map<String, String> insertUser(@RequestBody UserDTO dto) {
		//@RequestBody : HTTP 요청 객체의 Body에 저장된 JSON 객체를 UserDTO 타입으로 변환시켜서 저장해줌
		//이전의 반환타입은 String 이었음
		
		//프론트엔드로 전송되는 데이터의 형식이 JSON 형식으로 전달이 되어야 하기 때문에 Map 자료구조를 이용함
			//Map은 데이터를 저장할 때 키와 값의 쌍으로 저장하는 특징을 가짐
		//Map 자료구조를 객체로 만들어서 사용할 수 있도록 지원하는 클래스: HashMap 
		Map<String, String> result = new HashMap<String, String>();
		
		if(userService.insertUser(dto) == 1) {
			result.put("result", "OK"); //JSON : {"result":"OK"}
		}else {
			result.put("result", "FAIL"); //JSON : {"result":"FAIL"}
		}
		
		
		return result;
	}
	 
	//회원 목록 가져오기 : GET
	@GetMapping("/user/getUserList")
	public List<UserDTO> getUserList() {
		List<UserDTO> userList = null;
		
		userList = userService.getUserList();
		if(userList != null) {
			System.out.println("회원목록이 정상적으로 조회되었습니다.");
		}else {
			System.out.println("회원목록으로 조회된 내용이 없습니다.");
		}
		
		return userList;
	}
	
	//회원번호(uIdx)로 회원정보 가져오기
	@GetMapping("/user/{uIdx}/getUserIdx")
	public UserDTO getUserIdx(@PathVariable("uIdx") String uIdx) {
		UserDTO dto = userService.getUserIdx(Integer.parseInt(uIdx));
		
		if(dto != null) {
			System.out.println("회원정보 조회 성공");
		}else {
			System.out.println("회원정보 조회 실패");
		}
		return dto;
	}
	
	//회원 정보 수정하기 : PUT
	@PutMapping("/user/updateUser")
	public Map<String, String> updateUser(@RequestBody UserDTO dto) {
		Map<String, String> result = new HashMap<String, String>();
		
		if(userService.updateUser(dto) == 1) {
			result.put("result", "OK");
		} else {
			result.put("result", "FAIL");
		}
		
		return result;
	}
	
	//회원정보 탈퇴 처리하기 : PUT
	@DeleteMapping("/user/{uIdx}/deleteUser")
	public Map<String, String> deleteUser(@PathVariable("uIdx") String uIdx) {
		Map<String, String> result = new HashMap<String, String>();
		
		if(userService.deleteUser(Integer.parseInt(uIdx)) == 1) {
			result.put("result", "OK"); //회원 탈퇴 요청 처리 성공
		} else {
			result.put("result", "FAIL"); //실패
		}
		
		return result;
	}
	
	//회원 1개 정보 가져오기 : GET
	@GetMapping("/user/{id}/getuser")
	public UserDTO getUser(@RequestBody String id2) {
		UserDTO dto = null;
		
		String id = id2 + "@naver.com";
		dto = userService.getUser(id);
		
		if(dto != null) {
			System.out.println("회원이 정상적으로 조회되었습니다.");
		}else {
			System.out.println("없는 아이디입니다.");
		}
		
		return dto;
	}
	
	//로그인 : POST
		@PostMapping("/user/login")
		public Map<String, String> login(@RequestBody UserDTO dto) {
			//@RequestBody : HTTP 요청 객체의 Body에 저장된 JSON 객체를 UserDTO 타입으로 변환시켜서 저장해줌
			//이전의 반환타입은 String 이었음
			
			//프론트엔드로 전송되는 데이터의 형식이 JSON 형식으로 전달이 되어야 하기 때문에 Map 자료구조를 이용함
				//Map은 데이터를 저장할 때 키와 값의 쌍으로 저장하는 특징을 가짐
			//Map 자료구조를 객체로 만들어서 사용할 수 있도록 지원하는 클래스: HashMap 
			Map<String, String> result = new HashMap<String, String>();
			
			UserDTO resDTO = userService.login(dto);
			if( resDTO != null) {
				result.put("result", "OK"); //JSON : {"result":"OK"}
				result.put("uIdx", String.valueOf(resDTO.getUIdx())); //문자열로 바꿔주는 함수(String.valueOf())
				result.put("userId", resDTO.getUserId());
				
			}else {
				result.put("result", "FAIL"); //JSON : {"result":"FAIL"}
			}
			
			return result;
		}
	
		
}
