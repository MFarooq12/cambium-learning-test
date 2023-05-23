package com.cambium;

import com.cambium.dtos.UserDTO;
import com.cambium.dtos.UserRequest;
import com.cambium.exceptions.NotFoundException;
import com.cambium.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Log4j2
class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userService;

	private ObjectMapper mapper = new ObjectMapper();

	private static List<UserDTO> userDTOList = new ArrayList<>();

	private static UserRequest userRequest;

	private static UserDTO userDTO;

	private static UserDTO userDTOCreatedResponse;

	@BeforeAll
	public static void setUpMockData() throws Exception {
		log.info("setting up mock data");
		userDTOCreatedResponse = UserDTO.builder().id(1l).firstName("John").lastName("Jerry").build();
		userRequest = UserRequest.builder().firstName("John").lastName("Jerry").build();
		userDTOList.add(UserDTO.builder().firstName("John").lastName("Wick").build());
		userDTOList.add(UserDTO.builder().firstName("Ali").lastName("Raza").build());
		userDTO = UserDTO.builder().firstName("John").lastName("Jerry").build();
	}

	@Test
	public void given_inValidCredentials_unauthorized_response() throws Exception {
		MvcResult result = mvc.perform(get("/api/v1/user/all").with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "pwd")).contentType(APPLICATION_JSON))
				.andReturn();
		Assertions.assertEquals(MockHttpServletResponse.SC_UNAUTHORIZED, result.getResponse().getStatus());
	}

	@Test
	public void given_validCredentials_authorized_response() throws Exception {
		MvcResult result = mvc.perform(get("/api/v1/user/all").with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")).contentType(APPLICATION_JSON))
				.andReturn();
		Assertions.assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
	}

	@Test
	public void when_callAllUsersApi_shouldReturnAllUsers() throws Exception {
		given(userService.getAllUsers()).willReturn(userDTOList);
		MvcResult result = mvc.perform(get("/api/v1/user/all").with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")).contentType(APPLICATION_JSON))
				.andReturn();
		List<UserDTO> userDTOListResponse = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserDTO>>() {
		});
		Assertions.assertEquals(userDTOList.size(), userDTOListResponse.size());
		Assertions.assertArrayEquals(userDTOList.toArray(), userDTOListResponse.toArray());
	}

	@Test
	public void when_callGetUserByIdApi_shouldReturnRequiredUser() throws Exception {
		given(userService.getUserById(1L)).willReturn(userDTO);
		MvcResult result = mvc.perform(get("/api/v1/user/1").with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")).contentType(APPLICATION_JSON))
				.andReturn();
		UserDTO userDTOResponse = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<UserDTO>() {
		});
		Assertions.assertEquals(userDTO, userDTOResponse);
	}

	@Test
	public void when_callGetUserByInvalidIdApi_shouldNotReturnUser() throws Exception {
		String recordNotFound = "User with id 2 does not exist.";
		given(userService.getUserById(2L)).willThrow(new NotFoundException(recordNotFound));
		MvcResult result = mvc.perform(get("/api/v1/user/2").with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")).contentType(APPLICATION_JSON))
				.andReturn();
		Assertions.assertEquals(MockHttpServletResponse.SC_NOT_FOUND, result.getResponse().getStatus());
		Assertions.assertEquals(recordNotFound, result.getResolvedException().getMessage());
	}

	@Test
	public void when_callSaveUserApi_shouldReturnUser() throws Exception {
		given(userService.save(userRequest)).willReturn(userDTOCreatedResponse);
		MvcResult result = mvc.perform(
				post("/api/v1/user").content(new ObjectMapper().writeValueAsString(userRequest)).with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password"))
						.contentType(APPLICATION_JSON)).andReturn();
		Assertions.assertEquals(MockHttpServletResponse.SC_CREATED, result.getResponse().getStatus());
		UserDTO userDTOResponse = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<UserDTO>() {
		});
		Assertions.assertEquals(userDTOCreatedResponse, userDTOResponse);
	}

	@Test
	public void when_callDeleteUserApi_shouldReturnUser() throws Exception {
		String recordDelete = "User deleted Successfully with id.";
		doNothing().when(userService).deleteUserById(1l);
		MvcResult result = mvc.perform(delete("/api/v1/user/1").with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")).contentType(APPLICATION_JSON))
				.andReturn();

		Assertions.assertEquals(recordDelete, result.getResponse().getContentAsString());
	}
}
