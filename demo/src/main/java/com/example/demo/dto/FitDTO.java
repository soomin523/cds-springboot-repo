package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FitDTO {

	@JsonProperty("uIdx")
	private int uIdx;			//회원번호(시퀀스를 이용)
	private String name;
	private String id;
	private String pw;
	private String tel;
	private String birthday;
	private String status;
	
}
