package com.cambium.controllers;

import com.cambium.dtos.UserDTO;
import com.cambium.dtos.UserRequest;
import com.cambium.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = { "User Management" })
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	@ApiOperation(value = "Save a user")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO saveUser(@RequestBody @Valid UserRequest userRequest) {
		return userService.save(userRequest);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Get a user by id")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@GetMapping("/all")
	@ApiOperation(value = "Get all users")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> getAll() {
		return userService.getAllUsers();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value = "Delete a user by id")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
		userService.deleteUserById(id);
		return ResponseEntity.ok(String.format("User deleted Successfully with id.", id));
	}

}
