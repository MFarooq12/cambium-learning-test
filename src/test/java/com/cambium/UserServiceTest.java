package com.cambium;

import com.cambium.dtos.UserDTO;
import com.cambium.dtos.UserRequest;
import com.cambium.entities.User;
import com.cambium.repositories.UserRepository;
import com.cambium.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    private UserRequest userRequest;
    private User user;
    private List<UserDTO> userDTOList;

    @BeforeEach
    void settUp() {
        userService = new UserServiceImpl(userRepository);
        userRequest = new UserRequest("Azhar", "Ali");
        user = new User(1L, "Azhar", "Ali");

        userDTOList = new ArrayList<>();
        userDTOList.add(UserDTO.builder().firstName("Azhar").lastName("Ali").build());
        userDTOList.add(UserDTO.builder().firstName("Bilal").lastName("Maqsood").build());
    }

    @Test
    void when_callSaveUser_shouldReturnUser() {
        given(userRepository.save(Mockito.any(User.class))).willReturn(user);
        given(userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(user.getFirstName(), user.getLastName())).willReturn(Optional.empty());

        UserDTO savedUser = userService.save(userRequest);

        Assertions.assertEquals(user.getFirstName(), savedUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), savedUser.getLastName());
    }

    @Test
    void when_callSaveDuplicateUser_shouldReturnException() {
        given(userRepository.save(Mockito.any(User.class))).willReturn(user);
        given(userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(user.getFirstName(), user.getLastName())).willReturn(Optional.ofNullable(user));
        Assertions.assertThrows(Exception.class, ()-> userService.save(userRequest));
    }

    @Test
    public void when_callGetUserById_shouldReturnUser() {
        Long id = 2L;
        given(userRepository.findById(id)).willReturn(Optional.ofNullable(user));
        UserDTO fetchedUser = userService.getUserById(id);

        Assertions.assertEquals(user.getId(), fetchedUser.getId());
    }

    @Test
    public void when_callDeleteUserByID_shouldDeleteTheRecord() {
        Long id = 2L;
        given(userRepository.findById(id)).willReturn(Optional.ofNullable(user));
        userService.deleteUserById(2L);
    }
}
