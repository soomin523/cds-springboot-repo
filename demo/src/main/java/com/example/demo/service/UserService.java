package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDTO;

public interface UserService {

	int insertUser(UserDTO dto);

	List<UserDTO> getUserList();

	int updateUser(UserDTO dto);

	UserDTO getUser(String id);

	UserDTO login(UserDTO dto);

	UserDTO getUserIdx(int uIdx);

	int deleteUser(int uIdx);

}
