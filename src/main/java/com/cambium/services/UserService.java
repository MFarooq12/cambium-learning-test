package com.cambium.services;

import com.cambium.dtos.UserDTO;

import java.util.List;

import com.cambium.dtos.UserRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

	UserDTO save(UserRequest userRequest);

	UserDTO getUserById(Long id);

	List<UserDTO> getAllUsers();

	void deleteUserById(Long id);
}
