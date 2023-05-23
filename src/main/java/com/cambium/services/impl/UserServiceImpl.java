package com.cambium.services.impl;

import com.cambium.dtos.UserDTO;
import com.cambium.dtos.UserRequest;
import com.cambium.entities.User;
import com.cambium.exceptions.DuplicateException;
import com.cambium.exceptions.NotFoundException;
import com.cambium.repositories.UserRepository;
import com.cambium.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO save(UserRequest userRequest) {
        userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(userRequest.getFirstName(), userRequest.getLastName()).ifPresent(user -> {
            throw new DuplicateException(String.format("User with First Name %s and Last Name %s already exist.", user.getFirstName(), user.getLastName()));
        });

        User user = UserDTO.userRequestToEntity(userRequest);
        log.info("Going to save the User {}", userRequest);
        user = userRepository.save(user);
        log.info("Successfully Saved the User {}", user);
        return UserDTO.toDto(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
        return UserDTO.toDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Order.asc("lastName").ignoreCase()))
                .stream().map(UserDTO::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
        userRepository.delete(user);
    }
}
